package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

/**
 * 每日固定时间自动恢复体力值
 * 
 * @author yandajun
 * 
 */
public class AutoRecoverEnergyDailyTask extends AbstractDailyTask {
	private Human human;

	public AutoRecoverEnergyDailyTask(Human human) {
		this.human = human;
	}

	@Override
	public int getTimeTaskType() {
		return TimeTaskType.AUTO_RECOVER_ENERGY_DAILY_TASK.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_DAY_RECOVER_ENERGY_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_DAY_RECOVER_ENERGY_TIME, time);
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
		// 每日体力恢复
		int dayRecoverNum = GameServerAssist.getGameConstants()
				.getEnergyDailyRecoverNum();
		// 距离上一次恢复的天数
		int days = (int) ((GameServerAssist.getSystemTimeService().now() - getLastRunTime()) / TimeUtils.DAY);
		int energy = human.getEnergy() + dayRecoverNum * days;
		int maxEnergy = GameServerAssist.getGameConstants().getMaxEnergy();
		if (energy > maxEnergy) {
			energy = maxEnergy;
		}
		human.setEnergy(energy, EnergyLogReason.ENERGY_DAILY_RECOVER, "");
	}

}
