package com.hifun.soul.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

import com.hifun.soul.core.client.NIOClient;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.recognizer.BaseMessageRecognizer;
import com.hifun.soul.core.server.CommonMessageRecognizer;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.server.QueueMessageProcessor;
import com.hifun.soul.core.session.MinaSession;
import com.hifun.soul.core.util.Assert;
import com.hifun.soul.gameserver.player.msg.CGPlayerLogin;
import com.hifun.soul.robot.logger.Loggers;
import com.hifun.soul.robot.msg.RobotClientSessionClosedMsg;
import com.hifun.soul.robot.msg.RobotClientSessionOpenedMsg;
import com.hifun.soul.robot.startup.IRobotClientSession;
import com.hifun.soul.robot.startup.RobotClientIoHandler;
import com.hifun.soul.robot.startup.RobotClientMsgHandler;
import com.hifun.soul.robot.task.IRobotTask;

public class Robot {
	public static final Logger robotLogger = Loggers.ROBOT_LOGGER;
	
	private static final String PASSWORD = "1";
	
	private int id;
	
	private String passportId;
	
	private String password = Robot.PASSWORD;
	
	private String serverIp;
	
	private int serverPort;
	
	private List<IRobotTask> taskList;
	
	private NIOClient nioclient;
	
	/** 玩家与GameServer的会话 */
	private IRobotClientSession session;
	
	private boolean canHandleMsg = false;
	
	public Robot(String passportId,int id, String serverIp ,int serverPort)
	{
		this.passportId = passportId;
		this.id = id;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.taskList = new ArrayList<IRobotTask>();
	}
	
	/**
	 * 启动连接
	 */
	public void start()
	{
		nioclient = buildConnection();		
		nioclient.getMessageProcessor().start();
		nioclient.open();
	}
	
	/**
	 * 销毁连接
	 */
	public void destory()
	{
		if (this.nioclient != null) {
			this.nioclient.getMessageProcessor().stop();
			this.nioclient.close();
			this.nioclient = null;
		}
	}
	
	/**
	 * 是否连接
	 * @return
	 */
	public boolean isConnected()
	{
		if (this.session != null) {
			return this.session.isConnected();
		}
		return false;
	}
	
	/**
	 *
	 * 发送消息 
	 * @param msg
	 */
	public void sendMessage(IMessage msg) {
		Assert.notNull(msg);
		if (session != null) {
			session.write(msg);			
		}
	}
	
	/**
	 * 建立连接
	 * 
	 * @return
	 */
	public NIOClient buildConnection()
	{
		RobotClientMsgHandler _messageHandler = new RobotClientMsgHandler();
		IMessageProcessor _messageProcessor = new QueueMessageProcessor("NioMessageProcessor",_messageHandler);
		BaseMessageRecognizer _recognizer = new CommonMessageRecognizer();
		RobotClientIoHandler _ioHandler = new RobotClientIoHandler();
		ExecutorService _executorService = Executors.newSingleThreadExecutor();
		NIOClient _client = new NIOClient("Game Server",this.serverIp,this.serverPort,
										  _executorService,
										  _messageProcessor,
										  _recognizer, 
										  _ioHandler, 
										  this.new ConnectionCallback());
		return _client;
	}
	
	private class ConnectionCallback implements NIOClient.ConnectionCallback
	{		
		@Override
		public void onOpen(NIOClient client, IMessage msg) {
			RobotClientSessionOpenedMsg message = (RobotClientSessionOpenedMsg)msg;
			RobotManager.getInstance().addRobot(message.getSession().getIoSession(),Robot.this);
			Robot.this.setSession(message.getSession());
			message.getSession().setRobot(Robot.this);
			RobotManager.getInstance().addRobot((MinaSession)message.getSession(), Robot.this);

			// 连接服务器端成功之后, 登陆消息
			CGPlayerLogin cgPlayerLogin = new CGPlayerLogin();
			cgPlayerLogin.setAccount(getName());
			cgPlayerLogin.setPassword(getName());
			Robot.this.sendMessage(cgPlayerLogin);
		}

		@Override
		public void onClose(NIOClient client, IMessage msg) {
			RobotClientSessionClosedMsg message = (RobotClientSessionClosedMsg)msg;
			RobotManager.getInstance().removeRobot(message.getSession().getIoSession());
			Robot robot = RobotManager.getInstance().removeRobot((MinaSession)message.getSession());
			if(robot != null)
			{
				robot.setSession(null);
				// 销毁连接
				robot.destory();
			}
			message.getSession().setRobot(null);
		}		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public List<IRobotTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<IRobotTask> taskList) {
		this.taskList = taskList;
	}
	
	public IRobotTask getTask(int i) {
		return taskList.get(i);
	}
	
	public void addTask(IRobotTask robotTask) {
		this.taskList.add(robotTask);
	}

	public NIOClient getNioclient() {
		return nioclient;
	}

	public void setNioclient(NIOClient nioclient) {
		this.nioclient = nioclient;
	}

	public IRobotClientSession getSession() {
		return session;
	}

	public void setSession(IRobotClientSession session) {
		this.session = session;
	}
	
	public boolean isCanHandleMsg() {
		return canHandleMsg;
	}

	public void setCanHandleMsg(boolean canHandleMsg) {
		this.canHandleMsg = canHandleMsg;
	}

	public void startTasks() {
		for (int i = 0; i < this.taskList.size(); i++) {
			// 获取机器人执行任务
			IRobotTask task = this.getTask(i);
	
			if (task == null) {
				return;
			}

			// 创建机器人行为
			RobotAction action = new RobotAction(task);
			// 获取第一次执行的时间延迟
			int delay = task.getDelay();
			// 获取循环执行时的时间间隔
			int period = task.getPeriod();
			
			if (task.isRepeatable()) {
				// 循环执行机器人操作
				RobotManager.getInstance().scheduleWithFixedDelay(action, delay, period);
			} else/* if (!strategy.isLoopExecute()) */{
				// 仅执行一次机器人操作
				RobotManager.getInstance().scheduleOnce(action, delay);
			}
		}
	}
	
	public String getName(){
		return String.valueOf(getPassportId()+getId());
	}
	
}
