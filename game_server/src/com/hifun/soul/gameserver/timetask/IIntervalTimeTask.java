package com.hifun.soul.gameserver.timetask;


/**
 * 
 * 固定时间间隔类任务
 * 
 * @author magicstone
 *
 */
public interface IIntervalTimeTask extends IHeartBeatTask {

	/**
	 * 时间间隔
	 * 
	 * @return
	 */
	public long getTimeSpan();
	
	/**
	 * 获取上次执行时间
	 * 
	 * @return
	 */
	public long getLastRunTime();
	
	/**
	 * 设置上次执行时间
	 * 
	 * @param time
	 */
	public void setLastRunTime(long time);
}
