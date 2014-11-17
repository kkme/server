package com.hifun.soul.gameserver.helper;

public interface IHelper {

	/**
	 * 小助手类型
	 * 
	 * @return
	 */
	public int getHelpType();
	
	/**
	 * 小助手状态
	 * 
	 * @return
	 */
	public int getState();
	
	/**
	 * 获取使用次数
	 * 
	 * @return
	 */
	public int getUsedTimes();
	
	/**
	 * 获取总共次数
	 * 
	 * @return
	 */
	public int getTotalTimes();
	
}
