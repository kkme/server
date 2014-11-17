package com.hifun.soul.gameserver.mars.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanMarsLoserEntity;
import com.hifun.soul.gamedb.entity.HumanMarsRoomEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.event.MarsKillEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.ConsumeItem;
import com.hifun.soul.gameserver.item.DynamicPropertyType;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.mars.MarsMember;
import com.hifun.soul.gameserver.mars.MarsPlayerType;
import com.hifun.soul.gameserver.mars.MarsRewardState;
import com.hifun.soul.gameserver.mars.MarsRoomLockState;
import com.hifun.soul.gameserver.mars.convert.MarsLoserConverter;
import com.hifun.soul.gameserver.mars.convert.MarsRoomConverter;
import com.hifun.soul.gameserver.mars.msg.info.MarsBetInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo;
import com.hifun.soul.gameserver.mars.template.MarsBetTemplate;
import com.hifun.soul.gameserver.mars.template.MarsRoomTemplate;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.reward.RewardType;
import com.hifun.soul.gameserver.reward.service.RewardService;
import com.hifun.soul.proto.common.Mars.MarsLoser;
import com.hifun.soul.proto.common.Mars.MarsRoom;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsLoser;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsRoom;

/**
 * 角色战神之巅管理器
 * 
 * @author yandajun
 * 
 */
