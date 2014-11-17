package com.hifun.soul.gameserver.matchbattle.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.msg.CGJoinMatchBattleScene;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGJoinMatchBattleSceneHandler implements
		IMessageHandlerWithType<CGJoinMatchBattleScene> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_JOIN_MATCH_BATTLE_SCENE;
	}

	@Override
	public void execute(CGJoinMatchBattleScene message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		human.getHumanMatchBattleManager().onOpenClick();
		
	}

}
