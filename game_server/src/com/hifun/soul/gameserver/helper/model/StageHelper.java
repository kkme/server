package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;

public class StageHelper implements IHelper {

	private HumanStageManager manager;
	
	public StageHelper(HumanStageManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.STAGE.getIndex();
	}

	@Override
	public int getState() {
		// 判断是否有体力值
		if(manager.getHuman().getEnergy() > 0){
			return HelpStateType.CAN_START.getIndex();
		}
		else{
			return HelpStateType.CLOSED.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return 0;
	}

	@Override
	public int getTotalTimes() {
		return 0;
	}

}
