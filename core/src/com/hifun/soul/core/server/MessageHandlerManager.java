package com.hifun.soul.core.server;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;

/**
 * 
 * 消息handler的管理器
 * 
 * @author magicstone
 * 
 */

public class MessageHandlerManager {
	@SuppressWarnings("rawtypes")
	private Map<Short, IMessageHandlerWithType> handlerMap = new HashMap<Short, IMessageHandlerWithType>();

	@SuppressWarnings("rawtypes")
	public void init() {
		Map<String, IMessageHandlerWithType> handlers = ApplicationContext
				.getApplicationContext().getBeansOfType(
						IMessageHandlerWithType.class);
		for (IMessageHandlerWithType<IMessage> each : handlers.values()) {
			handlerMap.put(each.getMessageType(), each);
		}
	}

	/**
	 * 根据消息返回消息对应的handler
	 * 
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private IMessageHandlerWithType<IMessage> getHandler(IMessage message) {
		if (message == null) {
			return null;
		}

		return handlerMap.get(message.getType());
	}

	/**
	 * 处理消息;
	 * 
	 * @param message
	 */
	public void handleMessage(IMessage message) {
		IMessageHandlerWithType<IMessage> handler = getHandler(message);
		if (handler != null) {
			handler.execute(message);
		} else {
			// 如果没有处理器的话, 自处理;
			message.execute();
		}
	}

}
