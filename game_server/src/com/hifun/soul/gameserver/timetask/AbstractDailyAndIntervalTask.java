package com.hifun.soul.gameserver.timetask;

/**
 * 固定时间点执行
 * 周期性大于一天
 * 
 * @author magicstone
 *
 */
public abstract class AbstractDailyAndIntervalTask extends AbstractDailyTask implements
		IIntervalTimeTask {
	private boolean stop = false;
	
	public boolean isStop() {
		return stop;
	}
	
	@Override
	public void stop() {
		stop = true;
	}
	
	private long getTimeUpTime() {
		return getLastRunTime() + getTimeSpan(); 
	}

	@Override
	public boolean isTimeUp(long now) {
		return (now>getTimeUpTime() && super.isTimeUp(now));
	}
}
