package com.hifun.soul.gameserver.boss.manager;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.IHumanActivityManager;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.boss.BossInfo;
import com.hifun.soul.gameserver.boss.BossRoleInfo;
import com.hifun.soul.gameserver.boss.BossState;
import com.hifun.soul.gameserver.boss.template.BossRankRewardTemplate;
import com.hifun.soul.gameserver.boss.template.BossTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.ConsumeItem;
import com.hifun.soul.gameserver.item.DynamicPropertyType;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.reward.RewardType;

public class HumanBossManager implements ILoginManager, IHumanActivityManager {
	/** Boss战伤害奖励金币最小值 */
	private static final int BOSS_DAMAGE_REWARD_COIN_MIN = 100;
	/** Boss战伤害奖励荣誉最小值 */
	private static final int BOSS_DAMAGE_REWARD_HONOUR_MIN = 1;
	private Human _human;

	public HumanBossManager(Human human) {
		this._human = human;

		this._human.registerLoginManager(this);
	}

	public Human getHuman() {
		return this._human;
	}

	@Override
	public void onLogin() {
		sendRewardNotify();
	}

	@Override
	public ActivityType getActivityType() {
		return ActivityType.BOSS_WAR;
	}

	@Override
	public void onOpenClick() {
		GameServerAssist.getBossWarService().onEnterWar(this._human);
	}

	/**
	 * 是否能领取排名奖励
	 * 
	 * @return
	 */
	public boolean canGetBossWarRankReward() {
		if (!readyForRecieveReward()) {
			return false;
		}
		BossRoleInfo bossRoleInfo = GameServerAssist.getBossWarService().getBossRoleInfo(_human.getHumanGuid());
		if(!bossRoleInfo.getHasRankReward()){
			return false;
		}
		if (bossRoleInfo.getRank() <= 0 ) {
			return false;
		}
		return true;
	}

