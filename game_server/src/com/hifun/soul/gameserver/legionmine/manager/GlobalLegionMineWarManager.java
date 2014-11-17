package com.hifun.soul.gameserver.legionmine.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import com.hifun.soul.common.constants.Loggers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.LegionMineMemberEntity;
import com.hifun.soul.gameserver.activity.ActivityState;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.ITimingActivityManager;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legionmine.LegionBuf;
import com.hifun.soul.gameserver.legionmine.LegionMine;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.SelfBuf;
import com.hifun.soul.gameserver.legionmine.converter.LegionMineMemberConverter;
import com.hifun.soul.gameserver.legionmine.enums.JoinLegionType;
import com.hifun.soul.gameserver.legionmine.enums.LegionBufType;
import com.hifun.soul.gameserver.legionmine.enums.OperateType;
import com.hifun.soul.gameserver.legionmine.enums.SelfBufType;
import com.hifun.soul.gameserver.legionmine.msg.GCLegionMineWarEnd;
import com.hifun.soul.gameserver.legionmine.msg.GCLegionMineWarHarvest;
import com.hifun.soul.gameserver.legionmine.msg.GCOpenLegionMineWarPanel;
import com.hifun.soul.gameserver.legionmine.msg.GCUpdateLegionBufList;
import com.hifun.soul.gameserver.legionmine.msg.GCUpdateLegionMineList;
import com.hifun.soul.gameserver.legionmine.msg.GCUpdateMineWarInfo;
import com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo;
import com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo;
import com.hifun.soul.gameserver.legionmine.template.LegionBufTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineConstantsTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineRewardTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineRichRateTemplate;
import com.hifun.soul.gameserver.legionmine.template.LegionMineTemplate;
import com.hifun.soul.gameserver.reward.RewardType;

/**
 * 全局军团矿战业务管理器
 * 
 * @author yandajun
 * 
 */
