package com.hifun.soul.gameserver.elitestage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEliteStageEntity;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.elitestage.model.EliteStageInfo;
import com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo;
import com.hifun.soul.gameserver.elitestage.msg.GCEliteStageBattleResult;
import com.hifun.soul.gameserver.elitestage.msg.GCNewEliteStageTypeOpen;
import com.hifun.soul.gameserver.elitestage.msg.GCUpdateEliteStageChallengeCounter;
import com.hifun.soul.gameserver.elitestage.msg.GCUpdateEliteStageChallengeState;
import com.hifun.soul.gameserver.elitestage.template.EliteStageTemplate;
import com.hifun.soul.gameserver.elitestage.template.EliteStageTypeTemplate;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.stagestar.StageStarType;
import com.hifun.soul.gameserver.vip.model.CommonActionCounter;
import com.hifun.soul.proto.common.EliteStage.EliteStageState;
import com.hifun.soul.proto.common.EliteStage.EliteStageState.Builder;
import com.hifun.soul.proto.common.EliteStage.HumanEliteStageInfo;

/**
 * 精英副本管理器
 * 
 * @author magicstone
 * 
 */
public class HumanEliteStageManager implements IHumanPersistenceManager, ICachableComponent, IEventListener{

	/** 今日刷新副本状态的次数 */
	private int refreshStateNum;
	/** 当前副本类型id */
	private int currentStageTypeId=0;
	/** Key:副本类型id，value:副本类型名称 */
	private List<KeyValuePair<Integer, String>> openedStageTypes = new ArrayList<KeyValuePair<Integer, String>>();
	private List<Integer> openedStageTypeIds = new ArrayList<Integer>();
	/** Key:副本id，value:副本状态 */
	private Map<Integer,EliteStageState.Builder> eliteStageStateMap = new HashMap<Integer, EliteStageState.Builder>();
	private CacheEntry<Long,HumanEliteStageEntity> cache = new CacheEntry<Long, HumanEliteStageEntity>();
	private int rewardItemId=0;
	/** 开启的最大关卡id */
	private int maxOpenedStageId = 0;
	private Human human;
	public HumanEliteStageManager(Human human){
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		registerEventListener();
	}
	
