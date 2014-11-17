package com.hifun.soul.manageserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.FriendEntity;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanQuestionEntity;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.manageserver.RpcCallbackManager;
import com.hifun.soul.manageserver.ServerSessionManager;
import com.hifun.soul.manageserver.msg.MGCheckOnlineState;
import com.hifun.soul.manageserver.msg.MGKickOffAllCharacter;
import com.hifun.soul.manageserver.msg.MGKickOffCharacter;
import com.hifun.soul.manageserver.msg.MGQueryOnlineNum;
import com.hifun.soul.proto.data.entity.Entity;
import com.hifun.soul.proto.data.entity.Entity.Human;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineMap;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineStage;
import com.hifun.soul.proto.services.Services.CharacterBaseInfo;
import com.hifun.soul.proto.services.Services.CharacterRpcService;
import com.hifun.soul.proto.services.Services.CheckCharacterOnlineStateRequest;
import com.hifun.soul.proto.services.Services.CheckCharacterOnlineStateResponse;
import com.hifun.soul.proto.services.Services.GetCharacterListRequest;
import com.hifun.soul.proto.services.Services.GetCharacterListResponse;
import com.hifun.soul.proto.services.Services.GetHumanCDResponse;
import com.hifun.soul.proto.services.Services.GetHumanCostnotifyResponse;
import com.hifun.soul.proto.services.Services.GetHumanFriendResponse;
import com.hifun.soul.proto.services.Services.GetHumanHoroscopeResponse;
import com.hifun.soul.proto.services.Services.GetHumanInfoRequest;
import com.hifun.soul.proto.services.Services.GetHumanItemsResponse;
import com.hifun.soul.proto.services.Services.GetHumanLoginRewardResponse;
import com.hifun.soul.proto.services.Services.GetHumanMineResponse;
import com.hifun.soul.proto.services.Services.GetHumanQuestResponse;
import com.hifun.soul.proto.services.Services.GetHumanQuestionResponse;
import com.hifun.soul.proto.services.Services.GetHumanSkillResponse;
import com.hifun.soul.proto.services.Services.GetHumanStageResponse;
import com.hifun.soul.proto.services.Services.GetHumanStoneCollectResponse;
import com.hifun.soul.proto.services.Services.GetHumanTechnologyResponse;
import com.hifun.soul.proto.services.Services.HumanFriendInfo;
import com.hifun.soul.proto.services.Services.HumanQuestionData;
import com.hifun.soul.proto.services.Services.KickOffAllCharacterRequest;
import com.hifun.soul.proto.services.Services.KickOffAllCharacterResponse;
import com.hifun.soul.proto.services.Services.KickOffCharacterRequest;
import com.hifun.soul.proto.services.Services.KickOffCharacterResponse;
import com.hifun.soul.proto.services.Services.QueryCurrentOnlineNumRequest;
import com.hifun.soul.proto.services.Services.QueryCurrentOnlineNumResponse;
import com.hifun.soul.proto.services.Services.QueryHumanGiftResponse;
import com.hifun.soul.proto.services.Services.QueryHumanIdByNameRequest;
import com.hifun.soul.proto.services.Services.QueryHumanIdByNameResponse;
import com.hifun.soul.proto.services.Services.QueryHumanRefineResponse;
import com.hifun.soul.proto.services.Services.QueryHumanWarriorInfoResponse;
import com.hifun.soul.proto.services.Services.QueryOnlineHumanRequest;
import com.hifun.soul.proto.services.Services.QueryOnlineHumanResponse;

public class CharacterService extends CharacterRpcService{
	
	private ServerSessionManager sessionManager;
	private RpcCallbackManager callbackManager;
	private DBServiceManager dbManager;
	
	// 角色信息缓存
	private Map<Long, Entity.Human.Builder> humanCache = new HashMap<Long, Entity.Human.Builder>();

	public CharacterService(DBServiceManager dbManager,ServerSessionManager sessionManager,RpcCallbackManager callbackManager) {
		this.sessionManager = sessionManager;
		this.callbackManager = callbackManager;
		this.dbManager = dbManager;
	}
	
