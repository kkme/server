package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.msg.CGRequestCancelHostingBattle;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 取消托管状态逻辑;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGRequestCancelHostingBattleHandler implements
		IMessageHandlerWithType<CGRequestCancelHostingBattle> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REQUEST_CANCEL_HOSTING_BATTLE;
	}

	@Override
	public void execute(CGRequestCancelHostingBattle message) {
		Human human = message.getPlayer().getHuman();
		// 是否在托管状态
		if (message.getPlayer().getState() != PlayerState.HOSTING_BATTLING) {
			return;
		}
		// 战斗上下文不为空
		Battle battle = human.getBattleContext().getBattle();
		if (battle == null) {
			return;
		}
		battle.unitRequestCancelHostingBattle(human);
	}

}
