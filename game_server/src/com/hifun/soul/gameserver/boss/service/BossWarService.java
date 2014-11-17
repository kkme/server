package com.hifun.soul.gameserver.boss.service;

import java.util.ArrayList;
import java.util.Collections;
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
import com.hifun.soul.gamedb.entity.BossEntity;
import com.hifun.soul.gamedb.entity.BossRoleEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.activity.ActivityState;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.GlobalActivityManager;
import com.hifun.soul.gameserver.activity.ITimingActivityManager;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.boss.BossInfo;
import com.hifun.soul.gameserver.boss.BossRoleInfo;
import com.hifun.soul.gameserver.boss.BossState;
import com.hifun.soul.gameserver.boss.converter.BossInfoToEntityConverter;
import com.hifun.soul.gameserver.boss.converter.BossRoleInfoToEntityConverter;
import com.hifun.soul.gameserver.boss.msg.GCHasBossReward;
import com.hifun.soul.gameserver.boss.msg.GCJoinBossWar;
import com.hifun.soul.gameserver.boss.template.BossRankRewardTemplate;
import com.hifun.soul.gameserver.boss.template.BossTemplate;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.MonsterFactory;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

@Scope("singleton")
@Component
public class BossWarService implements IInitializeRequired, ITimingActivityManager,ICachableComponent {
	/** boss战开始后让玩家等待的时间 */
	private static final long BOSS_WAR_WAIT_TIME = 2 * 60 * 1000;
	private Logger logger = Loggers.BOSS_LOGGER;
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
	@Autowired
	private BossWarTemplateManager bossWarTemplateManager;
	/** boss信息 */
	private volatile BossInfo boss = null;
	/** Boss模版 */
	private BossTemplate bossTemplate = null;
	/** 参与boss战的玩家信息 */
	private Map<Long, BossRoleInfo> roleInfoMap = new ConcurrentHashMap<Long, BossRoleInfo>();
	/** bossInfo转化器 */
	private BossInfoToEntityConverter bossInfoConverter = new BossInfoToEntityConverter();
	/** bossRoleInfo转化器 */
	private BossRoleInfoToEntityConverter bossRoleInfoConverter = new BossRoleInfoToEntityConverter();
	private long beginTime;
	/** 击杀boss的人 */
	private Human killer;
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Integer,IEntity> bossCache = new CacheEntry<Integer, IEntity>();
	private CacheEntry<Long,IEntity> bossRoleCache = new CacheEntry<Long, IEntity>();
	private BossRoleInfoSorter sorter = new BossRoleInfoSorter();

