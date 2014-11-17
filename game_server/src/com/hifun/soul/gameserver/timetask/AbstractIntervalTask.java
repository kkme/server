package com.hifun.soul.gameserver.timetask;


public abstract class AbstractIntervalTask implements IIntervalTimeTask {

	private boolean stop = false;
	
	private long lastRunTime = 0l;
	
	private long runTime;
	
	public boolean isStop() {
		return stop;
	}

	@Override
	public void stop() {
		stop = true;
	}

	@Override
	public boolean isTimeUp(long now) {
		if(now > getTimeUpTime()){
			runTime = now;
			return true;
		}
		return false;
	}

	@Override
	public void nextRound() {
		setLastRunTime(getRunTime());
	}
	
	private long getTimeUpTime() {
		return getLastRunTime() + getTimeSpan(); 
	}

	@Override
	public long getLastRunTime() {
		return lastRunTime;
	}

	@Override
	public void setLastRunTime(long time) {
		this.lastRunTime = time;
	}
	
	@Override
	public long getRunTime() {
		return runTime;
	}
}
