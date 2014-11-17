package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.turntable.manager.HumanTurntableManager;

public class LotteryHelper implements IHelper {
	private HumanTurntableManager manager;
	
	public LotteryHelper(HumanTurntableManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.LOTTERY.getIndex();
	}

	@Override
	public int getState() {
		if(manager.getTurntableTime() > 0){
			return HelpStateType.CAN_START.getIndex();
		}
		else{
			return HelpStateType.CLOSED.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return GameServerAssist.getGameConstants().getTurntableFreeTime() - manager.getTurntableTime();
	}

	@Override
	public int getTotalTimes() {
		return GameServerAssist.getGameConstants().getTurntableFreeTime();
	}

}
