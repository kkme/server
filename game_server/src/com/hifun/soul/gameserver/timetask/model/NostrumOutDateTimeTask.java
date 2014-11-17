package com.hifun.soul.gameserver.timetask.model;


import com.hifun.soul.gameserver.nostrum.manager.HumanNostrumManager;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

/**
 * 秘药到期(心跳任务)
 * 
 * @author yandajun
 * 
 */
public class NostrumOutDateTimeTask implements IHeartBeatTask {
	private HumanNostrumManager manager;

	public NostrumOutDateTimeTask(HumanNostrumManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		manager.outOfDateNostrums();
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
