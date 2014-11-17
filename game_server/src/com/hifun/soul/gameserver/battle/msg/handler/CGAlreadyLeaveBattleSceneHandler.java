package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.msg.CGAlreadyLeaveBattleScene;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGAlreadyLeaveBattleSceneHandler implements IMessageHandlerWithType<CGAlreadyLeaveBattleScene> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_ALREADY_LEAVE_BATTLE_SCENE;
	}

	@Override
	public void execute(CGAlreadyLeaveBattleScene message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		if(message.getClientSceneType() == ClientGameSceneType.MATCH_BATTLE_VIEW.getIndex()){
			human.getHumanMatchBattleManager().clientAlreadyLeaveBattleScene();
		}
	}

}
