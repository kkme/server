package com.hifun.soul.gameserver.timetask;

public interface IHeartBeatTask {

	/**
	 * task的具体执行方法
	 * 
	 */
	public void run();
	
	/**
	 * 停止task的执行
	 * 
	 */
	public void stop();
	
	/**
	 * 是否到了任务执行时间
	 * 
	 * @return
	 */
	public boolean isTimeUp(long now);
	
	/**
	 * 进入下一轮
	 * 
	 */
	public void nextRound();
	
	/**
	 * 最近一次执行时间
	 * isTimeUp为true的时候设置
	 * 
	 * @return
	 */
	public long getRunTime();
}
