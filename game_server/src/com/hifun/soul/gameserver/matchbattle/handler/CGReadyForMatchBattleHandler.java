package com.hifun.soul.gameserver.matchbattle.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.msg.CGReadyForMatchBattle;

@Component
public class CGReadyForMatchBattleHandler implements IMessageHandlerWithType<CGReadyForMatchBattle> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_READY_FOR_MATCH_BATTLE;
	}

	@Override
	public void execute(CGReadyForMatchBattle message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		//cd判断
		HumanCdManager cdManager = human.getHumanCdManager();		
		if(cdManager.canAddCd(CdType.MATCH_BATTLE_CD, 1)){
			human.getHumanMatchBattleManager().readyForMatch();
		}			
	}
}
