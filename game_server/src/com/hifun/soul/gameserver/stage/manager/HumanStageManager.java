package com.hifun.soul.gameserver.stage.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanStageDramaEntity;
import com.hifun.soul.gamedb.entity.HumanStageMapStateEntity;
import com.hifun.soul.gamedb.entity.HumanStageStarEntity;
import com.hifun.soul.gamedb.entity.HumanStageStarRewardEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.CollectItemEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.stage.MapPerfectRewardState;
import com.hifun.soul.gameserver.stage.StageDramaStateInfo;
import com.hifun.soul.gameserver.stage.StageMapState;
import com.hifun.soul.gameserver.stage.StageMapStateInfo;
import com.hifun.soul.gameserver.stage.StageMapType;
import com.hifun.soul.gameserver.stage.StageStarRewardState;
import com.hifun.soul.gameserver.stage.TriggerType;
import com.hifun.soul.gameserver.stage.converter.StageDramaInfoToEntityConverter;
import com.hifun.soul.gameserver.stage.converter.StageMapInfoToEntityConverter;
import com.hifun.soul.gameserver.stage.converter.StageStarInfoToEntityConverter;
import com.hifun.soul.gameserver.stage.converter.StageStarRewardInfoToEntityConverter;
import com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo;
import com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo;
import com.hifun.soul.gameserver.stage.model.StageMapInfo;
import com.hifun.soul.gameserver.stage.model.StageSimpleInfo;
import com.hifun.soul.gameserver.stage.model.StageStarRewardItemInfo;
import com.hifun.soul.gameserver.stage.model.StageUnPerfect;
import com.hifun.soul.gameserver.stage.msg.GCEnterBattleScene;
import com.hifun.soul.gameserver.stage.msg.GCGetMapPerfectReward;
import com.hifun.soul.gameserver.stage.msg.GCGetStageStarReward;
import com.hifun.soul.gameserver.stage.msg.GCPassStageReward;
import com.hifun.soul.gameserver.stage.msg.GCShowMapPerfectRewardPanel;
import com.hifun.soul.gameserver.stage.msg.GCStarAndRewardState;
import com.hifun.soul.gameserver.stage.msg.GCUpdatePerfectRewardState;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StagePassRewardTemplate;
import com.hifun.soul.gameserver.stage.template.StageStarRewardTemplate;
import com.hifun.soul.gameserver.stage.template.StageStrongholdTemplate;
import com.hifun.soul.gameserver.stage.template.StageTemplate;
import com.hifun.soul.gameserver.stagestar.StageStarInfo;
import com.hifun.soul.gameserver.stagestar.StageStarType;
import com.hifun.soul.gameserver.turntable.template.ItemRateData;
import com.hifun.soul.proto.common.Stages.StageDramaData;
import com.hifun.soul.proto.common.Stages.StageDramaStateData;
import com.hifun.soul.proto.common.Stages.StageStarData;
import com.hifun.soul.proto.common.Stages.StageStarRewardData;
import com.hifun.soul.proto.data.entity.Entity.HumanStageDrama;
import com.hifun.soul.proto.data.entity.Entity.HumanStageMapState;
import com.hifun.soul.proto.data.entity.Entity.HumanStageReward;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStar;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStarReward;

