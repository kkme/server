package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
//import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.msg.CGRequestBattleGiveUp;
//import com.hifun.soul.gameserver.battle.msg.GCBattleGiveUp;
//import com.hifun.soul.gameserver.human.Human;
//import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 玩家请求放弃战斗;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGRequestBattleGiveUpHandler implements
		IMessageHandlerWithType<CGRequestBattleGiveUp> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REQUEST_BATTLE_GIVE_UP;
	}

	@Override
	public void execute(CGRequestBattleGiveUp message) {
		// 检查是否在战斗状态
//		Human human = message.getPlayer().getHuman();
//		if (!PlayerState.isInBattleState(message.getPlayer().getState())) {
//			return;
//		}
//		// 战斗上下文不为空
//		Battle battle = human.getBattleContext().getBattle();
//		if (battle == null) {
//			return;
//		}
//		// 广播玩家放弃
//		GCBattleGiveUp giveUpMsg = new GCBattleGiveUp();
//		giveUpMsg.setLosserGuid(human.getHumanGuid());
//		battle.broadcastToBattleUnits(giveUpMsg);
//		// 服务器退出战斗的处理逻辑
//		battle.onBattleUnitQuit(human);
	}

}
