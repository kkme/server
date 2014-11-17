package com.hifun.soul.gameserver.yellowvip.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.yellowvip.YellowVipRewardType;
import com.hifun.soul.gameserver.yellowvip.manager.HumanYellowVipManager;
import com.hifun.soul.gameserver.yellowvip.msg.CGReceiveYellowVipReward;

@Component
public class CGReceiveYellowVipRewardHandler implements IMessageHandlerWithType<CGReceiveYellowVipReward> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_RECEIVE_YELLOW_VIP_REWARD;
	}

	@Override
	public void execute(CGReceiveYellowVipReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}		
		Human human = player.getHuman();
		if(human == null){
			return;
		}	
		HumanYellowVipManager yellowVipManager = human.getHumanYellowVipManager();
		if(yellowVipManager.getYellowVipLevel()<=0){
			return;
		}
		if(message.getRewardType()==YellowVipRewardType.ONCE_REWARD.getIndex()){
			yellowVipManager.receiveOnceReward();
		}else if(message.getRewardType() == YellowVipRewardType.DAILY_REWARD.getIndex()){
			yellowVipManager.receiveDailyReward();
		}else if(message.getRewardType() == YellowVipRewardType.YEAR_VIP_REWARD.getIndex()){
			yellowVipManager.receiveYearVipDailyReward();
		}
	}

}