	@Override
	public void init() {
		cacheManager.registerOtherCachableComponent(this);
		bossTemplate = templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, BossTemplate.class);
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
		List<?> result = dbService.findByNamedQueryAndNamedParam(DataQueryConstants.QUERY_BOSS_INFO,
				new String[] { "id" }, new Object[] { bossTemplate.getBossId() });
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<BossEntity> results = (List<BossEntity>) result;
			try {
				Monster monster = monsterFactory.createMonster(getBossTemplate().getBossId());
				BossEntity bossEntity = results.get(0);
				if (boss == null) {
					boss = new BossInfo();
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
		List<?> result = dbService.findByNamedQuery(DataQueryConstants.QUERY_BOSS_ROLE_INFO);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<BossRoleEntity> results = (List<BossRoleEntity>) result;
			for (BossRoleEntity entity : results) {
				BossRoleInfo bossRoleInfo = new BossRoleInfo();
				bossRoleInfo.setChargedstrikeRate(entity.getChargedstrikeRate());
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
			updateBossWarRankInfo();
			// 同步数据库
			for(BossRoleInfo bossRoleInfo : roleInfoMap.values()){
				updateBossRoleInfoToDB(bossRoleInfo);
			}
		}
	}

	/**
	 * 获取boss的信息
	 * 
	 * @return
	 */
	public BossInfo getBossInfo() {
		return boss;
	}

	/**
	 * 获取boss战中角色的信息
	 * 
	 * @param humanGuid
	 * @return
	 */
	public BossRoleInfo getBossRoleInfo(long humanGuid) {
		return roleInfoMap.get(humanGuid);
	}
	
	/**
	 * 标记更新
	 * @param bossRoleInfo
	 */
	public void updateBossRoleInfoToDB(BossRoleInfo bossRoleInfo){
		if(bossRoleInfo == null){
			return;
		}
		bossRoleCache.addUpdate(bossRoleInfo.getHumanGuid(), bossRoleInfoConverter.convert(bossRoleInfo));
	}

	/**
	 * 添加到boss战玩家列表
	 * 
	 * @param bossRoleInfo
	 */
	public void addBossRoleInfo(BossRoleInfo bossRoleInfo) {
		roleInfoMap.put(bossRoleInfo.getHumanGuid(), bossRoleInfo);
		dataService.insert(bossRoleInfoConverter.convert(bossRoleInfo), null);
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
		BossRoleInfo bossRoleInfo = getBossRoleInfo(humanGuid);
		if (bossRoleInfo == null) {
			return "";
		}
		String damageDesc = sysLangService.read(LangConstants.BOSS_DAMAGE_DESC, bossRoleInfo.getDamage());
		return damageDesc;
	}

	/**
	 * 返回boss战的排行榜信息
	 * 
	 * @return
	 */
	public List<String> getDamageRankings() {
		// 重新排名
		List<BossRoleInfo> roleInfoList = getJoinBossRoleInfos();
		Collections.sort(roleInfoList, sorter);
		List<String> rankings = new ArrayList<String>();
		for (int i = 0; i < SharedConstants.BOSS_RANKING_SIZE
				&& i < roleInfoList.size(); i++) {
			BossRoleInfo bossRoleInfo = roleInfoList.get(i);
			if (bossRoleInfo != null && bossRoleInfo.getDamage() > 0) {
				String desc = sysLangService.read(LangConstants.BOSS_RANKING,
						bossRoleInfo.getName(), bossRoleInfo.getDamage());
				rankings.add(desc);
			}
		}
		return rankings;
	}

	/**
	 * 返回boss战的配置模版
	 * @return
	 */
	public BossTemplate getBossTemplate() {
		if (bossTemplate == null) {
			bossTemplate = templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, BossTemplate.class);
		}
		return bossTemplate;
	}

	/**
	 * 根据充能点计算应该对boss造成的伤害
	 * @param chargestrike
	 * @return
	 */
	public int getChargestrikeDamage(int attack, int chargestrike) {
		int damage = 0;
		BossTemplate bossTemplate = getBossTemplate();
		if (bossTemplate == null) {
			return damage;
		}
		if (chargestrike >= bossTemplate.getMaxChargedRate()) {
			damage = (int) (attack * bossTemplate.getMaxChargedDamage() / SharedConstants.DEFAULT_FLOAT_BASE);
		} else {
			damage = (int) (attack * chargestrike * bossTemplate.getChargedDamage() / SharedConstants.DEFAULT_FLOAT_BASE);
		}
		return damage;
	}

