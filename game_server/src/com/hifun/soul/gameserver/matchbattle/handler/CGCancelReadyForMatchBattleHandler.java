package com.hifun.soul.gameserver.matchbattle.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.msg.CGCancelReadyForMatchBattle;

@Component
public class CGCancelReadyForMatchBattleHandler implements IMessageHandlerWithType<CGCancelReadyForMatchBattle> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_CANCEL_READY_FOR_MATCH_BATTLE;
	}

	@Override
	public void execute(CGCancelReadyForMatchBattle message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		human.getHumanMatchBattleManager().cancelReadyForMatch();
	}

}
