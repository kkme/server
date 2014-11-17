package com.hifun.soul.gameserver.warrior.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanWarriorEntity;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.warrior.WarriorOpponent;
import com.hifun.soul.gameserver.warrior.WarriorQuest;
import com.hifun.soul.gameserver.warrior.WarriorQuestReward;
import com.hifun.soul.gameserver.warrior.WarriorQuestRewardState;
import com.hifun.soul.gameserver.warrior.WarriorQuestState;
import com.hifun.soul.gameserver.warrior.msg.GCGetWarriorQuestReward;
import com.hifun.soul.gameserver.warrior.msg.GCOpenWarriorWayPanel;
import com.hifun.soul.gameserver.warrior.msg.GCUpdateOpponent;
import com.hifun.soul.gameserver.warrior.msg.GCUpdateWarriorQuest;
import com.hifun.soul.gameserver.warrior.msg.GCUpdateWarriorQuestRewardState;
import com.hifun.soul.gameserver.warrior.msg.GCWarriorState;
import com.hifun.soul.gameserver.warrior.template.WarriorQuestTemplate;
import com.hifun.soul.proto.common.Warrior.HumanWarriorInfo;

public class HumanWarriorManager implements IHumanPersistenceManager, ICachableComponent, IEventListener, ILoginManager {
	private WarriorQuest[] quests;
	private WarriorQuestReward[] questRewards;
	private Human human;
	private WarriorTemplateManager templateManager;
	private int currentWarriorQuestIndex = 0;
	/** 当前对手 */
	private WarriorOpponent opponent;
	/** key：questId,value:index in quest array */
	private Map<Integer, Integer> questIdAndIndexMap = new HashMap<Integer, Integer>();
	/** 实体缓存器 */
	private CacheEntry<Long, IEntity> caches = new CacheEntry<Long, IEntity>();

	public HumanWarriorManager(Human human) {
		this.human = human;
		this.human.registerPersistenceManager(this);
		this.human.registerCachableManager(this);
		this.human.registerLoginManager(this);
		human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		templateManager = GameServerAssist.getWarriorTemplateManager();
		quests = templateManager.createWarriorQuests();
		for (int i = 0; i < quests.length; i++) {
			questIdAndIndexMap.put(quests[i].getId(), i);
		}
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		HumanWarriorInfo warriorInfo = humanEntity.getBuilder().getWarriorInfo();
		currentWarriorQuestIndex = warriorInfo.getCurrentQuestIndex();
		for (int i = 0; i < currentWarriorQuestIndex; i++) {
			quests[i].setQuestState(WarriorQuestState.FINISHED.getIndex());
			quests[i].setCompleteCount(quests[i].getTotalCount());
		}
		if (currentWarriorQuestIndex < quests.length) {
			if (warriorInfo.getCurrentQuestState() == WarriorQuestState.FINISHED.getIndex()) {
				quests[currentWarriorQuestIndex].setQuestState(WarriorQuestState.FINISHED.getIndex());
				quests[currentWarriorQuestIndex].setCompleteCount(quests[currentWarriorQuestIndex].getTotalCount());
			} else if (warriorInfo.getCurrentQuestState() == WarriorQuestState.ONGOING.getIndex()) {
				quests[currentWarriorQuestIndex].setQuestState(WarriorQuestState.ONGOING.getIndex());
				quests[currentWarriorQuestIndex].setCompleteCount(warriorInfo.getCurrentQuestCompleteNum());
			}
		}
		questRewards = templateManager.getWarriorQuestRewards(currentWarriorQuestIndex, human.getLevel());
		if (warriorInfo.getQuestRewardState() != 0) {
			questRewards[0].setState(warriorInfo.getQuestRewardState());
		}		
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		humanEntity.getBuilder().setWarriorInfo(this.convertToEntity().getBuilder());
	}