@Scope("singleton")
@Component
public class GlobalLegionMineWarManager implements IInitializeRequired,
		ITimingActivityManager, ICachableComponent {
	/** 大本营的索引 */
	public static final int ORIGIN_INDEX = 0;
	/** 红色军团在大本营可移动的索引 */
	public static final String RED_ORIGIN_CAN_MOVE_INDEXES = "1,2";
	/** 蓝色军团在大本营可移动的索引 */
	public static final String BLUE_ORIGIN_CAN_MOVE_INDEXES = "34,35";
	/** 红蓝军团 */
	private Map<JoinLegionType, JoinLegionInfo> legionInfoMap = new HashMap<JoinLegionType, JoinLegionInfo>();
	private Map<Integer, LegionMine> mineMap = new HashMap<Integer, LegionMine>();
	private Map<Long, LegionMineMember> mineMemberMap = new HashMap<Long, LegionMineMember>();
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();
	private LegionMineMemberConverter memberConverter = new LegionMineMemberConverter();
	private LegionMineWarTemplateManager mineTemplateManager;
	@Autowired
	private TimeService timeService;
	/** 上次随出加倍矿的时间 */
	private long lastDoubleMineTime;
	/** 加倍矿索引 */
	private int doubleMineIndex;
	private Logger logger = Loggers.LEGION_MINE_LOGGER;

	@Override
	public void init() {
		this.cacheManager.registerOtherCachableComponent(this);
		mineTemplateManager = GameServerAssist
				.getLegionMineWarTemplateManager();
	}

	public void start(IDBService dbService) {
		// 加载矿位信息
		loadMineInfos(dbService);
		// 加载玩家信息
		loadMineMemberInfos(dbService);
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
	 * 加载矿堆信息
	 * 
	 * @param dbService
	 */
	private void loadMineInfos(IDBService dbService) {
		// 从模板中加载矿位信息
		for (int mineIndex : mineTemplateManager.getLegionMineTemplates()
				.keySet()) {
			LegionMineTemplate mineTemplate = mineTemplateManager
					.getLegionMineTemplates().get(mineIndex);
			LegionMine mine = new LegionMine();
			mine.setMineIndex(mineIndex);
			mine.setRedMine(mineTemplate.isIsRedMine());
			mine.setMineType(mineTemplate.getMineType());
			mine.setSurroundIndexes(mineTemplate.getSurroundIndexes());
			mine.setCanMoveOrBattleIndexes(mineTemplate
					.getCanMoveOrBattleIndexes());
			mineMap.put(mineIndex, mine);
		}
	}

	/**
	 * 加载玩家信息
	 */
	private void loadMineMemberInfos(IDBService dbService) {
		// 从数据库中加载玩家信息
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_MINE_MEMBER);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionMineMemberEntity> memberEntityList = (List<LegionMineMemberEntity>) result;
			for (LegionMineMemberEntity entity : memberEntityList) {
				LegionMineMember mineMember = memberConverter
						.reverseConvert(entity);
				mineMemberMap.put(mineMember.getHumanId(), mineMember);
			}
		}
	}

	/**
	 * 增加玩家
	 */
	private LegionMineMember addLegionMineMember(long humanGuid,
			JoinLegionType legionType) {
		LegionMineMember mineMember = new LegionMineMember();
		mineMember.setHumanId(humanGuid);
		mineMemberMap.put(humanGuid, mineMember);
		GameServerAssist.getDataService().insert(
				memberConverter.convert(mineMember));
		return mineMember;
	}

	/**
	 * 从军团boss战获取参加军团的类型
	 */
	public JoinLegionType getJoinLegionType(long humanGuid) {
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				humanGuid);
		if (legion == null) {
			return JoinLegionType.NO_LEGION;
		}
		for (JoinLegionType legionType : legionInfoMap.keySet()) {
			JoinLegionInfo joinLegionInfo = legionInfoMap.get(legionType);
			if (joinLegionInfo.getLegionId() == legion.getId()) {
				return JoinLegionType.indexOf(joinLegionInfo
						.getJoinLegionType());
			}
		}
		return JoinLegionType.NO_LEGION;
	}

	/**
	 * 获取玩家所在的矿位索引
	 * 
	 */
	public int getLegionMineMemberIndex(long humanGuid) {
		LegionMineMember member = getJoinLegionMineMember(humanGuid);
		if (member == null) {
			return ORIGIN_INDEX;
		} else {
			return member.getMineIndex();
		}
	}

	/**
	 * 获取参战的军团信息
	 */
	public List<JoinLegionInfo> getJoinLegionInfos() {
		List<JoinLegionInfo> joinLegionInfoList = new ArrayList<JoinLegionInfo>();
		for (JoinLegionType legionType : legionInfoMap.keySet()) {
			JoinLegionInfo legionInfo = legionInfoMap.get(legionType);
			int occupyValue = 0;
			List<LegionMineMember> legionMemberList = getJoinMembersOfLegion(JoinLegionType
					.indexOf(legionInfo.getJoinLegionType()));
			for (LegionMineMember member : legionMemberList) {
				occupyValue += member.getOccupyValue();
			}
			legionInfo.setOccupyValue(occupyValue);
			joinLegionInfoList.add(legionInfo);
		}
		return joinLegionInfoList;
	}

	/**
	 * 获取某一军团的占领值
	 */
	public int getOccupyValueOfLegion(JoinLegionType legionType) {
		List<JoinLegionInfo> joinLegionInfoList = getJoinLegionInfos();
		for (JoinLegionInfo legionInfo : joinLegionInfoList) {
			if (JoinLegionType.indexOf(legionInfo.getJoinLegionType()) == legionType) {
				return legionInfo.getOccupyValue();
			}
		}
		return 0;
	}

	/**
	 * 获取当前所有在线并在矿战中的玩家信息
	 * 
	 */
	public List<LegionMineMember> getOnlineLegionMineMembers() {
		List<LegionMineMember> legionMineMemberList = new ArrayList<LegionMineMember>();
		for (long humanGuid : mineMemberMap.keySet()) {
			// 必须在线，参加了此次矿战，并没有退出
			if (GameServerAssist.getGameWorld().getSceneHumanManager()
					.getHumanByGuid(humanGuid) != null
					&& mineMemberMap.get(humanGuid).isJoin()
					&& !mineMemberMap.get(humanGuid).isQuit()) {
				legionMineMemberList.add(mineMemberMap.get(humanGuid));
			}
		}
		return legionMineMemberList;
	}

	/**
	 * 获取参加此次矿战的所有玩家信息(按占领值排名)
	 * 
	 */
	public List<LegionMineMember> getJoinLegionMineMembers() {
		List<LegionMineMember> legionMineMemberList = new ArrayList<LegionMineMember>();
		for (long humanGuid : mineMemberMap.keySet()) {
			if (mineMemberMap.get(humanGuid).isJoin()) {
				legionMineMemberList.add(mineMemberMap.get(humanGuid));
			}
		}
		Collections.sort(legionMineMemberList,
				new Comparator<LegionMineMember>() {
					@Override
					public int compare(LegionMineMember o1, LegionMineMember o2) {
						return o2.getOccupyValue() - o1.getOccupyValue();
					}
				});
		return legionMineMemberList;
	}

	/**
	 * 获取参加此次矿战的某个玩家信息
	 */
	public LegionMineMember getJoinLegionMineMember(long humanGuid) {
		// 状态必须是参与
		LegionMineMember mineMember = mineMemberMap.get(humanGuid);
		if (mineMember != null && mineMember.isJoin()) {
			return mineMember;
		}
		return null;
	}

	/**
	 * 获取参加此次矿战的某个军团的玩家信息
	 */
	public List<LegionMineMember> getJoinMembersOfLegion(
			JoinLegionType legionType) {
		List<LegionMineMember> legionMemberList = new ArrayList<LegionMineMember>();
		if (legionType == JoinLegionType.NO_LEGION) {
			return legionMemberList;
		}
		for (LegionMineMember mineMember : getJoinLegionMineMembers()) {
			if (mineMember.getLegionType() == legionType) {
				legionMemberList.add(mineMember);
			}
		}
		return legionMemberList;
	}

	/**
	 * 获取某个矿堆
	 */
	public LegionMine getLegionMine(int mineIndex) {
		return mineMap.get(mineIndex);
	}

	/**
	 * 获取某个玩家所在的矿堆
	 */
	public LegionMine getLegionMine(long humanGuid) {
		if (mineMemberMap.get(humanGuid).getMineIndex() == ORIGIN_INDEX) {
			return null;
		}
		return mineMap.get(mineMemberMap.get(humanGuid).getMineIndex());
	}

	/**
	 * 获取某个玩家的军团内占领值排名
	 */
	public int getMemberOccupyValueRank(LegionMineMember mineMember) {
		long humanGuid = mineMember.getHumanId();
		JoinLegionType legionType = mineMember.getLegionType();
		List<LegionMineMember> mineMemberList = getJoinMembersOfLegion(legionType);
		if (mineMemberList.size() <= 0) {
			return -1;
		}
		for (int i = 0; i < mineMemberList.size(); i++) {
			if (mineMemberList.get(i).getHumanId() == humanGuid
					&& mineMemberList.get(i).getOccupyValue() >= GameServerAssist
							.getLegionMineWarTemplateManager()
							.getConstantsTemplate().getRankMinOccupyValue()) {
				return i + 1;
			}
		}
		return -1;
	}

	/**
	 * 在矿上的人数
	 */
	public int getOccupyMemberNum() {
		List<LegionMineMember> mineMemberList = getOnlineLegionMineMembers();
		int num = 0;
		for (LegionMineMember member : mineMemberList) {
			if (member.getMineIndex() != ORIGIN_INDEX) {
				num++;
			}
		}
		return num;
	}

	/**
	 * 当前在矿战中的人数
	 */
	public int getOnlineMineMemberNum() {
		return getOnlineLegionMineMembers().size();
	}

	/**
	 * 某个矿位上的玩家
	 */
	public List<LegionMineMember> getMembersOnMine(int mineIndex) {
		List<LegionMineMember> memberListOnMine = new ArrayList<LegionMineMember>();
		for (LegionMineMember member : getOnlineLegionMineMembers()) {
			if (member.getMineIndex() == mineIndex) {
				memberListOnMine.add(member);
			}
		}
		return memberListOnMine;
	}

	/**
	 * 获取所有矿位
	 */
	public List<LegionMine> getAllLegionMines() {
		List<LegionMine> mineList = new ArrayList<LegionMine>();
		for (int mineIndex : mineMap.keySet()) {
			mineList.add(mineMap.get(mineIndex));
		}
		return mineList;
	}

	/**
	 * 获取某个军团占领的矿位
	 */
	public List<LegionMine> getMinesOfLegion(JoinLegionType legionType) {
		List<LegionMine> mineList = new ArrayList<LegionMine>();
		for (int mineIndex : mineMap.keySet()) {
			if (mineMap.get(mineIndex).getOccupyLegionType() == legionType) {
				mineList.add(mineMap.get(mineIndex));
			}
		}
		return mineList;
	}

	/**
	 * 军团是否占领红矿
	 */
	public boolean legionIsOccupyRedMine(JoinLegionType legionType) {
		for (int mineIndex : mineMap.keySet()) {
			LegionMine legionMine = mineMap.get(mineIndex);
			if (legionMine.isRedMine()
					&& legionMine.getOccupyLegionType() == legionType) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 生成军团buf信息
	 */
	public List<LegionBuf> generateLegionBufInfos(JoinLegionType legionType) {
		List<LegionBuf> legionBufList = new ArrayList<LegionBuf>();
		// 占领红矿buf
		if (legionIsOccupyRedMine(legionType)) {
			LegionBuf redBuf = new LegionBuf();
			redBuf.setLegionBufType(LegionBufType.RED_MINE_AMEND.getIndex());
			redBuf.setBufDesc(GameServerAssist.getSysLangService().read(
					LangConstants.RED_MINE_LEGION_BUF,
					mineTemplateManager.getConstantsTemplate()
							.getRedMineRevenue() / 100));
			redBuf.setBufIcon(mineTemplateManager.getConstantsTemplate()
					.getRedMineBufIcon());
			legionBufList.add(redBuf);
		}
		// 占领数少而获取的军团buf
		LegionBuf occupyBuf = getOccupyNumLegionBuf(legionType);
		if (occupyBuf != null) {
			legionBufList.add(occupyBuf);
		}
		return legionBufList;
	}

	/**
	 * 获取占领数少军团buf
	 * 
	 */
	public LegionBuf getOccupyNumLegionBuf(JoinLegionType legionType) {
		// 所属军团占领的矿坑数
		List<LegionMine> mineList = getMinesOfLegion(legionType);
		LegionBufTemplate template = mineTemplateManager
				.getLegionBufTemplate(mineList.size());
		if (template != null) {
			LegionBuf legionBuf = new LegionBuf();
			legionBuf.setLegionBufType(LegionBufType.OCCUPY_MINE_AMEND
					.getIndex());
			legionBuf.setBufDesc(GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_BUF, template.getMineNum(),
					template.getEffect() / 100));
			legionBuf.setBufIcon(template.getBufIcon());
			return legionBuf;
		}
		return null;
	}

	/**
	 * 军团buf攻防加成
	 */
	public int getLegionBufRate(LegionMineMember mineMember) {
		JoinLegionType legionType = mineMember.getLegionType();
		List<LegionMine> mineList = getMinesOfLegion(legionType);
		LegionBufTemplate template = mineTemplateManager
				.getLegionBufTemplate(mineList.size());
		if (template != null) {
			return template.getEffect();
		}
		return 0;
	}

	/**
	 * 更新玩家信息
	 */
	public void updateLegionMineMember(LegionMineMember mineMember) {
		cache.addUpdate(mineMember.getHumanId(),
				memberConverter.convert(mineMember));
	}

	/**
	 * 战斗回调
	 */
	public void battleCallback(Human human, long beBattledId, boolean isWin) {
		LegionMineMember mineMember = getJoinLegionMineMember(human
				.getHumanGuid());
		if (!isWin) {
			// 收获CD减少30s
			long reduceTime = (long) (mineTemplateManager
					.getConstantsTemplate().getFailReduceHarvestCdTime() * TimeUtils.MIN);
			human.getHumanCdManager().reduceCdTime(
					CdType.LEGION_MINE_WAR_HARVEST, reduceTime);
			human.getHumanCdManager().snapCdQueueInfo(
					CdType.LEGION_MINE_WAR_HARVEST);
			// 主动战斗失败提示
			human.sendImportantMessage(LangConstants.LEGION_MINE_BATTLE_FAILED,
					mineTemplateManager.getConstantsTemplate()
							.getFailReduceHarvestCdTime() * 60);
		} else {
			// 收获CD减少1min
			long reduceTime = mineTemplateManager.getConstantsTemplate()
					.getWinReduceHarvestCdTime() * TimeUtils.MIN;
			human.getHumanCdManager().reduceCdTime(
					CdType.LEGION_MINE_WAR_HARVEST, reduceTime);
			human.getHumanCdManager().snapCdQueueInfo(
					CdType.LEGION_MINE_WAR_HARVEST);
			// 主动战斗胜利提示
			human.sendImportantMessage(
					LangConstants.LEGION_MINE_BATTLE_SUCCESS_CD,
					mineTemplateManager.getConstantsTemplate()
							.getWinReduceHarvestCdTime() * 60);
			// 将对方踢回大本营
			LegionMineMember beBattledMember = getJoinLegionMineMember(beBattledId);
			int battleMineIndex = beBattledMember.getMineIndex();
			backToOrigin(beBattledMember);
			// 对方概率获得指令
			if (MathUtils.shake(mineTemplateManager.getConstantsTemplate()
					.getGetSelfBufRate() / SharedConstants.DEFAULT_FLOAT_BASE)) {
				// 随机一个指令
				int randBufType = RandomUtils
						.nextInt(SelfBufType.values().length) + 1;
				beBattledMember.addSelfBuf(randBufType);
			}
			// 对方所在矿，没人占领了，直接移动过去
			if (getLegionMine(battleMineIndex).getOccupyNum() == 0) {
				move(mineMember, battleMineIndex);
			}

			// 概率获得奖励物品
			human.getHumanLegionMineWarManager().addBattleReward();
		}
	}

	/**
	 * 返回大本营
	 */
	public void backToOrigin(LegionMineMember mineMember) {
		move(mineMember, ORIGIN_INDEX);
	}

	/**
	 * 退出(目前离线做相同处理)
	 */
	public void quit(LegionMineMember mineMember) {
		mineMember.setQuit(true);
		mineMember.removeAllSelfBufs();
		mineMember.setWatching(false);
		backToOrigin(mineMember);
	}

	/**
	 * 根据角色所在的矿位来生成矿位列表信息
	 * 
	 */
	public List<LegionMineInfo> generateLegionMineInfos(
			LegionMineMember mineMember, OperateType operateType) {
		List<LegionMineInfo> mineInfoList = new ArrayList<LegionMineInfo>();
		for (LegionMine legionMine : getAllLegionMines()) {
			LegionMineInfo mineInfo = new LegionMineInfo();
			mineInfo.setMineIndex(legionMine.getMineIndex());
			mineInfo.setOccupyNum(legionMine.getOccupyNum());
			mineInfo.setJoinLegionType(legionMine.getOccupyLegionType()
					.getIndex());
			mineInfo.setSurroundState(legionMine.getSurroundState().getIndex());
			mineInfo.setIsDouble(legionMine.isDouble());
			mineInfo.setIsRedMine(legionMine.isRedMine());
			mineInfo.setCanBattle(canBattle(legionMine, mineMember));
			mineInfo.setCanMove(canMove(legionMine, mineMember));
			mineInfo.setCanDisturb(canDisturb(legionMine, mineMember,
					operateType));
			mineInfo.setOccupyNumVisible(canWatch(legionMine, mineMember));
			mineInfo.setIsSelf(legionMine.getMineIndex() == getLegionMineMemberIndex(mineMember
					.getHumanId()));
			mineInfo.setMineType(legionMine.getMineType());
			mineInfoList.add(mineInfo);
		}
		return mineInfoList;
	}

	/**
	 * 该矿位是否可战斗
	 */
	private boolean canBattle(LegionMine legionMine, LegionMineMember mineMember) {
		// 正在使用驰指令，不可战斗
		if (mineMember.isUsingRunBuf()) {
			return false;
		}
		LegionMine humanMine = getLegionMine(mineMember.getHumanId());
		// 该矿位可战斗条件：1.矿上有军团占领；该军团跟自己属不同军团；不在同一个矿；2.在可移动范围
		if (legionMine.getOccupyLegionType() != JoinLegionType.NO_LEGION
				&& legionMine.getOccupyLegionType() != mineMember
						.getLegionType()
				&& legionMine.getMineIndex() != getLegionMineMemberIndex(mineMember
						.getHumanId())) {
			// 如果正在使用袭指令
			if (mineMember.isUsingRaidBuf()) {
				return true;
			} else {
				String[] canBattleIndexes = null;
				if (humanMine == null) {
					if (mineMember.getLegionType() == JoinLegionType.RED_LEGION) {
						canBattleIndexes = RED_ORIGIN_CAN_MOVE_INDEXES
								.split(",");
					} else {
						canBattleIndexes = BLUE_ORIGIN_CAN_MOVE_INDEXES
								.split(",");
					}
				} else {
					canBattleIndexes = humanMine.getCanMoveOrBattleIndexes()
							.split(",");
				}
				for (String canBattleIndex : canBattleIndexes) {
					if (legionMine.getMineIndex() == Integer
							.parseInt(canBattleIndex)) {
						return true;
					}
				}

			}
		}
		return false;
	}

	/**
	 * 该矿位是否可移动
	 */
	private boolean canMove(LegionMine legionMine, LegionMineMember mineMember) {
		long humanGuid = mineMember.getHumanId();
		// 正在使用袭指令，不可移动
		if (mineMember.isUsingRaidBuf()) {
			return false;
		}
		LegionMine humanMine = getLegionMine(humanGuid);
		// 该矿位可移动条件：1.无人占领 或 该军团跟自己属相同军团 且不在同一个矿；2.在可移动范围
		if (legionMine.getOccupyLegionType() == JoinLegionType.NO_LEGION
				|| (legionMine.getOccupyLegionType() == mineMember
						.getLegionType() && legionMine.getMineIndex() != getLegionMineMemberIndex(humanGuid))) {
			// 如果正在使用驰指令
			if (mineMember.isUsingRunBuf()) {
				return true;
			} else {
				String[] canMoveIndexes = null;
				if (humanMine == null) {
					if (mineMember.getLegionType() == JoinLegionType.RED_LEGION) {
						canMoveIndexes = RED_ORIGIN_CAN_MOVE_INDEXES.split(",");
					} else {
						canMoveIndexes = BLUE_ORIGIN_CAN_MOVE_INDEXES
								.split(",");
					}
				} else {
					canMoveIndexes = humanMine.getCanMoveOrBattleIndexes()
							.split(",");
				}
				for (String canMoveIndex : canMoveIndexes) {
					if (legionMine.getMineIndex() == Integer
							.parseInt(canMoveIndex)) {
						return true;
					}
				}

			}
		}
		return false;
	}

	/**
	 * 该矿位是否可侦查
	 */
	private boolean canWatch(LegionMine legionMine, LegionMineMember mineMember) {
		long humanGuid = mineMember.getHumanId();
		LegionMine humanMine = getLegionMine(humanGuid);
		// 大本营中不能侦查
		if (humanMine == null) {
			return false;
		}
		// 自己的矿位显示人数
		if (legionMine.getMineIndex() == getLegionMineMemberIndex(humanGuid)) {
			return true;
		}
		// 该矿位可侦查条件：1.矿上有军团占领；该军团跟自己属不同军团 2.在可移动范围
		if (legionMine.getOccupyLegionType() != JoinLegionType.NO_LEGION
				&& legionMine.getOccupyLegionType() != mineMember
						.getLegionType()) {
			// 如果正在侦查
			if (getJoinLegionMineMember(humanGuid).isWatching()) {
				String[] canWatchIndexes = null;
				canWatchIndexes = humanMine.getSurroundIndexes().split(",");
				for (String canWatchIndex : canWatchIndexes) {
					if (legionMine.getMineIndex() == Integer
							.parseInt(canWatchIndex)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 该矿位是否可干扰
	 */
	private boolean canDisturb(LegionMine legionMine,
			LegionMineMember mineMember, OperateType operateType) {
		long humanGuid = mineMember.getHumanId();
		LegionMine humanMine = getLegionMine(humanGuid);
		// 大本营中不能干扰
		if (humanMine == null) {
			return false;
		}
		// 该矿位可干扰条件：1.矿上有军团占领；该军团跟自己属不同军团 2.在可移动范围
		if (legionMine.getOccupyLegionType() != JoinLegionType.NO_LEGION
				&& legionMine.getOccupyLegionType() != mineMember
						.getLegionType()) {
			if (operateType == OperateType.DISTURB) {
				String[] canWatchIndexes = null;
				canWatchIndexes = humanMine.getSurroundIndexes().split(",");
				for (String canWatchIndex : canWatchIndexes) {
					if (legionMine.getMineIndex() == Integer
							.parseInt(canWatchIndex)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 广播矿位列表信息
	 */
	public void broadCastMineInfos() {
		List<LegionMineMember> mineMemberList = getOnlineLegionMineMembers();
		for (LegionMineMember mineMember : mineMemberList) {
			Human human = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(mineMember.getHumanId());
			if (human != null) {
				GCUpdateLegionMineList msg = new GCUpdateLegionMineList();
				List<LegionMineInfo> mineInfoList = generateLegionMineInfos(
						mineMember, OperateType.COMMON);
				msg.setMineInfos(mineInfoList.toArray(new LegionMineInfo[0]));
				human.sendMessage(msg);
			}
		}
	}

	/**
	 * 广播更新矿战信息
	 */
	public void broadCastMineWarInfo() {
		List<LegionMineMember> mineMemberList = getOnlineLegionMineMembers();
		for (LegionMineMember member : mineMemberList) {
			Human human = GameServerAssist.getGameWorld()
					.getSceneHumanManager().getHumanByGuid(member.getHumanId());
			if (human != null) {
				GCUpdateMineWarInfo msg = new GCUpdateMineWarInfo();
				LegionMine selfMine = getLegionMine(human.getHumanGuid());
				if (selfMine != null) {
					msg.setOccupyNum(selfMine.getOccupyNum());
					msg.setMaxOccupyNum(selfMine.getMaxOccupyNum());
					msg.setHoldMineIndex(selfMine.getMineIndex());
					LegionMineRichRateTemplate richRateTempalte = selfMine
							.getRichRateTemplate();
					if (richRateTempalte != null) {
						msg.setRichRateDesc(richRateTempalte.getRichRateDesc());
						msg.setRevenueRate(richRateTempalte.getRevenueRate());
					}
					msg.setOccupyValue(getCurrentOccupyValue(human
							.getHumanGuid()));
				}
				human.sendMessage(msg);
			}
		}
	}

	/**
	 * 广播军团占领值信息
	 */
	public void broadCastJoinLegionInfos(long havestHumanGuid) {
		List<LegionMineMember> mineMemberList = getOnlineLegionMineMembers();
		for (LegionMineMember mineMember : mineMemberList) {
			Human human = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(mineMember.getHumanId());
			if (human != null) {
				GCLegionMineWarHarvest msg = new GCLegionMineWarHarvest();
				msg.setOccupyValue(mineMember.getOccupyValue());
				msg.setRank(getMemberOccupyValueRank(mineMember));
				msg.setJoinLegionInfos(getJoinLegionInfos().toArray(
						new JoinLegionInfo[0]));
				msg.setHavestHumanGuid(havestHumanGuid);
				human.sendMessage(msg);
			}
		}
	}

	/**
	 * 移动
	 */
	public void move(LegionMineMember mineMember, int toIndex) {
		mineMember.setMineIndex(toIndex);
		mineMember.setOccupyTime(timeService.now());
		updateLegionMineMember(mineMember);
		// 更新军团buf列表
		for (JoinLegionType legionType : JoinLegionType.values()) {
			for (LegionMineMember member : getJoinMembersOfLegion(legionType)) {
				Human human = GameServerAssist.getGameWorld()
						.getSceneHumanManager()
						.getHumanByGuid(member.getHumanId());
				if (human != null && !member.isQuit()) {
					GCUpdateLegionBufList msg = new GCUpdateLegionBufList();
					msg.setLegionBufs(generateLegionBufInfos(legionType)
							.toArray(new LegionBuf[0]));
					human.sendMessage(msg);
				}
			}
		}
		// 广播通知其他客户端
		broadCastMineInfos();
		// 广播更新矿战信息
		broadCastMineWarInfo();

		LegionMine toMine = getLegionMine(toIndex);
		logger.info("end move:selfLegionType:" + mineMember.getLegionType()
				+ ", toLegitonType:" + toMine.getOccupyLegionType());
		if (toMine != null) {
			if (toMine.getOccupyLegionType() != mineMember.getLegionType()) {
				logger.error("move occured error , member:"
						+ mineMember.getHumanId() + ", toIndex:" + toIndex);
			}
		}
	}

	@Override
	public void onStart() {
		doubleMineIndex = 0;
		setLastDoubleMineTime(timeService.now());
		// 发布矿战开启公告
		String content = GameServerAssist.getSysLangService().read(
				LangConstants.LEGION_MINE_WAR_OPEN);
		GameServerAssist.getBulletinManager().sendSystemBulletin(content);
		// 初始化参加军团信息
		initJoinLegionInfos();
		// 清理掉之前的数据
		resetLegionMemberData();
	}

	/**
	 * 重置数据
	 */
	private void resetLegionMemberData() {
		for (long humanGuid : mineMemberMap.keySet()) {
			// 清理掉上次矿战奖励
			Human human = GameServerAssist.getGameWorld()
					.getSceneHumanManager().getHumanByGuid(humanGuid);
			if (human != null) {
				if (human.getHumanLegionMineWarManager()
						.hasLegionMineRankReward()) {
					// 从可领取奖励列表中移除该奖励
					GameServerAssist.getRewardService()
							.sendRemoveCommonRewardMessage(human,
									RewardType.LEGION_MINE_RANK_REWARD);
				}
				if (human.getHumanLegionMineWarManager()
						.hasLegionMineBattleReward()) {
					// 从可领取奖励列表中移除该奖励
					GameServerAssist.getRewardService()
							.sendRemoveCommonRewardMessage(human,
									RewardType.LEGION_MINE_BATTLE_REWARD);
					human.getHumanLegionMineWarManager().clearBattleReward();
				}
			}
			mineMemberMap.get(humanGuid).resetDate();
			updateLegionMineMember(mineMemberMap.get(humanGuid));
		}
	}

	public void onEnterWar(Human human) {
		// 如果没有加入军团
		if (GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid()) == null) {
			human.sendErrorMessage(LangConstants.LEGION_MINE_NOT_JOINED);
			return;
		}
		// 如果所在军团没有进入boss战前两名
		JoinLegionType legionType = getJoinLegionType(human.getHumanGuid());
		if (legionType == JoinLegionType.NO_LEGION) {
			human.sendErrorMessage(LangConstants.LEGION_MINE_NO_RIGHT);
			return;
		}
		// 军团子功能是否开启
		if (!GameServerAssist.getGlobalLegionManager().legionFuncIsOpen(human,
				LegionBuildingType.MINE_WAR, true)) {
			return;
		}
		// 军团矿战是否开始
		if (!legionMineWarIsOpen()) {
			return;
		}

		LegionMineMember mineMember = mineMemberMap.get(human.getHumanGuid());
		if (mineMember == null) {
			mineMember = addLegionMineMember(human.getHumanGuid(), legionType);
		}
		mineMember.setJoin(true);
		mineMember.setQuit(false);
		mineMember.setOccupyTime(timeService.now());
		mineMember.setLegionType(legionType);

		// 发送面板信息
		sendPanelInfo(human, mineMember);
	}

	/**
	 * 判断军团矿战是否开始
	 */
	private boolean legionMineWarIsOpen() {
		ActivityState activityState = GameServerAssist
				.getGlobalActivityManager()
				.getActivity(ActivityType.LEGION_MINE).getState();
		return activityState == ActivityState.OPEN;
	}

	/**
	 * 发送面板信息
	 * 
	 * @param human
	 * @param mineMember
	 */
	public void sendPanelInfo(Human human, LegionMineMember mineMember) {
		LegionMineConstantsTemplate constantsTempalte = GameServerAssist
				.getLegionMineWarTemplateManager().getConstantsTemplate();
		GCOpenLegionMineWarPanel msg = new GCOpenLegionMineWarPanel();
		// 剩余时间
		msg.setRemainTime((int) (GameServerAssist.getGlobalActivityManager()
				.getRemainTime(ActivityType.LEGION_MINE) / TimeUtils.SECOND));
		msg.setMeditationEncourageCost(constantsTempalte
				.getMeditationEncourageCost());
		msg.setCrystalEncourageCost(constantsTempalte.getCrystalEncourageCost());
		msg.setEncourageIsFull(mineMember.getEncourageRate() >= constantsTempalte
				.getMaxEncourageRate());
		msg.setAttackRate(mineMember.getAttackRate());
		msg.setDefenseRate(mineMember.getDefenseRate());
		msg.setWarExploit(mineMember.getOccupyValue());
		msg.setRank(getMemberOccupyValueRank(mineMember));
		LegionMine selfMine = getLegionMine(human.getHumanGuid());
		if (selfMine != null) {
			msg.setOccupyMemberNum(selfMine.getOccupyNum());
			msg.setTotalMemberNum(selfMine.getMaxOccupyNum());
			LegionMineRichRateTemplate richRateTempalte = selfMine
					.getRichRateTemplate();
			if (richRateTempalte != null) {
				msg.setRichRateDesc(richRateTempalte.getRichRateDesc());
				msg.setRevenueRate(richRateTempalte.getRevenueRate());
			}
		}
		msg.setCurrentOccupyValue(getCurrentOccupyValue(human.getHumanGuid()));
		msg.setWatchCrystalCost(constantsTempalte.getWatchCostCraystal());
		msg.setHoldMineIndex(mineMember.getMineIndex());
		msg.setIsOverturn(mineMember.getLegionType() == JoinLegionType.BLUE_LEGION);
		msg.setWatchHoldTime(constantsTempalte.getWatchHoldTime());
		// 军团buf信息
		msg.setLegionBufs(generateLegionBufInfos(mineMember.getLegionType())
				.toArray(new LegionBuf[0]));
		// 个人buf信息
		msg.setSelfBufs(mineMember.getSelfBufList().toArray(new SelfBuf[0]));
		// 军团矿堆信息
		msg.setMineInfos(generateLegionMineInfos(mineMember, OperateType.COMMON)
				.toArray(new LegionMineInfo[0]));
		// 矿战军团信息
		msg.setJoinLegionInfos(getJoinLegionInfos().toArray(
				new JoinLegionInfo[0]));
		msg.setRankMinOccupyValue(constantsTempalte.getRankMinOccupyValue());

		human.sendMessage(msg);
		// 下发CD信息
		human.getHumanCdManager().snapCdQueueInfo(CdType.LEGION_MINE_WAR_MOVE);
		human.getHumanCdManager()
				.snapCdQueueInfo(CdType.LEGION_MINE_WAR_BATTLE);
		human.getHumanCdManager().snapCdQueueInfo(
				CdType.LEGION_MINE_WAR_HARVEST);
	}

	/**
	 * 获得当前可收获占领值
	 */
	public int getCurrentOccupyValue(long humanGuid) {
		LegionMineMember mineMember = getJoinLegionMineMember(humanGuid);
		int occupyValue = 0;
		// 矿位收益
		LegionMine legionMine = getLegionMine(mineMember.getMineIndex());
		if (legionMine != null) {
			occupyValue = legionMine.getMineRevenue();
			// 如果所在军团占领了红矿，收益增加30%
			if (legionIsOccupyRedMine(mineMember.getLegionType())) {
				occupyValue += occupyValue
						* GameServerAssist.getLegionMineWarTemplateManager()
								.getConstantsTemplate().getRedMineRevenue()
						/ SharedConstants.DEFAULT_FLOAT_BASE;
			}
		}
		return occupyValue;
	}

	/**
	 * 初始化参加军团信息
	 */
	private void initJoinLegionInfos() {
		List<JoinLegionInfo> joinLegionInfoList = GameServerAssist
				.getLegionBossService().getJoinLegionInfos();
		for (JoinLegionInfo legionInfo : joinLegionInfoList) {
			legionInfoMap.put(
					JoinLegionType.indexOf(legionInfo.getJoinLegionType()),
					legionInfo);
		}
	}

	/**
	 * 活动结束发送奖励
	 */
	@Override
	public void onStop() {
		// 根据军团胜负进行奖励
		rewardWinLegion();
		rewardFailLegion();
		// 通知客户端活动结束
		List<LegionMineMember> mineMemberList = getJoinLegionMineMembers();
		for (LegionMineMember mineMember : mineMemberList) {
			Human human = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(mineMember.getHumanId());
			if (human != null) {
				// 正在矿战发起的战斗玩家结束战斗
				if (human.isInBattleState()
						&& human.getBattleContext().getBattle().getBattleType() == BattleType.PVP_LEGION_MINE) {
					human.getBattleContext().getBattle()
							.onBattleUnitQuit(human);
				}
				GCLegionMineWarEnd msg = new GCLegionMineWarEnd();
				human.sendMessage(msg);
			}
			// 排名奖励状态
			int rank = GameServerAssist.getGlobalLegionMineWarManager()
					.getMemberOccupyValueRank(mineMember);
			LegionMineRewardTemplate rewardTemplate = GameServerAssist
					.getLegionMineWarTemplateManager().getRewardTemplate(rank);
			if (rewardTemplate != null
					&& mineMember.getOccupyValue() >= GameServerAssist
							.getLegionMineWarTemplateManager()
							.getConstantsTemplate().getRankMinOccupyValue()) {
				mineMember.setRank(rank);// 通知客户端排名奖励
				if (isLegionWin(mineMember.getLegionType().getIndex())) {
					mineMember.setLegionWin(true);
				} else {
					mineMember.setLegionWin(false);
				}
				if (human != null) {
					human.getHumanLegionMineWarManager().sendRewardNotify();
				}
			} else {
				mineMember.setRank(0);
			}
			updateLegionMineMember(mineMember);
		}
	}

	/**
	 * 根据占领值对军团进行排名
	 * 
	 */
	private List<JoinLegionInfo> rankJoinLegionInfos() {
		List<JoinLegionInfo> joinLegionInfoList = getJoinLegionInfos();
		if (joinLegionInfoList.size() >= 2) {
			Collections.sort(joinLegionInfoList,
					new Comparator<JoinLegionInfo>() {
						@Override
						public int compare(JoinLegionInfo o1, JoinLegionInfo o2) {
							return o2.getOccupyValue() - o1.getOccupyValue();
						}
					});
		}
		return joinLegionInfoList;
	}

	/**
	 * 判断军团是否取胜
	 */
	private boolean isLegionWin(int legionType) {
		JoinLegionInfo winLegionInfo = rankJoinLegionInfos().get(0);
		if (winLegionInfo != null
				&& legionType == winLegionInfo.getJoinLegionType()) {
			return true;
		}
		return false;
	}

	/**
	 * 胜利军团奖励
	 */
	private void rewardWinLegion() {
		if (rankJoinLegionInfos().size() < 1) {
			return;
		}
		JoinLegionInfo winLegionInfo = rankJoinLegionInfos().get(0);
		if (winLegionInfo != null) {
			Legion winLegion = GameServerAssist.getGlobalLegionManager()
					.getLegionById(winLegionInfo.getLegionId());
			int addExp = GameServerAssist.getLegionMineWarTemplateManager()
					.getConstantsTemplate().getLegionWinRewardExperience();
			GameServerAssist.getGlobalLegionManager().addExperience(null,
					winLegion, addExp, false);
			// 发布公告
			String result = GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_MINE_WAR_REWARD,
					winLegion.getLegionName());
			GameServerAssist.getBulletinManager().sendSystemBulletin(result);
		} else {
			// 公告通知矿战已经结束
			String content = GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_MINE_WAR_CLOSED);
			GameServerAssist.getBulletinManager().sendSystemBulletin(content);
		}
	}

	/**
	 * 失败军团奖励
	 */
	private void rewardFailLegion() {
		if (rankJoinLegionInfos().size() < 2) {
			return;
		}
		JoinLegionInfo failLegionInfo = rankJoinLegionInfos().get(1);
		if (failLegionInfo != null) {
			Legion failLegion = GameServerAssist.getGlobalLegionManager()
					.getLegionById(failLegionInfo.getLegionId());
			int addExp = GameServerAssist.getLegionMineWarTemplateManager()
					.getConstantsTemplate().getLegionFailRewardExperience();
			GameServerAssist.getGlobalLegionManager().addExperience(null,
					failLegion, addExp, false);
		}
	}

	public long getLastDoubleMineTime() {
		return lastDoubleMineTime;
	}

	public void setLastDoubleMineTime(long time) {
		lastDoubleMineTime = time;
	}

	public int getDoubleMineIndex() {
		return doubleMineIndex;
	}

	/**
	 * 随机出双倍矿
	 */
	public void randomDoubleMine() {
		doubleMineIndex = RandomUtils
				.nextInt(SharedConstants.TOTAL_LEGION_MINE_NUM) + 1;
		broadCastMineInfos();
	}
}
