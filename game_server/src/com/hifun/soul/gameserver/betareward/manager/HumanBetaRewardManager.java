package com.hifun.soul.gameserver.betareward.manager;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.betareward.service.BetaRewardTemplateManager;
import com.hifun.soul.gameserver.betareward.template.BetaRewardTemplate;
import com.hifun.soul.gameserver.betareward.template.BetaUserTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

public class HumanBetaRewardManager implements ILoginManager {
	private Human human;
	private static final int BETA_REWARD_STATE = 1;
	private BetaRewardTemplateManager betaRewardTemplateManager;
	
	public HumanBetaRewardManager(Human human) {
		this.human = human;
		betaRewardTemplateManager = GameServerAssist.getBetaRewardTemplateManager();
		
		this.human.registerLoginManager(this);
	}
	
	/**
	 * 封测奖励领取状态
	 * @return
	 */
	private int getBetaRewardState() {
		return human.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.BETA_REWARD_STATE);
	}
	
	/**
	 * 封测奖励领取状态
	 * @param state
	 */
	private void setBetaRewardState(int state) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.BETA_REWARD_STATE, state);
	}

	@Override
	public void onLogin() {
		// 校验封测奖励
		checkBetaReward();
	}
	
	private void checkBetaReward(){
		// 如果状态已经为领取过直接返回
		if(getBetaRewardState() == BETA_REWARD_STATE){
			return;
		}
		// 判断玩家是否为封测用户
		BetaUserTemplate betaUserTemplate = betaRewardTemplateManager
				.getBetaUserTemplate(human.getPlayer().getAccount());
		if(betaUserTemplate == null){
			return;
		}
		// 根据封测用户的等级找到对应的奖励模版
		BetaRewardTemplate betaRewardTemplate = betaRewardTemplateManager
				.getBetaRewardTemplate(betaUserTemplate.getLevel());
		if(betaRewardTemplate == null){
			return;
		}
		// 判断玩家背包是否有空格子
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			return;
		}
		// 给玩家封测奖励包并设置状态为已经领取
		if(human.getBagManager().putItems(BagType.MAIN_BAG, betaRewardTemplate.getRewardId(), 1, ItemLogReason.BETA_REWARD, "")){
			setBetaRewardState(BETA_REWARD_STATE);
		}
	}
}
