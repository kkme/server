package com.hifun.soul.gameserver.event;

/**
 * 事件监听器接口;
 * 
 * @author crazyjohn
 */
public interface IEventListener {
	/**
	 * 处理事件
	 * 
	 * @param event
	 *            事件
	 */
	public void onEvent(IEvent event);
}
