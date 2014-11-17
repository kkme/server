package com.hifun.soul.gameserver.matchbattle.msg.internal;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.common.GameServerAssist;

public class DoBattleMatchMessage extends SceneScheduleMessage{

	@Override
	public void execute() {
		if(isCanceled()){
			return;
		}
		GameServerAssist.getMatchBattleService().beginMatch();
	}

}
