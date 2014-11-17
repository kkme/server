package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.loginreward.manager.HumanLoginRewardManager;

public class LoginRewardHelper implements IHelper {

	private HumanLoginRewardManager manager;
	
	public LoginRewardHelper(HumanLoginRewardManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.LOGINREWARD.getIndex();
	}

	@Override
	public int getState() {
		if(manager.hasLoginReward()){
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
