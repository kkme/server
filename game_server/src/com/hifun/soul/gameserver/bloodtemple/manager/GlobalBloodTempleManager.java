package com.hifun.soul.gameserver.bloodtemple.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.LogReasons.PrestigeLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.BloodTempleLogEntity;
import com.hifun.soul.gamedb.entity.BloodTempleRoomEntity;
import com.hifun.soul.gamedb.entity.HumanBloodTemplePrestigeEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleLog;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleOwnerType;
import com.hifun.soul.gameserver.bloodtemple.BloodTempleRoom;
import com.hifun.soul.gameserver.bloodtemple.HumanBloodTemplePrestige;
import com.hifun.soul.gameserver.bloodtemple.converter.BloodTempleLogToEntityConverter;
import com.hifun.soul.gameserver.bloodtemple.converter.BloodTempleRoomToInfoConverter;
import com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleEnemyInfo;
import com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo;
import com.hifun.soul.gameserver.bloodtemple.msg.GCExtractBloodTemplePrestige;
import com.hifun.soul.gameserver.bloodtemple.msg.GCOpenBloodTemplePanel;
import com.hifun.soul.gameserver.bloodtemple.msg.GCShowBloodTempleEnemy;
import com.hifun.soul.gameserver.abattoir.msg.WrestlerInfo;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.bloodtemple.converter.BloodTempleRoomToEntityConverter;
import com.hifun.soul.gameserver.bloodtemple.converter.HumanBloodTemplePrestigeToEntityConverter;
import com.hifun.soul.gameserver.bloodtemple.template.BloodTempleRoomTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.title.template.HumanTitleTemplate;

/**
 * 全局嗜血神殿管理器
 * 
 * @author yandajun
 * 
 */
