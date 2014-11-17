package com.hifun.soul.gameserver.timetask;

/**
 * 
 * 每日固定时间执行的任务
 * 
 * @author magicstone
 *
 */
public interface IDailyTimeTask extends IHeartBeatTask {

	/**
	 * 获取定时任务的类型
	 * 
	 * @return
	 */
	public int getTimeTaskType();
	
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
	
	/**
	 * 判断是否需要执行漏掉的时间点
	 * 
	 * @return
	 */
	public boolean needRunMissing();
}
