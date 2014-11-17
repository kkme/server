package com.hifun.soul.gameserver.autobattle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.autobattle.manager.HumanAutoBattleManager;
import com.hifun.soul.gameserver.autobattle.msg.CGCancelStageAutoBattle;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;

@Component
public class CGCancelStageAutoBattleHandler implements
		IMessageHandlerWithType<CGCancelStageAutoBattle> {

	@Override
	public short getMessageType() {
		return MessageType.CG_CANCEL_STAGE_AUTO_BATTLE;
	}

	@Override
	public void execute(CGCancelStageAutoBattle message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断当前玩家状态是否是在扫荡中
		if(player.getState() != PlayerState.AUTOBATTLEING){
			return;
		}
		
		// 取消扫荡任务调度
		HumanAutoBattleManager autoBattleManager = human.getHumanAutoBattleManager();
		autoBattleManager.cancelAutoBattle();
	}

}
