package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

/**
 * 押运结束任务
 * 
 * @author yandajun
 * 
 */
@Component
public class GlobalEscortEndTask implements IHeartBeatTask {
	@Autowired
	private GlobalEscortManager globalEscortManager;

	@Override
	public void run() {
		globalEscortManager.refreshEscortList();
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
