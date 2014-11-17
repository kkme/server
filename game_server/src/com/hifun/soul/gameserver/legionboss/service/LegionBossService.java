package com.hifun.soul.gameserver.legionboss.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.LegionBossEntity;
import com.hifun.soul.gamedb.entity.LegionBossRoleEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.activity.ActivityState;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.GlobalActivityManager;
import com.hifun.soul.gameserver.activity.ITimingActivityManager;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legionboss.LegionBossInfo;
import com.hifun.soul.gameserver.legionboss.LegionBossLegionInfo;
import com.hifun.soul.gameserver.legionboss.LegionBossRoleInfo;
import com.hifun.soul.gameserver.legionboss.LegionBossState;
import com.hifun.soul.gameserver.legionboss.converter.BossInfoToEntityConverter;
import com.hifun.soul.gameserver.legionboss.converter.BossRoleInfoToEntityConverter;
import com.hifun.soul.gameserver.legionboss.msg.GCHasLegionBossReward;
import com.hifun.soul.gameserver.legionboss.msg.GCJoinLegionBossWar;
import com.hifun.soul.gameserver.legionboss.template.LegionBossTemplate;
import com.hifun.soul.gameserver.legionmine.enums.JoinLegionType;
import com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.MonsterFactory;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

@Scope("singleton")
@Component
public class LegionBossService implements IInitializeRequired,
		ITimingActivityManager, ICachableComponent {
	/** boss战开始后让玩家等待的时间 */
	private static final long LEGION_BOSS_WAIT_TIME = 2 * 60 * 1000;
	private Logger logger = Loggers.LEGION_BOSS_LOGGER;
	@Autowired
	private IDataService dataService;
	@Autowired
	private MonsterFactory monsterFactory;
	@Autowired
	private SysLangService sysLangService;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private BulletinManager bulletinManager;
	@Autowired
	private GameWorld sceneManager;
	@Autowired
	private GlobalActivityManager globalActivityManager;
	@Autowired
	private SystemTimeService systemTimeService;
	/** boss信息 */
	private volatile LegionBossInfo boss = null;
	/** Boss模版 */
	private LegionBossTemplate bossTemplate = null;
	private GlobalLegionManager globalLegionManager;
	/** 参与boss战的玩家信息 */
	private Map<Long, LegionBossRoleInfo> roleInfoMap = new ConcurrentHashMap<Long, LegionBossRoleInfo>();
	/** bossInfo转化器 */
	private BossInfoToEntityConverter bossInfoConverter = new BossInfoToEntityConverter();
	/** bossRoleInfo转化器 */
	private BossRoleInfoToEntityConverter bossRoleInfoConverter = new BossRoleInfoToEntityConverter();
	private long beginTime;
	/** 击杀boss的人 */
	private Human killer;
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Integer, IEntity> bossCache = new CacheEntry<Integer, IEntity>();
	private CacheEntry<Long, IEntity> bossRoleCache = new CacheEntry<Long, IEntity>();
	private LegionBossRoleInfoSorter sorter = new LegionBossRoleInfoSorter();

	@Override
	public void init() {
		cacheManager.registerOtherCachableComponent(this);
		bossTemplate = templateService.get(
				SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID,
				LegionBossTemplate.class);
		globalLegionManager = GameServerAssist.getGlobalLegionManager();
	}

	/**
	 * 从数据库中加载
	 * 
	 */
	public void start(IDBService dbService) {
		// 加载boss信息
		loadBossInfo(dbService);
		// 加载参与boss战玩家信息
		loadBossRoleInfo(dbService);
	}

	/**
	 * 加载boss信息
	 * 
	 */
	private void loadBossInfo(IDBService dbService) {
		List<?> result = dbService.findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_LEGION_BOSS_INFO,
				new String[] { "id" },
				new Object[] { bossTemplate.getBossId() });
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionBossEntity> results = (List<LegionBossEntity>) result;
			try {
				Monster monster = monsterFactory
						.createMonster(getBossTemplate().getBossId());
				LegionBossEntity bossEntity = results.get(0);
				if (boss == null) {
					boss = new LegionBossInfo();
				}
				boss.setBossState(bossEntity.getBossState());
				boss.setBossId((Integer) bossEntity.getId());
				boss.setIcon(monster.getTemplate().getPicId());
				boss.setJoinPeopleNum(bossEntity.getJoinPeopleNum());
				boss.setKillerId(bossEntity.getKillId());
				boss.setLevel(monster.getTemplate().getLevel());
				boss.setName(monster.getTemplate().getName());
				boss.setRemainBlood(bossEntity.getRemainBlood());
				boss.setTotalBlood(monster.getTemplate().getHp());
			} catch (Exception e) {
				logger.error("get lastResetRankRewardTime error!");
			}
		}
	}

	/**
	 * 加载参与boss战玩家的信息
	 * 
	 */
	private void loadBossRoleInfo(IDBService dbService) {
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_LEGION_BOSS_ROLE_INFO);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionBossRoleEntity> results = (List<LegionBossRoleEntity>) result;
			for (LegionBossRoleEntity entity : results) {
				LegionBossRoleInfo bossRoleInfo = new LegionBossRoleInfo();
				bossRoleInfo
						.setChargedstrikeRate(entity.getChargedstrikeRate());
				bossRoleInfo.setDamage(entity.getDamage());
				bossRoleInfo.setEncourageRate(entity.getEncourageRate());
				bossRoleInfo.setHasDamageReward(entity.getHasDamageReward());
				bossRoleInfo.setHasKillReward(entity.getHasKillReward());
				bossRoleInfo.setHasRankReward(entity.getHasRankReward());
				bossRoleInfo.setHumanGuid((Long) entity.getId());
				bossRoleInfo.setName(entity.getName());
				bossRoleInfo.setRank(entity.getRank());
				bossRoleInfo.setJoin(entity.getIsJoin());
				bossRoleInfo.setStageReward(entity.getStageReward());
				roleInfoMap.put(bossRoleInfo.getHumanGuid(), bossRoleInfo);
			}
			// 更新排名
			// updateBossWarRankInfo();
			// 同步数据库
			// for (LegionBossRoleInfo bossRoleInfo : roleInfoMap.values()) {
			// updateBossRoleInfoToDB(bossRoleInfo);
			// }
		}
	}

	/**
	 * 获取boss的信息
	 * 
	 * @return
	 */
	public LegionBossInfo getBossInfo() {
		return boss;
	}

	/**
	 * 获取boss战中角色的信息
	 * 
	 * @param humanGuid
	 * @return
	 */
	public LegionBossRoleInfo getBossRoleInfo(long humanGuid) {
		return roleInfoMap.get(humanGuid);
	}

	/**
	 * 标记更新
	 * 
	 * @param bossRoleInfo
	 */
	public void updateBossRoleInfoToDB(LegionBossRoleInfo bossRoleInfo) {
		if (bossRoleInfo == null) {
			return;
		}
		bossRoleCache.addUpdate(bossRoleInfo.getHumanGuid(),
				bossRoleInfoConverter.convert(bossRoleInfo));
	}

	/**
	 * 添加到boss战玩家列表
	 * 
	 * @param bossRoleInfo
	 */
	public void addBossRoleInfo(LegionBossRoleInfo bossRoleInfo) {
		roleInfoMap.put(bossRoleInfo.getHumanGuid(), bossRoleInfo);
		dataService.insert(bossRoleInfoConverter.convert(bossRoleInfo));
		boss.setJoinPeopleNum(getJoinBossRoleInfos().size());
		updateBossInfoToDB();
	}

	/**
	 * 更新boss信息
	 */
	public void updateBossInfoToDB() {
		bossCache.addUpdate(boss.getBossId(), bossInfoConverter.convert(boss));
	}

	/**
	 * 根据humanGuid返回自己在boss战中的信息
	 * 
	 * @param humanGuid
	 * @return
	 */
	public String getDamageDesc(long humanGuid) {
		LegionBossRoleInfo bossRoleInfo = getBossRoleInfo(humanGuid);
		if (bossRoleInfo == null) {
			return "";
		}
		String damageDesc = sysLangService.read(
				LangConstants.SELF_BOSS_DAMAGE_DESC, bossRoleInfo.getDamage());
		return damageDesc;
	}

	/**
	 * 军团伤害描述
	 * 
	 * @param humanGuid
	 * @return
	 */
	public String getLegionDamageDesc(long humanGuid) {
		LegionBossRoleInfo bossRoleInfo = getBossRoleInfo(humanGuid);
		if (bossRoleInfo == null) {
			return "";
		}
		Legion legion = globalLegionManager.getLegion(humanGuid);
		if (legion == null) {
			return "";
		}
		String damageDesc = sysLangService.read(
				LangConstants.LEGION_BOSS_DAMAGE_DESC, legion.getLegionName(),
				bossRoleInfo.getDamage());
		return damageDesc;
	}

	/**
	 * 返回boss战的军团成员排行榜信息
	 * 
	 * @return
	 */
	public List<String> getMemberDamageRankings() {
		// 重新排名
		List<LegionBossRoleInfo> roleInfoList = getJoinBossRoleInfos();
		Collections.sort(roleInfoList, sorter);
		List<String> rankings = new ArrayList<String>();
		for (int i = 0; i < SharedConstants.LEGION_BOSS_RANKING_SIZE
				&& i < roleInfoList.size(); i++) {
			LegionBossRoleInfo bossRoleInfo = roleInfoList.get(i);
			if (bossRoleInfo != null && bossRoleInfo.getDamage() > 0) {
				String desc = sysLangService.read(
						LangConstants.MEMBER_BOSS_RANKING,
						bossRoleInfo.getName(), bossRoleInfo.getDamage());
				rankings.add(desc);
			}
		}
		return rankings;
	}

	/**
	 * 返回boss战的军团排行榜信息
	 * 
	 * @return
	 */
	public List<String> getLegionDamageRankings() {
		List<String> rankings = new ArrayList<String>();
		List<LegionBossLegionInfo> legionRankInfos = getJoinBossLegionInfos();
		for (LegionBossLegionInfo legionInfo : legionRankInfos) {
			String desc = sysLangService.read(
					LangConstants.LEGION_BOSS_RANKING,
					legionInfo.getLegionName(), legionInfo.getDamage());
			rankings.add(desc);
		}
		return rankings;
	}

	/**
	 * 返回boss战的配置模版
	 * 
	 * @return
	 */
	public LegionBossTemplate getBossTemplate() {
		if (bossTemplate == null) {
			bossTemplate = templateService.get(
					SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID,
					LegionBossTemplate.class);
		}
		return bossTemplate;
	}

	/**
	 * 根据充能点计算应该对boss造成的伤害
	 * 
	 * @param chargestrike
	 * @return
	 */
	public int getChargestrikeDamage(int attack, int chargestrike) {
		int damage = 0;
		LegionBossTemplate bossTemplate = getBossTemplate();
		if (bossTemplate == null) {
			return damage;
		}
		if (chargestrike >= bossTemplate.getMaxChargedRate()) {
			damage = (int) (attack * bossTemplate.getMaxChargedDamage() / SharedConstants.DEFAULT_FLOAT_BASE);
		} else {
			damage = (int) (attack * chargestrike
					* bossTemplate.getChargedDamage() / SharedConstants.DEFAULT_FLOAT_BASE);
		}
		return damage;
	}

	/**
	 * boss造成伤害之后的处理
	 * 
	 * @param human
	 * @param damage
	 */
	public void damageBoss(Human human, int damage, boolean isWin) {
		LegionBossRoleInfo bossRoleInfo = getBossRoleInfo(human.getHumanGuid());
		if (bossRoleInfo == null) {
			return;
		}
		// 伤害值小于等于0直接返回
		if (damage <= 0) {
			return;
		}
		// boss战如果已经结束，那这一次的伤害不计算在内
		if (LegionBossState.LIVE.getIndex() != getBossInfo().getBossState()) {
			return;
		}
		// 判断此次攻击是否击败boss
		if (damage >= getBossInfo().getRemainBlood()) {
			if (isWin) {
				// 击杀boss的人
				killer = human;
				// 修改boss的血量
				getBossInfo().setRemainBlood(0);
				getBossInfo().setKillerId(human.getHumanGuid());
				updateBossInfoToDB();
				// 修改自己的伤害值
				bossRoleInfo.setDamage(bossRoleInfo.getDamage() + damage);
				// 更新排名
				// updateBossWarRankInfo();
				// 同步数据库
				updateBossRoleInfoToDB(bossRoleInfo);
				// 修改活动的状态
				globalActivityManager.abortAcivity(ActivityType.LEGION_BOSS);
			}
			// 如果打败了，则默认再给boss加一滴血
			else {
				// 修改boss剩余血量
				getBossInfo().setRemainBlood(1);
				updateBossInfoToDB();
				// 修改自己的伤害值
				bossRoleInfo.setDamage(bossRoleInfo.getDamage() + damage);
				// 重新排名
				// updateBossWarRankInfo();
				// 同步数据库
				updateBossRoleInfoToDB(bossRoleInfo);
			}
		} else {
			// 修改boss剩余血量
			getBossInfo().setRemainBlood(
					getBossInfo().getRemainBlood() - damage);
			updateBossInfoToDB();
			// 修改自己的伤害值
			bossRoleInfo.setDamage(bossRoleInfo.getDamage() + damage);
			// 重新排名
			// updateBossWarRankInfo();
			// 同步数据库
			updateBossRoleInfoToDB(bossRoleInfo);
		}
		// 阶段性伤害奖励
		stageDamageReward(human, damage);

		// 更新面板
		updateBossWarPanel(human, bossRoleInfo);

	}

	/**
	 * 阶段性伤害奖励
	 */
	private void stageDamageReward(Human human, int damage) {
		LegionBossRoleInfo bossRoleInfo = getBossRoleInfo(human.getHumanGuid());
		if (bossRoleInfo == null) {
			return;
		}
		LegionBossTemplate bossTemplate = getBossTemplate();
		double maxTotalStageReward = bossTemplate.getMaxTotalStageReward();
		double totalStageReward = bossRoleInfo.getStageReward();
		if (totalStageReward >= maxTotalStageReward) {
			return;
		}
		double reward = damage * (maxTotalStageReward / boss.getTotalBlood());
		if (reward > bossTemplate.getMaxSingleStageReward() * human.getLevel()) {
			reward = bossTemplate.getMaxSingleStageReward() * human.getLevel();
		}
		if (reward < bossTemplate.getMinSingleStageReward()) {
			reward = bossTemplate.getMinSingleStageReward();
		}
		totalStageReward += reward;
		if (totalStageReward > maxTotalStageReward) {
			reward = maxTotalStageReward - totalStageReward;
			totalStageReward = maxTotalStageReward;
		}
		bossRoleInfo.setStageReward((int) totalStageReward);
		updateBossRoleInfoToDB(bossRoleInfo);
		human.sendImportantMessage(LangConstants.BOSS_STAGE_REWARD,
				(int) reward);
	}

	/**
	 * 更新boss战排名
	 */
	public void updateBossWarRankInfo() {
		// List<LegionBossRoleInfo> roleInfoList = getJoinBossRoleInfos();
		// Collections.sort(roleInfoList, sorter);
		// for (int i = 0; i < roleInfoList.size(); i++) {
		// roleInfoList.get(i).setRank(i + 1);
		// }
	}

	/**
	 * 更新奖励状态
	 */
	private void updateBosswarReward(List<LegionBossRoleInfo> roleInfoList) {
		SceneHumanManager humanManager = GameServerAssist.getGameWorld()
				.getSceneHumanManager();
		// 修改奖励领取状态
		for (int i = 0; i < roleInfoList.size(); i++) {
			LegionBossRoleInfo bossRoleInfo = roleInfoList.get(i);
			if (bossRoleInfo.getDamage() <= 0) {
				break;
			}
			bossRoleInfo.setRank(i + 1);
			bossRoleInfo.setChargedstrikeRate(0);
			bossRoleInfo.setEncourageRate(0);
			bossRoleInfo.setHasKillReward(bossRoleInfo.getHumanGuid() == boss
					.getKillerId());
			bossRoleInfo.setHasDamageReward(getBossTemplate()
					.isHasDamageReward() && bossRoleInfo.getDamage() > 0);
			updateBossRoleInfoToDB(bossRoleInfo);
			// 在线玩家推送奖励
			Human onlineHuman = humanManager.getHumanByGuid(bossRoleInfo
					.getHumanGuid());
			if (onlineHuman != null) {
				onlineHuman.getHumanLegionBossManager().sendRewardNotify();
			}
		}
	}

	/**
	 * 更新面板
	 * 
	 * @param human
	 * @param bossRoleInfo
	 */
	public void updateBossWarPanel(Human human, LegionBossRoleInfo bossRoleInfo) {
		human.getHumanCdManager().snapCdQueueInfo(CdType.LEGION_BOSS_CHARGED);
		human.getHumanCdManager().snapCdQueueInfo(CdType.LEGION_BOSS_ATTACK);
		GCJoinLegionBossWar gcMsg = new GCJoinLegionBossWar();
		gcMsg.setBossInfo(getBossInfo());
		gcMsg.setRemainTime((int) (globalActivityManager
				.getRemainTime(ActivityType.LEGION_BOSS) / TimeUtils.SECOND));
		gcMsg.setChargedstrikeRate(bossRoleInfo.getChargedstrikeRate() * 100
				/ bossTemplate.getMaxChargedRate());
		gcMsg.setSelfDamageDesc(getDamageDesc(human.getHumanGuid()));
		gcMsg.setLegionDamageDesc(getLegionDamageDesc(human.getHumanGuid()));
		gcMsg.setMemberDamageRankings(getMemberDamageRankings().toArray(
				new String[0]));
		gcMsg.setLegionDamageRankings(getLegionDamageRankings().toArray(
				new String[0]));
		gcMsg.setEncourageRate(bossRoleInfo.getEncourageRate() * 100
				/ bossTemplate.getMaxEncourageDamage());
		gcMsg.setEncouragedIsFull(bossRoleInfo.getEncourageRate() >= bossTemplate
				.getMaxEncourageDamage());
		gcMsg.setChargedIsFull(bossRoleInfo.getChargedstrikeRate() >= bossTemplate
				.getMaxChargedRate());
		gcMsg.setMeditation(bossTemplate.getMeditation());
		gcMsg.setCrystal(bossTemplate.getCrystal());
		gcMsg.setBossWarDesc(globalActivityManager.getActivity(
				ActivityType.LEGION_BOSS).getDesc());
		gcMsg.setBossState(getBossInfo().getBossState());
		long waitCdTime = LEGION_BOSS_WAIT_TIME
				- (systemTimeService.now() - beginTime);
		if (waitCdTime < 0) {
			waitCdTime = 0;
		}
		gcMsg.setWaitCDTime(new Float(waitCdTime).intValue());
		human.sendMessage(gcMsg);
	}

	/**
	 * 判断boss战是否开启
	 * 
	 * @param activityTemplateManager
	 * @param bossWarService
	 * @return
	 */
	public boolean bossWarIsOpen() {
		// 判断当前是否正处在boss战的时间段
		ActivityState activityState = globalActivityManager
				.getActivityState(ActivityType.LEGION_BOSS);
		if (activityState == null || activityState != ActivityState.OPEN) {
			return false;
		}
		// 获取boss信息
		LegionBossInfo bossInfo = getBossInfo();
		if (bossInfo == null) {
			return false;
		}
		// boss当前的状态是否可以攻击
		if (bossInfo.getBossState() != LegionBossState.LIVE.getIndex()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否能挑战boss了
	 * 
	 * @return
	 */
	public boolean bossWarReadyForFight() {
		// boss战准备时间CD未冷却
		if (systemTimeService.now() - beginTime <= LEGION_BOSS_WAIT_TIME) {
			return false;
		}
		return true;
	}

	/**
	 * 获取boss的副本
	 * 
	 * @return
	 */
	public Monster getMonster() {
		Monster monster = null;
		monster = monsterFactory.createMonster(getBossInfo().getBossId());
		return monster;
	}

	public void onEnterWar(Human human) {
		// 如果没有加入军团
		if (globalLegionManager.getLegion(human.getHumanGuid()) == null) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_NOT_JOINED);
			return;
		}
		// 判断当前是否正处在boss战的时间段
		if (!bossWarIsOpen()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_NOT_OPEN);
			return;
		}
		// 获取自己在boss战中的信息
		LegionBossRoleInfo bossRoleInfo = getBossRoleInfo(human.getHumanGuid());
		if (bossRoleInfo == null) {
			bossRoleInfo = new LegionBossRoleInfo();
			bossRoleInfo.setHumanGuid(human.getHumanGuid());
			bossRoleInfo.setName(human.getName());
			bossRoleInfo.setDamage(0);
			bossRoleInfo.setChargedstrikeRate(0);
			bossRoleInfo.setEncourageRate(0);
			bossRoleInfo.setHasDamageReward(false);
			bossRoleInfo.setHasKillReward(false);
			bossRoleInfo.setHasRankReward(false);
			bossRoleInfo.setRank(getJoinBossRoleInfos().size() + 1);
			bossRoleInfo.setJoin(true);
			addBossRoleInfo(bossRoleInfo);
		} else {
			if (!bossRoleInfo.isJoin()) {
				bossRoleInfo.setJoin(true);
				updateBossRoleInfoToDB(bossRoleInfo);
				boss.setJoinPeopleNum(getJoinBossRoleInfos().size());
				updateBossInfoToDB();
			}
		}
		// 更新面板
		updateBossWarPanel(human, bossRoleInfo);
	}

	@Override
	public void onStart() {
		logger.info("legion boss war started!");
		// boss战开启
		// 如果是第一次开启boss战
		if (boss == null) {
			boss = new LegionBossInfo();
			Monster monster = monsterFactory.createMonster(getBossTemplate()
					.getBossId());
			boss.setBossId(monster.getTemplate().getId());
			boss.setBossState(LegionBossState.LIVE.getIndex());
			boss.setIcon(monster.getTemplate().getPicId());
			boss.setJoinPeopleNum(0);
			boss.setKillerId(0L);
			boss.setLevel(monster.getTemplate().getLevel());
			boss.setName(monster.getTemplate().getName());
			boss.setRemainBlood(monster.getTemplate().getHp());
			boss.setTotalBlood(monster.getTemplate().getHp());
			dataService.insert(bossInfoConverter.convert(boss), null);
			// 清除bossRoleInfo
			resetBossRoleInfo();
			// 设置开始时间
			beginTime = systemTimeService.now();
			// 发送公告
			sendBossWarOpenBulletin();
		}
		// 如果是新一次的boss重新开启
		else {
			boss.setBossState(LegionBossState.LIVE.getIndex());
			boss.setJoinPeopleNum(0);
			boss.setKillerId(0L);
			boss.setRemainBlood(boss.getTotalBlood());
			updateBossInfoToDB();
			// 清除bossRoleInfo
			resetBossRoleInfo();
			// 设置开始时间
			beginTime = systemTimeService.now();
			// 发送公告
			sendBossWarOpenBulletin();
		}
	}

	/**
	 * 获取参与boss战的所有军团成员
	 * 
	 * @return
	 */
	private List<LegionBossRoleInfo> getJoinBossRoleInfos() {
		List<LegionBossRoleInfo> bossRoleInfos = new ArrayList<LegionBossRoleInfo>();
		for (LegionBossRoleInfo roleInfo : roleInfoMap.values()) {
			if (roleInfo != null && roleInfo.isJoin()) {
				bossRoleInfos.add(roleInfo);
			}
		}
		return bossRoleInfos;
	}

	/**
	 * 获取参与boss战的所有军团
	 * 
	 * @return
	 */
	private List<LegionBossLegionInfo> getJoinBossLegionInfos() {
		List<LegionBossRoleInfo> roleInfoList = getJoinBossRoleInfos();
		Collections.sort(roleInfoList, sorter);
		Map<Long, Integer> legionDamageMap = new HashMap<Long, Integer>();
		for (int i = 0; i < roleInfoList.size(); i++) {
			LegionBossRoleInfo bossRoleInfo = roleInfoList.get(i);
			if (bossRoleInfo == null || bossRoleInfo.getDamage() <= 0) {
				continue;
			}
			Legion legion = globalLegionManager.getLegion(bossRoleInfo
					.getHumanGuid());
			if (legion == null) {
				continue;
			}
			int oldDamage = 0;
			if (legionDamageMap.get(legion.getId()) != null) {
				oldDamage = legionDamageMap.get(legion.getId());
			}
			legionDamageMap.put(legion.getId(),
					oldDamage + bossRoleInfo.getDamage());
		}
		List<LegionBossLegionInfo> legionDamageList = new ArrayList<LegionBossLegionInfo>();
		for (long legionId : legionDamageMap.keySet()) {
			LegionBossLegionInfo legionInfo = new LegionBossLegionInfo();
			legionInfo.setLegionId(legionId);
			legionInfo.setDamage(legionDamageMap.get(legionId));
			legionDamageList.add(legionInfo);
		}
		Collections.sort(legionDamageList,
				new Comparator<LegionBossLegionInfo>() {

					@Override
					public int compare(LegionBossLegionInfo o1,
							LegionBossLegionInfo o2) {
						return o2.getDamage() - o1.getDamage();
					}
				});
		for (int i = 0; i < legionDamageList.size()
				&& i < SharedConstants.LEGION_BOSS_RANKING_SIZE; i++) {
			LegionBossLegionInfo legionDamageInfo = legionDamageList.get(i);
			legionDamageInfo.setLegionName(globalLegionManager.getLegionById(
					legionDamageInfo.getLegionId()).getLegionName());
			legionDamageInfo.setRank(i + 1);
		}
		return legionDamageList;
	}

	/**
	 * 重置上次打boss信息
	 */
	private void resetBossRoleInfo() {
		// 清除bossRoleInfo
		for (LegionBossRoleInfo roleInfo : getJoinBossRoleInfos()) {
			roleInfo.setChargedstrikeRate(0);
			roleInfo.setDamage(0);
			roleInfo.setEncourageRate(0);
			roleInfo.setHasDamageReward(false);
			roleInfo.setHasKillReward(false);
			roleInfo.setHasRankReward(false);
			roleInfo.setJoin(false);
			roleInfo.setRank(-1);
			roleInfoMap.put(roleInfo.getHumanGuid(), roleInfo);
			bossRoleCache.addUpdate(roleInfo.getHumanGuid(),
					bossRoleInfoConverter.convert(roleInfo));
		}
	}

	/**
	 * 发送boss战活动开启公告
	 */
	private void sendBossWarOpenBulletin() {
		String content = sysLangService.read(LangConstants.LEGION_BOSS_OPEN);
		bulletinManager.sendSystemBulletin(content);
	}

	@Override
	public void onStop() {
		if (boss != null
				&& boss.getBossState() == LegionBossState.LIVE.getIndex()) {
			// 设置boss状态
			if (boss.getRemainBlood() > 0) {
				boss.setBossState(LegionBossState.ESCAPE.getIndex());
				boss.setRemainBlood(0);
			} else {
				boss.setBossState(LegionBossState.DEAD.getIndex());
			}
			updateBossInfoToDB();
			// 公告通知boss战已经结束
			if (killer == null) {
				// 公告通知boss战已经结束
				String content = sysLangService
						.read(LangConstants.LEGION_BOSS_CLOSE);
				bulletinManager.sendSystemBulletin(content);
			} else {
				String content = sysLangService.read(
						LangConstants.KILL_LEGION_BOSS, killer.getName());
				bulletinManager.sendSystemBulletin(content);
			}
			// 战斗中玩家结束战斗
			for (LegionBossRoleInfo role : getJoinBossRoleInfos()) {
				Human otherHuman = sceneManager.getSceneHumanManager()
						.getHumanByGuid(role.getHumanGuid());
				if (otherHuman != null) {
					if (otherHuman.isInBattleState()
							&& otherHuman.getBattleContext().getBattle()
									.getBattleType() == BattleType.PVE_LEGION_BOSS) {
						if (killer == null
								|| killer.getHumanGuid() != otherHuman
										.getHumanGuid()) {
							otherHuman.getBattleContext().getBattle()
									.onBattleUnitQuit(otherHuman);
						}
					}
					updateBossWarPanel(otherHuman,
							getBossRoleInfo(otherHuman.getHumanGuid()));
					GCHasLegionBossReward gcMsg = new GCHasLegionBossReward();
					otherHuman.sendMessage(gcMsg);
				}
			}
			// 排名所有玩家并将奖励状态设为可领取
			List<LegionBossRoleInfo> roleInfoList = getJoinBossRoleInfos();
			Collections.sort(roleInfoList, sorter);
			updateBosswarReward(roleInfoList);
			// 系统通知boss战结束和排名奖励信息
			for (Human someone : sceneManager.getSceneHumanManager()
					.getAllHumans()) {
				if (killer == null) {
					someone.sendChatMessage(LangConstants.LEGION_BOSS_CLOSE);
				} else {
					// 系统通知boss战结束
					someone.sendChatMessage(LangConstants.KILL_LEGION_BOSS,
							getBossInfo().getName(), killer.getName());
				}
				if (roleInfoList.size() >= 3) {
					// 系统通知排名信息
					someone.sendChatMessage(
							LangConstants.LEGION_BOSS_RANK_INFO, roleInfoList
									.get(0).getName(), roleInfoList.get(0)
									.getDamage()
									* 1.0f
									* 100
									/ getBossInfo().getTotalBlood(),
							roleInfoList.get(1).getName(), roleInfoList.get(1)
									.getDamage()
									* 1.0f
									* 100
									/ getBossInfo().getTotalBlood(),
							roleInfoList.get(2).getName(), roleInfoList.get(2)
									.getDamage()
									* 1.0f
									* 100
									/ getBossInfo().getTotalBlood());
				}
			}
			// 通知玩家有奖励
			for (LegionBossRoleInfo role : getJoinBossRoleInfos()) {
				Human otherHuman = sceneManager.getSceneHumanManager()
						.getHumanByGuid(role.getHumanGuid());
				if (otherHuman != null) {
					GCHasLegionBossReward gcMsg = new GCHasLegionBossReward();
					otherHuman.sendMessage(gcMsg);
				}
			}
		}
		killer = null;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateEntityList = new ArrayList<IEntity>();
		updateEntityList.addAll(bossCache.getAllUpdateData());
		updateEntityList.addAll(bossRoleCache.getAllUpdateData());
		return updateEntityList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	/**
	 * 获取参加军团矿战的军团信息
	 */
	public List<JoinLegionInfo> getJoinLegionInfos() {
		List<JoinLegionInfo> joinLegionInfoList = new ArrayList<JoinLegionInfo>();
		List<LegionBossLegionInfo> bossLegionInfo = getJoinBossLegionInfos();
		if (bossLegionInfo.size() >= 1) {
			JoinLegionInfo redLegionInfo = new JoinLegionInfo();
			redLegionInfo.setLegionId(bossLegionInfo.get(0).getLegionId());
			redLegionInfo.setLegionName(bossLegionInfo.get(0).getLegionName());
			redLegionInfo.setJoinLegionType(JoinLegionType.RED_LEGION
					.getIndex());
			joinLegionInfoList.add(redLegionInfo);
		}
		if (bossLegionInfo.size() >= 2) {
			JoinLegionInfo blueLegionInfo = new JoinLegionInfo();
			blueLegionInfo.setLegionId(bossLegionInfo.get(1).getLegionId());
			blueLegionInfo.setLegionName(bossLegionInfo.get(1).getLegionName());
			blueLegionInfo.setJoinLegionType(JoinLegionType.BLUE_LEGION
					.getIndex());
			joinLegionInfoList.add(blueLegionInfo);
		}
		return joinLegionInfoList;
	}
}