	/**
	 * 是否能领取击杀奖励
	 * 
	 * @return
	 */
	public boolean canGetKillBossReward() {
		if (!readyForRecieveReward()) {
			return false;
		}
		BossRoleInfo bossRoleInfo = GameServerAssist.getBossWarService().getBossRoleInfo(_human.getHumanGuid());
		if(!bossRoleInfo.getHasKillReward()){
			return false;
		}
		BossInfo bossInfo = GameServerAssist.getBossWarService().getBossInfo();
		if (bossInfo.getBossState() != BossState.DEAD.getIndex() || bossInfo.getKillerId() != _human.getHumanGuid()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否能获得伤害奖励
	 * 
	 * @return
	 */
	public boolean canGetBossDamageReward() {
		if (!readyForRecieveReward()) {
			return false;
		}
		BossRoleInfo bossRoleInfo = GameServerAssist.getBossWarService().getBossRoleInfo(_human.getHumanGuid());
		if (!bossRoleInfo.getHasDamageReward() || bossRoleInfo.getDamage() <= 0 ) {
			return false;
		}
		return true;
	}

	private boolean readyForRecieveReward() {
		// boss如果还活着不可以领奖
		BossInfo bossInfo = GameServerAssist.getBossWarService().getBossInfo();
		if (bossInfo == null) {
			return false;
		}
		if (BossState.LIVE.getIndex() == bossInfo.getBossState()) {			
			return false;
		}
		// 判断当前玩家是否有参与boss战
		BossRoleInfo bossRoleInfo = GameServerAssist.getBossWarService().getBossRoleInfo(_human.getHumanGuid());
		if (bossRoleInfo == null) {
			return false;
		}
		return true;
	}

	/**
	 * 领取boss战排名奖励
	 * 
	 * @return
	 */
	public boolean recieveBossWarRankReward() {
		if (!canGetBossWarRankReward()) {
			return false;
		}
		BossRoleInfo bossRoleInfo = GameServerAssist.getBossWarService().getBossRoleInfo(_human.getHumanGuid());
		BossRankRewardTemplate template = GameServerAssist.getBossWarTemplateManager().getSuitableRewardTemplate(bossRoleInfo.getRank());
		if (template != null) {
			if (_human.getBagManager().isFull(BagType.MAIN_BAG)) {
				_human.sendWarningMessage(LangConstants.BAG_IS_FULL);
				return false;
			}
			_human.getBagManager().putItems(BagType.MAIN_BAG, template.getGiftId(), 1, ItemLogReason.BOSS_REWARD, "");
		}
		// 更新数据库
		bossRoleInfo.setHasRankReward(false);
		GameServerAssist.getBossWarService().updateBossRoleInfoToDB(bossRoleInfo);
		// 从可领取奖励列表中移除该奖励
		GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(_human,RewardType.BOSS_WAR_RANK_REWARD);
		return true;
	}

	/**
	 * 领取击杀boss奖励
	 * 
	 * @return
	 */
	public boolean recieveKillBossReward() {
		if (!canGetKillBossReward()) {
			return false;
		}
		BossInfo bossInfo = GameServerAssist.getBossWarService().getBossInfo();
		BossRoleInfo bossRoleInfo = GameServerAssist.getBossWarService().getBossRoleInfo(_human.getHumanGuid());
		BossTemplate bossTemplate = GameServerAssist.getBossWarService().getBossTemplate();
		if (bossTemplate == null) {
			return false;
		}
		if (_human.getHumanGuid() == bossInfo.getKillerId()) {
			if (_human.getBagManager().isFull(BagType.MAIN_BAG)) {
				_human.sendWarningMessage(LangConstants.BAG_IS_FULL);
				return false;
			}
			_human.getBagManager().putItems(BagType.MAIN_BAG, bossTemplate.getGiftId(), 1, ItemLogReason.BOSS_REWARD,"");
		}
		// 更新数据库
		bossRoleInfo.setHasKillReward(false);
		GameServerAssist.getBossWarService().updateBossRoleInfoToDB(bossRoleInfo);
		// 从可领取奖励列表中移除该奖励
		GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(_human,RewardType.BOSS_KILL_REWARD);
		return true;
	}

	/**
	 * 领取boss伤害奖励
	 * 
	 * @return
	 */
	public boolean recieveBossDamageReward() {
		if (!canGetBossDamageReward()) {
			return false;
		}
		BossInfo bossInfo = GameServerAssist.getBossWarService().getBossInfo();
		BossRoleInfo bossRoleInfo = GameServerAssist.getBossWarService().getBossRoleInfo(_human.getHumanGuid());
		BossTemplate bossTemplate = GameServerAssist.getBossWarService().getBossTemplate();
		if (bossTemplate == null) {
			return false;
		}
		if(_human.getBagManager().getFreeSize(BagType.MAIN_BAG)<=0){
			_human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return false;
		}
		ConsumeItem rewardItem = (ConsumeItem)ItemFactory.creatNewItem(_human, ItemConstantId.BOSS_WAR_DAMAGE_SPREE_ID); 
		if(rewardItem==null){
			return false;
		}
		int money = (int) (bossRoleInfo.getDamage() * 1.0f * bossTemplate.getTotalCoin() / bossInfo.getTotalBlood());
		if(money<BOSS_DAMAGE_REWARD_COIN_MIN){
			money = BOSS_DAMAGE_REWARD_COIN_MIN;
		}
		else if(money > _human.getLevel()*bossTemplate.getMaxCoinRate()){
			money = _human.getLevel()*bossTemplate.getMaxCoinRate();
		}		
		ConsumeItemFeature itemFeature = (ConsumeItemFeature)rewardItem.getFeature();
		itemFeature.setDynamicProperty(DynamicPropertyType.COIN, money);		
		int honour = (int) (bossRoleInfo.getDamage() * 1.0f * bossTemplate.getTotalHonour() / bossInfo.getTotalBlood());
		if(honour<BOSS_DAMAGE_REWARD_HONOUR_MIN){
			honour = BOSS_DAMAGE_REWARD_HONOUR_MIN;
		}
		// 荣誉获取的最大值跟等级不挂钩
		else if(honour > bossTemplate.getMaxHonourRate()){
			honour = bossTemplate.getMaxHonourRate();
		}
		itemFeature.setDynamicProperty(DynamicPropertyType.HONOUR, honour);		
		_human.getBagManager().putItem(BagType.MAIN_BAG, rewardItem, ItemLogReason.BOSS_REWARD, "");		
		// 更新数据库
		bossRoleInfo.setHasDamageReward(false);
		GameServerAssist.getBossWarService().updateBossRoleInfoToDB(bossRoleInfo);
		// 从可领取奖励列表中移除该奖励
		GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(_human,RewardType.BOSS_WAR_DAMAGE_REWARD);
		return true;
	}
	
	/** 
	 * 通知Boss战奖励
	 */
	public void sendRewardNotify(){
		if(canGetBossWarRankReward()){
			GameServerAssist.getRewardService().sendAddCommonRewardMessage(_human,RewardType.BOSS_WAR_RANK_REWARD);
		}
		if(canGetKillBossReward()){
			GameServerAssist.getRewardService().sendAddCommonRewardMessage(_human,RewardType.BOSS_KILL_REWARD);
		}
		if(canGetBossDamageReward()){
			GameServerAssist.getRewardService().sendAddCommonRewardMessage(_human,RewardType.BOSS_WAR_DAMAGE_REWARD);
		}
	}

}
