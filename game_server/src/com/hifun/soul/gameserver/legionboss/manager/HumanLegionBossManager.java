package com.hifun.soul.gameserver.legionboss.manager;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.IHumanActivityManager;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.ConsumeItem;
import com.hifun.soul.gameserver.item.DynamicPropertyType;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.legionboss.LegionBossInfo;
import com.hifun.soul.gameserver.legionboss.LegionBossRoleInfo;
import com.hifun.soul.gameserver.legionboss.LegionBossState;
import com.hifun.soul.gameserver.legionboss.service.LegionBossService;
import com.hifun.soul.gameserver.legionboss.template.LegionBossTemplate;
import com.hifun.soul.gameserver.reward.RewardType;

public class HumanLegionBossManager implements ILoginManager,
		IHumanActivityManager {
	/** Boss战伤害奖励金币最小值 */
	private static final int BOSS_DAMAGE_REWARD_COIN_MIN = 100;
	/** Boss战伤害奖励荣誉最小值 */
	private static final int BOSS_DAMAGE_REWARD_HONOUR_MIN = 1;
	private Human _human;
	private LegionBossService legionBossService;

	public HumanLegionBossManager(Human human) {
		this._human = human;
		this._human.registerLoginManager(this);
		legionBossService = GameServerAssist.getLegionBossService();
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
		return ActivityType.LEGION_BOSS;
	}

	@Override
	public void onOpenClick() {
		legionBossService.onEnterWar(this._human);
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
		LegionBossRoleInfo bossRoleInfo = legionBossService
				.getBossRoleInfo(_human.getHumanGuid());
		if (!bossRoleInfo.getHasRankReward()) {
			return false;
		}
		if (bossRoleInfo.getRank() <= 0) {
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
		LegionBossRoleInfo bossRoleInfo = legionBossService
				.getBossRoleInfo(_human.getHumanGuid());
		if (!bossRoleInfo.getHasKillReward()) {
			return false;
		}
		LegionBossInfo bossInfo = legionBossService.getBossInfo();
		if (bossInfo.getBossState() != LegionBossState.DEAD.getIndex()
				|| bossInfo.getKillerId() != _human.getHumanGuid()) {
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
		LegionBossRoleInfo bossRoleInfo = legionBossService
				.getBossRoleInfo(_human.getHumanGuid());
		if (!bossRoleInfo.getHasDamageReward() || bossRoleInfo.getDamage() <= 0) {
			return false;
		}
		return true;
	}

	private boolean readyForRecieveReward() {
		// boss如果还活着不可以领奖
		LegionBossInfo bossInfo = legionBossService.getBossInfo();
		if (bossInfo == null) {
			return false;
		}
		if (LegionBossState.LIVE.getIndex() == bossInfo.getBossState()) {
			return false;
		}
		// 判断当前玩家是否有参与boss战
		LegionBossRoleInfo bossRoleInfo = legionBossService
				.getBossRoleInfo(_human.getHumanGuid());
		if (bossRoleInfo == null) {
			return false;
		}
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
		LegionBossInfo bossInfo = legionBossService.getBossInfo();
		LegionBossRoleInfo bossRoleInfo = legionBossService
				.getBossRoleInfo(_human.getHumanGuid());
		LegionBossTemplate bossTemplate = legionBossService.getBossTemplate();
		if (bossTemplate == null) {
			return false;
		}
		if (_human.getBagManager().getFreeSize(BagType.MAIN_BAG) <= 0) {
			_human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return false;
		}
		ConsumeItem rewardItem = (ConsumeItem) ItemFactory.creatNewItem(_human,
				ItemConstantId.LEGION_BOSS_DAMAGE_SPREE_ID);
		if (rewardItem == null) {
			return false;
		}
		int money = (int) (bossRoleInfo.getDamage() * 1.0f
				* bossTemplate.getTotalContribution() / bossInfo.getTotalBlood());
		if (money < BOSS_DAMAGE_REWARD_COIN_MIN) {
			money = BOSS_DAMAGE_REWARD_COIN_MIN;
		} else if (money > _human.getLevel() * bossTemplate.getMaxCoinRate()) {
			money = _human.getLevel() * bossTemplate.getMaxCoinRate();
		}
		ConsumeItemFeature itemFeature = (ConsumeItemFeature) rewardItem
				.getFeature();
		itemFeature.setDynamicProperty(DynamicPropertyType.COIN, money);
		int honour = (int) (bossRoleInfo.getDamage() * 1.0f
				* bossTemplate.getTotalHonour() / bossInfo.getTotalBlood());
		if (honour < BOSS_DAMAGE_REWARD_HONOUR_MIN) {
			honour = BOSS_DAMAGE_REWARD_HONOUR_MIN;
		}
		// 荣誉获取的最大值跟等级不挂钩
		else if (honour > bossTemplate.getMaxHonorRate()) {
			honour = bossTemplate.getMaxHonorRate();
		}
		itemFeature.setDynamicProperty(DynamicPropertyType.HONOUR, honour);
		_human.getBagManager().putItem(BagType.MAIN_BAG, rewardItem,
				ItemLogReason.LEGION_BOSS_REWARD, "");
		// 更新数据库
		bossRoleInfo.setHasDamageReward(false);
		legionBossService.updateBossRoleInfoToDB(bossRoleInfo);
		// 从可领取奖励列表中移除该奖励
		GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(
				_human, RewardType.LEGION_BOSS_DAMAGE_REWARD);
		return true;
	}

	/**
	 * 通知Boss战奖励
	 */
	public void sendRewardNotify() {
		if (canGetBossDamageReward()) {
			GameServerAssist.getRewardService().sendAddCommonRewardMessage(
					_human, RewardType.LEGION_BOSS_DAMAGE_REWARD);
		}
	}

}
