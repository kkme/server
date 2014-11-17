package com.hifun.soul.gameserver.matchbattle.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.msg.CGUpdateAutoMatchBattleSetting;

@Component
public class CGUpdateAutoMatchBattleSettingHandler implements IMessageHandlerWithType<CGUpdateAutoMatchBattleSetting> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_UPDATE_AUTO_MATCH_BATTLE_SETTING;
	}

	@Override
	public void execute(CGUpdateAutoMatchBattleSetting message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		GameServerAssist.getMatchBattleService().updateAutoMatchSetting(human.getHumanGuid(),message.getIsAutoJoinBattle());
	}

}
