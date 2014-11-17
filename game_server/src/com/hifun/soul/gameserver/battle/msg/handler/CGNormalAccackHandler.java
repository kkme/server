package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.msg.CGNormalAttack;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 普通攻击处理;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGNormalAccackHandler implements
		IMessageHandlerWithType<CGNormalAttack> {

	@Override
	public short getMessageType() {
		return MessageType.CG_NORMAL_ATTACK;
	}

	@Override
	public void execute(CGNormalAttack message) {
		Player player = message.getPlayer();
		// 玩家必须在战斗状态
		if (player.getState() != PlayerState.BATTLING) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		Battle battle = human.getBattleContext().getBattle();
		if (battle == null) {
			return;
		}
		battle.onNormalAttack(human, message.getRow1(), message.getCol1(),
				message.getRow2(), message.getCol2());
	}

}
