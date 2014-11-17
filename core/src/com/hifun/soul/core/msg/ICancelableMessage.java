package com.hifun.soul.core.msg;

/**
 * 可以取消的消息;
 * 
 * @author crazyjohn
 * 
 */
public interface ICancelableMessage {

	/**
	 * 取消调度;
	 */
	public void cancel();

	/**
	 * 调度是否已经取消了;
	 * 
	 * @return true 表示已经取消了;
	 */
	public boolean isCanceled();
}
