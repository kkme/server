package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.timetask.AbstractDailyTask;
import com.hifun.soul.gameserver.timetask.TimeTaskType;

public class ResetMineDataDailyTask extends AbstractDailyTask {

	private Human _human;
	
	public ResetMineDataDailyTask(Human human){
		_human = human;
	}
	@Override
	public int getTimeTaskType() {		
		return TimeTaskType.RESET_MINE_DATA.getIndex();
	}

	@Override
	public long getLastRunTime() {
		return _human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LAST_MINE_DATA_RESET_TIME);
	}

	@Override
	public void setLastRunTime(long time) {
		_human.getPropertyManager().setLongPropertyValue(HumanLongProperty.LAST_MINE_DATA_RESET_TIME, time);

	}

	@Override
	public boolean needRunMissing() {
		return true;
	}
	
	//向客户端下发矿坑列表的标识位 
	//will@注：不能在矿场管理器的onLogin方法中下发，会导致矿场收获图标的闪现问题
	private int sendMineListFlag = 0;
	
	@Override
	public boolean isTimeUp(long now){
		if(sendMineListFlag<2){
			if(sendMineListFlag==1){
				_human.getHumanMineManager().sendMineFieldListMessage();
			}
			sendMineListFlag++;
		}
		return super.isTimeUp(now);		
	}

	@Override
	public void run() {
		if(isStop()){
			return;
		}
		_human.getHumanMineManager().resetDailyData();
		sendMineListFlag = 2;
	}

}
