package com.hifun.soul.gameserver.yellowvip.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.yellowvip.manager.HumanYellowVipManager;
import com.hifun.soul.gameserver.yellowvip.msg.CGOpenYellowVipRewardPanel;
import com.hifun.soul.gameserver.yellowvip.msg.GCOpenYellowVipRewardPanel;

@Component
public class CGOpenYellowVipRewardPanelHandler implements IMessageHandlerWithType<CGOpenYellowVipRewardPanel> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_OPEN_YELLOW_VIP_REWARD_PANEL;
	}

	@Override
	public void execute(CGOpenYellowVipRewardPanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}		
		Human human = player.getHuman();
		if(human == null){
			return;
		}	
		HumanYellowVipManager yellowVipManager = human.getHumanYellowVipManager();
		GCOpenYellowVipRewardPanel gcMsg = new GCOpenYellowVipRewardPanel();
		gcMsg.setOnceRewardState(yellowVipManager.getYellowVipOnceRewardState());
		gcMsg.setDailyRewardState(yellowVipManager.getYellowVipDailyRewardState());
		gcMsg.setYearVipRewardState(yellowVipManager.getYearYellowVipRewardState());
		gcMsg.setLevelUpRewardState(yellowVipManager.getYellowVipLevelUpRewardState());
		player.sendMessage(gcMsg);
	}

}
