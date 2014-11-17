package com.hifun.soul.gameserver.event;


/**
 * 事件总线接口;
 * 
 * @author crazyjohn
 * 
 */

public interface IEventBus {
	/**
	 * 追加监听;
	 * 
	 * @param type
	 *            事件类型;
	 * @param listener
	 *            事件监听器;
	 */
	public void addListener(EventType type, IEventListener listener);


	/**
	 * 移除监听
	 * 
	 * @param type
	 *            事件类型
	 * @param listener
	 *            事件处理器
	 */
	public void removeListener(EventType type, IEventListener listener);

	/**
	 * 投递事件
	 * 
	 * @param event
	 *            事件
	 */
	public void fireEvent(IEvent event);
}
