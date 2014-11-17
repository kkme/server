package com.hifun.soul.manageweb.character.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.character.CharacterQueryCondition;
import com.hifun.soul.manageweb.common.PagingInfo;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.common.Character.CharProperty;
import com.hifun.soul.proto.common.EliteStage.HumanEliteStageInfo;
import com.hifun.soul.proto.common.Mine.HumanMineInfo;
import com.hifun.soul.proto.common.Refine.RefineMapData;
import com.hifun.soul.proto.common.Refine.RefineStageData;
import com.hifun.soul.proto.common.Warrior.HumanWarriorInfo;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties;
import com.hifun.soul.proto.data.entity.Entity.HumanCarriedSkill;
import com.hifun.soul.proto.data.entity.Entity.HumanCostNotify;
import com.hifun.soul.proto.data.entity.Entity.HumanDailyQuestRewardBox;
import com.hifun.soul.proto.data.entity.Entity.HumanGift;
import com.hifun.soul.proto.data.entity.Entity.HumanHoroscope;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;
import com.hifun.soul.proto.data.entity.Entity.HumanLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanMainCityInfo;
import com.hifun.soul.proto.data.entity.Entity.HumanMeditation;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestData;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestFinishData;
import com.hifun.soul.proto.data.entity.Entity.HumanStageMapState;
import com.hifun.soul.proto.data.entity.Entity.HumanStargazer;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology;
import com.hifun.soul.proto.services.Services.CharacterBaseInfo;
import com.hifun.soul.proto.services.Services.CharacterRpcService;
import com.hifun.soul.proto.services.Services.CheckCharacterOnlineStateRequest;
import com.hifun.soul.proto.services.Services.CheckCharacterOnlineStateResponse;
import com.hifun.soul.proto.services.Services.GetCharacterListRequest;
import com.hifun.soul.proto.services.Services.GetCharacterListResponse;
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
import com.hifun.soul.proto.services.Services.KickOffCharacterRequest;
import com.hifun.soul.proto.services.Services.KickOffCharacterResponse;
import com.hifun.soul.proto.services.Services.QueryCurrentOnlineNumRequest;
import com.hifun.soul.proto.services.Services.QueryCurrentOnlineNumResponse;
import com.hifun.soul.proto.services.Services.QueryHumanGiftResponse;
import com.hifun.soul.proto.services.Services.QueryHumanRefineResponse;
import com.hifun.soul.proto.services.Services.QueryHumanWarriorInfoResponse;
import com.opensymphony.xwork2.ActionContext;

