package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.activity.ActivityState;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.boss.manager.HumanBossManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;

public class BossWarHelper implements IHelper {
	private HumanBossManager manager;
	
	public BossWarHelper(HumanBossManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.BOSSWAR.getIndex();
	}

	@Override
	public int getState() {
		HumanCdManager cdManager = manager.getHuman().getHumanCdManager();
		cdManager.removeCd(CdType.BOSS_WAR_START);
		int result = 0;
		ActivityState bossWarState = GameServerAssist.getGlobalActivityManager().getActivity(ActivityType.BOSS_WAR).getState();
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getHuman(), GameFuncType.BOSS_WAR, false) 
				|| bossWarState == ActivityState.FINISH
				|| bossWarState == ActivityState.LOCK
				|| bossWarState == ActivityState.UNVISABLE){
			result = HelpStateType.CLOSED.getIndex();
		}
		// boss战是否是在cd中
		else if(bossWarState == ActivityState.READY){
			long bossWarStartCdTime = GameServerAssist.getGlobalActivityManager().getNextOpenTimeDiff(ActivityType.BOSS_WAR);
			cdManager.addCd(CdType.BOSS_WAR_START, bossWarStartCdTime);			
			result = HelpStateType.RUNING.getIndex();
		}
		else if(bossWarState == ActivityState.OPEN){
			result = HelpStateType.CAN_START.getIndex();			
		}
		cdManager.snapCdQueueInfo(CdType.BOSS_WAR_START);
		return result;		
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
