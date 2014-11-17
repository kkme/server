package com.hifun.soul.gameserver.timetask.model;

import java.util.Calendar;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.recharge.manager.HumanRechargeManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 重置每周累计充值数据
 * 
 * @author yandajun
 * 
 */
public class ResetTotalRechargeWeeklyTask extends AbstractDailyTask {

	private HumanRechargeManager manager;

	public ResetTotalRechargeWeeklyTask(HumanRechargeManager manager) {
		this.manager = manager;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_WEEK_TOTAL_RECHARGE.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastTotalRechargeWeeklyResetTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastTotalRechargeWeeklyResetTime(time);

	}

	@Override
	public boolean needRunMissing() {
		return true;
	}

	@Override
	public boolean isTimeUp(long now) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now);
		if (this.getLastRunTime() > 0
				&& (this.getLastRunTime() + 7 * TimeUtils.DAY) < now) {
			setRunTime(now);
			return true;
		} else {
			if (this.getLastRunTime() > 0) {
				Calendar current = Calendar.getInstance();
				current.setTimeInMillis(now);
				int currentWeek = current.get(Calendar.WEEK_OF_YEAR);
				Calendar calLastRun = Calendar.getInstance();
				calLastRun.setTimeInMillis(this.getLastRunTime());
				int lastResetWeek = calLastRun.get(Calendar.WEEK_OF_YEAR);
				if (currentWeek == lastResetWeek) {
					int lastResetDayOfWeek = calLastRun
							.get(Calendar.DAY_OF_WEEK);
					if (lastResetDayOfWeek > 1) {
						return false;
					}
					int currentDayOfWeek = current.get(Calendar.DAY_OF_WEEK);
					if (currentDayOfWeek == 1) {
						return false;
					}
				} else if (currentWeek - lastResetWeek == 1) {
					int currentDayOfWeek = current.get(Calendar.DAY_OF_WEEK);
					if (currentDayOfWeek == 1) {
						return false;
					}
				}
				return super.isTimeUp(now);
			}
			return super.isTimeUp(now);
		}
	}

	@Override
	public void run() {
		if (isStop()) {
			return;
		}
		manager.resetTotalRechargeWeeklyData();

	}

}
