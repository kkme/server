package com.hifun.soul.core.server;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.IMessage;

/**
 * 
 * 消息管理器
 * 
 * @author magicstone
 * 
 */

public class MessageManager {
	private Map<Short, IMessage> messageMap = new HashMap<Short, IMessage>();

	public void init() {
		Map<String, IMessage> msgs = ApplicationContext.getApplicationContext()
				.getBeansOfType(IMessage.class);
		for (IMessage each : msgs.values()) {
			messageMap.put(each.getType(), each);
		}
	}

	public IMessage getMessage(short msgType) {
		return messageMap.get(msgType);
	}

}
