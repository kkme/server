package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.MeditationState;

public class MuseHelper implements IHelper {
	private HumanMeditationManager manager;
	
	public MuseHelper(HumanMeditationManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.MUSE.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.TECHNOLOGY, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 如果当前状态是FINISH返回可以领取
		else if(manager.getMeditatiomState() == MeditationState.FINISH){
			return HelpStateType.CAN_GET.getIndex();
		}
		// 如果当前状态是INPROGRESS并且cd小于等于0返回可以领取
		else if(manager.getMeditatiomState() == MeditationState.INPROGRESS
				&& manager.getOwner().getHumanCdManager().getRemainCd(CdType.MEDITATION) <= 0){
			return HelpStateType.CAN_GET.getIndex();
		}
		// 如果当前状态是INPROGRESS并且cd大于0返回进行中
		else if(manager.getMeditatiomState() == MeditationState.INPROGRESS
				&& manager.getOwner().getHumanCdManager().getRemainCd(CdType.MEDITATION) > 0){
			return HelpStateType.RUNING.getIndex();
		}
		// 如果当前状态是STOP返回可以冥想
		else if(manager.getMeditatiomState() == MeditationState.STOP){
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
