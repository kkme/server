package com.hifun.soul.aspect.qq;

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