public class HumanMarsManager implements IHumanPersistenceManager,
		ILoginManager, ICachableComponent, IEventListener {
	private Human human;
	private MarsTemplateManager marsTemplateManager;
	private GlobalMarsManager globalMarsManager;
	private GameConstants gameConstants;
	/** 房间类型 - 房间信息 */
	private Map<Integer, MarsRoomInfo> roomInfoMap = new HashMap<Integer, MarsRoomInfo>();
	/** 玩家ID - 遭遇到的玩家信息(原来是手下败将) */
	private Map<Long, MarsPlayerInfo> loserInfoMap = new HashMap<Long, MarsPlayerInfo>();
	/** 房间转换器 */
	private MarsRoomConverter roomConverter;
	/** 手下败将转换器 */
	private MarsLoserConverter loserConverter;
	/** 缓存 */
	private CacheEntry<Integer, IEntity> integerCache = new CacheEntry<Integer, IEntity>();
	private CacheEntry<Long, IEntity> longCache = new CacheEntry<Long, IEntity>();

	public HumanMarsManager(Human human) {
		this.human = human;
		marsTemplateManager = GameServerAssist.getMarsTemplateManager();
		globalMarsManager = GameServerAssist.getGlobalMarsManager();
		gameConstants = GameServerAssist.getGameConstants();
		roomConverter = new MarsRoomConverter(human);
		loserConverter = new MarsLoserConverter(human);
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		this.human.registerLoginManager(this);
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		this.human.getEventBus().addListener(EventType.VIP_LEVEL_CHANGE_EVENT,
				this);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 从数据库中加载房间数据
		for (HumanMarsRoom.Builder builder : humanEntity.getBuilder()
				.getMarsRoomBuilderList()) {
			MarsRoom marsRoom = builder.getMarsRoom();
			MarsRoomInfo marsRoomInfo = new MarsRoomInfo();
			marsRoomInfo.setRoomType(marsRoom.getRoomType());
			marsRoomInfo.setIsLocked(marsRoom.getIsLocked());
			MarsPlayerInfo ownerInfo = new MarsPlayerInfo();
			ownerInfo.setPlayerId(marsRoom.getOwnerId());
			ownerInfo.setOccupationType(marsRoom.getOwnerOccupation());
			ownerInfo.setPlayerLevel(marsRoom.getOwnerLevel());
			ownerInfo.setPlayerName(marsRoom.getOwnerName());
			ownerInfo.setTodayKillValue(marsRoom.getTodayKillValue());
			ownerInfo.setPlayerType(MarsPlayerType.indexOf(marsRoom
					.getOwnerType()));
			marsRoomInfo.setOwnerInfo(ownerInfo);
			roomInfoMap.put(marsRoom.getRoomType(), marsRoomInfo);
		}
		// 从数据库中加载手下败将数据
		for (HumanMarsLoser.Builder builder : humanEntity.getBuilder()
				.getMarsLoserBuilderList()) {
			MarsLoser marsLoser = builder.getMarsLoser();
			MarsPlayerInfo marsLoserInfo = new MarsPlayerInfo();
			marsLoserInfo.setPlayerId(marsLoser.getLoserId());
			marsLoserInfo.setOccupationType(marsLoser.getLoserOccupation());
			marsLoserInfo.setPlayerLevel(marsLoser.getLoserLevel());
			marsLoserInfo.setPlayerName(marsLoser.getLoserName());
			marsLoserInfo.setTodayKillValue(marsLoser.getTodayKillValue());
			marsLoserInfo.setIsLoser(marsLoser.getIsLoser());
			int playerType = marsLoser.getPlayerType();
			if (playerType == 0) {
				playerType = MarsPlayerType.NPC.getIndex();
			}
			marsLoserInfo.setPlayerType(MarsPlayerType.indexOf(playerType));
			loserInfoMap.put(marsLoserInfo.getPlayerId(), marsLoserInfo);
		}
	}

	@Override
	public void onLogin() {
		// 初始化房间数据
		initRoomInfos(false);
		// 发送领奖通知
		sendRewardNotify();
		// 由于可能停服导致全局的战神之巅玩家数据重置，在登陆的时候同步个人数据至全局
		sychronizedDataToGlobal();
	}

	/**
	 * 初始化房间数据
	 */
	private void initRoomInfos(boolean isLevelUp) {
		for (int roomType : marsTemplateManager.getMarsRoomTemplates().keySet()) {
			MarsRoomInfo roomInfo = roomInfoMap.get(roomType);
			// 如果还没有数据，就随机出在线玩家或npc(在线玩家优先)
			if (roomInfo == null) {
				roomInfo = new MarsRoomInfo();
				roomInfo.setRoomType(roomType);
				roomInfo.setIsLocked(MarsRoomLockState.UNLOCKED.getIndex());
				// 房间设置房主
				setOwnerForRoom(roomInfo);
			}
			// 如果是升级
			if (isLevelUp) {
				// 房间重新设置房主
				setOwnerForRoom(roomInfo);
			}
			// 产生杀戮值从房间模板中取
			roomInfo.setProduceKillValue(marsTemplateManager
					.getMarsRoomTemplates().get(roomType).getKillValue());
			// 产生星魂从奖励模板中取
			roomInfo.setProduceStarSoul(marsTemplateManager
					.getMarsBattleRewardTemplate(human.getLevel())
					.getStarSoul());
			// 加倍信息从下注模板中取
			List<MarsBetInfo> betInfoList = new ArrayList<MarsBetInfo>();
			for (int Id : marsTemplateManager.getMarsBetTemplates().keySet()) {
				MarsBetTemplate betTemplate = marsTemplateManager
						.getMarsBetTemplates().get(Id);
				MarsBetInfo betInfo = new MarsBetInfo();
				betInfo.setMultiple(betTemplate.getMultiple());
				betInfo.setCostNum(betTemplate.getCostNum());
				if (human.getVipLevel() >= betTemplate.getVipLevel()) {
					betInfo.setVisible(true);
					betInfoList.add(betInfo);
				}
			}
			roomInfo.setBetInfos(betInfoList.toArray(new MarsBetInfo[0]));
			roomInfoMap.put(roomType, roomInfo);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 保存房间数据
		for (int roomType : roomInfoMap.keySet()) {
			HumanMarsRoomEntity roomEntity = roomConverter.convert(roomInfoMap
					.get(roomType));
			humanEntity.getBuilder().addMarsRoom(roomEntity.getBuilder());
		}
		// 保存遭遇到的玩家数据
		for (long loserId : loserInfoMap.keySet()) {
			HumanMarsLoserEntity loserEntity = loserConverter
					.convert(loserInfoMap.get(loserId));
			humanEntity.getBuilder().addMarsLoser(loserEntity.getBuilder());
		}

	}

	/**
	 * 为房间设置房主
	 */
	public void setOwnerForRoom(MarsRoomInfo roomInfo) {
		int roomType = roomInfo.getRoomType();
		// 优先在线玩家
		MarsPlayerInfo ownerInfo = getPlayerInfoForRoom(roomType);
		if (ownerInfo == null) {
			// 其次npc
			ownerInfo = getNpcInfoForRoom(roomType);
		}
		if (ownerInfo == null) {
			// 都打过了，从遭遇到的玩家中随机取
			int randNum = RandomUtils.nextInt(loserInfoMap.size());
			int i = 0;
			for (long loserId : loserInfoMap.keySet()) {
				if (i == randNum) {
					ownerInfo = loserInfoMap.get(loserId);
					break;
				}
				i++;
			}
		}
		roomInfo.setOwnerInfo(ownerInfo);
		// 更新缓存
		HumanMarsRoomEntity roomEntity = roomConverter.convert(roomInfo);
		integerCache.addUpdate(roomInfo.getRoomType(), roomEntity);
	}

	/**
	 * 为房间获取玩家
	 */
	private MarsPlayerInfo getPlayerInfoForRoom(int roomType) {
		MarsRoomTemplate roomTemplate = marsTemplateManager
				.getMarsRoomTemplate(roomType);
		// 根据房间等级上下限随机在线玩家
		MarsPlayerInfo playerInfo = null;
		List<Human> suitableHumans = new ArrayList<Human>();
		for (Human onlineHuman : GameServerAssist.getGameWorld()
				.getSceneHumanManager().getAllHumans()) {
			// 自己或已推荐的不选择，并且不能与别的房主重复
			if (onlineHuman.getHumanGuid() == human.getHumanGuid()
					|| isInLoserList(onlineHuman.getHumanGuid())
					|| isRoomOwner(onlineHuman.getHumanGuid())) {
				continue;
			}
			if (onlineHuman.getLevel() >= human.getLevel()
					+ roomTemplate.getLowestLevelParam()
					&& onlineHuman.getLevel() <= human.getLevel()
							+ roomTemplate.getHighestLevelParam()) {
				suitableHumans.add(onlineHuman);
			}
		}
		if (suitableHumans.size() != 0) {
			playerInfo = new MarsPlayerInfo();
			Human randomHuman = suitableHumans.get(RandomUtils
					.nextInt(suitableHumans.size()));
			playerInfo.setPlayerType(MarsPlayerType.PLAYER);
			playerInfo.setPlayerId(randomHuman.getHumanGuid());
			playerInfo.setPlayerLevel(randomHuman.getLevel());
			playerInfo
					.setOccupationType(randomHuman.getOccupation().getIndex());
			playerInfo.setPlayerName(randomHuman.getName());
			// 今日杀戮值
			playerInfo.setTodayKillValue(randomHuman.getMarsTodayKillValue());
		}
		return playerInfo;
	}

	/**
	 * 为房间获取npc
	 */
	private MarsPlayerInfo getNpcInfoForRoom(int roomType) {
		MarsRoomTemplate roomTemplate = marsTemplateManager
				.getMarsRoomTemplate(roomType);
		// 根据房间等级上下限随机npc
		int startLevel = human.getLevel() + roomTemplate.getLowestLevelParam();
		int endLevel = human.getLevel() + roomTemplate.getHighestLevelParam();
		MarsPlayerInfo playerInfo = null;
		List<Monster> monsters = GameServerAssist.getMonsterFactory()
				.getMarsNpcs(startLevel, endLevel);
		List<Monster> suitableMonsters = new ArrayList<Monster>();
		for (Monster monster : monsters) {
			if (!isInLoserList(monster.getUnitGuid())) {
				suitableMonsters.add(monster);
			}
		}
		if (suitableMonsters.size() != 0) {
			playerInfo = new MarsPlayerInfo();
			Monster randomMonster = suitableMonsters.get(RandomUtils
					.nextInt(suitableMonsters.size()));
			playerInfo.setPlayerType(MarsPlayerType.NPC);
			playerInfo.setPlayerId(randomMonster.getUnitGuid());
			playerInfo.setPlayerLevel(randomMonster.getLevel());
			playerInfo.setOccupationType(randomMonster.getOccupation()
					.getIndex());
			playerInfo.setPlayerName(randomMonster.getName());
			// 怪物的今日杀戮值为0
			playerInfo.setTodayKillValue(0);
		}
		return playerInfo;
	}

	/**
	 * 是否在今日遭遇到的玩家列表中(原来是手下败将)
	 */
	private boolean isInLoserList(long humanId) {
		for (long loserId : loserInfoMap.keySet()) {
			if (humanId == loserInfoMap.get(loserId).getPlayerId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否已经是房主
	 */
	private boolean isRoomOwner(long humanGuid) {
		for (Integer roomType : roomInfoMap.keySet()) {
			if (roomInfoMap.get(roomType).getOwnerInfo().getPlayerId() == humanGuid) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> list = integerCache.getAllUpdateData();
		list.addAll(longCache.getAllUpdateData());
		return list;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> list = integerCache.getAllDeleteData();
		list.addAll(longCache.getAllDeleteData());
		return list;
	}

	/**
	 * 获取单个房间信息
	 */
	public MarsRoomInfo getRoomInfo(int roomType) {
		return roomInfoMap.get(roomType);
	}

	/**
	 * 获取房间列表
	 */
	public List<MarsRoomInfo> getRoomInfoList() {
		List<MarsRoomInfo> roomInfoList = new ArrayList<MarsRoomInfo>();
		for (int roomType : roomInfoMap.keySet()) {
			roomInfoList.add(roomInfoMap.get(roomType));
		}
		return roomInfoList;
	}

	/**
	 * 获取手下败将列表
	 */
	public List<MarsPlayerInfo> getLoserInfoList() {
		List<MarsPlayerInfo> loserList = new ArrayList<MarsPlayerInfo>();
		for (long loserId : loserInfoMap.keySet()) {
			MarsPlayerInfo loserInfo = loserInfoMap.get(loserId);
			if (loserInfo.getIsLoser() == 1) {
				loserList.add(loserInfo);
			}
		}
		return loserList;
	}

	/**
	 * 击杀战斗结束回调
	 */
	public void killCallBack(int roomType, boolean isWin, int multiple) {
		if (multiple > 1) {
			human.setMarsRemainMultipleNum(human.getMarsRemainMultipleNum() - 1);
		}
		MarsRoomInfo roomInfo = getRoomInfo(roomType);
		if (isWin) {// 成功
			human.setMarsRemainKillNum(human.getMarsRemainKillNum() - 1);
			// 玩家获得星魂、杀戮值、军团贡献(不加倍)；次日领取的金币(乘以杀戮值)
			human.setStarSoul(human.getStarSoul()
					+ roomInfo.getProduceStarSoul() * multiple);
			human.setMarsTodayKillValue(human.getMarsTodayKillValue()
					+ roomInfo.getProduceKillValue() * multiple);
			int addCoin = marsTemplateManager.getMarsBattleRewardTemplate(
					human.getLevel()).getCoin()
					* roomInfo.getProduceKillValue() * multiple;
			// 获得军团科技收益加成
			addCoin = human.getHumanLegionManager().getLegionTechnologyAmend(
					LegionTechnologyType.MARS, addCoin);
			human.setMarsTodayKillCoin(human.getMarsTodayKillCoin() + addCoin);
			// 军团贡献值
			int legionContribution = marsTemplateManager.getMarsRoomTemplate(
					roomInfo.getRoomType()).getLegionContribution();
			GameServerAssist.getGlobalLegionManager().addExperienceByHonor(
					human, legionContribution);
			// 更新全局战神之巅玩家杀戮值
			sychronizedDataToGlobal();

			// 添加手下败将
			MarsPlayerInfo oldOwnerInfo = roomInfo.getOwnerInfo();
			MarsPlayerInfo loserInfo = new MarsPlayerInfo();
			try {
				PropertyUtils.copyProperties(loserInfo, oldOwnerInfo);
				loserInfo.setIsLoser(1);
				loserInfo.setKillTime(GameServerAssist.getSystemTimeService()
						.now());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			addLoser(loserInfo);
			// 推送下一个对手
			setOwnerForRoom(roomInfo);
		} else {// 失败，锁定房间
			roomInfo.setIsLocked(MarsRoomLockState.LOCKED.getIndex());
			// 更新缓存
			HumanMarsRoomEntity roomEntity = roomConverter.convert(roomInfo);
			integerCache.addUpdate(roomInfo.getRoomType(), roomEntity);
		}
		// 发送事件
		human.getEventBus().fireEvent(new MarsKillEvent());
	}

	/**
	 * 同步个人数据至全局
	 */
	private void sychronizedDataToGlobal() {
		MarsMember marsMember = globalMarsManager.getMarsMember(human
				.getHumanGuid());
		if (marsMember == null) {
			return;
		}
		marsMember.setTodayKillValue(human.getMarsTodayKillValue());
		marsMember.setHumanLevel(human.getLevel());
		globalMarsManager.updateMarsMember(marsMember);
	}

	/**
	 * 添加遭遇到的玩家(原来是手下败将）
	 */
	public void addLoser(MarsPlayerInfo loserInfo) {
		loserInfoMap.put(loserInfo.getPlayerId(), loserInfo);
		longCache.addUpdate(loserInfo.getPlayerId(),
				loserConverter.convert(loserInfo));
	}

	/**
	 * 重置战神之巅每日数据
	 */
	public void resetMarsDailyData() {
		human.setMarsBuyMultipleNum(0);
		human.setMarsRemainMultipleNum(gameConstants.getMultipleNum());
		human.setMarsRemainKillNum(gameConstants.getDayKillNum());
		human.setMarsAcceptRewardNum(0);

		// 结算当天领奖数据
		caculateMarsKillReward();
		// 重置今日杀戮值、金币
		human.setMarsTodayKillCoin(0);
		human.setMarsTodayKillValue(0);
		// 重置当日房间
		roomInfoMap.clear();
		initRoomInfos(false);
		// 重置当日遇到的玩家
		loserInfoMap.clear();
	}

	/**
	 * 结算当天领奖数据
	 */
	private void caculateMarsKillReward() {
		human.setMarsRewardCoin(human.getMarsTodayKillCoin());
		if (human.getMarsRewardCoin() != 0) {
			human.setMarsRewardState(MarsRewardState.CAN_GET.getIndex());
		} else {
			human.setMarsRewardState(MarsRewardState.NO_REWARD.getIndex());
		}
	}

	/**
	 * 是否可领取击杀奖励
	 */
	public boolean canGetMarsKillReward() {
		if (human.getMarsRewardState() == MarsRewardState.CAN_GET.getIndex()) {
			return true;
		}
		return false;
	}

	/**
	 * 领取战神之巅击杀奖励
	 */
	public void recieveMarsKillReward() {
		if (!canGetMarsKillReward()) {
			return;
		}
		// 生成奖励物品，携带动态属性
		ConsumeItem rewardItem = (ConsumeItem) ItemFactory.creatNewItem(human,
				ItemConstantId.MARS_KILL_REWARD_ID);
		if (rewardItem == null) {
			return;
		}
		ConsumeItemFeature itemFeature = (ConsumeItemFeature) rewardItem
				.getFeature();
		int coin = human.getMarsRewardCoin();
		itemFeature.setDynamicProperty(DynamicPropertyType.COIN, coin);
		if (human.getBagManager().putItem(BagType.MAIN_BAG, rewardItem,
				ItemLogReason.MARS_KILL_REWARD, "")) {
			human.setMarsRewardState(MarsRewardState.GETTED.getIndex());
			GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(
					human, RewardType.MARS_KILL_REWARD);
		}
	}

	/**
	 * 发送领奖通知
	 */
	public void sendRewardNotify() {
		if (canGetMarsKillReward()) {
			RewardService rewardService = ApplicationContext
					.getApplicationContext().getBean(RewardService.class);
			rewardService.sendAddCommonRewardMessage(human,
					RewardType.MARS_KILL_REWARD);
		}
	}

	@Override
	public void onEvent(IEvent event) {
		// 角色升级
		if (event.getType() == EventType.LEVEL_UP_EVENT) {
			// 重置房间的主人数据
			initRoomInfos(true);
			// 更新战神之巅玩家数据
			sychronizedDataToGlobal();
		}
		// 角色VIP等级变化
		if (event.getType() == EventType.VIP_LEVEL_CHANGE_EVENT) {
			// 重置房间的加倍信息
			initRoomInfos(false);
		}
	}

	/**
	 * 获取剩余接受挑战奖励次数
	 */
	public int getRemainAcceptRewardNum() {
		return gameConstants.getDayReceiveRewardNum()
				- human.getMarsAcceptRewardNum();
	}
}
