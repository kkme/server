package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.warrior.manager.HumanWarriorManager;

public class WarriorHelper implements IHelper {
	private HumanWarriorManager manager;

	public WarriorHelper(HumanWarriorManager warriorManager) {
		manager = warriorManager;
	}

	@Override
	public int getHelpType() {
		return HelpType.WARRIOR.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.REFINE, false)) {
			return HelpStateType.CLOSED.getIndex();
		}
		if (manager.isFinishedAllChanllegeQuest()) {
			return HelpStateType.CLOSED.getIndex();
		} else if (!manager
				.getOwner()
				.getHumanCdManager()
				.canAddCd(CdType.WARRIOR_QUEST_CD,
						GameServerAssist.getGameConstants().getWarriorQuestCdTime() * TimeUtils.SECOND)) {
			return HelpStateType.RUNING.getIndex();
		} else {
			return HelpStateType.CAN_START.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return manager.getChanllengeSuccessTimes();
	}

	@Override
	public int getTotalTimes() {		
		return GameServerAssist.getWarriorTemplateManager().getWarriorChanllengeCount();
	}

}
