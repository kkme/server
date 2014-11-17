package com.hifun.soul.core.msg.injection;

import com.hifun.soul.core.msg.IMessage;

/**
 * 消息处理器接口;<br>
 * 
 * <pre>
 * 1. 每个处理器只处理一种消息;
 * 2. 生命周期由spring去管理;
 * </pre>
 * 
 * @author crazyjohn
 * 
 * @param <MessageBean> 要处理的消息类型
 */
public interface IMessageHandlerWithType<MessageBean extends IMessage> {

	/**
	 * 获取处理的消息类型
	 * 
	 * @return 返回此处理器对应的消息类型
	 */
	public short getMessageType();

	/**
	 * 消息自处理方法;
	 * 
	 * @param message
	 */
	public void execute(MessageBean message);
}
