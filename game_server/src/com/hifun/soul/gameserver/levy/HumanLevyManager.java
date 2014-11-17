package com.hifun.soul.gameserver.levy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TrainCoinLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanMainCityEntity;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.event.MainCityCollectStoneEvent;
import com.hifun.soul.gameserver.event.RevenueEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.levy.msg.GCCollectMagicStone;
import com.hifun.soul.gameserver.levy.msg.GCCollectMagicStoneComplete;
import com.hifun.soul.gameserver.levy.msg.GCLevy;
import com.hifun.soul.gameserver.levy.msg.GCSendMainCityMonsterInfo;
import com.hifun.soul.gameserver.levy.template.MagicStoneTemplate;
import com.hifun.soul.gameserver.levy.template.MainCityMonsterTemplate;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.proto.data.entity.Entity.HumanMainCityInfo;

/**
 * 征收管理器
 * 
 * @author magicstone
 * 
 */
public class HumanLevyManager implements IHumanPersistenceManager,
		ICachableComponent, ILoginManager, IEventListener {
	/** 收集目标个数 */
	public static final int COLLECT_TARGET_SIZE = 5;
	/** 角色 */
	private Human human;
	/** 主城模板管理器 */
	private LevyTemplateManager templateManager;
	/** 收集目标 */
	private List<MagicStoneInfo> collectTargets = new ArrayList<MagicStoneInfo>();
	/** 已收集的魔法石 */
	private List<MagicStoneInfo> collectedMagicStones = new ArrayList<MagicStoneInfo>();
	/** 剩余免费收集次数 */
	private int remainFreeCollectNum;
	/** 花费魔晶收集次数 */
	private int costCrystalCollectNum;
	/** 税收加成比例 */
	private int levyExtraRate;
	/** 未收集到的魔法石id列表(key:在收集目标数组中的位置索引,value:魔法石id) */
	private Map<Integer, Integer> targetMagicStoneIds = new HashMap<Integer, Integer>();
	private CacheEntry<Long, HumanMainCityEntity> cache = new CacheEntry<Long, HumanMainCityEntity>();
	/** 骰子点数 */
	private int[] betPoints;

	public HumanLevyManager(Human human) {
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		this.human.registerLoginManager(this);
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		templateManager = GameServerAssist.getLevyTemplateManager();
	}

	/**
	 * 获取剩余征收次数
	 * 
	 * @return
	 */
	public int getRemainLevyNum() {
		return human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.LEVY_REMAIN_TIMES);
	}

	/**
	 * 设置税收剩余次数
	 * 
	 * @param remainNum
	 */
	private void setRemainLevyNum(int remainNum) {
		human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.LEVY_REMAIN_TIMES, remainNum);
	}

	/**
	 * 重置税收剩余次数
	 */
	public void resetData() {
		// 重置税收剩余次数
		human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.LEVY_REMAIN_TIMES,
						GameServerAssist.getGameConstants().getLevyTime());
		human.setLevyBetRemainNum(GameServerAssist.getGameConstants()
				.getLevyBetTime());
		human.setLevyCertainWinUsedNum(0);

		this.remainFreeCollectNum = GameServerAssist.getGameConstants()
				.getFreeCollectNum();
		this.costCrystalCollectNum = 0;
		this.levyExtraRate = 0;
		refreshCollectTarget();
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		human.getHumanCdManager().removeCdFree(CdType.LEVY);

		// 重置攻城怪物数量
		resetMainCityMonsterNum();
	}

	/**
	 * 获取税收收益(训练币)
	 * 
	 * @return
	 */
	public int getLevyRevenue() {
		int revenue = GameServerAssist.getLevelUpTemplateManager()
				.getLevyRevenue(human.getLevel());
		float revenueRate = human.getHumanAntiIndulgeManager().getRevenueRate();
		revenue = (int) (revenue * revenueRate * (1 + levyExtraRate
				/ SharedConstants.DEFAULT_FLOAT_BASE));
		return revenue;
	}

	/**
	 * 征税
	 */
	public void handleLevy() {
		HumanCdManager cdManager = human.getHumanCdManager();
		int remainLevyNum = getRemainLevyNum();
		if (remainLevyNum <= 0) {
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, "可征收次数");
			return;
		}
		long cdTime = cdManager.getSpendTime(CdType.LEVY, 0);
		if (cdManager.canAddCd(CdType.LEVY, cdTime)) {
			int revenue = getLevyRevenue();
			remainLevyNum--;
			setRemainLevyNum(remainLevyNum);
			// 军团科技收益加成
			revenue = human.getHumanLegionManager().getLegionTechnologyAmend(
					LegionTechnologyType.MAIN_CITY_LEVY, revenue);
			human.addTrainCoin(revenue, true, TrainCoinLogReason.LEVY_BET_WIN,
					"");
			if (remainLevyNum > 0) {
				cdManager.addCd(CdType.LEVY, cdTime);
			}
			cdManager.snapCdQueueInfo(CdType.LEVY);
			GCLevy gcMsg = new GCLevy();
			gcMsg.setLevyRevenue(revenue);
			gcMsg.setRemainLevyNum(remainLevyNum);
			human.sendMessage(gcMsg);
			// 发送税收事件
			RevenueEvent revenueEvent = new RevenueEvent();
			human.getEventBus().fireEvent(revenueEvent);
		}

	}

	public Human getOwner() {
		return human;
	}

	public List<MagicStoneInfo> getCollectTargets() {
		return this.collectTargets;
	}

	public List<MagicStoneInfo> getCollectedMagicStones() {
		return this.collectedMagicStones;
	}

	public int getCostCrystalNum() {
		return templateManager
				.getCollectCostCrystalNum(this.costCrystalCollectNum + 1);
	}

	public int getFreeCollectNum() {
		return this.remainFreeCollectNum;
	}

	public int getLevyExtraRate() {
		return this.levyExtraRate;
	}

	public void setLevyExtraRate(int levyExtraRate) {
		this.levyExtraRate = levyExtraRate;
	}

	/**
	 * 获取使用魔晶收集的次数
	 * 
	 * @return
	 */
	public int getCostCrystalCollectNum() {
		return costCrystalCollectNum;
	}

	/**
	 * 收集魔法石处理
	 */
	public void handleCollectMagicStone() {
		// 判断当前是否已经到上限
		if (levyExtraRate >= GameServerAssist.getGameConstants()
				.getMaxLeveExtraRate()) {
			human.sendErrorMessage(LangConstants.LEVY_FULL);
			return;
		}

		if (this.remainFreeCollectNum > 0) {
			remainFreeCollectNum--;
			doCollectMagicStone();
		} else {
			int maxCrystalCollect = GameServerAssist
					.getVipPrivilegeTemplateManager()
					.getVipTemplate(human.getVipLevel())
					.getMaxCrystalCollectNum();
			if (this.costCrystalCollectNum >= maxCrystalCollect) {
				human.sendErrorMessage(LangConstants.CRYSTAL_COLLECT_TIME_USE_OUT);
				return;
			}
			int crystalCostNum = this.getCostCrystalNum();
			if (human.getWallet().costMoney(CurrencyType.CRYSTAL,
					crystalCostNum, MoneyLogReason.CRYSTAL_COLLECT_MAGIC_STONE,
					"")) {
				this.costCrystalCollectNum++;
				doCollectMagicStone();
			}
		}
	}

	/**
	 * 执行收集魔法石
	 */
	private void doCollectMagicStone() {
		// 上次通灵收集到的宝石个数
		int lastSelectedNum = COLLECT_TARGET_SIZE - targetMagicStoneIds.size();
		if (collectedMagicStones.size() == 0) {
			MagicStoneInfo[] magicStones = templateManager
					.getRandomMagicStones(COLLECT_TARGET_SIZE, false);
			for (MagicStoneInfo stone : magicStones) {
				for (Integer index : targetMagicStoneIds.keySet()) {
					if (stone.getId() == targetMagicStoneIds.get(index)) {
						stone.setCollected(true);
						stone.setTargetIndex(index);
						collectTargets.get(index).setCollected(true);
						targetMagicStoneIds.remove(index);
						break;
					}
				}
				collectedMagicStones.add(stone);
			}
		} else {
			for (int i = 0; i < collectedMagicStones.size(); i++) {
				MagicStoneInfo stone = collectedMagicStones.get(i);
				if (stone.getCollected()) {
					continue;
				}
				MagicStoneInfo[] newStone = templateManager
						.getRandomMagicStones(1, false);
				for (Integer index : targetMagicStoneIds.keySet()) {
					if (newStone[0].getId() == targetMagicStoneIds.get(index)) {
						newStone[0].setCollected(true);
						newStone[0].setTargetIndex(index);
						collectTargets.get(index).setCollected(true);
						targetMagicStoneIds.remove(index);
						break;
					}
				}
				collectedMagicStones.remove(i);
				collectedMagicStones.add(i, newStone[0]);
			}
		}
		// 根据已选中的宝石个数，奖励灵气值(不重复奖励)
		// 新选中的宝石数量
		int newSelectedNum = COLLECT_TARGET_SIZE - targetMagicStoneIds.size();
		if (targetMagicStoneIds.size() != 0 && newSelectedNum > lastSelectedNum) {
			rewardAura(newSelectedNum);
		}
		int maxCrystalCollect = GameServerAssist
				.getVipPrivilegeTemplateManager()
				.getVipTemplate(human.getVipLevel()).getMaxCrystalCollectNum();
		GCCollectMagicStone gcCollectMsg = new GCCollectMagicStone();
		gcCollectMsg.setCollected(collectedMagicStones
				.toArray(new MagicStoneInfo[0]));
		gcCollectMsg.setCostCrystalNum(getCostCrystalNum());
		gcCollectMsg.setFreeCollectNum(this.remainFreeCollectNum);
		gcCollectMsg.setCrystalCollectRemainNum(maxCrystalCollect
				- this.costCrystalCollectNum);
		human.sendMessage(gcCollectMsg);
		if (targetMagicStoneIds.size() == 0) {
			onCollectCompleted();
		}
		cache.addUpdate(human.getHumanGuid(), convertToEntity());
		// 发送通灵事件
		human.getEventBus().fireEvent(new MainCityCollectStoneEvent());
	}

	/**
	 * 收集任务完成
	 */
	private void onCollectCompleted() {
		int rewardMoney = GameServerAssist.getGameConstants()
				.getCollectRewardBaseNum() * human.getLevel();
		// 由以前的奖励金币，改为奖励灵气值
		int rewardAura = templateManager.getAura(human.getLevel(),
				COLLECT_TARGET_SIZE);
		// 军团科技加成灵气值收益
		rewardAura = human.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.MAIN_CITY_COLLECT_STONE, rewardAura);
		human.setAura(human.getAura() + rewardAura);
		human.sendImportantMessage(LangConstants.MAIN_CITY_AURA,
				COLLECT_TARGET_SIZE, rewardAura);

		// 税收加成也不要了(edit by yandajun 20140324)

		refreshCollectTarget();
		GCCollectMagicStoneComplete gcCompleteMsg = new GCCollectMagicStoneComplete();
		gcCompleteMsg.setCollectTarget(collectTargets
				.toArray(new MagicStoneInfo[0]));
		gcCompleteMsg.setLevyRevenue(this.getLevyRevenue());
		gcCompleteMsg.setLevyExtraRate(levyExtraRate);
		gcCompleteMsg.setRewardCurrencyType(GameServerAssist.getGameConstants()
				.getCollectRewardType());
		gcCompleteMsg.setRewardCurrencyNum(rewardMoney);
		human.sendMessage(gcCompleteMsg);
	}

	/**
	 * 收集宝石奖励灵气值
	 */
	private void rewardAura(int num) {
		int rewardAura = templateManager.getAura(human.getLevel(), num);
		human.setAura(human.getAura() + rewardAura);
		human.sendImportantMessage(LangConstants.MAIN_CITY_AURA, num,
				rewardAura);
	}

	/**
	 * 刷新收集目标
	 */
	private void refreshCollectTarget() {
		MagicStoneInfo[] collectTarget = templateManager.getRandomMagicStones(
				COLLECT_TARGET_SIZE, true);
		this.collectedMagicStones.clear();
		this.collectTargets.clear();
		targetMagicStoneIds.clear();
		for (int i = 0; i < collectTarget.length; i++) {
			MagicStoneInfo stone = collectTarget[i];
			collectTargets.add(stone);
			targetMagicStoneIds.put(i, stone.getId());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (HumanMainCityEntity entity : cache.getAllUpdateData()) {
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		HumanMainCityInfo mainCityInfo = humanEntity.getBuilder()
				.getMainCityInfo();
		this.costCrystalCollectNum = mainCityInfo.getCostCrystalCollectNum();
		this.levyExtraRate = mainCityInfo.getLevyExtraRate();
		this.remainFreeCollectNum = mainCityInfo.getRemainFreeCollectNum();
		if (mainCityInfo.getCollectTargetList().size() == 0) {
			this.refreshCollectTarget();
		} else {
			this.collectTargets.clear();
			this.targetMagicStoneIds.clear();
			int index = 0;
			for (Integer id : mainCityInfo.getCollectTargetList()) {
				MagicStoneTemplate template = templateManager
						.getMagicStoneTemplate(id);
				MagicStoneInfo magicStone = new MagicStoneInfo();
				magicStone.setId(id);
				magicStone.setIcon(template.getIcon());
				targetMagicStoneIds.put(index, id);
				collectTargets.add(magicStone);
				index++;
			}
			this.collectedMagicStones.clear();
			for (Integer id : mainCityInfo.getCurrentCollectedList()) {
				MagicStoneTemplate template = templateManager
						.getMagicStoneTemplate(id);
				MagicStoneInfo magicStone = new MagicStoneInfo();
				magicStone.setId(id);
				magicStone.setIcon(template.getIcon());
				for (Integer key : targetMagicStoneIds.keySet()) {
					if (magicStone.getId() == targetMagicStoneIds.get(key)) {
						magicStone.setCollected(true);
						magicStone.setTargetIndex(key);
						collectTargets.get(key).setCollected(true);
						targetMagicStoneIds.remove(key);
						break;
					}
				}
				this.collectedMagicStones.add(magicStone);
			}
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		humanEntity.getBuilder()
				.setMainCityInfo(convertToEntity().getBuilder());
	}

	@Override
	public void onLogin() {
		// 押注默认点数
		betPoints = new int[] { SharedConstants.LEVY_BET_DEFAULT_POINT,
				SharedConstants.LEVY_BET_DEFAULT_POINT,
				SharedConstants.LEVY_BET_DEFAULT_POINT };
	}

	private HumanMainCityEntity convertToEntity() {
		HumanMainCityInfo.Builder builder = HumanMainCityInfo.newBuilder();
		builder.setHumanGuid(human.getHumanGuid());
		builder.setLevyExtraRate(levyExtraRate);
		builder.setRemainFreeCollectNum(remainFreeCollectNum);
		builder.setCostCrystalCollectNum(costCrystalCollectNum);
		for (MagicStoneInfo stone : this.collectTargets) {
			builder.addCollectTarget(stone.getId());
		}
		for (MagicStoneInfo stone : this.collectedMagicStones) {
			builder.addCurrentCollected(stone.getId());
		}
		return new HumanMainCityEntity(builder);
	}

	/**
	 * 获得骰子的点数
	 */
	public int[] getBetPoints() {
		return betPoints;
	}

	/**
	 * 设置骰子的点数
	 */
	public void setBetPoints(int[] betPoints) {
		this.betPoints = betPoints;
	}

	/**
	 * 重置主城怪物数量
	 */
	private void resetMainCityMonsterNum() {
		MainCityMonsterTemplate monsterTemplate = templateManager
				.getMonsterTemplate(human.getLevel());
		if (monsterTemplate != null) {
			human.setMainCityMonsterRemainNum(monsterTemplate.getMonsterNum());
		}
		sendMonsterInfo();
	}

	/**
	 * 发送攻城怪物信息
	 */
	public void sendMonsterInfo() {
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MAIN_CITY_MONSTER, false)) {
			return;
		}
		// 下发CD信息
		human.getHumanCdManager().snapCdQueueInfo(CdType.MAIN_CITY_BATTLE);
		GCSendMainCityMonsterInfo msg = new GCSendMainCityMonsterInfo();
		MainCityMonsterInfo monsterInfo = getMonsterInfo();
		if (monsterInfo == null) {
			return;
		}
		monsterInfo.setMonsterId(monsterInfo.getMonsterId());
		msg.setMonsterLevel(monsterInfo.getMonsterLevel());
		msg.setMonsterName(monsterInfo.getMonsterName());
		msg.setTotalNum(monsterInfo.getTotalNum());
		msg.setRemainNum(monsterInfo.getRemainNum());
		human.sendMessage(msg);
	}

	/**
	 * 获取攻城怪物信息
	 */
	public MainCityMonsterInfo getMonsterInfo() {
		MainCityMonsterTemplate monsterTemplate = templateManager
				.getMonsterTemplate(human.getLevel());
		if (monsterTemplate == null) {
			return null;
		}
		int monsterId = monsterTemplate.getMonsterId();
		Monster monster = GameServerAssist.getMonsterFactory().createMonster(
				monsterId);
		if (monster == null) {
			return null;
		}
		MainCityMonsterInfo monsterInfo = new MainCityMonsterInfo();
		monsterInfo.setMonsterId(monsterId);
		monsterInfo.setMonsterName(monster.getName());
		monsterInfo.setMonsterLevel(monster.getLevel());
		monsterInfo.setTotalNum(monsterTemplate.getMonsterNum());
		monsterInfo.setRemainNum(human.getMainCityMonsterRemainNum());
		monsterInfo.setRewardCoin(monsterTemplate.getRewardCoin());
		monsterInfo.setRewardExperience(monsterTemplate.getRewardExperience());
		return monsterInfo;
	}

	/**
	 * 攻打怪物胜利回调
	 */
	public void mainCityBattleWinCallback() {
		MainCityMonsterInfo monsterInfo = getMonsterInfo();
		if (monsterInfo == null) {
			return;
		}
		// 奖励经验
		int experience = monsterInfo.getRewardExperience();
		human.addExperience(experience, true,
				ExperienceLogReason.MAIN_CITY_MONSTER, "");
		// 奖励金币
		int coin = monsterInfo.getRewardCoin();
		// 获得军团科技收益加成
		coin = human.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.MAIN_CITY_MONSTER, coin);
		human.getWallet().addMoney(CurrencyType.COIN, coin, true,
				MoneyLogReason.MAIN_CITY_MONSTER, "");
		// 减少怪物数量
		human.setMainCityMonsterRemainNum(human.getMainCityMonsterRemainNum() - 1);

		// 返回消息
		sendMonsterInfo();
	}

	@Override
	public void onEvent(IEvent event) {
		// 角色升级，发送城堡盗贼信息
		if (event.getType() == EventType.LEVEL_UP_EVENT) {
			sendMonsterInfo();
		}
	}
}
