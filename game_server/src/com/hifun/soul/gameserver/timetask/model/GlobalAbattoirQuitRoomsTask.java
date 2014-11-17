package com.hifun.soul.gameserver.timetask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.abattoir.manager.GlobalAbattoirManager;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

/**
 * 角斗场到点退出房间
 * 
 * @author yandajun
 * 
 */
@Component
public class GlobalAbattoirQuitRoomsTask implements IHeartBeatTask {
	@Autowired
	private GlobalAbattoirManager manager;

	@Override
	public void run() {
		manager.systemQuitRooms();

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
