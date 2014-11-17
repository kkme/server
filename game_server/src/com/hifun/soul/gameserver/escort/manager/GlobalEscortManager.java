package com.hifun.soul.gameserver.escort.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
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
import com.hifun.soul.gamedb.entity.EscortEntity;
import com.hifun.soul.gamedb.entity.EscortHelpEntity;
import com.hifun.soul.gamedb.entity.EscortInviteEntity;
import com.hifun.soul.gamedb.entity.EscortLegionPrayEntity;
import com.hifun.soul.gamedb.entity.EscortLogEntity;
import com.hifun.soul.gamedb.entity.EscortRobRankEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.ITimingActivityManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.escort.EscortHelp;
import com.hifun.soul.gameserver.escort.EscortInvite;
import com.hifun.soul.gameserver.escort.EscortLog;
import com.hifun.soul.gameserver.escort.EscortRobRank;
import com.hifun.soul.gameserver.escort.converter.EscortConverters;
import com.hifun.soul.gameserver.escort.enums.EscortInviteSate;
import com.hifun.soul.gameserver.escort.enums.EscortRewardState;
import com.hifun.soul.gameserver.escort.enums.EscortState;
import com.hifun.soul.gameserver.escort.info.EscortAmendInfo;
import com.hifun.soul.gameserver.escort.info.EscortInfo;
import com.hifun.soul.gameserver.escort.info.LegionPrayInfo;
import com.hifun.soul.gameserver.escort.info.RobRankInfo;
import com.hifun.soul.gameserver.escort.msg.GCEscortInviteFriendApply;
import com.hifun.soul.gameserver.escort.msg.GCEscortLegionPray;
import com.hifun.soul.gameserver.escort.msg.GCUpdateEscortPanel;
import com.hifun.soul.gameserver.escort.template.EscortMonsterTemplate;
import com.hifun.soul.gameserver.escort.template.EscortRewardTemplate;
import com.hifun.soul.gameserver.event.EscortEvent;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

/**
 * 全局押运管理器
 * 
 * @author yandajun
 * 
 */
