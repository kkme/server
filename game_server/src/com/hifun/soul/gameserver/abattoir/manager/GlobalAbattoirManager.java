package com.hifun.soul.gameserver.abattoir.manager;

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
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.AbattoirLogEntity;
import com.hifun.soul.gamedb.entity.AbattoirRoomEntity;
import com.hifun.soul.gamedb.entity.HumanAbattoirPrestigeEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.abattoir.AbattoirLog;
import com.hifun.soul.gameserver.abattoir.AbattoirOwnerType;
import com.hifun.soul.gameserver.abattoir.AbattoirRoom;
import com.hifun.soul.gameserver.abattoir.HumanAbattoirPrestige;
import com.hifun.soul.gameserver.abattoir.converter.AbattoirLogToEntityConverter;
import com.hifun.soul.gameserver.abattoir.converter.AbattoirRoomToEntityConverter;
import com.hifun.soul.gameserver.abattoir.converter.AbattoirRoomToInfoConverter;
import com.hifun.soul.gameserver.abattoir.converter.HumanAbattoirPrestigeToEntityConverter;
import com.hifun.soul.gameserver.abattoir.msg.AbattoirEnemyInfo;
import com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo;
import com.hifun.soul.gameserver.abattoir.msg.GCExtractAbattoirPrestige;
import com.hifun.soul.gameserver.abattoir.msg.GCOpenAbattoirPanel;
import com.hifun.soul.gameserver.abattoir.msg.GCShowAbattoirEnemy;
import com.hifun.soul.gameserver.abattoir.msg.WrestlerInfo;
import com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemp;
import com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemplate;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;

/**
 * 全局角斗场管理器
 * 
 * @author yandajun
 * 
 */