	private void registerEventListener() {
		human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		human.getEventBus().addListener(EventType.VIP_LEVEL_CHANGE_EVENT, this);
	}
	
	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateEntitys = new ArrayList<IEntity>();
		for(HumanEliteStageEntity entity : cache.getAllUpdateData()){
			updateEntitys.add(entity);
		}
		return updateEntitys;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return null;
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		HumanEliteStageInfo.Builder humanEliteStageInfoBuilder = humanEntity.getBuilder().getEliteStageInfoBuilder();
		refreshStateNum = humanEliteStageInfoBuilder.getRefreshStageStateNum();
		EliteStageState.Builder lastStage = null;
		for(EliteStageState.Builder stageState : humanEliteStageInfoBuilder.getStageStateBuilderList()){
			eliteStageStateMap.put(stageState.getStageId(), stageState);
			if(lastStage==null || lastStage.getStageId()<stageState.getStageId()){
				lastStage = stageState;	
				maxOpenedStageId = lastStage.getStageId();
			}
		}
		for(Integer typeId : humanEliteStageInfoBuilder.getOpenedStageTypeIdsList()){
			openedStageTypeIds.add(typeId);
			KeyValuePair<Integer, String> pair = new KeyValuePair<Integer, String>();
			pair.setKey(typeId);
			pair.setValue(GameServerAssist.getEliteStageTemplateManager().getEliteStageTypeTempalte(typeId).getName());
			openedStageTypes.add(pair);
		}
		if(openedStageTypes.size()>0){
			currentStageTypeId=openedStageTypes.get(openedStageTypes.size()-1).getKey();
		}
		checkEliteStage();
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		HumanEliteStageInfo.Builder humanEliteStageInfoBuilder = humanEntity.getBuilder().getEliteStageInfoBuilder();
		humanEliteStageInfoBuilder.setHumanGuid(humanEntity.getBuilder().getGuid());		
		for(KeyValuePair<Integer, String> pair : openedStageTypes){
			humanEliteStageInfoBuilder.addOpenedStageTypeIds(pair.getKey());			
		}
		for(EliteStageState.Builder stageState : eliteStageStateMap.values()){
			humanEliteStageInfoBuilder.addStageState(stageState);
		}
		humanEliteStageInfoBuilder.setRefreshStageStateNum(refreshStateNum);
		humanEntity.getBuilder().setEliteStageInfo(humanEliteStageInfoBuilder);
	}
	
	/**
	 * 获取当前类型id
	 * 
	 * @return
	 */
	public int getCurrentStageTypeId() {
		return currentStageTypeId;
	}
	
	/**
	 * 设置当前类型id
	 * @param stageTypeId
	 */
	public void setCurrentStageTypeId(int stageTypeId){
		this.currentStageTypeId = stageTypeId;
	}
	
	/**
	 * 获取当前挑战副本的id
	 * @param stageTypeId 精英副本类型Id
	 * @return
	 */
	public int getCurrentStageId(int stageTypeId) {		
		int stageId = 0;
		for(EliteStageInfo stageInfo : GameServerAssist.getEliteStageTemplateManager().getEliteStageInfos(human.getOccupation().getIndex(),stageTypeId)){
			stageId = stageInfo.getStageId();
			if(this.eliteStageStateMap.containsKey(stageId)){
				if(eliteStageStateMap.get(stageId).getConquerState()==false){
					return stageId;
				}
			}
		}
		return stageId;
	}
	
	/**
	 * 获取副本今日的挑战状态
	 * 
	 * @param stageId 精英副本id
	 * @return
	 */
	public boolean getChallengeState(int stageId) {
		EliteStageState.Builder stageState = eliteStageStateMap.get(stageId);
		if(stageState==null){
			return false;
		}		
		return stageState.getChallengeState();
	}
	
	/**
	 * 是否已经战胜过该关卡
	 * 
	 * @param stageId 精英副本id
	 * @return
	 */
	public boolean getConquerState(int stageId){
		EliteStageState.Builder stageState = eliteStageStateMap.get(stageId);
		if(stageState==null){
			return false;
		}		
		return stageState.getConquerState();
	}
	
	/**
	 * 获取关卡开始状态
	 * 
	 * @param stageId
	 * @return
	 */
	public boolean getOpenState(int stageId) {
		EliteStageState.Builder stageState = eliteStageStateMap.get(stageId);
		if(stageState==null){
			return false;
		}
		return true;		
	}
	
	/**
	 * 获取关卡星级
	 * 
	 * @param stageId
	 * @return
	 */
	public int getStageStar(int stageId) {
		EliteStageState.Builder stageState = eliteStageStateMap.get(stageId);
		if(stageState==null){
			return StageStarType.DefaultStar.getIndex();
		}
		return stageState.getStageStar();
	}
	
	/**
	 * 获取精英副本类型列表
	 * 
	 * @return
	 */
	public EliteStageTypeInfo[] getEliteStageTypeList() {
		List<EliteStageTypeInfo> typeInfoList = new ArrayList<EliteStageTypeInfo>();
		for (KeyValuePair<Integer, String> typePair : openedStageTypes) {
			EliteStageTypeTemplate typeTemplate = GameServerAssist
					.getEliteStageTemplateManager().getEliteStageTypeTempalte(
							typePair.getKey());
			if (typeTemplate != null) {
				EliteStageTypeInfo typeInfo = new EliteStageTypeInfo();
				typeInfo.setStageType(typePair.getKey());
				typeInfo.setTypeName(typeTemplate.getName());
				typeInfo.setOpenLevel(typeTemplate.getOpenLevel());
				typeInfoList.add(typeInfo);
			}
		}
		return typeInfoList.toArray(new EliteStageTypeInfo[0]);
	}
	
	/**
	 * 刷新挑战状态
	 * 
	 * @param stageTypeId 副本类型
	 */
	public void refreshChallengeState(int stageTypeId) {
		int totalCount = GameServerAssist.getVipPrivilegeTemplateManager().getMaxRefreshEliteStageTimes(human.getVipLevel());
		if(totalCount <= refreshStateNum){
			return;
		}		
		if(GameServerAssist.getEliteStageTemplateManager().getEliteStageInfos(human.getOccupation().getIndex(),stageTypeId)==null){
			return;
		}
		int stageId=0;
		List<Integer> sameTypeStageIds = new ArrayList<Integer>();
		for(EliteStageInfo stageInfo : GameServerAssist.getEliteStageTemplateManager().getEliteStageInfos(human.getOccupation().getIndex(),stageTypeId)){
			stageId = stageInfo.getStageId();
			if(this.eliteStageStateMap.containsKey(stageId)){
				eliteStageStateMap.get(stageId).setChallengeState(false);
				sameTypeStageIds.add(stageId);
			}
		}
		if(sameTypeStageIds.size()==0){
			return;
		}
		this.refreshStateNum++;
		int[] updatedStageIds = new int[sameTypeStageIds.size()];
		for(int i=0;i<updatedStageIds.length;i++){
			updatedStageIds[i]=sameTypeStageIds.get(i);
		}
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());
		sendUpdateStageStateMessage(updatedStageIds);
		sendRefreshChallengeStateInfo();
	}
	
	/**
	 * 发送刷新挑战状态相关信息
	 */
	public void sendRefreshChallengeStateInfo(){		
		int totalCount = GameServerAssist.getVipPrivilegeTemplateManager().getMaxRefreshEliteStageTimes(human.getVipLevel());
		int remainCount = totalCount>refreshStateNum ? totalCount-refreshStateNum : 0;
		GCUpdateEliteStageChallengeCounter  gcCounterMsg = new GCUpdateEliteStageChallengeCounter();
		gcCounterMsg.setTotalCount(totalCount);
		gcCounterMsg.setRemainCount(remainCount);
		gcCounterMsg.setCurrencyNum(GameServerAssist.getEliteStageTemplateManager().getRefreshStageStateCost(refreshStateNum + 1));
		human.sendMessage(gcCounterMsg);
	}
	
	/**
	 * 发送副本挑战状态更新消息
	 * @param updatedStageIds
	 */
	private void sendUpdateStageStateMessage(int[] updatedStageIds){
		GCUpdateEliteStageChallengeState gcUpdateChallengeStageMsg = new GCUpdateEliteStageChallengeState();
		gcUpdateChallengeStageMsg.setChangedStageIds(updatedStageIds);
		human.sendMessage(gcUpdateChallengeStageMsg);
	}
	
	
	public 	HumanEliteStageEntity convertToEntity(){
		HumanEliteStageInfo.Builder builder = HumanEliteStageInfo.newBuilder();
		builder.setHumanGuid(human.getHumanGuid());		
		for(KeyValuePair<Integer, String> pair : openedStageTypes){
			builder.addOpenedStageTypeIds(pair.getKey());			
		}
		for(EliteStageState.Builder stageState : eliteStageStateMap.values()){
			builder.addStageState(stageState);
		}
		builder.setRefreshStageStateNum(refreshStateNum);
		HumanEliteStageEntity entity = new HumanEliteStageEntity(builder);
		return entity;
	}
	
	/**
	 * 能否挑战当前副本
	 * @param stageId
	 * @return
	 */
	public boolean canAttackStage(int stageId) {
		EliteStageTemplate template = GameServerAssist.getEliteStageTemplateManager().getEliteStageTemplate(stageId);
		if(template==null || human.getLevel()<template.getOpenLevel()){
			return false;
		}
		if(this.eliteStageStateMap.containsKey(stageId)){
			if(eliteStageStateMap.get(stageId).getChallengeState() == false) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void onEvent(IEvent event) {
		switch (event.getType()) {
		case LEVEL_UP_EVENT:
			checkEliteStage();
			break;
		case VIP_LEVEL_CHANGE_EVENT:
			sendRefreshChallengeStateInfo();
			break;
		}
	}
	
	/**
	 * 更新副本战斗结束状态
	 * @param stageId
	 */
	public void updateBattleResult(int stageId,boolean isWin){
		if(!this.eliteStageStateMap.containsKey(stageId)){
			return;
		}
		// 设置精英副本关卡的等级
		// 根据战斗情况判断此次战斗的星级
		EliteStageTemplate eliteStageTemplate = GameServerAssist.getEliteStageTemplateManager().getEliteStageTemplate(stageId);
		int star = StageStarType.StageStarOne.getIndex();
		// 剩余血量/玩家血量>=加星血量条件/10000
		double hpRate = human.getBattleContext().getBattleProperty().getHp()/(human.getHp()*1.0);
		if(hpRate>=eliteStageTemplate.getAddStarOne()/SharedConstants.DEFAULT_FLOAT_BASE){
			star++;
		}
		if(hpRate>=eliteStageTemplate.getAddStarTwo()/SharedConstants.DEFAULT_FLOAT_BASE){
			star++;
		}
		if(hpRate>=eliteStageTemplate.getAddStarThree()/SharedConstants.DEFAULT_FLOAT_BASE){
			star++;
		}
		if(hpRate>=eliteStageTemplate.getAddStarFour()/SharedConstants.DEFAULT_FLOAT_BASE){
			star++;
		}
		EliteStageState.Builder builder = eliteStageStateMap.get(stageId);
		if(builder == null){
			return;
		}
		if(isWin && builder.getStageStar() < star){
			//天赋点增加
			human.getHumanGiftManager().addGiftPointByStar(star-builder.getStageStar());
			builder.setStageStar(star);
		}
		float rewardRate = GameServerAssist.getStageTemplateManager().getRewardRateByStar(star);
		if(isWin){
			this.genRewardItemId(stageId);
			onBattleWin(stageId,rewardRate);
		}
		this.sendBattleResultMessage(stageId, isWin, star, rewardRate);
	}
	
	/**
	 * 取得战斗胜利的处理
	 * @param stageId
	 */
	public void onBattleWin(int stageId, float rewardRate){
		if(!this.eliteStageStateMap.containsKey(stageId)){
			return;
		}
		
		EliteStageState.Builder builder = eliteStageStateMap.get(stageId);
		builder.setChallengeState(true);
		if (builder.getConquerState() == false) {			
			builder.setConquerState(true);
			if(stageId==this.maxOpenedStageId){
				checkEliteStage();
			}
		}
		
		getBattleReward(stageId, rewardRate);
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());	
	}
	
	private void getBattleReward(int stageId, float rewardRate){
		EliteStageTemplate template = GameServerAssist.getEliteStageTemplateManager().getEliteStageTemplate(stageId);
		human.getWallet().addMoney(CurrencyType.COIN, (int) (template.getCoinNum()*rewardRate), true, MoneyLogReason.ELITE_STAGE_REWARD, "");
		human.getHumanTechnologyManager().addTechnologyPoints((int) (template.getTechPoint()*rewardRate),true,TechPointLogReason.ELITE_STAGE_ADD_TECH_POINT, "");
		human.addExperience((int) (template.getExp()*rewardRate),true,ExperienceLogReason.BATTLE_ADD_EXP, "");		
		if(this.rewardItemId <= 0){			
			return;	
		}
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);			
			return;
		}
		human.getBagManager().putItems(BagType.MAIN_BAG, rewardItemId, 1, ItemLogReason.ELITE_STAGE_REWARD, "");
	}
	
	/**
	 * 当升级或者副本攻克状态改变时刷新开放的副本类型和开放的副本
	 */
	private void checkEliteStage(){
		EliteStageTemplate template = null;
		Map<Integer, EliteStageTemplate> eliteStageTemplates = GameServerAssist.getEliteStageTemplateManager()
				.getStageTemplatesByOccupation(human.getOccupation().getIndex());
		if(maxOpenedStageId == 0){			
			int flag = 0;
			int humanLevel = human.getLevel();
			for(Integer stageId : eliteStageTemplates.keySet()){
				template = eliteStageTemplates.get(stageId);
				if(humanLevel<template.getOpenLevel()){
					continue;
				}
				if(flag==0){
					flag=1;
					maxOpenedStageId = stageId;					
				}
				else if(stageId<maxOpenedStageId){
					maxOpenedStageId = stageId;
				}				
			}			
			template = eliteStageTemplates.get(this.maxOpenedStageId);		
			if(template==null || template.getNextStageId() == template.getId() || eliteStageTemplates.get(template.getNextStageId()) == null){
				return;
			}
		}
		else{
			EliteStageState.Builder stageStage = eliteStageStateMap.get(maxOpenedStageId);
			if(stageStage==null || stageStage.getConquerState()==false){
				//检查是否开启新的精英副本类型
				checkEliteStageType();
				return;
			}			
			template = eliteStageTemplates.get(this.maxOpenedStageId);		
			if(template==null || template.getNextStageId() == template.getId() || eliteStageTemplates.get(template.getNextStageId()) == null){
				return;
			}
			maxOpenedStageId = template.getNextStageId();			
		}		
		EliteStageState.Builder newStage = EliteStageState.newBuilder();
		newStage.setChallengeState(false);
		newStage.setConquerState(false);
		newStage.setStageId(maxOpenedStageId);
		newStage.setStageStar(StageStarType.DefaultStar.getIndex());
		eliteStageStateMap.put(newStage.getStageId(), newStage);
		this.sendUpdateStageStateMessage(new int[]{maxOpenedStageId});		
		//检查类型
		checkEliteStageType();
		
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());
	}
	
	/**
	 * 检查是否有新的副本类型开放
	 */
	private void checkEliteStageType(){
		EliteStageTemplateManager eliteStageTemplates = GameServerAssist.getEliteStageTemplateManager();
		EliteStageTemplate template = eliteStageTemplates.getEliteStageTemplate(this.maxOpenedStageId);		
		int stageType = template.getType();		
		if(!openedStageTypeIds.contains(stageType)){			
			List<KeyValuePair<Integer, String>> newTypes = new ArrayList<KeyValuePair<Integer,String>>(); 
			openedStageTypeIds.add(stageType);			
			this.currentStageTypeId = stageType;
			KeyValuePair<Integer, String> pair = new KeyValuePair<Integer, String>();
			pair.setKey(stageType);
			pair.setValue(GameServerAssist.getEliteStageTemplateManager().getEliteStageTypeTempalte(stageType).getName());
			openedStageTypes.add(pair);
			newTypes.add(pair);
			KeyValuePair<Integer, String>[] newStageTypes = KeyValuePair.newKeyValuePairArray(0);
			GCNewEliteStageTypeOpen gcNewTypeOpenMsg = new GCNewEliteStageTypeOpen();
			gcNewTypeOpenMsg.setEliteStageType(newTypes.toArray(newStageTypes));
			human.sendMessage(gcNewTypeOpenMsg);			
		}
	}
	
	
	
	/**
	 * 生成战斗胜利奖励物品id
	 * @param stageId
	 */
	public void genRewardItemId(int stageId) {
		rewardItemId=0;
		EliteStageTemplate template = GameServerAssist.getEliteStageTemplateManager().getEliteStageTemplate(stageId);		
		if (template == null) {
			return ;
		}
		// 奖励物品概率
		int rewardItemRate = template.getRewardItemRate();
		if (!MathUtils.shake(rewardItemRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
			human.sendWarningMessage(LangConstants.ELITE_NO_ITEM_REWARD);
			return;
		}
		List<Integer> weightList = new ArrayList<Integer>();
		for(Integer weight : template.getItemRateArr()){
			weightList.add(weight);
		}
		int[] rewardIndex = MathUtils.getRandomUniqueIndex(weightList, 1);
		rewardItemId = template.getItemIdArr()[rewardIndex[0]];
	}
	
	private void sendBattleResultMessage(int stageId,boolean isWin, int star, float rewardRate){
		GCEliteStageBattleResult gcBattleResultMsg = new GCEliteStageBattleResult();
		gcBattleResultMsg.setBattleResult(isWin);	
		CommonItem[] items=new CommonItem[0];
		if(isWin){			
			EliteStageTemplate template = GameServerAssist.getEliteStageTemplateManager().getEliteStageTemplate(stageId);
			gcBattleResultMsg.setCoinNum((int) (template.getCoinNum()*rewardRate));
			gcBattleResultMsg.setExperience((int) (template.getExp()*rewardRate));
			gcBattleResultMsg.setTechPoint((int) (template.getTechPoint()*rewardRate));
			gcBattleResultMsg.setStar(star);
			if(rewardItemId>0){
				CommonItem item = CommonItemBuilder.genCommonItem(rewardItemId);
				if(item!=null){
					items = new CommonItem[1];
					items[0]= item;
				}
			}	
		}
		gcBattleResultMsg.setStageId(stageId);
		gcBattleResultMsg.setItems(items);
		gcBattleResultMsg.setTotalStar(getTotalStar());
		this.human.sendMessage(gcBattleResultMsg);
	}

	
	/**
	 * 获取刷新精英副本计数器
	 * 
	 * @return
	 */
	public CommonActionCounter getChallengeCounter() {
		CommonActionCounter counter = new CommonActionCounter();
		counter.setCurrencyType(CurrencyType.CRYSTAL.getIndex());
		int totalCount = GameServerAssist.getVipPrivilegeTemplateManager().getMaxRefreshEliteStageTimes(human.getVipLevel());
		int remainCount = totalCount>refreshStateNum ? totalCount-refreshStateNum : 0;
		counter.setTotalCount(totalCount);
		counter.setRemainCount(remainCount);
		counter.setCurrencyNum(GameServerAssist.getEliteStageTemplateManager().getRefreshStageStateCost(refreshStateNum + 1));
		return counter;
	}

	/**
	 * 重置每日数据
	 */
	public void resetDailyData() {
		this.refreshStateNum=0;		
		for(EliteStageState.Builder stageState : eliteStageStateMap.values()){
			stageState.setChallengeState(false);
		}
		cache.addUpdate(human.getHumanGuid(), this.convertToEntity());
	}

	/**
	 * 精英副本数据
	 * 
	 * @return
	 */
	public Map<Integer,EliteStageState.Builder> getEliteStageStateMap() {
		return eliteStageStateMap;
	}
	
	/**
	 * 可以扫荡的精英副本名称
	 * 
	 * @return
	 */
	public List<EliteStageInfo> getCanAutoBattleEliteStages(int stageTypeId) {
		EliteStageInfo[] eliteStages = GameServerAssist.getEliteStageTemplateManager().getEliteStageInfos(human.getOccupation().getIndex(),stageTypeId);
		List<EliteStageInfo> stages = new ArrayList<EliteStageInfo>();
		for(EliteStageInfo eliteStageInfo : eliteStages){
			Builder builder = eliteStageStateMap.get(eliteStageInfo.getStageId());
			if(builder == null){
				continue;
			}
			if(GameServerAssist.getStageTemplateManager().canAutoBattle(builder.getStageStar())
					&& builder.getChallengeState() == false && builder.getConquerState()){
				stages.add(eliteStageInfo);
			}
		}
		Arrays.sort(stages.toArray(new EliteStageInfo[0]), new EliteStageSorter());
		return stages;
	}
	
	/**
	 * 精英关卡所有评分之和
	 * @return
	 */
	public int getTotalStar() {
		int star = 0;
		for(EliteStageState.Builder builder : eliteStageStateMap.values()){
			star+=builder.getStageStar();
		}
		return star;
	}
	
	public int getRewardItemId() {
		return this.rewardItemId;
	}
	
	public EliteStageInfo[] getVisibleEliteStages(int stageTypeId){
		EliteStageInfo[] result = new EliteStageInfo[0];
		if(!this.openedStageTypeIds.contains(stageTypeId)){
			return result;
		}		
		EliteStageTemplateManager templateManager = GameServerAssist.getEliteStageTemplateManager();
		EliteStageInfo[] stageInfos = templateManager.getEliteStageInfos(human.getOccupation().getIndex(), stageTypeId);
		EliteStageInfo[] eliteStages =  new EliteStageInfo[stageInfos.length];
		int humanLevel = human.getLevel();
		for(int i=0;i<eliteStages.length;i++){
			eliteStages[i] = new EliteStageInfo();
			eliteStages[i].setStageId(stageInfos[i].getStageId());
			eliteStages[i].setOpenLevel(stageInfos[i].getOpenLevel());
			eliteStages[i].setCoinNum(stageInfos[i].getCoinNum());
			eliteStages[i].setExp(stageInfos[i].getExp());
			eliteStages[i].setTechPoint(stageInfos[i].getTechPoint());			
			eliteStages[i].setMonsterIconId(stageInfos[i].getMonsterIconId());
			eliteStages[i].setMonsterName(stageInfos[i].getMonsterName());
			eliteStages[i].setItemNames(stageInfos[i].getItemNames());
			eliteStages[i].setEnergyNum(stageInfos[i].getEnergyNum());	
			int stageId = stageInfos[i].getStageId();
			EliteStageTemplate template = templateManager.getEliteStageTemplate(stageId);
			EliteStageState.Builder stageStage = eliteStageStateMap.get(stageId);
			if(stageStage!=null){
				eliteStages[i].setChallengeState(stageStage.getChallengeState());
				int openState = humanLevel >= template.getOpenLevel()? 1 : 2;
				eliteStages[i].setOpenState(openState);
				eliteStages[i].setStar(stageStage.getStageStar());
				eliteStages[i].setPassState(stageStage.getConquerState());
			}
			else{
				eliteStages[i].setChallengeState(false);
				eliteStages[i].setOpenState(0);
				eliteStages[i].setStar(0);
				eliteStages[i].setPassState(false);
			}
			EliteStageTypeTemplate typeTempalte = templateManager.getEliteStageTypeTempalte(template.getType());
			EliteStageTypeInfo typeInfo = new EliteStageTypeInfo();
			typeInfo.setStageType(typeTempalte.getId());
			typeInfo.setOpenLevel(typeTempalte.getOpenLevel());
			typeInfo.setTypeName(typeTempalte.getName());
			eliteStages[i].setTypeInfo(typeInfo);
		}
		return eliteStages;
	}
	
	public int getRefreshStateNum(){
		return refreshStateNum;
	}
	
}
class EliteStageSorter implements Comparator<EliteStageInfo>{

	@Override
	public int compare(EliteStageInfo arg0, EliteStageInfo arg1) {
		return arg0.getStageId() - arg1.getStageId();
	}
	
}
