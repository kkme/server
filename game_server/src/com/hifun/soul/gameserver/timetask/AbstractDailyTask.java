package com.hifun.soul.gameserver.timetask;

import java.util.Arrays;
import java.util.List;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.timetask.service.TimeTaskTemplateManager;


public abstract class AbstractDailyTask implements IDailyTimeTask {
	private boolean stop = false;
	protected long[] runTimes ;
	private long runTime;
	private TimeTaskTemplateManager timeTaskTemplateService = 
			ApplicationContext.getApplicationContext().getBean(TimeTaskTemplateManager.class);

	public AbstractDailyTask() {
		runTimes = getRunTimes(getTimeTaskType());
		if (runTimes != null) {
			Arrays.sort(runTimes);
		}
	}
	
	public boolean isStop() {
		return stop;
	}
	
	@Override
	public void stop() {
		stop = true;
	}

	@Override
	public boolean isTimeUp(long now) {
		if(runTimes == null){
			runTimes = getRunTimes(getTimeTaskType());
			if (runTimes != null) {
				Arrays.sort(runTimes);
			}
		}
		if(runTimes.length == 0){
			return false;
		}
		int size = runTimes.length;		
		long todayBeginMillSeconds = TimeUtils.getTodayBegin(GameServerAssist.getSystemTimeService());
		long lastRunTime = getLastRunTime()-todayBeginMillSeconds;
		long nowFromToday = now-todayBeginMillSeconds;
		// 当天的最后一个定时点任务已经执行过则返回false
		if(runTimes[size-1] < lastRunTime){
			return false;
		}
		
		// 判断是否正好经过第一个时间点
		if(lastRunTime <= runTimes[0]
				&& nowFromToday > runTimes[0]){
			runTime = runTimes[0]+todayBeginMillSeconds+1;
			return true;
		}
		
		// 判断当前时间是否正好经过一个执行时间点
		for(int i=0; i<size-1; i++){
			if(lastRunTime > runTimes[i]
					&& lastRunTime <= runTimes[i+1]
					&& nowFromToday > runTimes[i+1]){
				runTime = runTimes[i+1]+todayBeginMillSeconds+1;
				return true;
			}
		}
		
		// 判断上次执行时间是否小于昨天最后一个时间点
		if(lastRunTime <= (runTimes[size-1] - TimeUtils.DAY)){
			if(needRunMissing()){
				runTime = runTimes[size-1] - TimeUtils.DAY + todayBeginMillSeconds + 1;
				return true;
			}
			else
				return false;
		}
		
		return false;
	}

	@Override
	public void nextRound() {
		setLastRunTime(getRunTime());
	}
	
	/**
	 * 根据定时任务的类型找到对应的执行时间点
	 * 
	 * @param timeTaskType
	 * @return
	 */
	private long[] getRunTimes(int timeTaskType) {
		List<Long> times = timeTaskTemplateService.getTaskTimes(timeTaskType);
		if(times == null
				|| times.size() == 0){
			return null;
		}
		
		long[] timePoints = new long[times.size()];		
		for(int i=0; i<times.size(); i++){
			timePoints[i] = times.get(i);
		}
		
		return timePoints;
	}
	
	@Override
	public long getRunTime(){
		return runTime;
	}
	
	/**
	 * 设置运行时间
	 * 重写#{@link #isTimeUp(long)}时按需要设置该值
	 * 注：有固定时间间隔的定时任务谨慎调用此方法
	 * @param runTime
	 */
	protected void setRunTime(long runTime){
		this.runTime = runTime;
	}

}