	/**
	 * boss造成伤害之后的处理
	 * @param human
	 * @param damage
	 */
	public void damageBoss(Human human, int damage, boolean isWin) {
		BossRoleInfo bossRoleInfo = getBossRoleInfo(human.getHumanGuid());
		if (bossRoleInfo == null) {
			return;
		}
		// 伤害值小于等于0直接返回
		if(damage <= 0){
			return;
		}
		// boss战如果已经结束，那这一次的伤害不计算在内
		if (BossState.LIVE.getIndex() != getBossInfo().getBossState()) {
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
				updateBossWarRankInfo();
				// 同步数据库
				updateBossRoleInfoToDB(bossRoleInfo);
				// 修改活动的状态
				globalActivityManager.abortAcivity(ActivityType.BOSS_WAR);
			}
			// 如果打败了，则默认再给boss加一滴血
			else {
				// 修改boss剩余血量
				getBossInfo().setRemainBlood(1);
				updateBossInfoToDB();
				// 修改自己的伤害值
				bossRoleInfo.setDamage(bossRoleInfo.getDamage() + damage);
				// 重新排名
				updateBossWarRankInfo();
				// 同步数据库
				updateBossRoleInfoToDB(bossRoleInfo);
			}
		} else {
			// 修改boss剩余血量
			getBossInfo().setRemainBlood(getBossInfo().getRemainBlood() - damage);
			updateBossInfoToDB();
			// 修改自己的伤害值
			bossRoleInfo.setDamage(bossRoleInfo.getDamage() + damage);
			// 重新排名
			updateBossWarRankInfo();
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
		BossRoleInfo bossRoleInfo = getBossRoleInfo(human.getHumanGuid());
		if (bossRoleInfo == null) {
			return;
		}
		BossTemplate bossTemplate = getBossTemplate();
		double maxTotalStageReward = bossTemplate.getMaxTotalStageReward();
		double totalStageReward = bossRoleInfo.getStageReward();
		if (totalStageReward >= maxTotalStageReward) {
			return;
		}
		double reward = damage * maxTotalStageReward / boss.getTotalBlood();
		if (reward > bossTemplate.getMaxSingleStageReward() * human.getLevel()) {
			reward = bossTemplate.getMaxSingleStageReward()* human.getLevel();
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
				(int)reward);
	}
	
	/**
	 * 更新boss战排名
	 */
	private void updateBossWarRankInfo() {
		List<BossRoleInfo> roleInfoList = getJoinBossRoleInfos();
		Collections.sort(roleInfoList, sorter);
		for (int i = 0; i < roleInfoList.size(); i++) {
			roleInfoList.get(i).setRank(i + 1);
		}
	}

	/**
	 * 更新奖励状态
	 */
	private void updateBosswarReward(List<BossRoleInfo> roleInfoList) {
		SceneHumanManager humanManager = GameServerAssist.getGameWorld()
				.getSceneHumanManager();
		// 修改奖励领取状态
		for (int i = 0; i < roleInfoList.size(); i++) {
			BossRoleInfo bossRoleInfo = roleInfoList.get(i);
			if(bossRoleInfo.getDamage() <= 0){
				break;
			}
			bossRoleInfo.setRank(i + 1);
			bossRoleInfo.setChargedstrikeRate(0);
			bossRoleInfo.setEncourageRate(0);
			bossRoleInfo.setHasKillReward(bossRoleInfo.getHumanGuid() == boss.getKillerId());
			BossRankRewardTemplate template = bossWarTemplateManager.getSuitableRewardTemplate(bossRoleInfo.getRank());
			bossRoleInfo.setHasRankReward(template != null);
			bossRoleInfo.setHasDamageReward(getBossTemplate().isHasDamageReward() && bossRoleInfo.getDamage() > 0);
			updateBossRoleInfoToDB(bossRoleInfo);
			// 在线玩家推送奖励
			Human onlineHuman = humanManager.getHumanByGuid(bossRoleInfo.getHumanGuid());
			if (onlineHuman != null) {
				onlineHuman.getHumanBossManager().sendRewardNotify();
			}
		}
	}

