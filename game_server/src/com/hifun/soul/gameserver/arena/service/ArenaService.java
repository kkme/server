package com.hifun.soul.gameserver.arena.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.GlobalKeyConstants;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.ArenaMemberEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.arena.ArenaRankRewardState;
import com.hifun.soul.gameserver.arena.converter.ArenaMemberToEntityConverter;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.rank.RankType;
import com.hifun.soul.gameserver.scene.SceneHumanManager;
import com.hifun.soul.gameserver.tencent.TencentUserInfo;
import com.hifun.soul.gameserver.timetask.manager.GlobalTimeTaskManager;


/**
 * 竞技场服务
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class ArenaService implements IInitializeRequired,ICachableComponent {
	private Logger logger = Loggers.ARENA_LOGGER;
	@Autowired
	private SysLangService _sysLangService;
	@Autowired
	private IDataService _dataService;
	@Autowired
	private ArenaTemplateManager _templateManager;
	/** key为玩家的UUID */
	private ConcurrentHashMap<Long, ArenaMember> _arenaMemberMap_HumanGuid = new ConcurrentHashMap<Long, ArenaMember>();
	/** key为玩家的竞技场排名 */
	private ConcurrentHashMap<Integer, ArenaMember> _arenaMemberMap_Rank = new ConcurrentHashMap<Integer, ArenaMember>();
	/** 转化器 */
	private ArenaMemberToEntityConverter _converter = new ArenaMemberToEntityConverter();
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long,IEntity> cache = new CacheEntry<Long, IEntity>();
	@Autowired
	private GlobalTimeTaskManager globalTimeTaskManager;
	
	@Override
	public void init() {
		cacheManager.registerOtherCachableComponent(this);
	}
	
	public void start(IDBService dbService) {
		loadArenaMembers(dbService);
	}
	
	private void loadArenaMembers(IDBService dbService) {
		List<?> result = dbService.findByNamedQuery(DataQueryConstants.QUERY_ALL_ARENA_MEMBER);
		if(!result.isEmpty()){
			@SuppressWarnings("unchecked")
			List<ArenaMemberEntity> arenaMembers = (List<ArenaMemberEntity>)result;
			for(ArenaMemberEntity entity : arenaMembers){
				ArenaMember arenaMember = _converter.reverseConvert(entity);
				
				_arenaMemberMap_HumanGuid.put(arenaMember.getRoleId(), arenaMember);
				_arenaMemberMap_Rank.put(arenaMember.getRank(), arenaMember);
			}
		}
	}
	
	/**
	 * 获取玩家的竞技场信息
	 * 
	 * @param humanGuid
	 * @return
	 */
	public ArenaMember getArenaMember(long humanGuid) {
		return _arenaMemberMap_HumanGuid.get(humanGuid);
	}
	
	/**
	 * 获取玩家的竞技场信息
	 * 
	 * @param rank
	 * @return
	 */
	public ArenaMember getArenaMember(int rank) {
		return _arenaMemberMap_Rank.get(rank);
	}
	
	/**
	 * 是否有排名奖励可以领取
	 * 
	 * @param humanGuid
	 * @return
	 */
	public boolean hasRankReward(long humanGuid) {
		ArenaMember arenaMember = getArenaMember(humanGuid);
		if(arenaMember == null){
			return false;
		}
		
		return arenaMember.getRankRewardState() == ArenaRankRewardState.CAN_GET.getIndex();
	}
	
	/**
	 * 获取玩家的竞技场排名
	 * 
	 * @param humanGuid
	 * @return
	 */
	public int getArenaRank(long humanGuid) {
		ArenaMember arenaMember = getArenaMember(humanGuid);
		if(arenaMember == null){
			return 0;
		}
		
		return arenaMember.getRank();
	}
	
	/**
	 * 更新变更(奖品领取状况和玩家等级更新时调用)
	 * 
	 * @param humanGuid
	 */
	public void updateArenaMember(long humanGuid) {
		ArenaMember arenaMember = getArenaMember(humanGuid);
		if(arenaMember != null){
			cache.addUpdate(humanGuid, _converter.convert(arenaMember));
		}
	}
	
	/**
	 * 排名间隔
	 * 
	 * @param rank
	 * @return
	 */
	public int getInterval(int rank) {
		return (int) Math.ceil(((rank+SharedConstants.ARENA_VISIBLE_RATIO)/SharedConstants.ARENA_VISIBLE_RATIO)-1);
	}
	
	/**
	 * 获取rank排名可见的玩家列表
	 * 
	 * @param rank
	 * @return
	 */
	public List<ArenaMember> getVisibleArenaMembers(int rank) {
		List<ArenaMember> arenaMemberList = new ArrayList<ArenaMember>();
		if(rank > 0){
			List<Integer> rankList = new ArrayList<Integer>();
			if(rank <= SharedConstants.ARENA_VISIBLE_NUM){
				for(int i=0; i<SharedConstants.ARENA_VISIBLE_NUM; i++){
					rankList.add(SharedConstants.ARENA_VISIBLE_NUM-i);
				}
			}
			else{
				int interval = getInterval(rank);
				for(int i=1; i<=SharedConstants.ARENA_VISIBLE_NUM; i++){
					int rankTemp = rank - interval*i;
					if(rankTemp <= 0){
						break;
					}
					else{
						rankList.add(rankTemp);
					}
				}
			}
			
			for(int i=rankList.size()-1; i>=0; i--){
				ArenaMember arenaMember = getArenaMember(rankList.get(i));
				if(arenaMember != null){
					TencentUserInfo txUserInfo = GameServerAssist.getTencentUserInfoManager().getTencentUserInfo(arenaMember.getRoleId());
					if(txUserInfo != null){
						arenaMember.setYellowVipLevel(txUserInfo.getYellowVipLevel());
						arenaMember.setIsYearYellowVip(txUserInfo.getIsYearYellowVip());
						arenaMember.setIsYellowHighVip(txUserInfo.getIsYellowHighVip());
					}else{
						arenaMember.setYellowVipLevel(0);
						arenaMember.setIsYearYellowVip(false);
						arenaMember.setIsYellowHighVip(false);
					}
					arenaMemberList.add(arenaMember);
				}
			}
		}
		return arenaMemberList;
	}
	
	/**
	 * 获取竞技场排行榜上的玩家列表
	 * 
	 * @return
	 */
	public List<ArenaMember> getArenaRankArenaMembers(int pageIndex) {
		int fromIndex = SharedConstants.ARENA_RANK_PAGE_NUM*pageIndex+1;
		int toIndex = SharedConstants.ARENA_RANK_PAGE_NUM*(pageIndex+1);
		if(fromIndex <= 0){
			fromIndex = 1;
		}
		if(toIndex >= SharedConstants.ARENA_RANK_NUM){
			toIndex = SharedConstants.ARENA_RANK_NUM;
		}
		return getIntervalArenaMembers(fromIndex,toIndex);
	}
	
	/**
	 * 根据排名返回提示信息
	 * 
	 * @param rank
	 * @return
	 */
	public String getRankInfo(int rank) {
		String content = "";
		if(rank > SharedConstants.ARENA_RANK_NUM){
			content = _sysLangService.read(LangConstants.ARENA_NOT_IN_RANK_LIST, rank);
		}
		else{
			content = _sysLangService.read(LangConstants.ARENA_IN_RANK_LIST, rank);
		}
		return content;
	}
	
	/**
	 * 获取下一个排名
	 * 
	 * @return
	 */
	public int getNextRank() {
		return _arenaMemberMap_HumanGuid.size() + 1;
	}
	
	public void addArenaMember(ArenaMember arenaMember) {
		if(arenaMember == null){
			return;
		}
		arenaMember.setRankRewardState(ArenaRankRewardState.NO_REWARD.getIndex());
		
		_arenaMemberMap_HumanGuid.put(arenaMember.getRoleId(), arenaMember);
		_arenaMemberMap_Rank.put(arenaMember.getRank(), arenaMember);
		_dataService.insert(_converter.convert(arenaMember));
	}
	
	/**
	 * 根据挑战者id和被挑战者id重新排名
	 * 
	 * @param challengerId
	 * @param beChallengerId
	 */
	public void rankArenaMember(long challengerId, long beChallengerId) {
		ArenaMember challenger = getArenaMember(challengerId);
		if(challenger == null){
			logger.error(String.format("can not find challenger. humanGuid = %d", challengerId));
			return;
		}
		ArenaMember beChallenger = getArenaMember(beChallengerId);
		if(beChallenger == null){
			logger.error(String.format("can not find beChallenger. humanGuid = %d", beChallengerId));
			return;
		}
		
		// 挑战者排名在被挑战者之前
		if(challenger.getRank() <= beChallenger.getRank()){
			return;
		}
		
		// 获取两个排名之间的竞技场玩家数据
		List<ArenaMember> arenaMembers = getIntervalArenaMembers(beChallenger.getRank(),challenger.getRank()-1);
		
		// 排名变化(自己的排名替换为被挑战者排名,自己与被挑战者之间的竞技场玩家排名顺次降低一位)
		challenger.setRank(beChallenger.getRank());
		_arenaMemberMap_Rank.put(challenger.getRank(), challenger);
		updateArenaMember(challenger.getRoleId());
		for(ArenaMember arenaMember : arenaMembers){
			arenaMember.setRank(arenaMember.getRank() + 1);
			_arenaMemberMap_Rank.put(arenaMember.getRank(), arenaMember);
			updateArenaMember(arenaMember.getRoleId());
		}
		
	}
	
	/**
	 * 直接替换两个排名玩家。
	 * 
	 * @param fromRank
	 * @param toRank
	 * 
	 * 注： 仅供gm命令调用
	 */
	public void changeArenaMemberRank(int fromRank, int toRank) {
		if(fromRank <= 0
				|| toRank <= 0){
			return;
		}
		
		ArenaMember from = getArenaMember(fromRank);
		ArenaMember to = getArenaMember(toRank);
		
		if(from == null
				|| to == null){
			return;
		}
		
		from.setRank(toRank);
		to.setRank(fromRank);
	}
	
	/**
	 * 获取某个排名区间段的玩家竞技场数据
	 * 
	 * @param fromRank
	 * @param toRank
	 * @return
	 */
	private List<ArenaMember> getIntervalArenaMembers(int fromRank, int toRank) {
		List<ArenaMember> arenaMemberList = new ArrayList<ArenaMember>();
		for(int i=fromRank; i<=toRank; i++){
			ArenaMember arenaMember = getArenaMember(i);
			if(arenaMember != null){
				TencentUserInfo txUserInfo = GameServerAssist.getTencentUserInfoManager().getTencentUserInfo(arenaMember.getRoleId());
				if(txUserInfo != null){
					arenaMember.setYellowVipLevel(txUserInfo.getYellowVipLevel());
					arenaMember.setIsYearYellowVip(txUserInfo.getIsYearYellowVip());
					arenaMember.setIsYellowHighVip(txUserInfo.getIsYellowHighVip());
				}else{
					arenaMember.setYellowVipLevel(0);
					arenaMember.setIsYearYellowVip(false);
					arenaMember.setIsYellowHighVip(false);
				}
				arenaMemberList.add(arenaMember);
			}
		}
		return arenaMemberList;
	}
	
	/**
	 * 重置竞技场排名奖励
	 * 
	 */
	public void resetArenaRankReward() {
		SceneHumanManager humanManager = GameServerAssist.getGameWorld().getSceneHumanManager();
		for(ArenaMember arenaMember : _arenaMemberMap_HumanGuid.values()){
			int rankRewardId = _templateManager.getSuitableRankRewardId(RankType.ARENA_RANK.getIndex(),arenaMember.getRank());
			arenaMember.setRankRewardId(rankRewardId);
			arenaMember.setRankRewardState(ArenaRankRewardState.CAN_GET.getIndex());
			updateArenaMember(arenaMember.getRoleId());
			Human onlineHuman = humanManager.getHumanByGuid(arenaMember.getRoleId());
			if(onlineHuman != null){
				onlineHuman.getHumanArenaManager().sendRewardNotify();
			}
		}
	}
	
	public long getLastResetRankRewardTime() {
		return globalTimeTaskManager.getLastRunTime(GlobalKeyConstants.ARENA_RANK_REWARD_RESET_TIME);
	}
	
	/**
	 * 重置排行榜奖励调用
	 * 
	 * @param now
	 */
	public void setLastResetRankRewardTime(long now) {
		globalTimeTaskManager.setLastRunTime(GlobalKeyConstants.ARENA_RANK_REWARD_RESET_TIME, now);
	}

	/**
	 * 竞技场排行榜大小
	 * @return
	 */
	public int getTotalSize() {
		int totalSize = _arenaMemberMap_HumanGuid.size();
		return totalSize>=SharedConstants.ARENA_RANK_NUM?SharedConstants.ARENA_RANK_NUM:totalSize;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}
}
