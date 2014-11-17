package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.callback.FriendBattleFinishedCallback;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.battle.msg.CGStartBattleWithFriend;
import com.hifun.soul.gameserver.human.Human;

/**
 * 请求和角色开战;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGStartBattleWithFriendHandler implements
		IMessageHandlerWithType<CGStartBattleWithFriend> {
	@Autowired
	private IBattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_START_BATTLE_WITH_FRIEND;
	}

	@Override
	public void execute(final CGStartBattleWithFriend message) {
		Human human = message.getPlayer().getHuman();
		if(human.getHumanGuid() == message.getHumanGuid()){
			return;
		}
		battleManager.pvpBattleEnter(human,
				message.getHumanGuid(), new FriendBattleFinishedCallback(human));
	}

}
