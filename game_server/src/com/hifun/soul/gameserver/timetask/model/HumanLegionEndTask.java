package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

/**
 * 角色军团任务到时结束
 * 
 * @author yandajun
 * 
 */
public class HumanLegionEndTask implements IHeartBeatTask {

	private Human human;

	public HumanLegionEndTask(Human human) {
		this.human = human;
	}

	@Override
	public void run() {
		human.getHumanLegionManager().endTaskList();
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