	/**
	 * 发送更新面板消息
	 */
	public void sendOpenWarriorWayPanelMessage() {
		if (opponent == null) {
			opponent = OpponentMatcher.getOpponent(human);
		}
		GCOpenWarriorWayPanel gcMsg = new GCOpenWarriorWayPanel();
		gcMsg.setWarriorQuests(quests);
		gcMsg.setOpponentLevel(opponent.getBattleUnit().getLevel());
		gcMsg.setOpponentName(opponent.getBattleUnit().getUnitName());
		gcMsg.setOpponentOccupation(opponent.getBattleUnit().getOccupation().getIndex());
		gcMsg.setOpponentType(opponent.getOpponentType().getIndex());
		gcMsg.setOpponentId(opponent.getBattleUnit().getUnitGuid());
		gcMsg.setBattleWinRewardNum(opponent.getBattleWinRewardNum());		
		gcMsg.setRewards(questRewards);
		gcMsg.setAllRewards(templateManager.getAllWarriorQuestRewards(human.getLevel()));
		human.sendMessage(gcMsg);
	}

	/**
	 * 刷新对手
	 * 
	 * @param ignoreRefreshCdTime 是否忽略刷新时间限制 【对client的刷新是有cd限制的】
	 * @return 是否成功刷新对手
	 * 
	 */
	public boolean refreshOpponent(boolean ignoreRefreshCdTime) {
		boolean result = false;
		if (ignoreRefreshCdTime) {
			opponent = OpponentMatcher.getOpponent(human);
			result = true;
		}
		HumanCdManager cdManager = human.getHumanCdManager();
		long cdTime = GameServerAssist.getGameConstants().getWarriorRefreshOpponentCd() * TimeUtils.SECOND;
		if(cdManager.canAddCd(CdType.WARRIOR_REFRESH_OPPONENT_CD, cdTime)){
			opponent = OpponentMatcher.getOpponent(human);
			cdManager.addCd(CdType.WARRIOR_REFRESH_OPPONENT_CD, cdTime);
			cdManager.snapCdQueueInfo(CdType.WARRIOR_REFRESH_OPPONENT_CD);
			result = true;
		}
		return result;
	}

	/**
	 * 发送更新对手消息
	 */
	public void sendUpdateOpponentMessage() {
		GCUpdateOpponent gcMsg = new GCUpdateOpponent();
		gcMsg.setOpponentLevel(opponent.getBattleUnit().getLevel());
		gcMsg.setOpponentName(opponent.getBattleUnit().getUnitName());
		gcMsg.setOpponentOccupation(opponent.getBattleUnit().getOccupation().getIndex());
		gcMsg.setOpponentType(opponent.getOpponentType().getIndex());
		gcMsg.setBattleWinRewardNum(opponent.getBattleWinRewardNum());
		gcMsg.setOpponentId(opponent.getBattleUnit().getUnitGuid());
		human.sendMessage(gcMsg);
	}

