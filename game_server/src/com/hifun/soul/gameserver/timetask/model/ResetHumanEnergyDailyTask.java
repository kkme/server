package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 重置每日体力恢复次数
 * 
 * @author yandajun
 * 
 */
public class ResetHumanEnergyDailyTask extends AbstractDailyTask {
	private Human human;

	public ResetHumanEnergyDailyTask(Human human) {
		this.human = human;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_HUMEN_ENERGY_DAILY_TASK.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_RECOVER_ENERGY_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_RECOVER_ENERGY_TIME, time);
	}

	@Override
	public boolean needRunMissing() {
		return false;
	}

	@Override
	public void run() {
		if (isStop()) {
			return;
		}
		// 累加每日体力恢复次数
		int dayTimes = GameServerAssist.getGameConstants()
				.getEnergyHandRecoverTimes();
		int days = (int) ((GameServerAssist.getSystemTimeService().now() - getLastRunTime()) / TimeUtils.DAY);
		int totalTimes = dayTimes * days;
		int maxTimes = GameServerAssist.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel())
				.getMaxEnergyRecoverTotalTimes();
		human.setTotalRecoverEnergyNum(totalTimes
				+ human.getTotalRecoverEnergyNum());
		if (human.getTotalRecoverEnergyNum() > maxTimes) {
			human.setTotalRecoverEnergyNum(maxTimes);
		}
		// 重置每日恢复体力次数
		human.setDayRecoverEnergyNum(0);

	}

}
