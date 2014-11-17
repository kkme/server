package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.msg.CGBattleChat;
import com.hifun.soul.gameserver.battle.msg.GCBattleChat;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 战斗聊天消息处理器;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGBattleChatHandler implements
		IMessageHandlerWithType<CGBattleChat> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BATTLE_CHAT;
	}

	@Override
	public void execute(CGBattleChat message) {
		// 检查是否在战斗状态
		Human human = message.getPlayer().getHuman();
		if (!PlayerState.isInBattleState(message.getPlayer().getState())) {
			return;
		}
		// 战斗上下文不为空
		Battle battle = human.getBattleContext().getBattle();
		if (battle == null) {
			return;
		}
		// FIXME: crazyjohn 要做长度检查
		// broadcast
		GCBattleChat chatMsg = new GCBattleChat();
		chatMsg.setSenderGuid(human.getHumanGuid());
		chatMsg.setContent(message.getContent());
		battle.broadcastToBattleUnits(chatMsg);
	}

}
