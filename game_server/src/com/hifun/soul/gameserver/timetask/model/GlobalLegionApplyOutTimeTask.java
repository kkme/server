package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

/**
 * 军团申请超时任务
 * 
 * @author yandajun
 * 
 */
@Component
public class GlobalLegionApplyOutTimeTask implements IHeartBeatTask {
	@Autowired
	private GlobalLegionManager globalLegionManager;

	@Override
	public void run() {
		globalLegionManager.refreshLegionApplyList();
	}

	@Override
	public void stop() {

	}

	@Override
	public boolean isTimeUp(long now) {
		return true;
	}

	@Override
	public void nextRound() {

	}

	@Override
	public long getRunTime() {
		return 0;
	}

}
