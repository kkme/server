package com.hifun.soul.core.msg;


/**
 * 需要扔到玩家的消息队列中处理的消息
 * 
 * <pre>
 * 此类消息使用于WorldServer发送给GameServer的，需要在玩家所属线程
 * 中进行处理的消息，没有实现此接口的消息会在GameServer的主线程中处理
 * </pre>
 * 
 * @see GameMessageProcessor
 */
public interface PlayerQueueMessage {
	/**
	 * 获得玩家角色的UUID
	 * 
	 * @return
	 */
	long getRoleUUID();
}
