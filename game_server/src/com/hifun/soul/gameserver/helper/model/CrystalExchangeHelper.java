package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.crystalexchange.manager.HumanCrystalExchangeManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;

public class CrystalExchangeHelper implements IHelper {
	private HumanCrystalExchangeManager manager;
	
	public CrystalExchangeHelper(HumanCrystalExchangeManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.CRYSTALEXCHANGE.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getHuman(), GameFuncType.CRYSTAL_EXCHANGE, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断是否还有剩余次数
		else if(getUsedTimes() >= getTotalTimes()){
			return HelpStateType.CLOSED.getIndex();
		}
		else{
			return HelpStateType.CAN_START.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return manager.getUseTimes();
	}

	@Override
	public int getTotalTimes() {
		return GameServerAssist.getCrystalExchangeTemplateManager().getTotalCrystalExchangeTime(manager.getHuman().getVipLevel());
	}

}
