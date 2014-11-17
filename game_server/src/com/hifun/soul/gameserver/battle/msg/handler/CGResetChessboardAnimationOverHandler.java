package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.msg.CGResetChessboardAnimationOver;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGResetChessboardAnimationOverHandler implements
		IMessageHandlerWithType<CGResetChessboardAnimationOver> {

	@Override
	public short getMessageType() {
		return MessageType.CG_RESET_CHESSBOARD_ANIMATION_OVER;
	}

	@Override
	public void execute(CGResetChessboardAnimationOver message) {
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		IBattleContext context = human.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		battle.onResetChessboardFeedback();
	}

}
