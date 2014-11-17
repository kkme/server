package com.hifun.soul.gameserver.reward.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.reward.RewardBaseInfo;
import com.hifun.soul.gameserver.reward.RewardTemplateManager;
import com.hifun.soul.gameserver.reward.msg.GCAddCommonReward;
import com.hifun.soul.gameserver.reward.msg.GCRemoveCommonReward;

@Scope("singleton")
@Component
public class RewardService {
	@Autowired
	private RewardTemplateManager rewardTemplateManager;	

	/**
	 * 推送奖励信息
	 * 
	 * @param rewardId
	 */
	public void sendAddCommonRewardMessage(Human human,int rewardType) {
		if(human==null){
			return;
		}
		GCAddCommonReward gcMsg = new GCAddCommonReward();
		RewardBaseInfo rewardInfo = rewardTemplateManager.getRewardInfo(rewardType);
		gcMsg.setReward(rewardInfo);
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 发送移除可领取奖励消息
	 * @param rewardType
	 */
	public void sendRemoveCommonRewardMessage(Human human, int rewardType){
		if(human==null){
			return;
		}
		GCRemoveCommonReward gcMsg = new GCRemoveCommonReward();
		gcMsg.setId(rewardType);
		human.sendMessage(gcMsg);
	}
}
