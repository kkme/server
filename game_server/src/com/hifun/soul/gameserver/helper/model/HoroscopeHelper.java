package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;

public class HoroscopeHelper implements IHelper {
	private HumanHoroscopeManager manager;
	
	public HoroscopeHelper(HumanHoroscopeManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.HOROSCOPE.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getHuman(), GameFuncType.GAMBLE, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断是否有剩余次数
		else if(manager.getHoroscopeGambleTime() > 0){
			return HelpStateType.CAN_START.getIndex();
		}
		else{
			return HelpStateType.CLOSED.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return SharedConstants.HOROSCOPE_GAMBLE_TIME - manager.getHoroscopeGambleTime();
	}

	@Override
	public int getTotalTimes() {
		return SharedConstants.HOROSCOPE_GAMBLE_TIME;
	}

}
