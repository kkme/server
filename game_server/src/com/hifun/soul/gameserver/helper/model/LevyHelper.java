package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.levy.HumanLevyManager;

public class LevyHelper implements IHelper {
	private HumanLevyManager manager;
	public LevyHelper(HumanLevyManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.LEVY.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.MAIN_CITY, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断是否在cd中
		else if(manager.getOwner().getHumanCdManager().getRemainCd(CdType.LEVY) > 0){
			return HelpStateType.RUNING.getIndex();
		}
		// 判断是否还有剩余次数
		else if(manager.getRemainLevyNum() > 0){
			return HelpStateType.CAN_START.getIndex();
		}
		else{
			return HelpStateType.CLOSED.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return GameServerAssist.getGameConstants().getLevyTime() - manager.getRemainLevyNum();
	}

	@Override
	public int getTotalTimes() {
		return GameServerAssist.getGameConstants().getLevyTime();
	}

}
