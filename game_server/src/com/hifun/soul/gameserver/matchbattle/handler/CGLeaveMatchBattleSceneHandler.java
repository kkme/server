package com.hifun.soul.gameserver.matchbattle.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.msg.CGLeaveMatchBattleScene;

@Component
public class CGLeaveMatchBattleSceneHandler implements IMessageHandlerWithType<CGLeaveMatchBattleScene> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_LEAVE_MATCH_BATTLE_SCENE;
	}

	@Override
	public void execute(CGLeaveMatchBattleScene message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		GameServerAssist.getMatchBattleService().leaveMatchBattleScene(human);
	}

}
