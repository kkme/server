package com.hifun.soul.manageserver.msg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.rpc.msg.RpcErrorMessage;
import com.hifun.soul.core.rpc.msg.RpcRequestMessage;
import com.hifun.soul.core.rpc.msg.RpcResponseMessage;
import com.hifun.soul.core.rpc.server.RpcServiceRouter;
import com.hifun.soul.core.session.MinaSession;

/**
 * RPC请求消息处理器;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class RpcRequestMessageHandler implements
		IMessageHandlerWithType<RpcRequestMessage> {
	private Logger logger = LoggerFactory
			.getLogger(RpcRequestMessageHandler.class);
	/** 服务路由器 */
	@Autowired
	private RpcServiceRouter serviceRouter;

	public RpcRequestMessageHandler() {
		//serviceRouter.registerService(new UserService());
	}

	/**
	 * 发送返回值
	 * 
	 * @param returnValue
	 * @param session
	 */
	private void sendReturnReturnValue(Message returnValue, MinaSession session) {
		RpcResponseMessage responseMessage = new RpcResponseMessage();
		responseMessage.setReturnValues(returnValue.toByteArray());
		session.write(responseMessage);
	}

	/**
	 * 处理错误
	 * 
	 * @param error
	 *            错误信息
	 * @param session
	 *            client-sever 会话信息
	 */
	private void handleError(String error, MinaSession session) {
		RpcErrorMessage errorMsg = new RpcErrorMessage();
		errorMsg.setErrorInfo(error);
		session.write(errorMsg);
	}

	/**
	 * 获取服务路由器
	 * 
	 * @return
	 */
	public RpcServiceRouter getServiceRouter() {
		return serviceRouter;
	}

	@Override
	public short getMessageType() {
		return MessageType.RPC_REQUEST;
	}

	@Override
	public void execute(final RpcRequestMessage requestMsg) {
		try {
			serviceRouter.findServiceAndExecuteMethod(
					requestMsg.getServiceIndentifier(),
					requestMsg.getMethodIndentifier(), requestMsg.getParams(),
					new RpcCallback<Message>() {
						// 传入一个RpcCallback对象,可以使Handler和RPC服务路由器解耦合
						@Override
						public void run(Message parameter) {
							// 获取返回值然后发送
							sendReturnReturnValue(parameter,
									requestMsg.getSession());
						}
					});
		} catch (Exception e) {
			logger.error("RpcServiceRouter invoke error", e);
			handleError(e.getMessage(), requestMsg.getSession());
		}
	}

}
