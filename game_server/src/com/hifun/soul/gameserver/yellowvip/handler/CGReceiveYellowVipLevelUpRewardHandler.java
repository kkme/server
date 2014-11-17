package com.hifun.soul.gameserver.yellowvip.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.yellowvip.manager.HumanYellowVipManager;
import com.hifun.soul.gameserver.yellowvip.msg.CGReceiveYellowVipLevelUpReward;

@Component
public class CGReceiveYellowVipLevelUpRewardHandler implements IMessageHandlerWithType<CGReceiveYellowVipLevelUpReward> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_RECEIVE_YELLOW_VIP_LEVEL_UP_REWARD;
	}

	@Override
	public void execute(CGReceiveYellowVipLevelUpReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}		
		Human human = player.getHuman();
		if(human == null){
			return;
		}	
		HumanYellowVipManager yellowVipManager = human.getHumanYellowVipManager();
		yellowVipManager.receiveLevelUpReward(message.getRewardIndex());
	}

}