@Scope("singleton")
@Component
public class GlobalEscortManager implements IInitializeRequired,
		ICachableComponent, ITimingActivityManager {
	private Map<Long, EscortInfo> escortInfoMap = new HashMap<Long, EscortInfo>();
	private Map<Long, LegionPrayInfo> legionPrayInfoMap = new HashMap<Long, LegionPrayInfo>();
	private Map<Long, EscortInvite> inviteMap = new HashMap<Long, EscortInvite>();
	/** 拦截排行数据 ，与数据库对应 */
	private Map<Long, EscortRobRank> robRankMap = new HashMap<Long, EscortRobRank>();
	/** 今日拦截排行榜列表 */
	private List<RobRankInfo> todayRankList = new ArrayList<RobRankInfo>();
	/** 昨日拦截排行榜列表 */
	private List<RobRankInfo> yesterdayRankList = new ArrayList<RobRankInfo>();
	private LinkedList<EscortLog> escortLogList = new LinkedList<EscortLog>();
	private Map<Long, EscortHelp> helpMap = new HashMap<Long, EscortHelp>();
	private EscortTemplateManager templateManager;
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();
	@Autowired
	private IDataService dataService;
	@Autowired
	private TimeService timeService;
	/** 初始押运怪物类型 */
	public static final int initMonsterType = 0;

	@Override
	public void init() {
		this.cacheManager.registerOtherCachableComponent(this);
		templateManager = GameServerAssist.getEscortTemplateManager();
	}

	public void start(IDBService dbService) {
		// 加载押运信息
		List<?> escortResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_ESCORT);
		if (!escortResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<EscortEntity> escortEntityList = (List<EscortEntity>) escortResult;
			for (EscortEntity escortEntity : escortEntityList) {
				escortInfoMap.put((Long) escortEntity.getId(),
						EscortConverters.escortEntityToInfo(escortEntity));
			}
		}
		// 加载军团祈福信息
		List<?> legionPrayResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_ESCORT_LEGION_PRAY);
		if (!legionPrayResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<EscortLegionPrayEntity> legionPrayEntityList = (List<EscortLegionPrayEntity>) legionPrayResult;
			for (EscortLegionPrayEntity legionPrayEntity : legionPrayEntityList) {
				legionPrayInfoMap.put((Long) legionPrayEntity.getId(),
						EscortConverters
								.legionPrayEntityToInfo(legionPrayEntity));
			}
		}
		// 加载邀请好友信息
		List<?> inviteResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_ESCORT_INVITE);
		if (!inviteResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<EscortInviteEntity> inviteEntityList = (List<EscortInviteEntity>) inviteResult;
			for (EscortInviteEntity inviteEntity : inviteEntityList) {
				inviteMap.put((Long) inviteEntity.getId(),
						EscortConverters.entityToInvite(inviteEntity));
			}
		}
		// 加载拦截榜信息
		List<?> robRankResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ESCORT_ROB_RANK);
		if (!robRankResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<EscortRobRankEntity> rankEntityList = (List<EscortRobRankEntity>) robRankResult;
			for (EscortRobRankEntity rankEntity : rankEntityList) {
				robRankMap.put((Long) rankEntity.getId(),
						EscortConverters.entityToRobRank(rankEntity));
			}
		}
		// 加载拦截日志信息
		List<?> logResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_ESCORT_LOG);
		if (!logResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<EscortLogEntity> logEntityList = (List<EscortLogEntity>) logResult;
			for (EscortLogEntity logEntity : logEntityList) {
				escortLogList.add(EscortConverters.entityToLog(logEntity));
			}
		}
		// 加载押运协助信息
		List<?> helpResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_ESCORT_HELP);
		if (!helpResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<EscortHelpEntity> helpEntityList = (List<EscortHelpEntity>) helpResult;
			for (EscortHelpEntity helpEntity : helpEntityList) {
				helpMap.put((Long) helpEntity.getId(),
						EscortConverters.entityToHelp(helpEntity));
			}
		}
		// 初始化拦截榜数据
		refreshTodayRank();
		initYesterdayRankList();
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
	 * 更新押运面板
	 */
	public void updateEscortPanel(Human human) {
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		EscortTemplateManager templateManager = GameServerAssist
				.getEscortTemplateManager();
		GCUpdateEscortPanel msg = new GCUpdateEscortPanel();
		msg.setRemainEscortNum(human.getEscortRemainNum());
		msg.setRemainRobNum(human.getEscortRobRemainNum());
		int buyRobNum = human.getEscortRobBuyNum();
		msg.setBuyRobNumCost(templateManager.getBuyRobNumCost(buyRobNum + 1));
		msg.setRemainHelpNum(human.getEscortHelpRemainNum());
		msg.setHasEscortReward(globalEscortManager.hasEscortReward(human));
		msg.setHasHelpReward(globalEscortManager.hasHelpReward(human));
		LegionPrayInfo prayInfo = globalEscortManager.getLegionPrayInfo(human);
		if (prayInfo == null) {
			prayInfo = new LegionPrayInfo();
		}
		msg.setPrayInfo(prayInfo);
		msg.setEscortInfos(globalEscortManager.getEscortInfoList().toArray(
				new EscortInfo[0]));
		msg.setPageMonsterSize(templateManager.getConstantsTemplate()
				.getPageMonsterSize());
		msg.setPageRoadNum(templateManager.getConstantsTemplate()
				.getPageRoadNum());
		List<EscortLog> logList = globalEscortManager.getEscortLogList();
		String[] robLogs = new String[logList.size()];
		for (int i = 0; i < logList.size(); i++) {
			EscortLog log = logList.get(i);
			robLogs[i] = GameServerAssist.getSysLangService().read(
					LangConstants.ESCORT_ROB_LOG, log.getFirstName(),
					log.getSecondName(), log.getMonserName(), log.getRobCoin());
		}
		msg.setRobLogs(robLogs);
		msg.setLegionPrayCost(templateManager.getConstantsTemplate()
				.getLegionPrayCost());
		EscortAmendInfo amendInfo = new EscortAmendInfo();
		if (getEscortAmendRemainTime() > 0) {
			amendInfo.setAmendDesc(GameServerAssist.getGlobalActivityManager()
					.getActivity(ActivityType.ESCORT_AMEND).getDesc());
			amendInfo.setRemainTime((int) getEscortAmendRemainTime());
		}
		msg.setEscortAmendInfo(amendInfo);
		human.sendMessage(msg);
		// 下发CD消息
		human.getHumanCdManager().snapCdQueueInfo(CdType.ESCORT);
		human.getHumanCdManager().snapCdQueueInfo(CdType.ESCORT_ROB);
	}

	/**
	 * 广播面板信息
	 */
	private void broadCastEscortPanelInfo() {
		Collection<Player> players = GameServerAssist.getGameWorld()
				.getAllPlayers();
		for (Player player : players) {
			Human human = player.getHuman();
			// 针对在线并且开着板子的角色
			if (human != null && human.getHumanEscortManager().isOpenPanel()) {
				updateEscortPanel(human);
			}
		}
	}

	/**
	 * 获取军团祈福信息
	 */
	public LegionPrayInfo getLegionPrayInfo(Human human) {
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		if (legion != null) {
			LegionPrayInfo prayInfo = legionPrayInfoMap.get(legion.getId());
			if (prayInfo != null) {
				// 剩余时间，实时变化
				long remainTime = prayInfo.getPrayEndTime() - timeService.now();
				if (remainTime > 0) {
					prayInfo.setPrayRemainTime((int) (remainTime));
				} else {
					prayInfo.setPrayRemainTime(0);
				}
				return prayInfo;
			}
		}
		return null;
	}

	/**
	 * 获取押运列表
	 */
	public List<EscortInfo> getEscortInfoList() {
		List<EscortInfo> list = new ArrayList<EscortInfo>();
		// 剩余时间
		for (long escortId : escortInfoMap.keySet()) {
			EscortInfo escortInfo = escortInfoMap.get(escortId);
			if (escortInfo.getEscortState() == EscortState.UNDERWAY.getIndex()) {
				escortInfo
						.setEscortRemainTime((int) (escortInfo.getEndTime() - timeService
								.now()));
				list.add(escortInfo);
			}
		}
		// 按剩余时间倒叙排列
		Collections.sort(list, new Comparator<EscortInfo>() {
			@Override
			public int compare(EscortInfo o1, EscortInfo o2) {
				return o2.getEscortRemainTime() - o1.getEscortRemainTime();
			}
		});
		return list;
	}

	/**
	 * 获取押运信息
	 */
	public EscortInfo getEscortInfo(long huamGuid) {
		return escortInfoMap.get(huamGuid);
	}

	/**
	 * 不可以接受别人邀请
	 */
	public boolean canNotReceiveInvite(long helperId) {
		// 有同意的邀请在有效时间内
		for (long humanGuid : inviteMap.keySet()) {
			EscortInvite invite = inviteMap.get(humanGuid);
			if (invite.getInviteFriendId() == helperId
					&& invite.getInviteState() == EscortInviteSate.AGREE
							.getIndex()
					&& invite.getAgreeEndTime() > timeService.now()) {
				return true;
			}
		}
		// 有协助的押运正在进行中
		for (long huamGuid : escortInfoMap.keySet()) {
			EscortInfo escortInfo = getEscortInfo(huamGuid);
			if (escortInfo.getHelperId() == helperId
					&& escortInfo.getEscortState() == EscortState.UNDERWAY
							.getIndex()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新押运信息
	 */
	public void updateEscortInfo(EscortInfo escortInfo) {
		cache.addUpdate(escortInfo.getEscortId(),
				EscortConverters.escortInfoToEntity(escortInfo));
	}

	/**
	 * 获取协助信息
	 */
	public EscortHelp getHelpInfo(long humanGuid) {
		return helpMap.get(humanGuid);
	}

	/**
	 * 更新协助押运信息
	 */
	public void updateHelpInfo(EscortHelp help) {
		cache.addUpdate(help.getHumanGuid(), EscortConverters.helpToEntiy(help));
	}

	/**
	 * 获取日志列表
	 */
	public List<EscortLog> getEscortLogList() {
		return escortLogList;
	}

	/**
	 * 获取角色邀请信息
	 */
	public EscortInvite getEscortInvite(long humanGuid) {
		return inviteMap.get(humanGuid);
	}

	/**
	 * 获取邀请状态
	 */
	public int getInviteEscortState(Human human) {
		EscortInvite invite = inviteMap.get(human.getHumanGuid());
		if (invite == null) {
			return EscortInviteSate.NOT_INVITE.getIndex();
		} else {
			return invite.getInviteState();
		}
	}

	/**
	 * 邀请好友护送
	 */
	public int inviteEscortFriend(Human human, Human friend) {
		GCEscortInviteFriendApply gcApplyMsg = new GCEscortInviteFriendApply();
		gcApplyMsg.setApplyHumanGuid(human.getHumanGuid());
		gcApplyMsg.setApplyHumanName(human.getName());
		friend.sendMessage(gcApplyMsg);
		EscortInvite invite = inviteMap.get(human.getHumanGuid());
		if (invite == null) {
			invite = new EscortInvite();
			invite.setHumanGuid(human.getHumanGuid());
			invite.setInviteFriendId(friend.getHumanGuid());
			invite.setInviteState(EscortInviteSate.INVITE_NOT_AGREE.getIndex());
			EscortInviteEntity inviteEntity = EscortConverters
					.inviteToEntity(invite);
			inviteMap.put(human.getHumanGuid(), invite);
			dataService.insert(inviteEntity);
		} else {
			invite.setInviteFriendId(friend.getHumanGuid());
			invite.setInviteState(EscortInviteSate.INVITE_NOT_AGREE.getIndex());
			EscortInviteEntity inviteEntity = EscortConverters
					.inviteToEntity(invite);
			cache.addUpdate(human.getHumanGuid(), inviteEntity);
		}
		return invite.getInviteState();
	}

	/**
	 * 撤销邀请
	 */
	public int abortEscortInvite(Human human) {
		EscortInvite invite = inviteMap.get(human.getHumanGuid());
		invite.setInviteState(EscortInviteSate.NOT_INVITE.getIndex());
		EscortInviteEntity inviteEntity = EscortConverters
				.inviteToEntity(invite);
		cache.addUpdate(human.getHumanGuid(), inviteEntity);
		return invite.getInviteState();
	}

	/**
	 * 同意邀请
	 */
	public int agreeEscortInvite(long inviteId) {
		EscortInvite invite = inviteMap.get(inviteId);
		invite.setInviteState(EscortInviteSate.AGREE.getIndex());
		long now = timeService.now();
		int holdTime = GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getAgreeValidTime();
		invite.setAgreeEndTime(now + TimeUtils.SECOND * holdTime);
		EscortInviteEntity inviteEntity = EscortConverters
				.inviteToEntity(invite);
		cache.addUpdate(inviteId, inviteEntity);
		return invite.getInviteState();
	}

	/**
	 * 拒绝邀请
	 */
	public int rejectEscortInvite(long inviteId) {
		EscortInvite invite = inviteMap.get(inviteId);
		invite.setInviteState(EscortInviteSate.NOT_INVITE.getIndex());
		EscortInviteEntity inviteEntity = EscortConverters
				.inviteToEntity(invite);
		cache.addUpdate(inviteId, inviteEntity);
		return invite.getInviteState();
	}

	/**
	 * 开始押运
	 */
	public void startEscort(Human human, boolean isEncouraged) {
		EscortInfo escortInfo = getEscortInfo(human.getHumanGuid());
		boolean needInsertDB = false;
		if (escortInfo == null) {
			escortInfo = new EscortInfo();
			needInsertDB = true;
		}
		int monsterType = getEscortMonsterType(human.getHumanGuid());
		EscortMonsterTemplate monsterTemplate = templateManager
				.getMonsterTemplate(monsterType);
		escortInfo.setEscortId(human.getHumanGuid());
		escortInfo.setMonsterModelId(monsterTemplate.getModelId());
		escortInfo.setMonsterType(monsterType);
		escortInfo.setOwnerId(human.getHumanGuid());
		escortInfo.setOwnerName(human.getName());
		escortInfo.setOwnerLevel(human.getLevel());
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		if (legion != null) {
			escortInfo.setOwnerLegionName(legion.getLegionName());
		}
		int maxBeRobbedNum = templateManager.getConstantsTemplate()
				.getMaxBeRobbedNum();
		escortInfo.setRemainBeRobbedNum(maxBeRobbedNum);
		escortInfo.setMaxBeRobbedNum(maxBeRobbedNum);
		if (isEncouraged) {
			escortInfo.setEncourageRate(templateManager.getConstantsTemplate()
					.getEncourageAttackRate());
		}
		int escortRewardCoin = (int) (templateManager.getEscortRewardTemplate(
				human.getLevel()).getRewardCoin() * monsterTemplate
				.getCoinRate());
		long now = timeService.now();
		// 军团祈福收益加成
		int amendRate = 0;
		if (hasLegionPray(human)) {
			LegionPrayInfo prayInfo = getLegionPrayInfo(human);
			amendRate += prayInfo.getPrayRevenue();
		}
		// 固定时间段收益加成
		if (getEscortAmendRemainTime() > 0) {
			amendRate += templateManager.getConstantsTemplate()
					.getSomeTimeRevenueRate();
		}
		escortRewardCoin += escortRewardCoin * amendRate
				/ SharedConstants.DEFAULT_FLOAT_BASE;
		escortInfo.setEscortRewardCoin(escortRewardCoin);
		int escortTime = templateManager.getEscortRewardTemplate(
				human.getLevel()).getRescortTime();
		escortInfo.setEscortTime((int) (escortTime * monsterTemplate
				.getEscortTimeRate()));
		long spendTime = getEscortSpendTime(human);
		escortInfo.setEndTime(now + spendTime);

		// 添加押运CD(为了保证CD时间跟押运时间一致，放到这里)
		human.getHumanCdManager().addCd(CdType.ESCORT, spendTime);
		human.getHumanCdManager().snapCdQueueInfo(CdType.ESCORT);

		// 邀请同意，并且在有效时间内，添加护送者信息
		EscortInvite invite = getEscortInvite(human.getHumanGuid());
		if (invite != null
				&& invite.getInviteState() == EscortInviteSate.AGREE.getIndex()
				&& invite.getAgreeEndTime() > now) {
			// 获取好友信息
			FriendInfo friendInfo = GameServerAssist.getFriendService()
					.getFriendInfo(human.getHumanGuid(),
							invite.getInviteFriendId());
			escortInfo.setHelperId(invite.getInviteFriendId());
			escortInfo.setHelperLevel(friendInfo.getLevel());
			escortInfo.setHelperName(friendInfo.getRoleName());
			escortInfo.setHelpLevelDiff(friendInfo.getLevel()
					- human.getLevel());
			// 协助奖励金币
			float helpRewardCoinRate = templateManager
					.getHelpRewardCoinRate(escortInfo.getHelpLevelDiff());
			int helpRewardCoin = (int) (escortRewardCoin * helpRewardCoinRate);
			escortInfo.setHelpRewardCoin(helpRewardCoin);
			EscortHelp help = getHelpInfo(escortInfo.getHelperId());
			if (help == null) {
				help = new EscortHelp();
				help.setHumanGuid(escortInfo.getHelperId());
				help.setRemainHelpNum(templateManager.getConstantsTemplate()
						.getMaxHelpNum() - 1);
				helpMap.put(help.getHumanGuid(), help);
				dataService.insert(EscortConverters.helpToEntiy(help));
			} else {
				help.setRemainHelpNum(help.getRemainHelpNum() - 1);
				cache.addUpdate(help.getHumanGuid(),
						EscortConverters.helpToEntiy(help));
			}
		} else {
			// 清空协助好友的信息
			escortInfo.setHelperId(0L);
			escortInfo.setHelperLevel(0);
			escortInfo.setHelperName("");
			escortInfo.setHelpLevelDiff(0);
			escortInfo.setHelpRewardCoin(0);
		}
		if (invite != null) {
			// 邀请状态置为未邀请
			invite.setInviteState(EscortInviteSate.NOT_INVITE.getIndex());
			EscortInviteEntity inviteEntity = EscortConverters
					.inviteToEntity(invite);
			cache.addUpdate(invite.getHumanGuid(), inviteEntity);
		}

		escortInfo.setEscortState(EscortState.UNDERWAY.getIndex());
		escortInfoMap.put(human.getHumanGuid(), escortInfo);

		// 插入数据库
		if (needInsertDB) {
			dataService.insert(EscortConverters.escortInfoToEntity(escortInfo));
		} else {// 更新
			cache.addUpdate(human.getHumanGuid(),
					EscortConverters.escortInfoToEntity(escortInfo));
		}
		human.setEscortRemainNum(human.getEscortRemainNum() - 1);

		// 广播押运面板信息
		broadCastEscortPanelInfo();
	}

	/**
	 * 获取押运需要时间
	 */
	public long getEscortSpendTime(Human human) {
		int monsterType = getEscortMonsterType(human.getHumanGuid());
		EscortMonsterTemplate monsterTemplate = templateManager
				.getMonsterTemplate(monsterType);
		EscortRewardTemplate rewardTemplate = GameServerAssist
				.getEscortTemplateManager().getEscortRewardTemplate(
						human.getLevel());
		long spendTime = (long) (rewardTemplate.getRescortTime()
				* monsterTemplate.getEscortTimeRate() * TimeUtils.MIN);
		return spendTime;
	}

	/**
	 * 结束押运
	 */
	public void endEscort(EscortInfo escortInfo) {
		if (escortInfo.getEscortRewardCoin() > 0) {
			escortInfo.setEscortRewardState(EscortRewardState.HAS_NOT_GET
					.getIndex());
		}
		if (escortInfo.getHelperId() > 0 && escortInfo.getHelpRewardCoin() > 0) {
			EscortHelp help = getHelpInfo(escortInfo.getHelperId());
			if (help != null) {
				help.setRewardCoin(help.getRewardCoin()
						+ escortInfo.getHelpRewardCoin());
				help.setRewardState(EscortRewardState.HAS_NOT_GET.getIndex());
				updateHelpInfo(help);
			}
		}
		escortInfo.setEscortState(EscortState.END.getIndex());
		escortInfo.setEndTime(timeService.now());
		// 设置押运怪物品质
		Human escorter = GameServerAssist.getGameWorld().getSceneHumanManager()
				.getHumanByGuid(escortInfo.getEscortId());
		if (escorter != null) {
			VipPrivilegeTemplate vipTemplate = GameServerAssist
					.getVipPrivilegeTemplateManager().getVipTemplate(
							escorter.getVipLevel());
			setEscortMonsterType(escorter.getHumanGuid(),
					vipTemplate.getDefaultEscortMonsterType());
			// 发送事件
			escorter.getEventBus().fireEvent(new EscortEvent());
		} else {
			setEscortMonsterType(escortInfo.getOwnerId(), initMonsterType);
		}
		updateEscortInfo(escortInfo);

		// 广播押运面板信息
		broadCastEscortPanelInfo();
	}

	/**
	 * 军团祈福
	 */
	public void legionPray(Human human) {
		LegionPrayInfo prayInfo = getLegionPrayInfo(human);
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		boolean needInsertDB = false;
		if (prayInfo == null) {
			prayInfo = new LegionPrayInfo();
			needInsertDB = true;
		}
		prayInfo.setLegionId(legion.getId());
		prayInfo.setPrayMemberName(human.getName());
		long now = timeService.now();
		prayInfo.setPrayEndTime(now
				+ templateManager.getConstantsTemplate().getLegionPrayMinutes()
				* TimeUtils.MIN);
		prayInfo.setPrayRevenue(templateManager.getConstantsTemplate()
				.getLegionPrayRate());
		legionPrayInfoMap.put(legion.getId(), prayInfo);
		EscortLegionPrayEntity prayEntity = EscortConverters
				.legionPrayInfoToEntity(prayInfo);
		if (needInsertDB) {
			dataService.insert(prayEntity);
		} else {
			cache.addUpdate(legion.getId(), prayEntity);
		}
		GCEscortLegionPray msg = new GCEscortLegionPray();
		msg.setPrayInfo(prayInfo);
		human.sendMessage(msg);
	}

	/**
	 * 是否有军团祈福
	 */
	public boolean hasLegionPray(Human human) {
		LegionPrayInfo prayInfo = getLegionPrayInfo(human);
		if (prayInfo != null && prayInfo.getPrayEndTime() > timeService.now()) {
			return true;
		}
		return false;
	}

	/**
	 * 拦截回调
	 */
	public void robCallBack(Human human, long escortId, boolean isWin) {
		// 添加拦截CD
		long baseTime = GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getRobCdTime()
				* TimeUtils.MIN;
		long spendTime = human.getHumanCdManager().getSpendTime(
				CdType.ESCORT_ROB, baseTime);
		human.getHumanCdManager().addCd(CdType.ESCORT_ROB, spendTime);
		human.getHumanCdManager().snapCdQueueInfo(CdType.ESCORT_ROB);

		human.setEscortRobRemainNum(human.getEscortRobRemainNum() - 1);
		EscortInfo escortInfo = getEscortInfo(escortId);
		// 拦截结束
		escortInfo.setRobing(false);
		escortInfo.setRemainBeRobbedNum(escortInfo.getRemainBeRobbedNum() - 1);
		// 拦截胜利处理
		if (isWin) {
			// 拦截奖励 = 押运剩余金币 * 拦截等级差奖励金币系数
			int robLevelDiff = human.getLevel() - escortInfo.getOwnerLevel();
			float robRewardCoinRate = templateManager
					.getRobRewardCoinRate(robLevelDiff);
			int robRewardCoin = (int) (escortInfo.getEscortRewardCoin() * robRewardCoinRate);
			human.getWallet().addMoney(CurrencyType.COIN, robRewardCoin, true,
					MoneyLogReason.ESCORT_ROB_REWARD, "");
			escortInfo.setEscortRewardCoin(escortInfo.getEscortRewardCoin()
					- robRewardCoin);
			cache.addUpdate(escortId,
					EscortConverters.escortInfoToEntity(escortInfo));
			// 增加拦截日志
			EscortLog escortLog = new EscortLog();
			escortLog.setFirstId(human.getHumanGuid());
			escortLog.setFirstName(human.getName());
			escortLog.setSecondId(escortInfo.getOwnerId());
			escortLog.setSecondName(escortInfo.getOwnerName());
			escortLog.setMonserName(templateManager.getMonsterTemplate(
					escortInfo.getMonsterType()).getMonsterName());
			escortLog.setRobCoin(robRewardCoin);
			addRobLog(escortLog);
			// 拦截排行榜处理
			EscortRobRank robRank = getSelfRobRank(human.getHumanGuid());
			if (robRank == null) {
				robRank = new EscortRobRank();
				robRank.setRobberId(human.getHumanGuid());
				robRank.setRobberName(human.getName());
				robRank.setTodayRobCoin(robRewardCoin);
				robRankMap.put(human.getHumanGuid(), robRank);
				dataService.insert(EscortConverters.robRankToEntity(robRank));
			} else {
				robRank.setTodayRobCoin(robRank.getTodayRobCoin()
						+ robRewardCoin);
				updateRobRank(robRank);
			}
			// 刷新今日拦截排行榜
			refreshTodayRank();
		}
	}

	/**
	 * 添加拦截日志
	 */
	private void addRobLog(final EscortLog escortLog) {
		dataService.insert(EscortConverters.logToEntity(escortLog),
				new IDBCallback<Serializable>() {

					@Override
					public void onSucceed(Serializable result) {
						long id = (Long) result;
						escortLog.setId(id);
						escortLogList.addFirst(escortLog);
						if (escortLogList.size() > SharedConstants.ESCORT_ROB_LOG_NUM) {
							dataService.delete(EscortConverters
									.logToEntity(escortLogList.getLast()));
							escortLogList.removeLast();
						}
					}

					@Override
					public void onFailed(String errorMsg) {

					}
				});
	}

	/**
	 * 刷新今日拦截排行榜
	 */
	public void refreshTodayRank() {
		// 首先清理掉数据
		todayRankList.clear();
		List<RobRankInfo> rankInfoList = new ArrayList<RobRankInfo>();
		for (long robberId : robRankMap.keySet()) {
			EscortRobRank robRank = robRankMap.get(robberId);
			if (robRank.getTodayRobCoin() > 0) {
				RobRankInfo rankInfo = new RobRankInfo();
				rankInfo.setRobberId(robberId);
				rankInfo.setRobberName(robRank.getRobberName());
				rankInfo.setRobCoin(robRank.getTodayRobCoin());
				rankInfoList.add(rankInfo);
			}
		}
		if (rankInfoList.size() > 0) {
			// 按拦截金币排序
			Collections.sort(rankInfoList);
			// 设置排行
			int rank = 1;
			for (RobRankInfo rankInfo : rankInfoList) {
				if (rank <= templateManager.getMaxRobRank()) {
					rankInfo.setRank(rank);
					rankInfo.setRewardCoin(templateManager
							.getRobRankRewardCoin(rank));
					todayRankList.add(rankInfo);
				}
				rank++;
			}
		}
	}

	/**
	 * 零点生成昨日拦截排行榜
	 */
	public void generateYesterdayRank() {
		// 首先清理掉榜单数据
		yesterdayRankList.clear();
		// 由今日榜单转化过来
		List<RobRankInfo> todayList = getTodayRobRankList();
		for (RobRankInfo todayRank : todayList) {
			RobRankInfo yestRank = new RobRankInfo();
			yestRank.setRobberName(todayRank.getRobberName());
			yestRank.setRobberId(todayRank.getRobberId());
			yestRank.setRank(todayRank.getRank());
			yestRank.setRewardCoin(todayRank.getRewardCoin());
			yestRank.setRobCoin(todayRank.getRobCoin());
			yesterdayRankList.add(yestRank);
		}
	}

	/**
	 * 初始化昨日拦截排行榜
	 */
	public void initYesterdayRankList() {
		// 首先清理掉数据
		yesterdayRankList.clear();
		List<RobRankInfo> rankInfoList = new ArrayList<RobRankInfo>();
		for (long robberId : robRankMap.keySet()) {
			EscortRobRank robRank = robRankMap.get(robberId);
			if (robRank.getYesterdayRobCoin() > 0) {
				RobRankInfo rankInfo = new RobRankInfo();
				rankInfo.setRobberId(robberId);
				rankInfo.setRobberName(robRank.getRobberName());
				rankInfo.setRobCoin(robRank.getYesterdayRobCoin());
				rankInfoList.add(rankInfo);
			}
		}
		if (rankInfoList.size() > 0) {
			// 按拦截金币排序
			Collections.sort(rankInfoList);
			// 设置排行
			int rank = 1;
			for (RobRankInfo rankInfo : rankInfoList) {
				if (rank <= templateManager.getMaxRobRank()) {
					rankInfo.setRank(rank);
					rankInfo.setRewardCoin(templateManager
							.getRobRankRewardCoin(rank));
					yesterdayRankList.add(rankInfo);
				}
				rank++;
			}
		}
	}

	/**
	 * 获取角色拦截榜个人信息
	 */
	public EscortRobRank getSelfRobRank(long humanGuid) {
		return robRankMap.get(humanGuid);
	}

	/**
	 * 获得今日排行榜数据
	 */
	public RobRankInfo getTodayRobRank(long humanGuid) {
		for (RobRankInfo rankInfo : todayRankList) {
			if (rankInfo.getRobberId() == humanGuid) {
				return rankInfo;
			}
		}
		return null;
	}

	/**
	 * 获得昨日排行榜数据
	 */
	public RobRankInfo getYesterdayRobRank(long humanGuid) {
		for (RobRankInfo rankInfo : yesterdayRankList) {
			if (rankInfo.getRobberId() == humanGuid) {
				return rankInfo;
			}
		}
		return null;
	}

	/**
	 * 获得今日排行榜列表
	 */
	public List<RobRankInfo> getTodayRobRankList() {
		return todayRankList;
	}

	/**
	 * 获得昨日排行榜列表
	 */
	public List<RobRankInfo> getYesterdayRobRankList() {
		return yesterdayRankList;
	}

	/**
	 * 更新拦截排行数据
	 */
	public void updateRobRank(EscortRobRank robRank) {
		cache.addUpdate(robRank.getRobberId(),
				EscortConverters.robRankToEntity(robRank));
	}

	/**
	 * 是否有押运奖励
	 */
	public boolean hasEscortReward(Human human) {
		EscortInfo escortInfo = getEscortInfo(human.getHumanGuid());
		if (escortInfo != null
				&& escortInfo.getEscortRewardState() == EscortRewardState.HAS_NOT_GET
						.getIndex()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否有协助押运奖励
	 */
	public boolean hasHelpReward(Human human) {
		EscortHelp help = getHelpInfo(human.getHumanGuid());
		if (help != null
				&& help.getRewardState() == EscortRewardState.HAS_NOT_GET
						.getIndex()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否有拦截榜奖励
	 */
	public boolean hasRobRankReward(Human human) {
		EscortRobRank robRank = getSelfRobRank(human.getHumanGuid());
		if (robRank != null
				&& robRank.getRewardState() == EscortRewardState.HAS_NOT_GET
						.getIndex()) {
			return true;
		}
		return false;
	}

	/**
	 * 刷新押运列表，到时结束
	 */
	public void refreshEscortList() {
		for (long escortId : escortInfoMap.keySet()) {
			EscortInfo escortInfo = escortInfoMap.get(escortId);
			if (escortInfo.getEscortState() == EscortState.UNDERWAY.getIndex()
					&& escortInfo.getEndTime() < timeService.now()) {
				endEscort(escortInfo);
			}
		}
	}

	/**
	 * 重置每日排行数据 + 协助次数
	 */
	public void resetDailyRobRankData() {
		for (long robberId : robRankMap.keySet()) {
			EscortRobRank robRank = robRankMap.get(robberId);
			RobRankInfo todayRankInfo = getTodayRobRank(robberId);
			// 根据今日拦截排名，更新奖励状态
			if (todayRankInfo != null && todayRankInfo.getRank() > 0) {
				robRank.setRewardState(EscortRewardState.HAS_NOT_GET.getIndex());
			} else {
				robRank.setRewardState(EscortRewardState.NO_REWARD.getIndex());
			}
			// 今日拦截数据变为昨日的拦截数据
			robRank.setYesterdayRobCoin(robRank.getTodayRobCoin());
			// 今日数据置0
			robRank.setTodayRobCoin(0);
			cache.addUpdate(robberId, EscortConverters.robRankToEntity(robRank));
		}
		// 生成昨日拦截排行榜
		generateYesterdayRank();
		// 清理掉今日拦截排行榜
		todayRankList.clear();

		// 重置协助次数
		for (long helperId : helpMap.keySet()) {
			helpMap.get(helperId).setRemainHelpNum(
					templateManager.getConstantsTemplate().getMaxHelpNum());
		}
	}

	/**
	 * 获取押运怪物类型
	 */
	public int getEscortMonsterType(long humanGuid) {
		EscortInfo escortInfo = getEscortInfo(humanGuid);
		if (escortInfo != null) {
			return escortInfo.getMonsterType();
		}
		return initMonsterType;
	}

	/**
	 * 设置押运怪物类型
	 */
	public void setEscortMonsterType(long humanGuid, int monsterType) {
		EscortInfo escortInfo = getEscortInfo(humanGuid);
		if (escortInfo == null) {
			// 简易生成escortInfo，只是为了保存押运怪物类型；在开始押运时，会生成详细数据
			escortInfo = new EscortInfo();
			escortInfo.setEscortId(humanGuid);
			escortInfo.setOwnerId(humanGuid);
			escortInfo.setMonsterType(monsterType);
			escortInfo.setEndTime(timeService.now());
			escortInfo.setEscortState(EscortState.END.getIndex());
			dataService.insert(EscortConverters.escortInfoToEntity(escortInfo));
		} else {
			escortInfo.setMonsterType(monsterType);
			cache.addUpdate(humanGuid,
					EscortConverters.escortInfoToEntity(escortInfo));
		}
		escortInfoMap.put(humanGuid, escortInfo);
	}

	/**
	 * 跑商固定时间段收益加成开始
	 */
	@Override
	public void onStart() {
		String content = GameServerAssist.getSysLangService().read(
				LangConstants.ESCORT_AMEND_START);
		GameServerAssist.getBulletinManager().sendSystemBulletin(content);
	}

	/**
	 * 跑商固定时间段收益加成结束
	 */
	@Override
	public void onStop() {

	}

	/**
	 * 获取跑商收益加成剩余时间
	 */
	private long getEscortAmendRemainTime() {
		return GameServerAssist.getGlobalActivityManager().getRemainTime(
				ActivityType.ESCORT_AMEND);
	}
}
