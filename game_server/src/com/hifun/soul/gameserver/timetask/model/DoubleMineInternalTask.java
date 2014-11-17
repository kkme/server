package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.template.LegionMineConstantsTemplate;
import com.hifun.soul.gameserver.timetask.AbstractIntervalTask;

/**
 * 随机出加倍矿定时任务
 * 
 * @author yandajun
 * 
 */
@Component
public class DoubleMineInternalTask extends AbstractIntervalTask {
	@Autowired
	private GlobalLegionMineWarManager mineWarManager;

	@Override
	public long getTimeSpan() {
		long timeSpan;
		int doubleMineIndex = mineWarManager.getDoubleMineIndex();
		// 第一次刷加倍矿跟以后刷的间隔时间不一样
		LegionMineConstantsTemplate constantsTempalte = GameServerAssist
				.getLegionMineWarTemplateManager().getConstantsTemplate();

		int doubleDelyMins = constantsTempalte.getDoubleDelyTime();
		int doubleInternalMins = constantsTempalte.getDoubleInternalTime();
		if (doubleMineIndex == 0) {
			timeSpan = doubleDelyMins * TimeUtils.MIN;
		} else {
			timeSpan = doubleInternalMins * TimeUtils.MIN;
		}
		return timeSpan;
	}

	@Override
	public long getLastRunTime() {
		return mineWarManager.getLastDoubleMineTime();
	}

	@Override
	public void setLastRunTime(long time) {
		mineWarManager.setLastDoubleMineTime(time);
	}

	@Override
	public void run() {
		if (isStop()) {
			return;
		}
		mineWarManager.randomDoubleMine();
	}

}
