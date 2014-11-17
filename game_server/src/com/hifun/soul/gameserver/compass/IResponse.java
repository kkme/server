package com.hifun.soul.gameserver.compass;

public interface IResponse {
	/**
	 * 获取结果码;
	 * 
	 * @return
	 */
	public int getResultCode();

	/**
	 * 获取结果消息;
	 * 
	 * @return
	 */
	public String getMessage();
}
