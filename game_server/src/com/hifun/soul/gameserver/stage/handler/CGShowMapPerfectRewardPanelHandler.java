package com.hifun.soul.gameserver.stage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.stage.msg.CGShowMapPerfectRewardPanel;

@Component
public class CGShowMapPerfectRewardPanelHandler implements
		IMessageHandlerWithType<CGShowMapPerfectRewardPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_MAP_PERFECT_REWARD_PANEL;
	}

	@Override
	public void execute(CGShowMapPerfectRewardPanel message) {
		Human human = message.getPlayer().getHuman();
		// 更新完美通关奖励面板
		human.getHumanStageManager().updateMapPerfectRewardPanel(message.getMapId());
	}

}