	/**
	 * 获取勇士之心数量
	 * @return
	 */
	private int getWarrioHeartNum() {
		return human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.WARRIOR_HEART_NUM);
	}

	/**
	 * 添加勇士之心数
	 * @param addNum
	 */
	public void addWarriorHeartNum(int addNum) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.WARRIOR_HEART_NUM, getWarrioHeartNum() + addNum);
	}

	/**
	 * 获取当前对手信息
	 * @return
	 */
	public WarriorOpponent getOpponent() {
		return opponent;
	}

	/**
	 * 更新当前勇士任务计数器
	 * @param damageHpPercent 本次战斗对目标造成的伤害百分比
	 */
	public void updateQuestCounter(float damageHpPercent) {
		if (quests.length <= currentWarriorQuestIndex) {
			return;
		}
		// 检查是否达到任务目标
		WarriorQuest currentQuest = quests[this.currentWarriorQuestIndex];
		if (damageHpPercent * SharedConstants.DEFAULT_FLOAT_BASE < currentQuest.getDamageHpPercent()) {
			return;
		}
		int completeNum = currentQuest.getCompleteCount()+1;
		currentQuest.setCompleteCount(completeNum);
		if (completeNum >= currentQuest.getTotalCount()) {
			currentQuest.setQuestState(WarriorQuestState.FINISHED.getIndex());
			questRewards[0].setState(WarriorQuestRewardState.CAN_GET.getIndex());
			human.getHumanCdManager().addCd(CdType.WARRIOR_QUEST_CD,
					GameServerAssist.getGameConstants().getWarriorQuestCdTime() * TimeUtils.SECOND);
			human.getHumanCdManager().snapCdQueueInfo(CdType.WARRIOR_QUEST_CD);
			GCUpdateWarriorQuestRewardState gcUpdateRewardStateMsg = new GCUpdateWarriorQuestRewardState();
			gcUpdateRewardStateMsg.setState(questRewards[0].getState());
			human.sendMessage(gcUpdateRewardStateMsg);
		}
		// 发送更新消息
		GCUpdateWarriorQuest gcUpdateQuestMsg = new GCUpdateWarriorQuest();
		gcUpdateQuestMsg.setWarriorQuests(new WarriorQuest[] { currentQuest });
		human.sendMessage(gcUpdateQuestMsg);
		caches.addUpdate(human.getHumanGuid(), convertToEntity());
	}

	/**
	 * 领取勇士任务奖励
	 */
	public void receiveWarriorQuestRewards() {
		if (questRewards[0].getState() == WarriorQuestRewardState.CAN_GET.getIndex()) {
			human.getWallet().addMoney(CurrencyType.COIN, questRewards[0].getCoin(), true,
					MoneyLogReason.WARRIOR_QUEST_REWARD, "");
			human.addExperience(questRewards[0].getExp(), true, ExperienceLogReason.WARRIOR_QUEST_REWARD, "");
			// 加科技点
			human.getHumanTechnologyManager().addTechnologyPoints(questRewards[0].getTechPoint(),true,TechPointLogReason.WARRIOR_WAY_ADD_TECH_POINT, "");
			currentWarriorQuestIndex++;
			int questIndex = questIdAndIndexMap.get(questRewards[0].getQuestId());
			if (questIndex < quests.length - 1) {
				quests[currentWarriorQuestIndex].setQuestState(WarriorQuestState.ONGOING.getIndex());
				questRewards = templateManager.getWarriorQuestRewards(currentWarriorQuestIndex, human.getLevel());
				GCGetWarriorQuestReward gcGetRewardMsg = new GCGetWarriorQuestReward();
				gcGetRewardMsg.setRewards(questRewards);
				human.sendMessage(gcGetRewardMsg);
				// 发送更新消息
				GCUpdateWarriorQuest gcUpdateQuestMsg = new GCUpdateWarriorQuest();
				gcUpdateQuestMsg.setWarriorQuests(new WarriorQuest[] { quests[currentWarriorQuestIndex] });
				human.sendMessage(gcUpdateQuestMsg);
			} else {
				questRewards[0].setState(WarriorQuestRewardState.HAS_GOT.getIndex());
				GCUpdateWarriorQuestRewardState gcUpdateRewardStateMsg = new GCUpdateWarriorQuestRewardState();
				gcUpdateRewardStateMsg.setState(questRewards[0].getState());
				human.sendMessage(gcUpdateRewardStateMsg);
			}
			caches.addUpdate(human.getHumanGuid(), convertToEntity());
			// 同步状态
			checkAndSendWarriorState();
		}
	}

	/**
	 * 当前是否可以挑战
	 * @return
	 */
	public boolean canChallenge() {
		if (currentWarriorQuestIndex >= quests.length) {
			return false;
		}
		if (quests[currentWarriorQuestIndex].getQuestState() != WarriorQuestState.ONGOING.getIndex()) {
			return false;
		}
		if (!human.getHumanCdManager().canAddCd(CdType.WARRIOR_QUEST_CD,
				GameServerAssist.getGameConstants().getWarriorQuestCdTime() * TimeUtils.SECOND)) {
			return false;
		}
		return true;
	}

	/**
	 * 事件触发接口
	 */
	@Override
	public void onEvent(IEvent event) {
		if (event.getType() == EventType.LEVEL_UP_EVENT) {
			int rewardState = questRewards[0].getState();
			questRewards = templateManager.getWarriorQuestRewards(currentWarriorQuestIndex, human.getLevel());
			questRewards[0].setState(rewardState);
			opponent = OpponentMatcher.getOpponent(human);
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return caches.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return caches.getAllDeleteData();
	}

	/**
	 * 转变为实体
	 * @return
	 */
	public HumanWarriorEntity convertToEntity() {
		HumanWarriorInfo.Builder builder = HumanWarriorInfo.newBuilder();
		builder.setHumanGuid(human.getHumanGuid());
		builder.setCurrentQuestIndex(currentWarriorQuestIndex);
		if (currentWarriorQuestIndex < quests.length) {
			builder.setCurrentQuestState(quests[currentWarriorQuestIndex].getQuestState());
			if (quests[currentWarriorQuestIndex].getQuestState() == WarriorQuestState.ONGOING.getIndex()) {
				builder.setCurrentQuestCompleteNum(quests[currentWarriorQuestIndex].getCompleteCount());
			} else {
				builder.setCurrentQuestCompleteNum(0);
			}
		}
		if (questRewards != null || questRewards.length > 0) {
			builder.setQuestRewardState(questRewards[0].getState());
		}
		HumanWarriorEntity entity = new HumanWarriorEntity(builder);
		return entity;
	}

	/**
	 * 重设每日数据
	 */
	public void resetDailyData() {
		currentWarriorQuestIndex = 0;
		for (WarriorQuest quest : quests) {
			quest.setCompleteCount(0);
			quest.setQuestState(WarriorQuestState.UNOPEN.getIndex());
		}
		this.opponent = null;
		setAcceptChallengeRewardTimes(0);
		quests[0].setQuestState(WarriorQuestState.ONGOING.getIndex());
		questRewards = templateManager.getWarriorQuestRewards(currentWarriorQuestIndex, human.getLevel());
		caches.addUpdate(human.getHumanGuid(), convertToEntity());
	}

	@Override
	public void onLogin() {
		int maxRewardTime = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.ACCEPT_WARRIOR_CHALLENGE_MAX_REWARD_TIMES);
		int fixedMaxReward = GameServerAssist.getGameConstants().getMaxWarriorChallengeRewardTimes();
		if(maxRewardTime != fixedMaxReward){
			human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.ACCEPT_WARRIOR_CHALLENGE_MAX_REWARD_TIMES,fixedMaxReward);
		}
		// 登陆时同步状态
		checkAndSendWarriorState();
	}
	
	/**
	 * 校验并且发送当前勇者之路的状态
	 */
	private void checkAndSendWarriorState() {
		boolean finished = false;
		WarriorQuestTemplate template = templateManager.getLastWarriorQuestTemplate();
		if(template == null){
			return;
		}
		if(questRewards != null
				&& questRewards.length > 0
				&& questRewards[0].getQuestId() == template.getId()
				&& questRewards[0].getState() == WarriorQuestRewardState.HAS_GOT.getIndex()){
			finished = true;
		}
		GCWarriorState gcMsg = new GCWarriorState();
		gcMsg.setFinish(finished);
		human.sendMessage(gcMsg);
	}

	public int getAcceptChallengeRewardRemainTimes() {		
		int remain = GameServerAssist.getGameConstants().getMaxWarriorChallengeRewardTimes()-getAcceptChallengeRewardTimes();
		remain = remain>=0 ? remain : 0;
		return remain;
	}

	public void addAcceptChallengeRewardTimes() {
		setAcceptChallengeRewardTimes(getAcceptChallengeRewardTimes()+1);
	}
	
	private void setAcceptChallengeRewardTimes(int times){
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.ACCEPT_WARRIOR_CHALLENGE_REWARD_TIMES,times);
	}
	
	private int getAcceptChallengeRewardTimes(){
		return human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.ACCEPT_WARRIOR_CHALLENGE_REWARD_TIMES);
	}
	
	/**
	 * 是否已经完成了所有的挑战任务
	 * @return
	 */
	public boolean isFinishedAllChanllegeQuest(){
		if (currentWarriorQuestIndex >= quests.length) {
			return true;
		}
		if (quests[currentWarriorQuestIndex].getQuestState() != WarriorQuestState.ONGOING.getIndex()) {
			return true;
		}else{
			return false;
		}
	}
	
	public int getChanllengeSuccessTimes(){
		int result = 0;
		for(WarriorQuest quest : this.quests){
			result += quest.getCompleteCount();
		}
		return result;
	}
}