@Scope("singleton")
@Component
public class GlobalBloodTempleManager implements IInitializeRequired,
		ICachableComponent {
	private BloodTempleTemplateManager templateManager;
	/** 房间ID - 房间信息 */
	private Map<Integer, BloodTempleRoom> bloodTempleRooms = new HashMap<Integer, BloodTempleRoom>();
	/** 角色ID - 角色威望 */
	private Map<Long, HumanBloodTemplePrestige> bloodTemplePrestiges = new HashMap<Long, HumanBloodTemplePrestige>();

	private BloodTempleRoomToEntityConverter roomConverter = new BloodTempleRoomToEntityConverter();
	private HumanBloodTemplePrestigeToEntityConverter prestigeConverter = new HumanBloodTemplePrestigeToEntityConverter();
	private BloodTempleLogToEntityConverter logConverter = new BloodTempleLogToEntityConverter();
	@Autowired
	private IDataService dataService;
	@Autowired
	private TimeService timeService;
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Integer, IEntity> cache = new CacheEntry<Integer, IEntity>();
	private CacheEntry<Long, IEntity> longCache = new CacheEntry<Long, IEntity>();

	@Override
	public void init() {
		this.cacheManager.registerOtherCachableComponent(this);
		templateManager = GameServerAssist.getBloodTempleTemplateManager();
	}

	public void start(IDBService dbService) {
		loadBloodTempleRoomList(dbService);
	}

	/**
	 * 加载房间列表
	 */
	private void loadBloodTempleRoomList(IDBService dbService) {
		// 从数据库中获取房间信息
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_BLOOD_TEMPLE_ROOM);
		@SuppressWarnings("unchecked")
		List<BloodTempleRoomEntity> roomEntityList = (List<BloodTempleRoomEntity>) result;
		Map<Integer, BloodTempleRoomEntity> roomEntityMap = new HashMap<Integer, BloodTempleRoomEntity>();
		if (!result.isEmpty()) {
			for (BloodTempleRoomEntity entity : roomEntityList) {
				roomEntityMap.put((Integer) entity.getId(), entity);
			}
		}
		// 加载模板中房间数据
		Map<Integer, BloodTempleRoomTemplate> bloodRoomTemplates = templateManager
				.getBloodTempleRoomTemplates();
		for (Integer roomId : bloodRoomTemplates.keySet()) {
			BloodTempleRoomTemplate roomTemplate = bloodRoomTemplates
					.get(roomId);
			BloodTempleRoom room = new BloodTempleRoom();
			room.setRoomId(roomId);
			room.setRoomName(roomTemplate.getRoomName());
			room.setPageIndex(roomTemplate.getPageIndex());
			room.setRevenue(roomTemplate.getRevenue());
			room.setNpcLevel(roomTemplate.getNpcLevel());
			room.setProtectTime(roomTemplate.getProtectTime());
			BloodTempleRoomEntity entity = roomEntityMap.get(roomId);
			if (entity == null) {// 如果库中信息为空，随机npc信息插入库中
				setNpcForRoom(room);
				dbService.insert(roomConverter.convert(room));
			} else {
				room.setOwnerId(entity.getOwnerId());
				room.setOwnerOccupationType(entity.getOwnerOccupationType());
				room.setOwnerName(entity.getOwnerName());
				room.setOwnerLevel(entity.getOwnerLevel());
				room.setOwnerType(entity.getOwnerType());
				// 如果是玩家，要显示军衔、军团信息
				if (room.getOwnerType() == BloodTempleOwnerType.PLAYER_WRESTLER
						.getIndex()) {
					room.setOwnerTitleId(entity.getOwnerTitleId());
					HumanTitleTemplate titleTemplate = GameServerAssist
							.getTitleTemplateManager().getHumanTitleTemplate(
									entity.getOwnerTitleId());
					if (titleTemplate != null) {
						room.setOwnerTitleName(titleTemplate.getTitleName());
					}
				}
				room.setLootTime(entity.getLootTime());
			}
			bloodTempleRooms.put(roomId, room);
		}
		// 从数据库中获取角色威望信息
		List<?> prestigeResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_BLOOD_TEMPLE_PRESTIGE);
		if (!prestigeResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<HumanBloodTemplePrestigeEntity> prestigeEntityList = (List<HumanBloodTemplePrestigeEntity>) prestigeResult;
			for (HumanBloodTemplePrestigeEntity prestigeEntity : prestigeEntityList) {
				bloodTemplePrestiges.put((Long) prestigeEntity.getId(),
						prestigeConverter.reverseConvert(prestigeEntity));
			}
		}
	}

	/**
	 * 发送面板信息
	 */
	public void sendPanelInfo(Human human) {
		GCOpenBloodTemplePanel msg = new GCOpenBloodTemplePanel();
		WrestlerInfo wrestlerInfo = new WrestlerInfo();
		wrestlerInfo.setTitleId(human.getCurrentTitle());
		String titleName = "";
		if (human.getCurrentTitle() >= 1) {
			titleName = GameServerAssist.getTitleTemplateManager()
					.getHumanTitleTemplate(human.getCurrentTitle())
					.getTitleName();
		}
		wrestlerInfo.setTitleName(titleName);
		wrestlerInfo.setCurrentPrestige(human.getPrestige());
		wrestlerInfo.setMaxPrestige(GameServerAssist.getTitleTemplateManager()
				.getTitleUpNeedPrestige(human.getCurrentTitle()));
		wrestlerInfo.setRemainWrestleNum(human.getBloodTempleRemainNum());
		wrestlerInfo.setNextBuyNumCost(templateManager
				.getBuyWrestleNumCost(human.getBloodTempleBuyNum() + 1));

		BloodTempleRoom humanRoom = getHumanBloodTempleRoom(human
				.getHumanGuid());
		if (humanRoom != null) {// 如果玩家在房间里
			wrestlerInfo.setRoomName(humanRoom.getRoomName());
			wrestlerInfo.setRevenue(humanRoom.getRevenue());
			wrestlerInfo.setRoomId(humanRoom.getRoomId());
			// 在房间中计算累积收益
			wrestlerInfo.setTotalRevenue(caculateRoomRevenue(humanRoom)
					+ getHumanPrestige(human.getHumanGuid()).getPrestige());
			wrestlerInfo.setRoomPageIndex(humanRoom.getPageIndex());
			// 显示玩家所在页的房间
			List<BloodTempleRoomInfo> roomInfoList = new ArrayList<BloodTempleRoomInfo>();
			List<BloodTempleRoom> roomList = getBloodTempleRoomList(humanRoom
					.getPageIndex());
			for (BloodTempleRoom room : roomList) {
				BloodTempleRoomInfo roomInfo = BloodTempleRoomToInfoConverter
						.converter(room);
				roomInfoList.add(roomInfo);
			}
			Collections.sort(roomInfoList);
			msg.setRooms(roomInfoList.toArray(new BloodTempleRoomInfo[0]));
		} else {
			// 不在房间中累积收益
			wrestlerInfo.setTotalRevenue(getHumanPrestige(human.getHumanGuid())
					.getPrestige());
			// 默认显示第一页的房间
			List<BloodTempleRoomInfo> defaultRoomInfoList = new ArrayList<BloodTempleRoomInfo>();
			int pageIndex = SharedConstants.BLOOD_TEMPLE_DEFAULT_PAGE;
			List<BloodTempleRoom> roomList = getBloodTempleRoomList(pageIndex);
			for (BloodTempleRoom room : roomList) {
				BloodTempleRoomInfo roomInfo = BloodTempleRoomToInfoConverter
						.converter(room);
				defaultRoomInfoList.add(roomInfo);
			}
			Collections.sort(defaultRoomInfoList);
			msg.setRooms(defaultRoomInfoList
					.toArray(new BloodTempleRoomInfo[0]));
		}
		msg.setWrestlerInfo(wrestlerInfo);

		human.sendMessage(msg);
	}

	/**
	 * 在房间中计算累积收益
	 */
	private int caculateRoomRevenue(BloodTempleRoom humanRoom) {
		if (humanRoom == null) {
			return 0;
		}
		if (humanRoom.getOwnerType() != BloodTempleOwnerType.PLAYER_WRESTLER
				.getIndex()) {
			return 0;
		}
		long lastExtractTime = getHumanPrestige(humanRoom.getOwnerId())
				.getLastExtractTime();
		int roomRevenue = 0;
		if (lastExtractTime > humanRoom.getLootTime()) {
			roomRevenue = (int) ((timeService.now() - lastExtractTime)
					* humanRoom.getRevenue() / TimeUtils.MIN);
		} else {
			roomRevenue = (int) ((timeService.now() - humanRoom.getLootTime())
					* humanRoom.getRevenue() / TimeUtils.MIN);
		}
		return roomRevenue;
	}

	/**
	 * 获取角色所在的房间
	 */
	public BloodTempleRoom getHumanBloodTempleRoom(long roleId) {
		for (int roomId : bloodTempleRooms.keySet()) {
			BloodTempleRoom room = bloodTempleRooms.get(roomId);
			if (room.getOwnerId() == roleId) {
				return room;
			}
		}
		return null;
	}

	/**
	 * 获取房间
	 */
	public BloodTempleRoom getBloodTempleRoom(int roomId) {
		return bloodTempleRooms.get(roomId);
	}

	/**
	 * 获取页码内的房间
	 */
	public List<BloodTempleRoom> getBloodTempleRoomList(int pageIndex) {
		List<BloodTempleRoom> roomList = new ArrayList<BloodTempleRoom>();
		for (int roomId : bloodTempleRooms.keySet()) {
			BloodTempleRoom room = bloodTempleRooms.get(roomId);
			if (room.getPageIndex() == pageIndex) {
				roomList.add(room);
			}
		}
		return roomList;
	}

	/**
	 * 同npc抢夺回调
	 */
	public void lootNpcCallBack(Human human, IBattleUnit beChallenged,
			int roomId, boolean isWin) {
		human.setBloodTempleRemainNum(human.getBloodTempleRemainNum() - 1);
		// 抢夺的房间
		BloodTempleRoom room = getBloodTempleRoom(roomId);
		room.setFighting(false);
		if (isWin) {
			// 如果角色原先已在房间内
			BloodTempleRoom oldRoom = getHumanBloodTempleRoom(human
					.getHumanGuid());
			if (oldRoom != null) {
				// 执行退出房间的逻辑
				quitRoom(oldRoom);
			}
			// 抢夺的房间信息改变
			room.setOwnerId(human.getHumanGuid());
			room.setOwnerOccupationType(human.getOccupation().getIndex());
			room.setOwnerLevel(human.getLevel());
			room.setOwnerName(human.getName());
			room.setOwnerTitleId(human.getCurrentTitle());
			HumanTitleTemplate titleTemplate = GameServerAssist
					.getTitleTemplateManager().getHumanTitleTemplate(
							human.getCurrentTitle());
			if (titleTemplate != null) {
				room.setOwnerTitleName(titleTemplate.getTitleName());
			}
			room.setLootTime(timeService.now());
			room.setOwnerType(BloodTempleOwnerType.PLAYER_WRESTLER.getIndex());
			updateBloodTempleRoom(room);
			human.sendSuccessMessage(LangConstants.BLOOD_TEMPLE_LOOT_ROOM_SUCCESS);
		} else {
			human.sendImportantMessage(LangConstants.BLOOD_TEMPLE_LOOT_ROOM_FAILED);
		}
		// 发送挑战事件
		human.getHumanBloodTempleManager().fireChallengeEvent();
	}

	/**
	 * 同玩家抢夺回调
	 */
	public void lootPlayerCallBack(Human human, IBattleUnit player, int roomId,
			boolean isWin) {
		human.setBloodTempleRemainNum(human.getBloodTempleRemainNum() - 1);
		// 抢夺的房间
		BloodTempleRoom room = getBloodTempleRoom(roomId);
		room.setFighting(false);
		if (isWin) {
			// 如果角色原先已在房间内，属于切换房间
			BloodTempleRoom oldRoom = getHumanBloodTempleRoom(human
					.getHumanGuid());
			if (oldRoom != null) {
				// 执行退出房间的逻辑
				quitRoom(oldRoom);
			}
			HumanBloodTemplePrestige bloodTemplePrestige = getHumanPrestige(player
					.getUnitGuid());
			// 抢夺过程中，房主有可能 1.还在房间中(无动作；正在抢夺别的房间) 2. 中途退出 3.切换到别的房间
			// 计算原先房主的累积收益
			int roomRevenue = caculateRoomRevenue(room);
			bloodTemplePrestige.setPrestige(roomRevenue
					+ bloodTemplePrestige.getPrestige());
			bloodTemplePrestiges.put(player.getUnitGuid(), bloodTemplePrestige);
			longCache.addUpdate(player.getUnitGuid(),
					prestigeConverter.convert(bloodTemplePrestige));

			// 通知被抢夺的玩家
			Human beLooted = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(player.getUnitGuid());
			BloodTempleRoom beLootedHumanRoom = getHumanBloodTempleRoom(player
					.getUnitGuid());
			// 在线、还在这个房间里
			if (beLooted != null && beLootedHumanRoom != null
					&& beLootedHumanRoom.getRoomId() == roomId) {
				beLooted.sendImportantMessage(LangConstants.BLOOD_TEMPLE_ROOM_BE_LOOTED);
			}

			// 抢夺房间信息改变：房主 + 状态
			room.setOwnerId(human.getHumanGuid());
			room.setOwnerOccupationType(human.getOccupation().getIndex());
			room.setOwnerLevel(human.getLevel());
			room.setOwnerName(human.getName());
			HumanTitleTemplate titleTemplate = GameServerAssist
					.getTitleTemplateManager().getHumanTitleTemplate(
							human.getCurrentTitle());
			if (titleTemplate != null) {
				room.setOwnerTitleName(titleTemplate.getTitleName());
			}
			long now = timeService.now();
			room.setLootTime(now);
			room.setOwnerType(BloodTempleOwnerType.PLAYER_WRESTLER.getIndex());
			updateBloodTempleRoom(room);
			human.sendSuccessMessage(LangConstants.BLOOD_TEMPLE_LOOT_ROOM_SUCCESS);
			// 添加日志
			BloodTempleLog bloodTempleLog = new BloodTempleLog();
			bloodTempleLog.setFirstId(human.getHumanGuid());
			bloodTempleLog.setFirstLevel(human.getLevel());
			bloodTempleLog.setFirstName(human.getName());
			bloodTempleLog.setSecondId(player.getUnitGuid());
			bloodTempleLog.setResult(1);
			bloodTempleLog.setLootTime(now);
			addBloodTempleLog(bloodTempleLog);
			// 如果玩家在线，添加仇人信息
			if (beLooted != null) {
				beLooted.getHumanBloodTempleManager().addEnemyInfo(
						bloodTempleLog);
			}
		} else {
			human.sendImportantMessage(LangConstants.BLOOD_TEMPLE_LOOT_ROOM_FAILED);
		}
		// 发送挑战事件
		human.getHumanBloodTempleManager().fireChallengeEvent();
	}

	/**
	 * 退出房间
	 */
	public void quitRoom(BloodTempleRoom humanRoom) {
		// 计算房主的累积收益
		int roomRevenue = caculateRoomRevenue(humanRoom);
		HumanBloodTemplePrestige bloodTemplePrestige = getHumanPrestige(humanRoom
				.getOwnerId());
		bloodTemplePrestige.setPrestige(roomRevenue
				+ bloodTemplePrestige.getPrestige());
		bloodTemplePrestiges.put(humanRoom.getOwnerId(), bloodTemplePrestige);
		// 重新随机npc给房间
		setNpcForRoom(humanRoom);
		updateBloodTempleRoom(humanRoom);
		longCache.addUpdate(humanRoom.getOwnerId(),
				prestigeConverter.convert(bloodTemplePrestige));
	}

	/**
	 * 为房间随机npc
	 */
	private void setNpcForRoom(BloodTempleRoom room) {
		int npcLevel = room.getNpcLevel();
		List<Monster> monsters = GameServerAssist.getMonsterFactory()
				.getWarriorNpc(npcLevel);
		Monster monster = monsters.get(RandomUtils.nextInt(monsters.size()));
		long npcId = monster.getId();
		room.setOwnerId(npcId);
		room.setOwnerOccupationType(monster.getOccupation().getIndex());
		room.setOwnerName(monster.getName());
		room.setOwnerLevel(monster.getLevel());
		room.setOwnerType(BloodTempleOwnerType.NPC_WRESTLER.getIndex());
		room.setLootTime(timeService.now());
	}

	/**
	 * 提取嗜血神殿威望
	 */
	public void extractBloodTemplePrestige(Human human) {
		// 计算威望
		BloodTempleRoom humanRoom = getHumanBloodTempleRoom(human
				.getHumanGuid());
		int totalRevenue = getHumanPrestige(human.getHumanGuid()).getPrestige();
		if (humanRoom != null) {
			// 在房间中计算累积收益
			totalRevenue += caculateRoomRevenue(humanRoom);
		}
		HumanBloodTemplePrestige bloodTemplePrestige = getHumanPrestige(human
				.getHumanGuid());
		bloodTemplePrestige.setPrestige(0);
		bloodTemplePrestige.setLastExtractTime(timeService.now());
		bloodTemplePrestiges.put(human.getHumanGuid(), bloodTemplePrestige);
		longCache.addUpdate(human.getHumanGuid(),
				prestigeConverter.convert(bloodTemplePrestige));
		human.addPrestige(totalRevenue, true,
				PrestigeLogReason.BLOOD_TEMPLE_EXTRACT, "");
		// 返回消息
		GCExtractBloodTemplePrestige msg = new GCExtractBloodTemplePrestige();
		msg.setCurrentPrestige(human.getPrestige());
		human.sendMessage(msg);
	}

	/**
	 * 获取角色威望
	 */
	private HumanBloodTemplePrestige getHumanPrestige(long humanGuid) {
		if (bloodTemplePrestiges.get(humanGuid) == null) {// 第一次开板子
			HumanBloodTemplePrestige bloodTemplePrestige = new HumanBloodTemplePrestige();
			bloodTemplePrestige.setPrestige(0);
			bloodTemplePrestige.setHumanId(humanGuid);
			bloodTemplePrestige.setLastExtractTime(timeService.now());
			dataService.insert(prestigeConverter.convert(bloodTemplePrestige));
			bloodTemplePrestiges.put(humanGuid, bloodTemplePrestige);
		}
		return bloodTemplePrestiges.get(humanGuid);
	}

	/**
	 * 获取仇人列表
	 */
	public void sendBloodTempleEnemyListInfo(final Human human) {
		dataService.query(
				DataQueryConstants.QUERY_BLOOD_TEMPLE_LOG_BY_HUMAN_ID,
				new String[] { "secondId" },
				new Object[] { human.getHumanGuid() },
				new IDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> result) {
						List<BloodTempleEnemyInfo> enemyInfoList = new ArrayList<BloodTempleEnemyInfo>();
						if (!result.isEmpty()) {
							@SuppressWarnings("unchecked")
							List<BloodTempleLogEntity> logEntityList = (List<BloodTempleLogEntity>) result;
							for (BloodTempleLogEntity entity : logEntityList) {
								BloodTempleEnemyInfo enemyInfo = new BloodTempleEnemyInfo();
								enemyInfo.setEnemyId(entity.getFirstId());
								enemyInfo.setEnemyLevel(entity.getFirstLevel());
								enemyInfo.setEnemyName(entity.getFirstName());
								enemyInfo
										.setLootTimeInterval((int) (timeService
												.now() - entity.getLootTime()));
								enemyInfoList.add(enemyInfo);
							}
						}
						GCShowBloodTempleEnemy msg = new GCShowBloodTempleEnemy();
						msg.setEnemies(enemyInfoList
								.toArray(new BloodTempleEnemyInfo[0]));
						human.sendMessage(msg);
					}

					@Override
					public void onFailed(String errorMsg) {
					}
				});
	}

	/**
	 * 插入嗜血神殿日志
	 */
	private void addBloodTempleLog(BloodTempleLog bloodTempleLog) {
		dataService.insert(logConverter.convert(bloodTempleLog));
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateData = cache.getAllUpdateData();
		updateData.addAll(longCache.getAllUpdateData());
		return updateData;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteData = cache.getAllDeleteData();
		deleteData.addAll(longCache.getAllDeleteData());
		return deleteData;
	}

	/**
	 * 更新房主信息
	 */
	public void updateBloodTempleRoom(BloodTempleRoom room) {
		cache.addUpdate(room.getRoomId(), roomConverter.convert(room));
	}
}
