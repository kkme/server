package com.hifun.soul.payserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.hifun.soul.core.client.NIOClientConnection;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageParseException;
import com.hifun.soul.core.msg.recognizer.BaseMessageRecognizer;
import com.hifun.soul.core.server.IMessageHandler;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.core.server.TimerManager;
import com.hifun.soul.payserver.msg.PGSendItem;
import com.hifun.soul.payserver.msg.PSScheduleMessage;
import com.hifun.soul.payserver.net.GameIoHandler;
import com.qq.open.OpensnsException;
import com.qq.open.SnsSigCheck;

/**
 * 支付入口;
 * 
 * @author crazyjohn
 * 
 */
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 通信密钥
	private final static String secret = "c4c38ddf497bbfda016af3d87fc63eba&";
	// gameserver端口号
	private final static String PORT = "8008";
	// 订单超时时间, 两小时;
	private static final long PAY_TIME_OUT = 1000 * 60 * 60 * 2;
	// 调度相关
	private TimerManager timerManager = new TimerManager(
			"PayServerTimerManager");

	private Map<String, PayInfo> playerPayInfoMap = new ConcurrentHashMap<String, PayInfo>();
	// 跟游戏服的消息处理器; 无实际作用;
	private final QueueMessageProcessor messageProcessor = new QueueMessageProcessor(
			"PayServerProcessor", new IMessageHandler<IMessage>() {

				@Override
				public void execute(IMessage message) {
					message.execute();
				}

				@Override
				public short[] getTypes() {
					return new short[0];
				}

			});

	@Override
	public void init(ServletConfig config) throws ServletException {
		messageProcessor.start();
		timerManager.start(messageProcessor);
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method == null) {
			method = "defuaultMethod";
		}
		if ("register".equals(method)) {
			// 注册openid - serverip - token的映射;
			System.out.println(method);
			handleRegister(request, response);
		} else {
			// 下发道具请求;
			System.out.println(method);
			try {
				handleSendItem(request, response);
			} catch (Exception e) {
				throw new RuntimeException("SendItem error : " + e);
			}
		}
	}

	/**
	 * 发送道具;
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws OpensnsException
	 */
	private void handleSendItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			NoSuchAlgorithmException, InvalidKeyException, OpensnsException {
		// 响应结果;
		JSONObject result = new JSONObject();
		// 属性对象
		JSONObject props = new JSONObject();
		// 取出所有參數
		String sig = request.getParameter("sig");
		Enumeration<String> paramNames = request.getParameterNames();
		HashMap<String, String> params = new HashMap<String, String>();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			if ("sig".equals(name) || "method".equals(name)) {
				continue;
			}
			String value = request.getParameter(name);
			// 放到属性集里;
			props.put(name, value);
			params.put(name, value);
		}
		// 请求方式
		String uri = request.getRequestURI();
		String method = request.getMethod();

		// 获取输出流
		PrintWriter out = response.getWriter();
		// 取出openId和token找到对应的充值服务器
		String openid = request.getParameter("openid");
		String token = request.getParameter("token");

		if (openid == null || "".equals(openid)) {
			return;
		}
		if (token == null || "".equals(token)) {
			return;
		}

		// 验证支付信息是否合法
		PayInfo payInfo = this.playerPayInfoMap.remove(openid);
		if (payInfo == null) {
			// 发送失败结果码,无此订单
			sendResult(result, RetType.NO_THIS_TOKEN, out);
			return;
		}
		if (!payInfo.getToken().equals(token)) {
			// 发送失败结果码,无此订单
			sendResult(result, RetType.NO_THIS_TOKEN, out);
			return;
		}
		// 执行sig验证;
		boolean checkResult = SnsSigCheck.verifySig(method, uri, params,
				secret, sig);
		System.out.println("Sig check result: " + checkResult);
		if (!checkResult) {
			sendResult(result, RetType.PARAMS_WRONG, out);
			return;
		}
		// 取消本订单相关的超时调度
		payInfo.getSchedule().cancel();
		// 设置属性
		props.put("openkey", payInfo.getOpenKey());
		props.put("humanGUID", payInfo.getHumanGUID());
		// 跟指定服务器建立连接发货
		ExecutorService thread = Executors.newSingleThreadExecutor();
		NIOClientConnection connection = new NIOClientConnection("GameServer",
				payInfo.getServerIp(), payInfo.getPort(),
				new BaseMessageRecognizer() {
					@Override
					public IMessage createMessageImpl(short type)
							throws MessageParseException {
						return null;
					}
				}, new GameIoHandler(this, messageProcessor,
						payInfo.getServerIp()), thread);
		boolean isConnected = connection.open();
		try {
			if (isConnected) {
				PGSendItem sendItem = new PGSendItem();
				// 把各个属性转换成json字符串, 发送给游戏服;
				sendItem.setJsonProperties(props.toString());
				connection.sendMessage(sendItem);
				sendResult(result, RetType.OK, out);
			} else {
				System.out.println("Connect to gameserver failed: "
						+ payInfo.getServerIp());
				RetType type = RetType.NET_ERROR;
				type.setParam(payInfo.getServerIp());
				sendResult(result, type, out);
				return;
			}
		} finally {
			// 回收资源;
			thread.shutdown();
			if (connection.isConnect()) {
				connection.close();
			}
		}

	}

	private void sendResult(JSONObject result, RetType ret, PrintWriter out) {
		result.put("ret", ret.getIndex());
		result.put("msg", ret.getDesc() + ": " + ret.getParam());
		out.write(result.toString());
		out.flush();
	}

	/**
	 * 注册映射关系;
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 获取输出流
		PrintWriter out = response.getWriter();
		// 获取参数
		final String token = request.getParameter("token");
		String serverIp = getRemoteIp(request);
		final String openid = request.getParameter("openId");
		String port = request.getParameter("port");
		String sGUID = request.getParameter("humanGUID");
		String openKey = request.getParameter("openKey");
		if (sGUID == null || "".equals(sGUID)) {
			return;
		}
		long humanGUID = Long.parseLong(sGUID);
		// 校验参数的合法性
		if (token == null || token.isEmpty()) {
			return;
		}
		if (serverIp == null || serverIp.isEmpty()) {
			return;
		}
		if (openid == null || openid.isEmpty()) {
			return;
		}
		if (openKey == null || "".equals(openKey)) {
			return;
		}
		port = PORT;
		// 每个玩家再没有完成一笔充值之后继续发送充值，只会保留最后一次的token
		PSScheduleMessage schedule = new PSScheduleMessage() {
			@Override
			public void execute() {
				// 如果取消則直接返回;
				if (isCanceled()) {
					return;
				}
				// 移除未处理的超时订单;
				PayInfo aPay = playerPayInfoMap.get(openid);
				if (aPay == null) {
					return;
				}
				// 移除超时订单;
				if (aPay.getToken().equals(token)) {
					playerPayInfoMap.remove(openid);
				}
			}
		};
		// 添加token超時調度;
		timerManager.scheduleOnece(schedule, PAY_TIME_OUT);
		// 註冊信息
		this.playerPayInfoMap.put(openid, new PayInfo(token, serverIp, openid,
				Integer.valueOf(port), humanGUID, openKey, schedule));
		// 发送执行结果;
		JSONObject result = new JSONObject();
		result.put("ret", RetType.OK.getIndex());
		result.put("msg", RetType.OK.getDesc());
		out.write(result.toString());
		out.flush();
	}

	/**
	 * 获取到连接过来的ip地址
	 * 
	 * @param request
	 * @return
	 */
	private String getRemoteIp(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

}
