package com.hifun.soul.gameserver.legionmine.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.PrestigeLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanLegionMineBattleRewardEntity;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.IHumanActivityManager;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.template.LegionMineRewardTemplate;
import com.hifun.soul.gameserver.reward.RewardType;
import com.hifun.soul.proto.common.LegionMineBattleRewards.LegionMineBattleReward;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionMineBattleReward;

/**
 * 角色军团矿战管理器
 * 
 * @author yandajun
 * 
 */
public class HumanLegionMineWarManager implements ILoginManager,
		IHumanActivityManager, IEventListener, IHumanPersistenceManager,
		ICachableComponent {
	private Human human;
	GlobalLegionMineWarManager globalMineWarManager;
	Map<Integer, Integer> battleRewards = new HashMap<Integer, Integer>();
	private CacheEntry<Integer, IEntity> cache = new CacheEntry<Integer, IEntity>();

	public HumanLegionMineWarManager(Human human) {
		this.human = human;
		globalMineWarManager = GameServerAssist.getGlobalLegionMineWarManager();
		this.human.registerLoginManager(this);
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		human.getEventBus().addListener(EventType.HUMAN_OFF_LINE_EVENT, this);
	}

	@Override
	public ActivityType getActivityType() {
		return ActivityType.LEGION_MINE;
	}

	@Override
	public void onLogin() {
		sendRewardNotify();
	}

	@Override
	public void onOpenClick() {
		globalMineWarManager.onEnterWar(human);
	}

	@Override
	public void onEvent(IEvent event) {
		// 角色离线
		if (event.getType() == EventType.HUMAN_OFF_LINE_EVENT) {
			LegionMineMember mineMember = globalMineWarManager
					.getJoinLegionMineMember(human.getHumanGuid());
			if (mineMember != null) {
				globalMineWarManager.quit(mineMember);
			}
		}
	}

	/**
	 * 通知矿战奖励
	 */
	public void sendRewardNotify() {
		if (hasLegionMineRankReward()) {
			GameServerAssist.getRewardService().sendAddCommonRewardMessage(
					human, RewardType.LEGION_MINE_RANK_REWARD);
		}
		if (hasLegionMineBattleReward()) {
			GameServerAssist.getRewardService().sendAddCommonRewardMessage(
					human, RewardType.LEGION_MINE_BATTLE_REWARD);
		}
	}

	/**
	 * 领取矿战军团内排名奖励
	 */
	public void receiveLegionMineRankReward() {
		if (!hasLegionMineRankReward()) {
			return;
		}
		LegionMineMember mineMember = globalMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		int rank = mineMember.getRank();
		LegionMineRewardTemplate rewardTemplate = GameServerAssist
				.getLegionMineWarTemplateManager().getRewardTemplate(rank);
		if (rewardTemplate == null) {
			return;
		}

		// 奖励物品，失败军团奖励减半
		int itemNum = rewardTemplate.getItemNum();
		if (!mineMember.isLegionWin()) {
			itemNum = itemNum / 2;
		}
		// 判断背包容量是否足够
		if (!human.getBagManager().canContain(rewardTemplate.getItemId(),
				itemNum)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		if (human.getBagManager().putItems(BagType.MAIN_BAG,
				rewardTemplate.getItemId(), itemNum,
				ItemLogReason.LEGION_MINE_RANK_REWARD, "")) {
			// 奖励金币和威望
			int addMoney = rewardTemplate.getRewarCoin();
			human.getWallet().addMoney(CurrencyType.COIN, addMoney, true,
					MoneyLogReason.LEGION_MINE_REWARD, "");
			int addPrestige = rewardTemplate.getRewardPrestige();
			human.addPrestige(addPrestige, true,
					PrestigeLogReason.LEGION_MINE_WAR_REWARD, "");
			// 设置奖励领取状态
			mineMember.setRank(-1);
			globalMineWarManager.updateLegionMineMember(mineMember);
			// 从可领取奖励列表中移除该奖励
			GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(
					human, RewardType.LEGION_MINE_RANK_REWARD);
		}
	}

	/**
	 * 是否有军团矿战排名奖励
	 */
	public boolean hasLegionMineRankReward() {
		LegionMineMember mineMember = globalMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember != null && mineMember.getRank() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 领取矿战战斗奖励
	 */
	public void receiveLegionMineBattleReward() {
		if (!hasLegionMineBattleReward()) {
			return;
		}
		// 判断背包容量是否足够
		if (human.getBagManager().getFreeSize(BagType.MAIN_BAG) < battleRewards
				.size()) {
			human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}
		for (Integer itemId : battleRewards.keySet()) {
			int itemNum = battleRewards.get(itemId);
			human.getBagManager().putItems(BagType.MAIN_BAG, itemId, itemNum,
					true, ItemLogReason.LEGION_MINE_BATTLE_REWARD, "");
		}
		// 从可领取奖励列表中移除该奖励
		GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(
				human, RewardType.LEGION_MINE_BATTLE_REWARD);
		// 清空战斗奖励
		clearBattleReward();
	}

	/**
	 * 是否有军团矿战战斗奖励
	 */
	public boolean hasLegionMineBattleReward() {
		LegionMineMember mineMember = globalMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember != null && mineMember.isJoin()
				&& battleRewards.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for (HumanLegionMineBattleReward.Builder builder : humanEntity
				.getBuilder().getBattleRewardBuilderList()) {
			battleRewards.put(builder.getBattleReward().getItemId(), builder
					.getBattleReward().getItemNum());
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for (Integer itemId : battleRewards.keySet()) {
			HumanLegionMineBattleRewardEntity rewardEntity = new HumanLegionMineBattleRewardEntity();
			rewardEntity.getBuilder().setHumanGuid(human.getHumanGuid());
			rewardEntity.getBuilder().setBattleReward(
					LegionMineBattleReward.newBuilder().setItemId(itemId)
							.setItemNum(battleRewards.get(itemId)));
			humanEntity.getBuilder().addBattleReward(rewardEntity.getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	/**
	 * 添加军团矿战战斗奖励
	 */
	public void addBattleReward() {
		KeyValuePair<Integer, Integer> battleReward = GameServerAssist
				.getLegionMineWarTemplateManager().getRandomBattleReward(
						human.getLevel());
		if (battleReward != null && battleReward.getValue() > 0) {
			Integer itemId = battleReward.getKey();
			Integer itemNum = battleReward.getValue();
			if (battleRewards.containsKey(itemId)) {
				itemNum += battleRewards.get(itemId);
			}
			battleRewards.put(itemId, itemNum);
			HumanLegionMineBattleRewardEntity rewardEntity = new HumanLegionMineBattleRewardEntity();
			rewardEntity.getBuilder().setHumanGuid(human.getHumanGuid());
			rewardEntity.getBuilder().setBattleReward(
					LegionMineBattleReward.newBuilder().setItemId(itemId)
							.setItemNum(itemNum));
			cache.addUpdate(battleReward.getKey().intValue(), rewardEntity);
			// 战斗胜利提示
			human.sendImportantMessage(
					LangConstants.LEGION_MINE_BATTLE_SUCCESS_ITEM, itemNum);
		}
	}

	/**
	 * 清空战斗奖励
	 */
	public void clearBattleReward() {
		for (Integer itemId : battleRewards.keySet()) {
			cache.addDelete(itemId,
					toBattleRewardEntity(itemId, battleRewards.get(itemId)));
		}
		battleRewards.clear();
	}

	/**
	 * 转化为战斗奖励实体
	 */
	private HumanLegionMineBattleRewardEntity toBattleRewardEntity(int itemId,
			int itemNum) {
		HumanLegionMineBattleRewardEntity rewardEntity = new HumanLegionMineBattleRewardEntity();
		rewardEntity.getBuilder().setHumanGuid(human.getHumanGuid());
		rewardEntity.getBuilder().setBattleReward(
				LegionMineBattleReward.newBuilder().setItemId(itemId)
						.setItemNum(itemNum));
		return rewardEntity;
	}
}
