package com.hifun.soul.gameserver.reward;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.reward.template.RewardPushTemplate;

@Scope("singleton")
@Component
public class RewardTemplateManager implements IInitializeRequired{
	private Map<Integer,RewardPushTemplate> rewardPushTemplates;
	
	@Autowired
	private TemplateService templateService;
	@Override
	public void init() {
		rewardPushTemplates = templateService.getAll(RewardPushTemplate.class);
	}
	
	/**
	 * 获取奖励推送信息
	 * @param rewardId
	 * @return
	 */
	public RewardBaseInfo getRewardInfo(int rewardId) {
		if(!rewardPushTemplates.containsKey(rewardId)){
			return null;
		}
		RewardPushTemplate template = rewardPushTemplates.get(rewardId);
		RewardBaseInfo rewardInfo = new RewardBaseInfo();
		rewardInfo.setId(rewardId);
		rewardInfo.setName(template.getName());
		rewardInfo.setIcon(template.getIconId());
		return rewardInfo;
	}

}