	/**
	 * 更新面板
	 * @param human
	 * @param bossRoleInfo
	 */
	public void updateBossWarPanel(Human human, BossRoleInfo bossRoleInfo) {
		human.getHumanCdManager().snapCdQueueInfo(CdType.CHARGED_STRIKE_CD);
		human.getHumanCdManager().snapCdQueueInfo(CdType.BOSS_ATTACK);
		GCJoinBossWar gcMsg = new GCJoinBossWar();
		gcMsg.setBossInfo(getBossInfo());
		gcMsg.setRemainTime((int) (globalActivityManager.getRemainTime(ActivityType.BOSS_WAR) / TimeUtils.SECOND));
		gcMsg.setChargedstrikeRate(bossRoleInfo.getChargedstrikeRate() * 100 / bossTemplate.getMaxChargedRate());
		gcMsg.setDamageDesc(getDamageDesc(human.getHumanGuid()));
		gcMsg.setDamageRankings(getDamageRankings().toArray(new String[0]));
		gcMsg.setEncourageRate(bossRoleInfo.getEncourageRate()*100/bossTemplate.getMaxEncourageDamage());
		gcMsg.setIfFull(bossRoleInfo.getEncourageRate() >= bossTemplate.getMaxEncourageDamage());
		gcMsg.setCoin(bossTemplate.getCoin());
		gcMsg.setMeditation(bossTemplate.getMeditation());
		gcMsg.setCrystal(bossTemplate.getCrystal());
		gcMsg.setEncourageForgeStoneCost(bossTemplate.getForgeStoneCost());
		gcMsg.setBossWarDesc(globalActivityManager.getActivity(ActivityType.BOSS_WAR).getDesc());
		gcMsg.setBossState(getBossInfo().getBossState());
		long waitCdTime = BOSS_WAR_WAIT_TIME - (systemTimeService.now() - beginTime);
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
		ActivityState activityState = globalActivityManager.getActivityState(ActivityType.BOSS_WAR);
		if (activityState == null || activityState != ActivityState.OPEN) {
			return false;
		}
		// 获取boss信息
		BossInfo bossInfo = getBossInfo();
		if (bossInfo == null) {
			return false;
		}
		// boss当前的状态是否可以攻击
		if (bossInfo.getBossState() != BossState.LIVE.getIndex()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否能挑战boss了
	 * @return
	 */
	public boolean bossWarReadyForFight() {
		// boss战准备时间CD未冷却
		if (systemTimeService.now() - beginTime <= BOSS_WAR_WAIT_TIME) {
			return false;
		}
		return true;
	}

	/**
	 * 获取boss的副本
	 * @return
	 */
	public Monster getMonster() {
		Monster monster = null;
		monster = monsterFactory.createMonster(getBossInfo().getBossId());
		return monster;
	}

	public void onEnterWar(Human human) {
		// 判断当前是否正处在boss战的时间段
		if (!bossWarIsOpen()) {
			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_OPEN);
			return;
		}
		// 获取自己在boss战中的信息
		BossRoleInfo bossRoleInfo = getBossRoleInfo(human.getHumanGuid());
		if (bossRoleInfo == null) {
			bossRoleInfo = new BossRoleInfo();
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
		}
		else{
			if(!bossRoleInfo.isJoin()){
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
		logger.info("boss war started!");
		// boss战开启
		// 如果是第一次开启boss战
		if (boss == null) {
			boss = new BossInfo();
			Monster monster = monsterFactory.createMonster(getBossTemplate().getBossId());
			boss.setBossId(monster.getTemplate().getId());
			boss.setBossState(BossState.LIVE.getIndex());
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
		else{
			boss.setBossState(BossState.LIVE.getIndex());
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
	 * 获取参与boss战的人
	 * @return
	 */
	private List<BossRoleInfo> getJoinBossRoleInfos() {
		List<BossRoleInfo> bossRoleInfos = new ArrayList<BossRoleInfo>();
		for(BossRoleInfo roleInfo : roleInfoMap.values()){
			if(roleInfo != null && roleInfo.isJoin()){
				bossRoleInfos.add(roleInfo);
			}
		}
		return bossRoleInfos;
	}
	
	/**
	 * 重置上次打boss信息
	 */
	private void resetBossRoleInfo() {
		// 清除bossRoleInfo
		for (BossRoleInfo roleInfo : getJoinBossRoleInfos()) {
			roleInfo.setChargedstrikeRate(0);
			roleInfo.setDamage(0);
			roleInfo.setEncourageRate(0);
			roleInfo.setHasDamageReward(false);
			roleInfo.setHasKillReward(false);
			roleInfo.setHasRankReward(false);
			roleInfo.setJoin(false);
			roleInfo.setRank(-1);
			roleInfoMap.put(roleInfo.getHumanGuid(), roleInfo);
			bossRoleCache.addUpdate(roleInfo.getHumanGuid(), bossRoleInfoConverter.convert(roleInfo));
		}
	}
	
	/**
	 * 发送boss战活动开启公告
	 */
	private void sendBossWarOpenBulletin() {
		String content = sysLangService.read(LangConstants.BOSS_WAR_OPEN);
		bulletinManager.sendSystemBulletin(content);
	}

	@Override
	public void onStop() {
		if(boss != null && boss.getBossState() == BossState.LIVE.getIndex()){
			// 设置boss状态
			if(boss.getRemainBlood() > 0){
				boss.setBossState(BossState.ESCAPE.getIndex());
				boss.setRemainBlood(0);
			}
			else{
				boss.setBossState(BossState.DEAD.getIndex());
			}
			updateBossInfoToDB();
			// 公告通知boss战已经结束
			if(killer == null){
				// 公告通知boss战已经结束
				String content = sysLangService.read(LangConstants.BOSS_WAR_CLOSE);
				bulletinManager.sendSystemBulletin(content);
			}
			else{
				String content = sysLangService.read(LangConstants.KILL_BOSS, killer.getName());
				bulletinManager.sendSystemBulletin(content);
			}
			// 战斗中玩家结束战斗
			for (BossRoleInfo role : getJoinBossRoleInfos()) {
				Human otherHuman = sceneManager.getSceneHumanManager().getHumanByGuid(role.getHumanGuid());
				if (otherHuman != null) {
					if(otherHuman.isInBattleState()
							&& otherHuman.getBattleContext().getBattle().getBattleType() == BattleType.PVE_BOSS_WAR){
						if(killer == null || killer.getHumanGuid() != otherHuman.getHumanGuid()){
							otherHuman.getBattleContext().getBattle().onBattleUnitQuit(otherHuman);
						}
					}
					updateBossWarPanel(otherHuman, getBossRoleInfo(otherHuman.getHumanGuid()));
					GCHasBossReward gcMsg = new GCHasBossReward();
					otherHuman.sendMessage(gcMsg);
				}
			} 
			// 排名所有玩家并将奖励状态设为可领取
			List<BossRoleInfo> roleInfoList = getJoinBossRoleInfos();
			Collections.sort(roleInfoList, sorter);
			updateBosswarReward(roleInfoList);
			// 系统通知boss战结束和排名奖励信息
			for(Human someone :sceneManager.getSceneHumanManager().getAllHumans()){
				if(killer == null){
					someone.sendChatMessage(LangConstants.BOSS_WAR_CLOSE);
				}
				else{
					// 系统通知boss战结束
					someone.sendChatMessage(LangConstants.KILL_BOSS, getBossInfo().getName(),
							killer.getName());
				}
				if(roleInfoList.size() >= 3){
					// 系统通知排名信息
					someone.sendChatMessage(LangConstants.BOSS_RANK_INFO,
							roleInfoList.get(0).getName(), roleInfoList.get(0).getDamage() * 1.0f * 100 / getBossInfo().getTotalBlood(),
							roleInfoList.get(1).getName(), roleInfoList.get(1).getDamage() * 1.0f * 100 / getBossInfo().getTotalBlood(),
							roleInfoList.get(2).getName(), roleInfoList.get(2).getDamage() * 1.0f * 100 / getBossInfo().getTotalBlood());
				}
			}
			// 通知玩家有奖励
			for (BossRoleInfo role : getJoinBossRoleInfos()) {
				Human otherHuman = sceneManager.getSceneHumanManager().getHumanByGuid(role.getHumanGuid());
				if (otherHuman != null) {
					GCHasBossReward gcMsg = new GCHasBossReward();
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
}
