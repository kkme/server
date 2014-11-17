package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.arena.manager.HumanArenaManager;
import com.hifun.soul.gameserver.arena.template.ArenaConfigTemplate;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;

public class ArenaHelper implements IHelper {
	private HumanArenaManager manager;
	private ArenaConfigTemplate template;
	
	public ArenaHelper(HumanArenaManager manager) {
		this.manager = manager;
		template = GameServerAssist.getTemplateService().get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID,
				ArenaConfigTemplate.class);
	}
	
	@Override
	public int getHelpType() {
		return HelpType.ARENA.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getHuman(), GameFuncType.ARENA, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断是否是在cd中
		else if(manager.getHuman().getHumanCdManager().getRemainCd(CdType.ARENA_BATTLE) > 0){
			return HelpStateType.RUNING.getIndex();
		}
		// 判断是否还有战斗次数
		else if(getUsedTimes() < getTotalTimes()){
			return HelpStateType.CAN_START.getIndex();
		}
		else{
			return HelpStateType.CLOSED.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return getTotalTimes() - manager.getArenaBattleTime();
	}

	@Override
	public int getTotalTimes() {
		if(template != null){
			return template.getMaxBattleTimes();
		}
		return 0;
	}

}
