package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.levy.HumanLevyManager;

public class ChooseHelper implements IHelper {
	private HumanLevyManager manager;
	public ChooseHelper(HumanLevyManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.CHOOSE.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.MAIN_CITY, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断是否有剩余次数
		else if(manager.getFreeCollectNum() <= 0){
			return HelpStateType.CLOSED.getIndex();
		}
		else{
			return HelpStateType.CAN_START.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return GameServerAssist.getGameConstants().getFreeCollectNum() - manager.getFreeCollectNum();
	}

	@Override
	public int getTotalTimes() {
		return GameServerAssist.getGameConstants().getFreeCollectNum();
	}

}