	/**
	 * 获取角色列表
	 */
	@Override
	public void getCharacterList(RpcController controller, GetCharacterListRequest request,
			RpcCallback<GetCharacterListResponse> done) {
		long id = request.getCharacterId();
		String accountName = request.getAccountName();
		List<?> entityList = null;
		GetCharacterListResponse.Builder builder = GetCharacterListResponse
				.newBuilder();
		if (id != 0) {
			entityList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
					DataQueryConstants.QUERY_HUMAN_BY_ID, new String[] { "id" },
					new Object[] { id }, request.getMaxResult(), request.getStart());
			int totalCount = entityList== null ? 0:entityList.size();
			builder.setTotalCount(totalCount);
		}else if(accountName!=null && accountName.length()>0){
			entityList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
					DataQueryConstants.QUERY_CHARACTERINFO_BY_ACCOUNTNAME, new String[] { "accountName" },
					new Object[] { accountName }, request.getMaxResult(), request.getStart());
			int totalCount = entityList== null ? 0:entityList.size();
			builder.setTotalCount(totalCount);
		}else {
			String paramName = "%%";
			if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
				paramName = "%" + request.getCharacterName() + "%";
			}
			entityList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
					DataQueryConstants.QUERY_CHARACTER_LIST_BY_CONDITION, new String[] { "name" },
					new Object[] { paramName }, request.getMaxResult(), request.getStart());
			List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
					DataQueryConstants.QUERY_CHARACTER_COUNT_BY_CONDITION,
					new String[] {"name" },
					new Object[] {paramName});
			builder.setTotalCount((Long)resultList.get(0));
		}
		if (entityList == null || entityList.isEmpty()) {
			done.run(GetCharacterListResponse.newBuilder().setTotalCount(0).build());
			return;
		}
		humanCache.clear();
		CharacterBaseInfo.Builder baseInfo = null;
		for (int i = 0; i < entityList.size(); i++) {
			HumanEntity entity = (HumanEntity) entityList.get(i);
			Human.Builder humanBuilder = entity.getBuilder();	
			baseInfo = CharacterBaseInfo.newBuilder();
			baseInfo.setBaseProperties(humanBuilder.getBaseProperties());
			baseInfo.setOtherProperties(humanBuilder.getOtherProperties());
			builder.addBaseInfo(baseInfo);
			humanCache.put(entity.getId(), humanBuilder);
		}
		done.run(builder.build());		
	}	
	
	/**
	 * 踢人下线
	 */
	@Override
	public void kickOffCharacter(RpcController controller, KickOffCharacterRequest request,
			RpcCallback<KickOffCharacterResponse> done) {
		MGKickOffCharacter mgMsg = new MGKickOffCharacter();
		mgMsg.setHumanGuid(request.getHumanGuid());	
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);	
	}

	@Override
	public void checkCharacterOnlineState(RpcController arg0, CheckCharacterOnlineStateRequest request,
			RpcCallback<CheckCharacterOnlineStateResponse> done) {
		MGCheckOnlineState mgMsg = new MGCheckOnlineState();
		mgMsg.setHumanGuid(request.getHumanGuid());
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

	@Override
	public void queryCurrentOnlineNum(RpcController arg0, QueryCurrentOnlineNumRequest request,
			RpcCallback<QueryCurrentOnlineNumResponse> done) {
		MGQueryOnlineNum mgMsg = new MGQueryOnlineNum();
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);
	}

	

	@Override
	public void kickOffAllCharacter(RpcController controller, KickOffAllCharacterRequest request,
			RpcCallback<KickOffAllCharacterResponse> done) {
		MGKickOffAllCharacter mgMsg = new MGKickOffAllCharacter();
		callbackManager.registerCallback(mgMsg, done);
		sessionManager.getGameServer().write(mgMsg);	
	}
	
	private Human.Builder queryHumanById(long humanGuid){
		List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_HUMAN_BY_ID, new String[] { "id" },
				new Object[] { humanGuid });
		if(resultList==null || resultList.size()==0){
			return null;
		}
		HumanEntity entity = (HumanEntity) resultList.get(0);
		if(humanCache.size()>50){
			humanCache.clear();
		}
		humanCache.put(entity.getId(), entity.getBuilder());
		return entity.getBuilder();
	}

	@Override
	public void queryHumanItem(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanItemsResponse> done) {
		GetHumanItemsResponse.Builder response = GetHumanItemsResponse.newBuilder();		
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllItems(humanBuilder.getItemList());
		done.run(response.build());
	}

	@Override
	public void queryHumanTechnology(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanTechnologyResponse> done) {
		GetHumanTechnologyResponse.Builder response = GetHumanTechnologyResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllTechnology(humanBuilder.getTechnologyList());
		response.setMeditation(humanBuilder.getMeditation());
		done.run(response.build());
	}

	@Override
	public void queryHumanQuest(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanQuestResponse> done) {
		GetHumanQuestResponse.Builder response = GetHumanQuestResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllQuest(humanBuilder.getHumanQuestList());
		response.addAllFinishQuest(humanBuilder.getHumanFinishQuestList());
		response.addAllDailyRewardBox(humanBuilder.getDailyRewardBoxList());
		done.run(response.build());
	}

	@Override
	public void queryHumanHoroscope(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanHoroscopeResponse> done) {
		GetHumanHoroscopeResponse.Builder response = GetHumanHoroscopeResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllHoroscope(humanBuilder.getHoroscopeList());
		done.run(response.build());
		
	}

	@Override
	public void queryHumanStage(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanStageResponse> done) {
		GetHumanStageResponse.Builder response = GetHumanStageResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllStageMapState(humanBuilder.getStageMapStateList());
		response.setEliteStageInfo(humanBuilder.getEliteStageInfo());
		done.run(response.build());
		
	}

	@Override
	public void queryHumanSkill(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanSkillResponse> done) {
		GetHumanSkillResponse.Builder response = GetHumanSkillResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllSkill(humanBuilder.getSkillList());
		done.run(response.build());		
	}

	@Override
	public void queryHumanMine(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanMineResponse> done) {
		GetHumanMineResponse.Builder response = GetHumanMineResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.setMine(humanBuilder.getMine());
		done.run(response.build());		
	}

	@Override
	public void queryHumanFriend(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanFriendResponse> done) {
		GetHumanFriendResponse.Builder response = GetHumanFriendResponse.newBuilder();		
		List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_FRIEND_BY_HUMANID, new String[] { "humanId" },
				new Object[] { request.getHumanGuid() });
		if(resultList!=null && resultList.size()>0){
			HumanFriendInfo.Builder friendInfo = null;
			for(Object obj : resultList){
				FriendEntity friendEntity = (FriendEntity)obj;
				friendInfo = HumanFriendInfo.newBuilder();
				friendInfo.setFriendIds(friendEntity.getFriendIds());
				friendInfo.setId((Long)friendEntity.getId());
				friendInfo.setHumanName(friendEntity.getHumanName());
				friendInfo.setHumanOccupation(friendEntity.getHumanOccupation());
				friendInfo.setHumanLevel(friendEntity.getHumanLevel());
				String otherSendApplyIds = friendEntity.getOtherSendFriendApplyIds() != null ? friendEntity.getOtherSendFriendApplyIds() : "";
				friendInfo.setOtherSendFriendApplyIds(otherSendApplyIds);
				String recievedEnergyFriendIds = friendEntity.getRecievedEnergyFriendIds() != null ? friendEntity.getRecievedEnergyFriendIds(): "" ;
				friendInfo.setRecievedEnergyFriendIds(recievedEnergyFriendIds);
				String selfSendApplyIds = friendEntity.getSelfSendFriendApplyIds() != null ? friendEntity.getSelfSendFriendApplyIds(): "" ;
				friendInfo.setSelfSendFriendApplyIds(selfSendApplyIds);
				String sendEnergyToFriends = friendEntity.getSendEnergyToOtherFriendIds() != null ? friendEntity.getSendEnergyToOtherFriendIds() : "";
				friendInfo.setSendEnergyToOtherFriendIds(sendEnergyToFriends);
				String sendEnergyToSelfIds = friendEntity.getSendEnergyToSelfFriendIds()!=null ? friendEntity.getSendEnergyToSelfFriendIds() : "";
				friendInfo.setSendEnergyToSelfFriendIds(sendEnergyToSelfIds);
				response.addFriend(friendInfo);
			}
		}		
		done.run(response.build());
	}

	@Override
	public void queryHumanQuestion(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanQuestionResponse> done) {
		GetHumanQuestionResponse.Builder response = GetHumanQuestionResponse.newBuilder();
		List<?> resultList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_QUESTION_INFO_BY_HUMAN_ID, new String[] { "humanId" },
				new Object[] { request.getHumanGuid() });
		if(resultList!=null && resultList.size()>0){
			HumanQuestionData.Builder questionData = null;
			HumanQuestionEntity questionEntity = (HumanQuestionEntity) resultList.get(0);
			questionData = HumanQuestionData.newBuilder();
			questionData.setBuyBlessTimes(questionEntity.getBuyBlessTimes());
			questionData.setHumanId(questionEntity.getHumanGuid());
			questionData.setLastDailyResetTime(questionEntity.getLastDailyResetTime());
			questionData.setLastWeeklyResetTime(questionEntity.getLastWeeklyResetTime());
			questionData.setQuestionId(questionEntity.getQuestionId());
			questionData.setQuestionIndex(questionEntity.getQuestionIndex());
			if (questionEntity.getScoreExchangeState() != null) {
				String[] scoreExchangeStates = questionEntity.getScoreExchangeState().split(",");
				for (String state : scoreExchangeStates) {
					questionData.addScoreExchangeState(Integer.parseInt(state));
				}
			}
			questionData.setTotalScore(questionEntity.getTotalScore());
			questionData.setUsableBlessNum(questionEntity.getUsableBlessNum());
			response.setQuestion(questionData);			
		}
		done.run(response.build());
	}

	@Override
	public void queryHumanCostnotify(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanCostnotifyResponse> done) {
		GetHumanCostnotifyResponse.Builder response = GetHumanCostnotifyResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllCostNotify(humanBuilder.getCostNotifyList());
		done.run(response.build());		
	}

	@Override
	public void queryHumanCD(RpcController controller, GetHumanInfoRequest request, RpcCallback<GetHumanCDResponse> done) {
		GetHumanCDResponse.Builder response = GetHumanCDResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllCd(humanBuilder.getCdList());
		done.run(response.build());
		
	}

	@Override
	public void queryHumanLoginReward(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanLoginRewardResponse> done) {
		GetHumanLoginRewardResponse.Builder response = GetHumanLoginRewardResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.addAllLoginReward(humanBuilder.getLoginRewardList());
		done.run(response.build());
		
	}

	@Override
	public void queryHumanStoneCollect(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<GetHumanStoneCollectResponse> done) {
		GetHumanStoneCollectResponse.Builder response = GetHumanStoneCollectResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.setMainCityInfo(humanBuilder.getMainCityInfo());
		done.run(response.build());
		
	}

	@Override
	public void queryHumanIdByName(RpcController controller, QueryHumanIdByNameRequest request,
			RpcCallback<QueryHumanIdByNameResponse> done) {		
		long charId = queryHumanIdByName(request.getName());		
		QueryHumanIdByNameResponse response = QueryHumanIdByNameResponse.newBuilder().setCharId(charId).build();
		done.run(response);
	}
	
	/**
	 * 根据角色名称获取角色id
	 * @param humanName
	 * @return
	 */
	public long queryHumanIdByName(String humanName){
		List<?> entityList = dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				DataQueryConstants.QUERY_CHARACTER_ID_BY_NAME, new String[] { "name" },
				new Object[] { humanName });		
		long charId = 0;
		if(entityList != null && !entityList.isEmpty()){
			charId = (Long)entityList.get(0);
		}
		return charId;
	}

	@Override
	public void queryHumanGift(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<QueryHumanGiftResponse> done) {
		QueryHumanGiftResponse.Builder response = QueryHumanGiftResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		// edit by yandajun 2014/3/15 change table structer
		// response.setGift(humanBuilder.get);
		done.run(response.build());
		
	}

	@Override
	public void queryHumanWarriorInfo(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<QueryHumanWarriorInfoResponse> done) {
		QueryHumanWarriorInfoResponse.Builder response = QueryHumanWarriorInfoResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		response.setWarriorInfo(humanBuilder.getWarriorInfo());
		done.run(response.build());		
	}

	@Override
	public void queryHumanRefine(RpcController controller, GetHumanInfoRequest request,
			RpcCallback<QueryHumanRefineResponse> done) {
		QueryHumanRefineResponse.Builder response = QueryHumanRefineResponse.newBuilder();
		Human.Builder humanBuilder = null;
		if(!humanCache.containsKey(request.getHumanGuid())){
			humanBuilder = queryHumanById(request.getHumanGuid());
		}
		else{
			humanBuilder = humanCache.get(request.getHumanGuid());
		}
		if(humanBuilder == null){
			done.run(response.build());
			return;
		}
		for(HumanRefineMap refineMap : humanBuilder.getRefineMapList()){
			response.addRefineMapData(refineMap.getRefineMapData());
		}
		for(HumanRefineStage refineStage : humanBuilder.getRefineStageList()){
			response.addRefineStageData(refineStage.getRefineStageData());
		}		
		done.run(response.build());		
	}

	@Override
	public void queryOnlineHuman(RpcController controller, QueryOnlineHumanRequest request,
			RpcCallback<QueryOnlineHumanResponse> done) {
		// TODO Auto-generated method stub
		
	}
	
}
