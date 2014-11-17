package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

/**
 * 战俘营到点释放奴隶(心跳任务)
 * 
 * @author yandajun
 * 
 */
@Component
public class GlobalPrisonReleaseSlavesTask implements IHeartBeatTask {
	@Autowired
	private GlobalPrisonManager manager;

	@Override
	public void run() {
		manager.releaseSlaves();
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
