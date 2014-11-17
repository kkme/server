package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.arena.manager.HumanArenaManager;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetArenaBattleTimeTask extends AbstractDailyTask {

	private HumanArenaManager manager;
	
	public ResetArenaBattleTimeTask(HumanArenaManager manager){
		this.manager = manager;
	}
	
	@Override
	public int getTimeTaskType() {
		return TimeTaskType.RESET_ARENA_BATTLE_TIME.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return manager.getLastResetBattleTimeTime();
	}

	@Override
	public void setLastRunTime(long time) {
		manager.setLastResetBattleTimeTime(time);
	}

	@Override
	public boolean needRunMissing() {
		return true;
	}
	
	@Override
	public void run() {
		if(isStop()){
			return;
		}
		
		manager.resetBattleTime();
	}

}
