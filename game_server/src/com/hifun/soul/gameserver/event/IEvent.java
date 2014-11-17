package com.hifun.soul.gameserver.event;

/**
 * 事件接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IEvent {
	/**
	 * 获取事件类型
	 * 
	 * @return 类型常量
	 */
	public EventType getType();
}