public class HumanStageManager implements ICachableComponent,
		IHumanPersistenceManager, ILoginManager {
	private Human human;
	/** 地图状态 */
	private Map<Integer, StageMapStateInfo> mapStateInfoMap = new HashMap<Integer, StageMapStateInfo>();
	/** 关卡剧情信息 */
	private Map<Integer, Map<TriggerType, Boolean>> stageDramaStateMap = new HashMap<Integer, Map<TriggerType, Boolean>>();
	/** 关卡星级 */
	private Map<Integer, StageStarInfo> stageStarInfoMap = new HashMap<Integer, StageStarInfo>();
	/** 关卡评星奖励 */
	private Map<Integer, StageStarRewardData.Builder> stageStarRewardMap = new HashMap<Integer, StageStarRewardData.Builder>();
	/** 转化器 */
	private StageMapInfoToEntityConverter mapConverter;
	private StageDramaInfoToEntityConverter dramaConverter;
	private StageStarInfoToEntityConverter starConverter;
	private StageStarRewardInfoToEntityConverter starRewardConverter;
	/** 缓存 */
	private CacheEntry<Integer, StageMapStateInfo> mapCaches = new CacheEntry<Integer, StageMapStateInfo>();
	private CacheEntry<Integer, StageDramaStateInfo> dramaCaches = new CacheEntry<Integer, StageDramaStateInfo>();
	private CacheEntry<Integer, StageStarInfo> starCaches = new CacheEntry<Integer, StageStarInfo>();
	private CacheEntry<Integer, StageStarRewardData.Builder> starRewardCaches = new CacheEntry<Integer, StageStarRewardData.Builder>();
	/** 如果战斗完成之后需要切换地图将nextMapId设置为新地图的id */
	private int nextMapId = -1;
	/** 单个关卡最高评星 */
	private static final int MAX_STAGE_STAR = 5;

	public HumanStageManager(Human human) {
		this.human = human;
		mapConverter = new StageMapInfoToEntityConverter(human);
		dramaConverter = new StageDramaInfoToEntityConverter(human);
		starConverter = new StageStarInfoToEntityConverter(human);
		starRewardConverter = new StageStarRewardInfoToEntityConverter(human);

		this.human.registerCachableManager(this);
		this.human.registerPersistenceManager(this);
		this.human.registerLoginManager(this);
	}

	public Human getHuman() {
		return human;
	}

	public int getNextMapId() {
		return nextMapId;
	}

	public void setNextMapId(int nextMapId) {
		this.nextMapId = nextMapId;
	}

	/** 攻打到的关卡 */
	public int getNextStageId() {
		int nextStageId = human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.NEXT_STAGE_ID);
		return nextStageId > 0 ? nextStageId
				: SharedConstants.STAGE_DEFAULT_STAGEID;
	}

	public void setNextStageId(int nextStageId) {
		human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.NEXT_STAGE_ID, nextStageId);
	}

	/**
	 * 上一次攻打的关卡id,奖励领取完成之后置0
	 * 
	 * @return
	 */
	public int getStageId() {
		return human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.STAGE_ID);
	}

	public void setStageId(int stageId) {
		human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.STAGE_ID, stageId);
	}

	/** 上次关卡评星 */
	public int getLastStageStar() {
		int stageStar = human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.LAST_STAGE_BATTLE_STAR);
		return stageStar > 0 ? stageStar : StageStarType.DefaultStar.getIndex();
	}

	public void setLastStageStar(int lastStageStar) {
		human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.LAST_STAGE_BATTLE_STAR,
						lastStageStar);
	}

	
	/**
	 * 获取地图状态
	 * 
	 * @param mapId
	 * @return
	 */
	public StageMapState getStageMapState(Integer mapId) {
		StageMapStateInfo stageMapStateInfo = mapStateInfoMap.get(mapId);
		StageMapState stageState = null;
		if (stageMapStateInfo != null) {
			stageState = StageMapState.indexOf(stageMapStateInfo.getState());
		}
		if (stageState == null) {
			if (mapId == StageMapType.FOREST.getIndex()) {
				return StageMapState.OPEN;
			} else {
				return StageMapState.UNOPEN;
			}
		}
		return stageState;
	}

	/**
	 * 设置地图的状态
	 * 
	 * @param mapId
	 * @param stageState
	 */
	public void setStageMapState(int mapId, StageMapState stageState) {
		StageMapStateInfo stageMapStateInfo = mapStateInfoMap.get(mapId);
		if (stageMapStateInfo == null) {
			stageMapStateInfo = new StageMapStateInfo();
		}
		stageMapStateInfo.setMapId(mapId);
		stageMapStateInfo.setState(stageState.getIndex());
		mapStateInfoMap.put(mapId, stageMapStateInfo);
		// 同步缓存
		mapCaches.addUpdate(mapId, stageMapStateInfo);
	}

	/**
	 * 关卡剧情是否已经触发
	 * 
	 * @param stageId
	 * @param triggerType
	 * @return
	 */
	public boolean isTrigger(int stageId, TriggerType triggerType) {
		Map<TriggerType, Boolean> stageDramaState = stageDramaStateMap
				.get(stageId);
		if (stageDramaState == null) {
			return false;
		} else {
			Boolean state = stageDramaState.get(triggerType);
			if (state == null) {
				return false;
			}
			return state.booleanValue();
		}
	}

	/**
	 * 触发剧情
	 * 
	 * @param stageId
	 * @param triggerType
	 */
	public void triggerDrama(int stageId, TriggerType triggerType) {
		Map<TriggerType, Boolean> stageDramaState = stageDramaStateMap
				.get(stageId);
		if (stageDramaState == null) {
			stageDramaState = new HashMap<TriggerType, Boolean>();
		}
		stageDramaState.put(triggerType, true);

		stageDramaStateMap.put(stageId, stageDramaState);

		// 同步缓存
		StageDramaStateInfo stageDramaStateInfo = new StageDramaStateInfo();
		stageDramaStateInfo.setStageId(stageId);
		stageDramaStateInfo.setDramas(stageDramaState);
		dramaCaches.addUpdate(stageId, stageDramaStateInfo);
	}

	/**
	 * 领取完奖品之后清空战斗信息
	 * 
	 */
	public void clearBattleInfo() {
		setStageId(0);
	}

	/**
	 * 是否可以查看关卡详细信息
	 * 
	 * @param stageId
	 * @return
	 */
	public boolean canSeeStage(int stageId) {
		return getNextStageId() >= stageId;
	}

	/**
	 * 是否可以攻击关卡
	 * 
	 * @param stageId
	 * @return
	 */
	public boolean canAttackStage(int stageId) {
		// 是否已经打过之前的关卡
		if (getNextStageId() < stageId) {
			human.sendErrorMessage(LangConstants.STAGE_NOT_OPEN);
			return false;
		}
		// 是否有足够的精力值
		if (human.getEnergy() < SharedConstants.STAGE_ENERGY_NUM) {
			human.sendErrorMessage(LangConstants.ENERGY_NOT_ENOUGH);
			return false;
		}

		return true;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	/**
	 * 关卡评星
	 * 
	 * @param stageId
	 * @return
	 */
	public int getStageStar(int stageId) {
		StageStarInfo stageStarInfo = stageStarInfoMap.get(stageId);
		if (stageStarInfo == null) {
			return StageStarType.DefaultStar.getIndex();
		}

		return stageStarInfo.getStageStar();
	}

	/**
	 * 更新关卡评星
	 * 
	 * @param stageStarInfo
	 */
	public void updateStageStar(StageStarInfo stageStarInfo) {
		if (stageStarInfo == null) {
			return;
		}
		// 设置上一次关卡评星
		setLastStageStar(stageStarInfo.getStageStar());
		// 判断是否需要修改关卡最高评星等级
		StageStarInfo oldStageStarInfo = stageStarInfoMap.get(stageStarInfo
				.getStageId());
		if (oldStageStarInfo != null
				&& oldStageStarInfo.getStageStar() > stageStarInfo
						.getStageStar()) {
			return;
		}
		// 星级差
		int oldStar = 0;
		if (oldStageStarInfo != null) {
			oldStar = oldStageStarInfo.getStageStar();
		}
		int addStar = stageStarInfo.getStageStar() - oldStar;
		addStar = addStar > 0 ? addStar : 0;
		// 天赋点增加
		human.getHumanGiftManager().addGiftPointByStar(addStar);
		stageStarInfoMap.put(stageStarInfo.getStageId(), stageStarInfo);
		// 更新缓存
		starCaches.addUpdate(stageStarInfo.getStageId(), stageStarInfo);
		// 判断评星的变化是否会带来关卡评星奖励的状态变化
		int totalStar = getTotalStar();
		GCStarAndRewardState gcMsg = new GCStarAndRewardState();
		gcMsg.setTotalStar(totalStar);
		if ((totalStar - addStar) >= GameServerAssist.getStageTemplateManager()
				.getMaxStageStarRewardNum()) {
			gcMsg.setCanGet(false);
		}
		// 判断此次变化是否正好经过某个关卡评星的点
		else {
			StageStarRewardData.Builder stageStarRewardData = getNextStageStarRewardType(GameServerAssist
					.getStageTemplateManager().getStageStarRewardTypes());
			if (stageStarRewardData == null) {
				gcMsg.setCanGet(false);
			} else if (totalStar >= stageStarRewardData.getStar()) {
				gcMsg.setCanGet(true);
			} else {
				gcMsg.setCanGet(false);
			}
			if (stageStarRewardData != null
					&& (totalStar - addStar) < stageStarRewardData.getStar()
					&& totalStar >= stageStarRewardData.getStar()) {
				stageStarRewardData.setRewardState(StageStarRewardState.CANGET
						.getIndex());
				stageStarRewardMap.put(stageStarRewardData.getStar(),
						stageStarRewardData);
				starRewardCaches.addUpdate(stageStarRewardData.getStar(),
						stageStarRewardData);
			}
		}
		human.sendMessage(gcMsg);
	}

	/**
	 * 获取关卡奖励
	 * 
	 * @param stageTemplateManager
	 * @param stageTemplate
	 * @param stageId
	 * @param itemId
	 */
	public void getStageReward(StageTemplateManager stageTemplateManager,
			StageTemplate stageTemplate, float rewardRate, boolean giveItem,
			int itemId) {
		float revenueRate = human.getHumanAntiIndulgeManager().getRevenueRate();
		// 加钱
		int money = stageTemplate.getRewardCurrencyNum();
		money = (int) (money * revenueRate * rewardRate);
		money = human.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.STAGE, money);
		human.getWallet().addMoney(CurrencyType.COIN, money, true,
				MoneyLogReason.BATTLE_REWARD, "");
		// 加经验
		int exp = (int) (stageTemplate.getRewardExperience() * revenueRate * rewardRate);
		human.addExperience(exp, true, ExperienceLogReason.BATTLE_ADD_EXP, "");
		// 给物品
		if (revenueRate > 0 && giveItem) {
			// 发送收集物品事件
			CollectItemEvent collectItemEvent = new CollectItemEvent();
			collectItemEvent.setItemId(itemId);
			human.getEventBus().fireEvent(collectItemEvent);
			human.getBagManager().putItems(BagType.MAIN_BAG, itemId, 1,
					ItemLogReason.BATTLE_REWARD, "");

		}
	}

	/**
	 * 更新战斗场景
	 * 
	 * @param stageTemplateManager
	 * @param mapId
	 */
	public void updateBattleScene(StageTemplateManager stageTemplateManager,
			int mapId) {
		StageMapInfo stageMapInfo = stageTemplateManager.getStageMapInfo(mapId);
		if (stageMapInfo == null) {
			return;
		}
		stageMapInfo.setPassCount(stageTemplateManager.getPassStageCount(
				getNextStageId(), mapId));
		stageMapInfo.setTotalCount(stageTemplateManager.getStageCount(mapId));
		StageMapState stageMapState = getStageMapState(mapId);
		stageMapInfo.setMapState(stageMapState.getIndex());
		// 请求进入征战场景
		GCEnterBattleScene gcEnterBattleScene = new GCEnterBattleScene();
		gcEnterBattleScene.setStageMapInfo(stageMapInfo);
		// 下发地图据点信息
		List<StageStrongholdTemplate> holdTemplates = stageTemplateManager
				.getMapStrongholdListByMapId(mapId);
		List<MapStrongHoldInfo> holdInfos = new ArrayList<MapStrongHoldInfo>();
		// 获取当前关卡模版
		int nextStageId = this.getNextStageId();
		StageTemplate stageTemplate = stageTemplateManager
				.getStageTemplate(nextStageId);
		for (StageStrongholdTemplate each : holdTemplates) {
			MapStrongHoldInfo eachInfo = each.toInfo();
			holdInfos.add(eachInfo);
			// 设置状态
			if (eachInfo.getStrongholdId() <= stageTemplate.getStrongholdId()) {
				eachInfo.setIsOpen(true);
			} else {
				eachInfo.setIsOpen(false);
			}
		}
		gcEnterBattleScene.setMapStrongHoldInfos(holdInfos
				.toArray(new MapStrongHoldInfo[0]));
		human.sendMessage(gcEnterBattleScene);
		// 更新完美通关奖励领取状态
		updatePerfectRewardState();
	}

	/**
	 * 返回玩家下一个需要领取的评星奖励的星星数量
	 * 
	 * @param stageStarRewardTypes
	 * @return
	 */
	public StageStarRewardData.Builder getNextStageStarRewardType(
			Integer[] stageStarRewardTypes) {
		for (Integer star : stageStarRewardTypes) {
			StageStarRewardData.Builder stageStarRewardData = stageStarRewardMap
					.get(star);
			if (stageStarRewardData == null) {
				stageStarRewardData = StageStarRewardData.newBuilder();
				stageStarRewardData.setStar(star).setRewardState(
						StageStarRewardState.NOGET.getIndex());
				if (getTotalStar() >= star) {
					stageStarRewardData
							.setRewardState(StageStarRewardState.CANGET
									.getIndex());
				}
				stageStarRewardMap.put(star, stageStarRewardData);
				starRewardCaches.addUpdate(star, stageStarRewardData);
			}
			if (stageStarRewardData.getRewardState() != StageStarRewardState.GETTED
					.getIndex()) {
				return stageStarRewardData;
			}
		}
		return null;
	}

	/**
	 * 更新关卡评星奖励的状态
	 * 
	 * @param star
	 * @param stageStarRewardState
	 */
	public void updateStageStarRewardState(Integer star,
			Integer stageStarRewardState) {
		StageStarRewardData.Builder stageStarRewardData = stageStarRewardMap
				.get(star);
		if (stageStarRewardData == null) {
			stageStarRewardData = StageStarRewardData.newBuilder();
			stageStarRewardData.setStar(star).setRewardState(
					stageStarRewardState);
			stageStarRewardMap.put(star, stageStarRewardData);
			starRewardCaches.addUpdate(star, stageStarRewardData);
		} else {
			stageStarRewardData.setRewardState(stageStarRewardState);
			stageStarRewardMap.put(star, stageStarRewardData);
			starRewardCaches.addUpdate(star, stageStarRewardData);
		}
	}

	/**
	 * 玩家获取关卡星星总量
	 * 
	 * @return
	 */
	public int getTotalStar() {
		int star = 0;
		for (StageStarInfo stageStarInfo : stageStarInfoMap.values()) {
			star += stageStarInfo.getStageStar();
		}
		return star;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 关卡奖励
		for (HumanStageReward.Builder builder : humanEntity.getBuilder()
				.getStageRewardBuilderList()) {
			ItemRateData itemRateDate = new ItemRateData();
			itemRateDate.setItemId(builder.getItemRateData().getItemId());
			itemRateDate.setRate(builder.getItemRateData().getRate());
			itemRateDate.setIndex(builder.getItemRateData().getIndex());
			itemRateDate.setSelected(builder.getItemRateData().getIsSelected());
		}
		// 关卡地图状态
		for (HumanStageMapState.Builder builder : humanEntity.getBuilder()
				.getStageMapStateBuilderList()) {
			StageMapStateInfo stageMapStateInfo = new StageMapStateInfo();
			stageMapStateInfo.setMapId(builder.getStageMapStateData()
					.getMapId());
			stageMapStateInfo.setState(builder.getStageMapStateData()
					.getState());
			stageMapStateInfo.setPerfectMapRewardState(builder
					.getStageMapStateData().getPerfectMapRewardState());
			mapStateInfoMap
					.put(stageMapStateInfo.getMapId(), stageMapStateInfo);
		}
		// 关卡剧情
		for (HumanStageDrama.Builder builder : humanEntity.getBuilder()
				.getStageDramaBuilderList()) {
			StageDramaData stageDramaData = builder.getStageDramaData();
			Map<TriggerType, Boolean> stageDramaState = new HashMap<TriggerType, Boolean>();
			for (StageDramaStateData dramaState : stageDramaData
					.getDramaStateList()) {
				stageDramaState.put(
						TriggerType.indexOf(dramaState.getTriggerType()),
						dramaState.getTriggerState());
			}
			stageDramaStateMap
					.put(stageDramaData.getStageId(), stageDramaState);
		}
		// 关卡星级
		for (HumanStageStar.Builder builder : humanEntity.getBuilder()
				.getStageStarBuilderList()) {
			StageStarData stageStarData = builder.getStageStarData();
			StageStarInfo stageStarInfo = new StageStarInfo();
			stageStarInfo.setStageId(stageStarData.getStageId());
			stageStarInfo.setStageStar(stageStarData.getStageStar());
			stageStarInfoMap.put(stageStarInfo.getStageId(), stageStarInfo);
		}
		// 关卡评星建立
		for (HumanStageStarReward.Builder builder : humanEntity.getBuilder()
				.getStageStarRewardBuilderList()) {
			StageStarRewardData.Builder stageStarRewardData = builder
					.getStageStarRewardDataBuilder();
			stageStarRewardMap.put(stageStarRewardData.getStar(),
					stageStarRewardData);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {

		// 关卡地图状态
		for (StageMapStateInfo stageMapStateInfo : mapStateInfoMap.values()) {
			humanEntity.getBuilder().addStageMapState(
					this.mapConverter.convert(stageMapStateInfo).getBuilder());
		}
		// 关卡剧情
		for (Integer stageId : stageDramaStateMap.keySet()) {
			HumanStageDrama.Builder builder = HumanStageDrama.newBuilder();
			builder.setHumanGuid(human.getHumanGuid());
			StageDramaStateInfo stageDramaStateInfo = new StageDramaStateInfo();
			stageDramaStateInfo.setStageId(stageId);
			stageDramaStateInfo.setDramas(stageDramaStateMap.get(stageId));
			humanEntity.getBuilder().addStageDrama(
					this.dramaConverter.convert(stageDramaStateInfo)
							.getBuilder());
		}
		// 关卡评星
		for (StageStarInfo stageStarInfo : stageStarInfoMap.values()) {
			humanEntity.getBuilder().addStageStar(
					this.starConverter.convert(stageStarInfo).getBuilder());
		}
		// 关卡评星奖励
		for (StageStarRewardData.Builder stageStarRewardInfo : stageStarRewardMap
				.values()) {
			humanEntity.getBuilder().addStageStarReward(
					this.starRewardConverter.convert(stageStarRewardInfo)
							.getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (StageMapStateInfo stageMapStateInfo : mapCaches.getAllUpdateData()) {
			HumanStageMapStateEntity entity = this.mapConverter
					.convert(stageMapStateInfo);
			updateList.add(entity);
		}

		for (StageDramaStateInfo stageDramaStateInfo : dramaCaches
				.getAllUpdateData()) {
			HumanStageDramaEntity entity = this.dramaConverter
					.convert(stageDramaStateInfo);
			updateList.add(entity);
		}
		for (StageStarInfo stageStarInfo : starCaches.getAllUpdateData()) {
			HumanStageStarEntity entity = this.starConverter
					.convert(stageStarInfo);
			updateList.add(entity);
		}
		for (StageStarRewardData.Builder stageStarRewardInfo : starRewardCaches
				.getAllUpdateData()) {
			HumanStageStarRewardEntity entity = this.starRewardConverter
					.convert(stageStarRewardInfo);
			updateList.add(entity);
		}

		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	@Override
	public void onLogin() {
		// 下发星星总量
		GCStarAndRewardState gcMsg = new GCStarAndRewardState();
		gcMsg.setTotalStar(getTotalStar());
		gcMsg.setCanGet(false);
		human.sendMessage(gcMsg);
		// 更新关卡评星奖励状态
		updateStageStarRewardInfos();
		// 更新完美通关奖励领取状态
		updatePerfectRewardState();
	}

	/**
	 * 更新关卡评星奖励状态
	 */
	public void updateStageStarRewardInfos() {
		GCGetStageStarReward gcMsg = new GCGetStageStarReward();
		StageStarRewardData.Builder nextStageStarRewardData = getNextStageStarRewardType(GameServerAssist
				.getStageTemplateManager().getStageStarRewardTypes());
		if (nextStageStarRewardData == null) {
			gcMsg.setHasNextReward(false);
			gcMsg.setStar(0);
			gcMsg.setCanGet(false);
			gcMsg.setStageStarRewardItemInfos(new StageStarRewardItemInfo[0]);
		} else {
			gcMsg.setHasNextReward(true);
			int nextStar = nextStageStarRewardData.getStar();
			boolean canGet = getTotalStar() >= nextStageStarRewardData
					.getStar();
			StageStarRewardTemplate nextStageStarRewardTemplate = GameServerAssist
					.getStageTemplateManager().getStageStarRewardTemplate(
							nextStar);
			gcMsg.setStar(nextStar);
			gcMsg.setCanGet(canGet);
			gcMsg.setStageStarRewardItemInfos(nextStageStarRewardTemplate
					.getStageStarRewardItemInfos().toArray(
							new StageStarRewardItemInfo[0]));
		}
		human.sendMessage(gcMsg);
	}

	/**
	 * 更新所有地图的完美通关奖励信息
	 */
	private void updatePerfectRewardState() {
		List<PerfectMapRewardStateInfo> perfectMapRewardStateInfos = new ArrayList<PerfectMapRewardStateInfo>();
		for (StageMapType stageMapType : StageMapType.values()) {
			PerfectMapRewardStateInfo perfectMapRewardStateInfo = new PerfectMapRewardStateInfo();
			perfectMapRewardStateInfo.setMapId(stageMapType.getIndex());
			// check一下状态
			checkPerfectRewardState(getStageMapStateInfo(stageMapType
					.getIndex()));
			perfectMapRewardStateInfo
					.setPerfectRewardState(getStageMapStateInfo(
							stageMapType.getIndex()).getPerfectMapRewardState());
			perfectMapRewardStateInfos.add(perfectMapRewardStateInfo);
		}
		GCUpdatePerfectRewardState gcMsg = new GCUpdatePerfectRewardState();
		gcMsg.setPerfectMapRewardStateInfos(perfectMapRewardStateInfos
				.toArray(new PerfectMapRewardStateInfo[0]));
		human.sendMessage(gcMsg);
	}

	/**
	 * 更新完美通关奖励的领取状态
	 * 
	 * @param mapId
	 * @param perfectMapRewardState
	 */
	private void updateMapPerfectRewardState(int mapId,
			int perfectMapRewardState) {
		StageMapStateInfo stageMapStateInfo = getStageMapStateInfo(mapId);
		stageMapStateInfo.setPerfectMapRewardState(perfectMapRewardState);
		mapStateInfoMap.put(mapId, stageMapStateInfo);
		// 同步到缓存
		mapCaches.addUpdate(mapId, stageMapStateInfo);
	}

	/**
	 * 地图奖励信息
	 * 
	 * @param mapId
	 * @return
	 */
	private StageMapStateInfo getStageMapStateInfo(int mapId) {
		StageMapStateInfo stageMapStateInfo = mapStateInfoMap.get(mapId);
		if (stageMapStateInfo == null) {
			stageMapStateInfo = new StageMapStateInfo();
			stageMapStateInfo.setMapId(mapId);
			stageMapStateInfo.setState(getStageMapState(mapId).getIndex());
			mapStateInfoMap.put(mapId, stageMapStateInfo);
			// 同步到缓存
			mapCaches.addUpdate(mapId, stageMapStateInfo);
		}
		return stageMapStateInfo;
	}

	/**
	 * check一下是否可以领取奖励
	 * 
	 * @param stageMapStateInfo
	 * @return
	 */
	private boolean checkPerfectRewardState(StageMapStateInfo stageMapStateInfo) {
		// 领取过直接返回false
		if (stageMapStateInfo != null
				&& stageMapStateInfo.getPerfectMapRewardState() == MapPerfectRewardState.GETTED
						.getIndex()) {
			return false;
		}
		boolean canGet = false;
		if (stageMapStateInfo.getPerfectMapRewardState() != MapPerfectRewardState.CANGET
				.getIndex()) {
			// 获得该地图所有关卡信息
			List<StageSimpleInfo> stageSimpleInfos = GameServerAssist
					.getStageTemplateManager().getStageSimpleInfos(0,
							stageMapStateInfo.getMapId());
			canGet = true;
			// 判断是否所有关卡都已经满星
			// editby: crazyjohn SB这代码有bug，现在数据不是从人身上取出来的，而是从模版里取得
			for (StageSimpleInfo stageSimpleInfo : stageSimpleInfos) {
				int eachStar = this.getStageStar(stageSimpleInfo.getStageId());
				if (eachStar < HumanStageManager.MAX_STAGE_STAR) {
					canGet = false;
					break;
				}
			}
			// 可以领取时变更状态
			if (canGet) {
				stageMapStateInfo
						.setPerfectMapRewardState(MapPerfectRewardState.CANGET
								.getIndex());
				mapStateInfoMap.put(stageMapStateInfo.getMapId(),
						stageMapStateInfo);
				// 同步到缓存
				mapCaches.addUpdate(stageMapStateInfo.getMapId(),
						stageMapStateInfo);
			}
		} else {
			canGet = true;
		}

		return canGet;
	}

	/**
	 * 更新完美通关奖励的面板
	 */
	public void updateMapPerfectRewardPanel(int mapId) {
		StagePassRewardTemplate stagePassRewardTemplate = GameServerAssist
				.getStageTemplateManager().getStagePassRewardTemplate(mapId);
		if (stagePassRewardTemplate == null) {
			return;
		}
		// 判断是否已经征战到当前地图
		StageMapState stageMapState = getStageMapState(mapId);
		if (stageMapState == StageMapState.UNOPEN) {
			return;
		}
		// 判断是否已经领取过当前地图的完美通关奖励
		StageMapStateInfo stageMapStateInfo = getStageMapStateInfo(mapId);
		if (stageMapStateInfo.getPerfectMapRewardState() == MapPerfectRewardState.GETTED
				.getIndex()) {
			return;
		}
		// 是否可以领取完美通关奖励
		boolean canGet = checkPerfectRewardState(stageMapStateInfo);
		// 没有满分的关卡信息
		List<StageUnPerfect> unPerfectStages = new ArrayList<StageUnPerfect>();
		List<StageSimpleInfo> stageSimpleInfos = GameServerAssist
				.getStageTemplateManager().getStageSimpleInfos(0,
						stageMapStateInfo.getMapId());
		for (StageSimpleInfo eachInfo : stageSimpleInfos) {
			int eachStar = this.getStageStar(eachInfo.getStageId());
			if (eachStar < MAX_STAGE_STAR) {
				StageUnPerfect unPerfectStage = new StageUnPerfect();
				unPerfectStage.setStageId(eachInfo.getStageId());
				unPerfectStage.setPass(eachInfo.getPass());
				unPerfectStage.setStageName(unPerfectStage.getStageName());
				unPerfectStage.setStar(eachStar);
				unPerfectStages.add(unPerfectStage);
			}
		}

		// 获取到当前征战地图奖励信息
		List<StageStarRewardItemInfo> stageStarRewardItemInfo = stagePassRewardTemplate
				.getPerfectRewardItemInfos();
		// 下发响应消息
		GCShowMapPerfectRewardPanel gcMsg = new GCShowMapPerfectRewardPanel();
		gcMsg.setMapId(mapId);
		gcMsg.setCanGet(canGet);
		gcMsg.setUnPerfectStages(unPerfectStages.toArray(new StageUnPerfect[0]));
		gcMsg.setMapPerfectRewardItemInfos(stageStarRewardItemInfo
				.toArray(new StageStarRewardItemInfo[0]));
		human.sendMessage(gcMsg);
		// 更新完美通关奖励领取状态
		updatePerfectRewardState();
	}

	/**
	 * 领取某张地图的完美通关奖励
	 * 
	 * @param mapId
	 */
	public void getMapPerfectReward(int mapId) {
		StagePassRewardTemplate stagePassRewardTemplate = GameServerAssist
				.getStageTemplateManager().getStagePassRewardTemplate(mapId);
		if (stagePassRewardTemplate == null) {
			return;
		}
		// 判断当前地图的完美通关奖励是否已经领取过
		StageMapStateInfo stageMapStateInfo = getStageMapStateInfo(mapId);
		if (stageMapStateInfo.getPerfectMapRewardState() == MapPerfectRewardState.GETTED
				.getIndex()) {
			return;
		}
		// 判断当前完美通关奖励是否已经到可以领取的状态
		boolean canGet = checkPerfectRewardState(stageMapStateInfo);
		if (!canGet) {
			return;
		}
		// 判断是否有足够的背包格子
		HumanBagManager bagManager = human.getBagManager();
		List<StageStarRewardItemInfo> stageStarRewardItemInfos = stagePassRewardTemplate
				.getPerfectRewardItemInfos();
		if (bagManager.getFreeSize(BagType.MAIN_BAG) < stageStarRewardItemInfos
				.size()) {
			human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}
		// 设置领取状态
		updateMapPerfectRewardState(mapId,
				MapPerfectRewardState.GETTED.getIndex());
		// 给玩家奖励
		for (StageStarRewardItemInfo stageStarRewardItemInfo : stageStarRewardItemInfos) {
			bagManager.putItems(BagType.MAIN_BAG,
					stageStarRewardItemInfo.getItemId(),
					stageStarRewardItemInfo.getItemNum(),
					ItemLogReason.PERFECT_MAP_REWARD, "");
		}
		// 下发响应消息
		GCGetMapPerfectReward gcMsg = new GCGetMapPerfectReward();
		gcMsg.setMapId(mapId);
		gcMsg.setState(MapPerfectRewardState.GETTED.getIndex());
		human.sendMessage(gcMsg);
	}

	/**
	 * 通关奖励信息
	 * 
	 * @param mapId
	 */
	public void sendPassStageRewardInfo(int mapId) {
		StagePassRewardTemplate stagePassRewardTemplate = GameServerAssist
				.getStageTemplateManager().getStagePassRewardTemplate(mapId);
		if (stagePassRewardTemplate == null) {
			return;
		}
		SimpleCommonItem item1 = CommonItemBuilder
				.genSimpleCommonItem(stagePassRewardTemplate.getItem1Id());
		SimpleCommonItem item2 = CommonItemBuilder
				.genSimpleCommonItem(stagePassRewardTemplate.getItem2Id());
		if (item1 == null || item2 == null) {
			return;
		}
		GCPassStageReward gcPassStageReward = new GCPassStageReward();
		gcPassStageReward.setMapId(mapId);
		gcPassStageReward.setCoin(stagePassRewardTemplate.getCoin());
		gcPassStageReward.setItem1(item1);
		gcPassStageReward.setItem1Num(stagePassRewardTemplate.getItem1Num());
		gcPassStageReward.setItem2(item2);
		gcPassStageReward.setItem2Num(stagePassRewardTemplate.getItem2Num());
		human.sendMessage(gcPassStageReward);
	}
}
