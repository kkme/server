package com.hifun.soul.gameserver.stage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stage.msg.CGGetMapPerfectReward;

@Component
public class CGGetMapPerfectRewardHandler implements
		IMessageHandlerWithType<CGGetMapPerfectReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_MAP_PERFECT_REWARD;
	}

	@Override
	public void execute(CGGetMapPerfectReward message) {
		Human human = message.getPlayer().getHuman();
		// 领取完美通关奖励
		human.getHumanStageManager().getMapPerfectReward(message.getMapId());
	}

}
