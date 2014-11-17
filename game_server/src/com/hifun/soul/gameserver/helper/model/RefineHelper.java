package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.refine.manager.HumanRefineManager;

public class RefineHelper implements IHelper {
	private HumanRefineManager manager;
	
	public RefineHelper(HumanRefineManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.REFINE.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.REFINE, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		if(manager.isFinishCanAttactStages()){
			return HelpStateType.CLOSED.getIndex();
		}else{
			return HelpStateType.CAN_START.getIndex();
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