@Scope("singleton")
@Component
public class GlobalAbattoirManager implements IInitializeRequired,
		ICachableComponent {
	private AbattoirTemplateManager templateManager;
	/** 房间ID - 房间信息 */
	private Map<Integer, AbattoirRoom> abattoirRooms = new HashMap<Integer, AbattoirRoom>();
	/** 角色ID - 角色威望 */
	private Map<Long, HumanAbattoirPrestige> abattoirPrestiges = new HashMap<Long, HumanAbattoirPrestige>();

	private AbattoirRoomToEntityConverter roomConverter = new AbattoirRoomToEntityConverter();
	private HumanAbattoirPrestigeToEntityConverter prestigeConverter = new HumanAbattoirPrestigeToEntityConverter();
	private AbattoirLogToEntityConverter logConverter = new AbattoirLogToEntityConverter();
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
		templateManager = GameServerAssist.getAbattoirTemplateManager();
	}

	public void start(IDBService dbService) {
		loadAbattoirRoomList(dbService);
	}

	/**
	 * 加载房间列表
	 */
	private void loadAbattoirRoomList(IDBService dbService) {
		// 从数据库中获取房间信息
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_ABATTOIR_ROOM);
		Map<Integer, AbattoirRoomEntity> roomEntityMap = new HashMap<Integer, AbattoirRoomEntity>();
		@SuppressWarnings("unchecked")
		List<AbattoirRoomEntity> roomEntityList = (List<AbattoirRoomEntity>) result;
		if (!roomEntityList.isEmpty()) {
			for (AbattoirRoomEntity entity : roomEntityList) {
				roomEntityMap.put((Integer) entity.getId(), entity);
			}
		}
		// 加载模板中数据
		Map<Integer, AbattoirRoomTemp> abattoirRoomTemps = templateManager
				.getAbattoirRoomTemps();
		for (Integer roomId : abattoirRoomTemps.keySet()) {
			AbattoirRoomTemp roomTemp = abattoirRoomTemps.get(roomId);
			AbattoirRoom room = new AbattoirRoom();
			room.setRoomId(roomId);
			room.setRoomName(roomTemp.getRoomName());
			room.setRevenue(roomTemp.getRevenue());
			AbattoirRoomTemplate template = templateManager
					.getAbattoirRoomTemplate(roomId);
			room.setTimeLimit(template.getTimeLimit());
			room.setProtectTime(template.getProtectTime());
			AbattoirRoomEntity entity = roomEntityMap.get(roomId);
			if (entity == null) {// 首次加载，插入库中
				setNpcForRoom(room);
				dbService.insert(roomConverter.convert(room));
			} else {
				room.setOwnerId(entity.getOwnerId());
				room.setOwnerName(entity.getOwnerName());
				room.setOwnerOccupationType(entity.getOwnerOccupationType());
				room.setOwnerLevel(entity.getOwnerLevel());
				room.setLootTime(entity.getLootTime());
				room.setOwnerType(entity.getOwnerType());
			}
			abattoirRooms.put(roomId, room);
		}

		// 从数据库中获取角色威望信息
		List<?> prestigeResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_ABATTOIR_PRESTIGE);
		if (!prestigeResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<HumanAbattoirPrestigeEntity> prestigeEntityList = (List<HumanAbattoirPrestigeEntity>) prestigeResult;
			for (HumanAbattoirPrestigeEntity prestigeEntity : prestigeEntityList) {
				abattoirPrestiges.put((Long) prestigeEntity.getId(),
						prestigeConverter.reverseConvert(prestigeEntity));
			}
		}
	}

	/**
	 * 发送面板信息
	 */
	public void sendPanelInfo(Human human) {
		GCOpenAbattoirPanel msg = new GCOpenAbattoirPanel();
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
		wrestlerInfo.setRemainWrestleNum(human.getAbattoirRemainNum());
		wrestlerInfo.setNextBuyNumCost(templateManager
				.getBuyWrestleNumCost(human.getAbattoirBuyNum() + 1));

		List<AbattoirRoomInfo> humanRoomInfoList = new ArrayList<AbattoirRoomInfo>();
		List<AbattoirRoom> humanRoomList = null;
		AbattoirRoom humanRoom = getHumanAbattoirRoom(human.getHumanGuid());
		if (humanRoom != null) {// 如果玩家在房间里
			wrestlerInfo.setRemainTime((int) (humanRoom.getLootTime()
					+ humanRoom.getTimeLimit() * TimeUtils.MIN - timeService
					.now()));
			wrestlerInfo.setRoomName(humanRoom.getRoomName());
			wrestlerInfo.setRevenue(humanRoom.getRevenue());
			wrestlerInfo.setRoomId(humanRoom.getRoomId());
			// 在房间中计算累积收益
			wrestlerInfo.setTotalRevenue(caculateRoomRevenue(humanRoom)
					+ getHumanPrestige(human.getHumanGuid()).getPrestige());
			// 获取这个玩家所在房间同等级段的房间
			int roomLevelRangeId = templateManager.roomIdToTemplateId(humanRoom
					.getRoomId());
			humanRoomList = getAbattoirRoomList(roomLevelRangeId);
		} else {
			wrestlerInfo.setRemainTime(0);
			wrestlerInfo.setRoomName("");
			wrestlerInfo.setRevenue(0);
			wrestlerInfo.setRoomId(0);
			// 不在房间中累积收益
			wrestlerInfo.setTotalRevenue(getHumanPrestige(human.getHumanGuid())
					.getPrestige());
			// 获取玩家等级对应的房间
			int templateId = templateManager
					.levelToTemplateId(human.getLevel());
			humanRoomList = getAbattoirRoomList(templateId);
		}
		msg.setWrestlerInfo(wrestlerInfo);
		// 角色对应的房间
		for (AbattoirRoom room : humanRoomList) {
			AbattoirRoomInfo roomInfo = AbattoirRoomToInfoConverter
					.converter(room);
			humanRoomInfoList.add(roomInfo);
		}
		Collections.sort(humanRoomInfoList);
		msg.setRooms(humanRoomInfoList.toArray(new AbattoirRoomInfo[0]));
		human.sendMessage(msg);
	}

	/**
	 * 在房间中计算累积收益
	 */
	private int caculateRoomRevenue(AbattoirRoom humanRoom) {
		if (humanRoom == null) {
			return 0;
		}
		if (humanRoom.getOwnerType() != AbattoirOwnerType.PLAYER_WRESTLER
				.getIndex()) {
			return 0;
		}
		long lastExtractTime = getHumanPrestige(humanRoom.getOwnerId())
				.getLastExtractTime();
		// 累计时间不能超过房间持续时间
		long totalTime = 0;
		if (lastExtractTime > humanRoom.getLootTime()) {
			totalTime = timeService.now() - lastExtractTime;
		} else {
			totalTime = timeService.now() - humanRoom.getLootTime();
		}
		if (totalTime > humanRoom.getTimeLimit() * TimeUtils.MIN) {
			totalTime = humanRoom.getTimeLimit() * TimeUtils.MIN;
		}
		int roomRevenue = (int) (totalTime * humanRoom.getRevenue() / TimeUtils.MIN);
		return roomRevenue;
	}

	/**
	 * 获取角色所在的房间
	 */
	public AbattoirRoom getHumanAbattoirRoom(long roleId) {
		for (int roomId : abattoirRooms.keySet()) {
			AbattoirRoom room = abattoirRooms.get(roomId);
			if (room.getOwnerId() == roleId) {
				return room;
			}
		}
		return null;
	}

	/**
	 * 获取房间
	 */
	public AbattoirRoom getAbattoirRoom(int roomId) {
		return abattoirRooms.get(roomId);
	}

	/**
	 * 获取等级范围内的房间
	 */
	private List<AbattoirRoom> getAbattoirRoomList(int templateId) {
		List<AbattoirRoom> roomList = new ArrayList<AbattoirRoom>();
		for (int roomId : abattoirRooms.keySet()) {
			AbattoirRoom room = abattoirRooms.get(roomId);
			int rangeId = templateManager.roomIdToTemplateId(room.getRoomId());
			if (rangeId == templateId) {
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
		// 抢夺的房间
		AbattoirRoom room = getAbattoirRoom(roomId);
		room.setFighting(false);
		human.setAbattoirRemainNum(human.getAbattoirRemainNum() - 1);
		if (isWin) {
			// 如果角色原先已在房间内
			AbattoirRoom oldRoom = getHumanAbattoirRoom(human.getHumanGuid());
			if (oldRoom != null) {
				// 执行退出房间的逻辑
				quitRoom(oldRoom);
			}
			// 抢夺的房间信息改变
			room.setOwnerId(human.getHumanGuid());
			room.setOwnerLevel(human.getLevel());
			room.setOwnerName(human.getName());
			room.setOwnerOccupationType(human.getOccupation().getIndex());
			room.setLootTime(timeService.now());
			room.setOwnerType(AbattoirOwnerType.PLAYER_WRESTLER.getIndex());
			cache.addUpdate(room.getRoomId(), roomConverter.convert(room));
			human.sendSuccessMessage(LangConstants.ABATTOIR_LOOT_ROOM_SUCCESS);
		} else {
			human.sendImportantMessage(LangConstants.ABATTOIR_LOOT_ROOM_FAILED);
		}
		// 发送挑战事件
		human.getHumanAbattoirManager().fireChallengeEvent();
	}

	/**
	 * 同玩家抢夺回调
	 */
	public void lootPlayerCallBack(Human human, IBattleUnit player, int roomId,
			boolean isWin) {
		// 抢夺的房间
		AbattoirRoom room = getAbattoirRoom(roomId);
		room.setFighting(false);
		human.setAbattoirRemainNum(human.getAbattoirRemainNum() - 1);
		if (isWin) {
			// 如果角色原先已在房间内，
			AbattoirRoom oldRoom = getHumanAbattoirRoom(human.getHumanGuid());
			if (oldRoom != null) {
				// 执行退出房间的逻辑
				quitRoom(oldRoom);
			}
			// 抢夺房间信息改变：房主 + 状态
			HumanAbattoirPrestige abattoirPrestige = getHumanPrestige(player
					.getUnitGuid());
			// 抢夺过程中，房主有可能 1.还在房间中(无动作；正在抢夺别的房间) 2. 中途退出 3.切换到别的房间
			// 计算原先房主的累积收益
			int roomRevenue = caculateRoomRevenue(room);
			abattoirPrestige.setPrestige(roomRevenue
					+ abattoirPrestige.getPrestige());
			abattoirPrestiges.put(player.getUnitGuid(), abattoirPrestige);
			longCache.addUpdate(player.getUnitGuid(),
					prestigeConverter.convert(abattoirPrestige));

			// 通知被抢夺的玩家
			Human beLooted = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(player.getUnitGuid());
			AbattoirRoom beLootedHumanRoom = getHumanAbattoirRoom(player
					.getUnitGuid());
			// 在线、还在这个房间里
			if (beLooted != null && beLootedHumanRoom != null
					&& beLootedHumanRoom.getRoomId() == roomId) {
				beLooted.sendImportantMessage(LangConstants.ABATTOIR_ROOM_BE_LOOTED);
			}

			room.setOwnerId(human.getHumanGuid());
			room.setOwnerLevel(human.getLevel());
			room.setOwnerName(human.getName());
			room.setOwnerOccupationType(human.getOccupation().getIndex());
			long now = timeService.now();
			room.setLootTime(now);
			room.setOwnerType(AbattoirOwnerType.PLAYER_WRESTLER.getIndex());
			cache.addUpdate(room.getRoomId(), roomConverter.convert(room));
			human.sendSuccessMessage(LangConstants.ABATTOIR_LOOT_ROOM_SUCCESS);
			// 添加日志
			AbattoirLog abattoirLog = new AbattoirLog();
			abattoirLog.setFirstId(human.getHumanGuid());
			abattoirLog.setFirstLevel(human.getLevel());
			abattoirLog.setFirstName(human.getName());
			abattoirLog.setSecondId(player.getUnitGuid());
			abattoirLog.setResult(1);
			abattoirLog.setLootTime(now);
			addAbattoirLog(abattoirLog);

		} else {
			human.sendImportantMessage(LangConstants.ABATTOIR_LOOT_ROOM_FAILED);
		}
		// 发送挑战事件
		human.getHumanAbattoirManager().fireChallengeEvent();
	}

	/**
	 * 退出房间
	 */
	public void quitRoom(AbattoirRoom humanRoom) {
		// 计算房主的累积收益
		int roomRevenue = caculateRoomRevenue(humanRoom);
		HumanAbattoirPrestige abattoirPrestige = getHumanPrestige(humanRoom
				.getOwnerId());
		abattoirPrestige.setPrestige(roomRevenue
				+ abattoirPrestige.getPrestige());
		abattoirPrestiges.put(humanRoom.getOwnerId(), abattoirPrestige);
		// 重新随机npc给房间
		setNpcForRoom(humanRoom);
		cache.addUpdate(humanRoom.getRoomId(), roomConverter.convert(humanRoom));
		longCache.addUpdate(humanRoom.getOwnerId(),
				prestigeConverter.convert(abattoirPrestige));
	}

	/**
	 * 为房间随机npc
	 */
	private void setNpcForRoom(AbattoirRoom room) {
		List<Monster> monsters = new ArrayList<Monster>();
		AbattoirRoomTemp roomTemp = templateManager.getAbattoirRoomTemp(room
				.getRoomId());
		int levelRangeHighest = roomTemp.getNpcLevelHighest();
		int levelRangeLowest = roomTemp.getNpcLevelLowest();
		for (int level = levelRangeLowest; level <= levelRangeHighest; level++) {
			monsters.addAll(GameServerAssist.getMonsterFactory().getWarriorNpc(
					level));
		}
		Monster monster = monsters.get(RandomUtils.nextInt(monsters.size()));
		long npcId = monster.getId();
		room.setOwnerId(npcId);
		room.setOwnerType(AbattoirOwnerType.NPC_WRESTLER.getIndex());
		room.setOwnerName(monster.getName());
		room.setOwnerOccupationType(monster.getOccupation().getIndex());
		room.setOwnerLevel(monster.getLevel());
		room.setLootTime(timeService.now());
	}

	/**
	 * 提取角斗场威望
	 */
	public void extractAbattoirPrestige(Human human) {
		// 计算威望
		AbattoirRoom humanRoom = getHumanAbattoirRoom(human.getHumanGuid());
		int totalRevenue = getHumanPrestige(human.getHumanGuid()).getPrestige();
		if (humanRoom != null) {
			// 在房间中计算累积收益
			totalRevenue += caculateRoomRevenue(humanRoom);
		}
		HumanAbattoirPrestige abattoirPrestige = getHumanPrestige(human
				.getHumanGuid());
		abattoirPrestige.setPrestige(0);
		abattoirPrestige.setLastExtractTime(timeService.now());
		abattoirPrestiges.put(human.getHumanGuid(), abattoirPrestige);
		longCache.addUpdate(human.getHumanGuid(),
				prestigeConverter.convert(abattoirPrestige));
		human.addPrestige(totalRevenue, true,
				PrestigeLogReason.ABATTOIR_EXTRACT, "");
		// 返回消息
		GCExtractAbattoirPrestige msg = new GCExtractAbattoirPrestige();
		msg.setCurrentPrestige(human.getPrestige());
		human.sendMessage(msg);
	}

	/**
	 * 获取角色威望
	 */
	public HumanAbattoirPrestige getHumanPrestige(long humanGuid) {
		if (abattoirPrestiges.get(humanGuid) == null) {// 第一次开板子
			HumanAbattoirPrestige abattoirPrestige = new HumanAbattoirPrestige();
			abattoirPrestige.setPrestige(0);
			abattoirPrestige.setHumanId(humanGuid);
			abattoirPrestige.setLastExtractTime(timeService.now());
			dataService.insert(prestigeConverter.convert(abattoirPrestige));
			abattoirPrestiges.put(humanGuid, abattoirPrestige);
		}
		return abattoirPrestiges.get(humanGuid);
	}

	/**
	 * 获取仇人列表
	 */
	public void sendAbattoirEnemyListInfo(final Human human) {
		dataService.query(DataQueryConstants.QUERY_ABATTOIR_LOG_BY_HUMAN_ID,
				new String[] { "secondId" },
				new Object[] { human.getHumanGuid() },
				new IDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> result) {
						List<AbattoirEnemyInfo> enemyInfoList = new ArrayList<AbattoirEnemyInfo>();
						if (!result.isEmpty()) {
							@SuppressWarnings("unchecked")
							List<AbattoirLogEntity> logEntityList = (List<AbattoirLogEntity>) result;
							for (AbattoirLogEntity entity : logEntityList) {
								AbattoirEnemyInfo enemyInfo = new AbattoirEnemyInfo();
								enemyInfo.setEnemyId(entity.getFirstId());
								enemyInfo.setEnemyLevel(entity.getFirstLevel());
								enemyInfo.setEnemyName(entity.getFirstName());
								enemyInfo
										.setLootTimeInterval((int) (timeService
												.now() - entity.getLootTime()));
								enemyInfoList.add(enemyInfo);
							}
						}
						GCShowAbattoirEnemy msg = new GCShowAbattoirEnemy();
						msg.setEnemies(enemyInfoList
								.toArray(new AbattoirEnemyInfo[0]));
						human.sendMessage(msg);
					}

					@Override
					public void onFailed(String errorMsg) {
					}
				});
	}

	/**
	 * 插入角斗场日志
	 */
	private void addAbattoirLog(AbattoirLog abattoirLog) {
		dataService.insert(logConverter.convert(abattoirLog));
	}

	/**
	 * 定时任务：到点退出房间
	 */
	public void systemQuitRooms() {
		for (int roomId : abattoirRooms.keySet()) {
			AbattoirRoom room = abattoirRooms.get(roomId);
			// 房间是玩家占据着，并且时间到了
			if (room.getOwnerType() == AbattoirOwnerType.PLAYER_WRESTLER
					.getIndex()
					&& timeService.now() - room.getLootTime() > room
							.getTimeLimit() * TimeUtils.MIN) {
				quitRoom(room);
			}
		}
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
}