public class CharacterAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1411960040959113591L;

	
	private CharacterQueryCondition queryCondition;

	private List<CharacterBaseInfo> characters;
	
	private Map<Integer, Integer> otherProperties = new HashMap<Integer, Integer>();

	private HumanBaseProperties baseProperties;

	private List<HumanQuestData> humanQuestDatas;

	private List<HumanQuestFinishData> humanQuestFinishDatas;

	private List<EquipItem> equipedItems;

	private List<Item> bagItems;

	private List<HumanCarriedSkill> humanSkills;

	private List<HumanFriendInfo> humanFriends;

	private List<HumanHoroscope> horoscopes;

	private List<HumanStargazer> stargazer;

	private List<HumanTechnology> technologys;
	
	private List<HumanDailyQuestRewardBox> dailyRewardBox;
	
	private List<HumanStageMapState> stageMapState;
	
	private HumanEliteStageInfo eliteStageInfo;
	
	private HumanMeditation meditation;
	
	private HumanMineInfo mineInfo;
	
	private HumanQuestionData humanQuestion;
	
	private HumanMainCityInfo mainCityInfo;
	
	private List<HumanLoginReward> loginRewards;
	
	private List<HumanCostNotify> costnotifys;
	
	private HumanGift gifts;
	
	private HumanWarriorInfo warriorInfo;
	private List<RefineMapData> refineMap;
	private List<RefineStageData> refineStage;
	
	/**
	 * 在线玩家总数
	 */
	private int onlineNum;
	/**
	 * 查询的玩家是否在线
	 */
	private boolean isOnline;
	
	public CharacterAction(){
		queryCondition = new CharacterQueryCondition();
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setPageIndex(0);
		pagingInfo.setPageSize(20);
		queryCondition.setPagingInfo(pagingInfo);		
	}
	
	// #start folded properties
	
	public CharacterQueryCondition getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(CharacterQueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}

	public Map<Integer, Integer> getOtherProperties() {
		return otherProperties;
	}

	public HumanBaseProperties getBaseProperties() {
		return baseProperties;
	}

	public List<HumanQuestData> getHumanQuestDatas() {
		return humanQuestDatas;
	}

	public List<HumanQuestFinishData> getHumanQuestFinishDatas() {
		return humanQuestFinishDatas;
	}

	public List<EquipItem> getEquipedItems() {
		return equipedItems;
	}

	public List<Item> getBagItems() {
		return bagItems;
	}	

	public List<HumanCarriedSkill> getHumanSkills() {
		return humanSkills;
	}

	public List<HumanFriendInfo> getHumanFriends() {
		return humanFriends;
	}

	public List<HumanHoroscope> getHoroscopes() {
		return horoscopes;
	}

	public List<HumanStargazer> getStargazer() {
		return stargazer;
	}

	public List<HumanTechnology> getTechnologys() {
		return technologys;
	}

	public List<CharacterBaseInfo> getCharacters() {
		return characters;
	}

	public void setCharacters(List<CharacterBaseInfo> characters) {
		this.characters = characters;
	}

	public List<HumanDailyQuestRewardBox> getDailyRewardBox() {
		return dailyRewardBox;
	}


	public void setDailyRewardBox(List<HumanDailyQuestRewardBox> dailyRewardBox) {
		this.dailyRewardBox = dailyRewardBox;
	}
	
	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}
	
	public boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public List<HumanStageMapState> getStageMapState() {
		return stageMapState;
	}

	public void setStageMapState(List<HumanStageMapState> stageMapState) {
		this.stageMapState = stageMapState;
	}

	public HumanEliteStageInfo getEliteStageInfo() {
		return eliteStageInfo;
	}

	public void setEliteStageInfo(HumanEliteStageInfo eliteStageInfo) {
		this.eliteStageInfo = eliteStageInfo;
	}
	
	public HumanMeditation getMeditation(){
		return this.meditation;
	}
	
	public HumanMineInfo getMineInfo() {
		return mineInfo;
	}

	public HumanMainCityInfo getMainCityInfo() {
		return mainCityInfo;
	}

	public List<HumanLoginReward> getLoginRewards() {
		return loginRewards;
	}

	public List<HumanCostNotify> getCostnotifys() {
		return costnotifys;
	}

	public HumanQuestionData getHumanQuestion() {
		return humanQuestion;
	}
	
	//#end
	
	
	public HumanGift getGifts() {
		return gifts;
	}

	public HumanWarriorInfo getWarriorInfo() {
		return warriorInfo;
	}

	public List<RefineMapData> getRefineMap() {
		return refineMap;
	}

	public List<RefineStageData> getRefineStage() {
		return refineStage;
	}

	public String queryCharacterListForward(){
		return "success";
	}
	
	public String queryCharacterList() throws ServiceException {
		if (ActionContext.getContext().getSession().containsKey("characterList")) {
			ActionContext.getContext().getSession().remove("characterList");
		}
		int startIndex = queryCondition.getPagingInfo().getPageIndex()*queryCondition.getPagingInfo().getPageSize();
		long queryId = 0;
		try{
			queryId=Long.parseLong(queryCondition.getQueryId());
		}catch(Exception e){}
		CharacterRpcService.BlockingInterface characterService = Managers.getServiceManager().getCharacterService();		
		GetCharacterListResponse response = characterService.getCharacterList(
				null,
				GetCharacterListRequest.newBuilder().setStart(startIndex)
						.setMaxResult(queryCondition.getPagingInfo().getPageSize())
						.setCharacterName(queryCondition.getQueryName()).setCharacterId(queryId)
						.setAccountName(queryCondition.getAccountName())
						.build());
		characters = response.getBaseInfoList();
		ActionContext.getContext().getSession().put("characterList", characters);
		this.queryCondition.getPagingInfo().setTotalCount(response.getTotalCount());
		boolean result = characters != null;
		ManageLogger.log(ManageLogType.CHARACTER_LIST,PermissionType.CHARACTER_LIST, "", result);
		if (!result) {
			putErrorInfoToRequest("没有角色信息!");
			return "";
		}
		return "characterList";
	}

	/**
	 * 初始化角色列表
	 * 
	 * @throws ServiceException
	 */
	private void initCharacters() throws ServiceException {
		@SuppressWarnings("unchecked")
		List<CharacterBaseInfo> characters = (List<CharacterBaseInfo>) ActionContext.getContext().getSession().get("characterList");
		if (characters == null) {
			queryCharacterList();
		}
		this.setCharacters(characters);
	}

	/**
	 * 获取角色详细信息
	 * 
	 * @param characterGuid
	 * @return
	 * @throws ServiceException
	 */

	public String showCharacterPropertyInfo() throws ServiceException {
		initCharacters();
		for (CharacterBaseInfo baseInfo : characters) {
			String characterGuid = ServletActionContext.getRequest().getParameter("id");
			if (baseInfo.getBaseProperties().getHumanGuid() == Long.parseLong(characterGuid)) {
				this.baseProperties = baseInfo.getBaseProperties();
				for (CharProperty property : baseInfo.getOtherProperties().getPropsList()) {
					this.otherProperties.put(property.getKey(), (int) property.getValue());
				}
				break;
			}
		}
		return "success";
	}

	/**
	 * 获取角色物品信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterItemInfo() throws ServiceException {		
		initCharacters();
		equipedItems = new ArrayList<EquipItem>();
		bagItems = new ArrayList<Item>();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long guid = Long.parseLong(characterGuid);
		GetHumanItemsResponse response =  Managers.getServiceManager().getCharacterService().queryHumanItem(null, GetHumanInfoRequest.newBuilder().setHumanGuid(guid).build());
		List<HumanItem> humanItems = response.getItemsList();
		for (HumanItem itemBuilder : humanItems) {
			Item item = ItemFactory.convertHumanItemToItem(null, itemBuilder);
			if (item == null) {
				continue;
			}
			if(item.isEquip()){
				if(((EquipItemFeature)item.getFeature()).isEquiped()){
					equipedItems.add((EquipItem) item);
					continue;
				}
			}
			if (item.getBagType() == BagType.MAIN_BAG) {
				bagItems.add(item);
			}
		}
		return "success";
	}
	
	/**
	 * 获取角色好友信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterFriendInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanInfoRequest request = GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build();
		GetHumanFriendResponse response = Managers.getServiceManager().getCharacterService().queryHumanFriend(null, request);
		this.humanFriends = response.getFriendList();		
		return "success";
	}
	
	/**
	 * 获取角色任务信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterQuestInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanQuestResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanQuest(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.humanQuestDatas  = response.getQuestList();
		this.humanQuestFinishDatas = response.getFinishQuestList();
		this.dailyRewardBox = response.getDailyRewardBoxList();			
		return "success";
	}
	
	/**
	 * 获取角色的星运信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterHoroscopeInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanHoroscopeResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanHoroscope(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.horoscopes  = response.getHoroscopeList();
		this.stargazer = response.getStargazerList();		
		return "success";
	}
	
	/**
	 * 获取角色的星运信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterTechnologyInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanTechnologyResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanTechnology(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.technologys  = response.getTechnologyList();
		this.meditation = response.getMeditation();				
		return "success";
	}
	
	/**
	 * 检查玩家关卡情况
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showStageInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanStageResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanStage(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.stageMapState  = response.getStageMapStateList();	
		this.eliteStageInfo  = response.getEliteStageInfo();
		return "success";
	}
	
	/**
	 * 踢人下线
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String kickOffCharacter() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		CharacterRpcService.BlockingInterface characterService = Managers.getServiceManager().getCharacterService();
		KickOffCharacterRequest request = KickOffCharacterRequest.newBuilder().setHumanGuid(Long.parseLong(characterGuid)).build();
		KickOffCharacterResponse response = characterService.kickOffCharacter(null, request);
		ManageLogger.log(ManageLogType.KICK_OFF_CHARACTER,PermissionType.KICK_OFF_CHARACTER, "characterGuid:"+characterGuid, response.getResult());
		if(response.getResult()){
			return "success";
		}
		else{
			return "failed";
		}
	}
	
	/**
	 * 检查玩家在线状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String checkOnlineState() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		CharacterRpcService.BlockingInterface characterService = Managers.getServiceManager().getCharacterService();
		CheckCharacterOnlineStateRequest request = CheckCharacterOnlineStateRequest.newBuilder().setHumanGuid(Long.parseLong(characterGuid)).build();
		CheckCharacterOnlineStateResponse response = characterService.checkCharacterOnlineState(null, request);
		this.isOnline = response.getIsOnline();
		ManageLogger.log(ManageLogType.CHECK_ONLINE_STATE,PermissionType.CHECK_ONLINE_STATE, "characterGuid:"+characterGuid, true);
		return "success";
	}
	
	/**
	 * 检查玩家在线状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String queryCurrentOnlineNum() throws ServiceException {
		initCharacters();
		CharacterRpcService.BlockingInterface characterService = Managers.getServiceManager().getCharacterService();
		QueryCurrentOnlineNumRequest request = QueryCurrentOnlineNumRequest.newBuilder().build();
		QueryCurrentOnlineNumResponse response = characterService.queryCurrentOnlineNum(null, request);
		this.onlineNum = response.getOnlineNum();
		ManageLogger.log(ManageLogType.QUERY_ONLINE_NUMBER,PermissionType.QUERY_ONLINE_NUMBER, "", true);
		return "success";
	}
	
	/**
	 * 查询玩家技能信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterSkillInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanSkillResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanSkill(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.humanSkills  = response.getSkillList();
		ManageLogger.log(ManageLogType.SHOW_CHARACTER_SKILL_INFO,PermissionType.SHOW_CHARACTER_SKILL_INFO, "", true);
		return "success";
	}
	
	/**
	 * 查询玩家矿场信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterMineInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanMineResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanMine(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.mineInfo  = response.getMine();
		ManageLogger.log(ManageLogType.SHOW_CHARACTER_MINE_INFO,PermissionType.SHOW_CHARACTER_MINE_INFO, "", true);
		return "success";
	}
	
	/**
	 * 查询玩家奇遇答题信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCharacterQuestionInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanQuestionResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanQuestion(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.humanQuestion  = response.getQuestion();
		ManageLogger.log(ManageLogType.SHOW_CHARACTER_QUESTION_INFO,PermissionType.SHOW_CHARACTER_QUESTION_INFO, "", true);
		return "success";
	}
	
	/**
	 * 查询玩家主城宝石收集信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showStoneCollectInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanStoneCollectResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanStoneCollect(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.mainCityInfo  = response.getMainCityInfo();
		ManageLogger.log(ManageLogType.SHOW_STONE_COLLECT_INFO,PermissionType.SHOW_STONE_COLLECT_INFO, "", true);
		return "success";
	}
	/**
	 * 查询玩家连续登陆奖励信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showLoginRewardInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanLoginRewardResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanLoginReward(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.loginRewards  = response.getLoginRewardList();
		ManageLogger.log(ManageLogType.SHOW_LOGIN_REWARD_INFO,PermissionType.SHOW_LOGIN_REWARD_INFO, "", true);
		return "success";
	}
	
	/**
	 * 查询玩家消费提醒信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showCostnotifyInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		GetHumanCostnotifyResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanCostnotify(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.costnotifys  = response.getCostNotifyList();
		ManageLogger.log(ManageLogType.SHOW_COSTNOTIFY_INFO,PermissionType.SHOW_COSTNOTIFY_INFO, "", true);
		return "success";
	}
	
	/**
	 * 查询玩家天赋信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showGiftInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		QueryHumanGiftResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanGift(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		// editby:crazyjohn 2014-05-13
		//this.gifts  = response.getGift(0);
		ManageLogger.log(ManageLogType.SHOW_GIFT_INFO,PermissionType.SHOW_GIFT_INFO, "", true);
		return "success";
	}
	/**
	 * 查询玩家勇士之路信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showWarriorInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		QueryHumanWarriorInfoResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanWarriorInfo(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.warriorInfo  = response.getWarriorInfo();
		ManageLogger.log(ManageLogType.SHOW_WARRIOR_INFO,PermissionType.SHOW_WARRIOR_INFO, "", true);
		return "success";
	}
	/**
	 * 查询玩家试炼信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String showRefineInfo() throws ServiceException {
		initCharacters();
		String characterGuid = ServletActionContext.getRequest().getParameter("id");
		Long humanGuid = Long.parseLong(characterGuid);
		QueryHumanRefineResponse response = Managers.getServiceManager().getCharacterService()
						.queryHumanRefine(null, GetHumanInfoRequest.newBuilder().setHumanGuid(humanGuid).build());
		this.refineMap  = response.getRefineMapDataList();
		this.refineStage = response.getRefineStageDataList();
		ManageLogger.log(ManageLogType.SHOW_REFINE_INFO,PermissionType.SHOW_REFINE_INFO, "", true);
		return "success";
	}
}
