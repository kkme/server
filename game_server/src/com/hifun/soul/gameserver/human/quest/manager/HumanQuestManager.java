package com.hifun.soul.gameserver.human.quest.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.QuestLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanDailyQuestRewardBoxEntity;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanQuestDataEntity;
import com.hifun.soul.gamedb.entity.HumanQuestFinishDataEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.human.quest.Quest;
import com.hifun.soul.gameserver.human.quest.QuestInfo;
import com.hifun.soul.gameserver.human.quest.QuestInstanceToQuestInfoConverter;
import com.hifun.soul.gameserver.human.quest.QuestState;
import com.hifun.soul.gameserver.human.quest.QuestType;
import com.hifun.soul.gameserver.human.quest.aim.counter.IQuestAimCounter;
import com.hifun.soul.gameserver.human.quest.converter.QuestInstanceToEntityConverter;
import com.hifun.soul.gameserver.human.quest.daily.DailyItemReward;
import com.hifun.soul.gameserver.human.quest.daily.DailyQuestRewardBox;
import com.hifun.soul.gameserver.human.quest.daily.DailyRewardBoxState;
import com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo;
import com.hifun.soul.gameserver.human.quest.daily.template.DailyQuestRewardTemplate;
import com.hifun.soul.gameserver.human.quest.template.QuestTemplate;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.level.template.LevelConfigTemplate;
import com.hifun.soul.gameserver.logger.Loggers;
import com.hifun.soul.gameserver.quest.msg.GCAppendNewQuests;
import com.hifun.soul.gameserver.quest.msg.GCDailyQuestRewardBoxStateUpdate;
import com.hifun.soul.gameserver.quest.msg.GCDailyQuestState;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.proto.common.Quests.QuestAimCounterData;
import com.hifun.soul.proto.common.Quests.QuestStateData;
import com.hifun.soul.proto.data.entity.Entity.HumanDailyQuestRewardBox;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestData;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestFinishData;

/**
 * 角色任务管理器 <br>
 * FIXME: crazyjohn 主线任务和日常任务的相关逻辑需要重新抽象，代码不好理解，不好扩展;有待重构;<br>
 * 
 * @author crazyjohn
 * 
 */
public class HumanQuestManager implements ICachableComponent,
		IHumanPersistenceManager, IEventListener, ILoginManager {
	private static Logger logger = Loggers.HUMAN_QUEST_LOGGER;
	private QuestInstanceToEntityConverter questInstanceToEntityConverter = new QuestInstanceToEntityConverter();
	/** 已经接取,正在完成中的主线任务 */
	private Map<Integer, Quest> mainQuestAcceptingMap = new HashMap<Integer, Quest>();
	/** 所有的日常任务 */
	private Map<Integer, Quest> dailyQuestMap = new HashMap<Integer, Quest>();
	/** 日常任务的奖励宝箱 */
	private Map<Integer, DailyQuestRewardBox> dailyQuestRewardBoxMap = new HashMap<Integer, DailyQuestRewardBox>();
	/** 已经完成的主线任务信息 */
	private Set<Integer> questFinishedMap = new HashSet<Integer>();
	private Human human;
	/** 已经完成的任务数据缓存 */
	private CacheEntry<Integer, Quest> questFinishedCaches = new CacheEntry<Integer, Quest>();
	/** 正在进行的任务缓存 */
	private CacheEntry<Integer, Quest> questAcceptingCaches = new CacheEntry<Integer, Quest>();
	/** 日常任务奖励箱子缓存 */
	private CacheEntry<Integer, DailyQuestRewardBox> dailyQuestRewardBoxCaches = new CacheEntry<Integer, DailyQuestRewardBox>();
	/** 任务转换器 */
	private QuestInstanceToQuestInfoConverter converter = new QuestInstanceToQuestInfoConverter();

	public HumanQuestManager(Human human) {
		this.human = human;
		human.registerPersistenceManager(this);
		human.registerCachableManager(this);
		human.registerLoginManager(this);
		// 注册事件监听
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}

	public void setLastResetDailyQuestTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_DAILY_QUEST_RESET_TIME, lastTime);
	}

	public long getLastResetDailyQuestTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_DAILY_QUEST_RESET_TIME);
	}

	/**
	 * 检查主线任务是否可以开始;
	 * 
	 * @param questId
	 * @return
	 */
	private boolean checkMainQuestCanStart(int questId) {
		QuestTemplate questData = GameServerAssist.getQuestTemplateManager()
				.getQuestTemplateByQuestId(questId);

		// 任务是否存在
		if (questData == null) {
			if (logger.isWarnEnabled()) {
				logger.warn(String.format(
						"The quest is not avilable, human: %s, questId: %d",
						human, questId));
			}
			return false;
		}

		// 任务是否在接取中
		if (this.isAcceptingQuest(questId)) {
			if (logger.isWarnEnabled()) {
				logger.warn(String
						.format("The quest is already accepting, can not restart! human: %s, questId: %d",
								human, questId));
			}
			return false;
		}

		// 自身验证
		return true;
	}

	/**
	 * 完成主线任务;
	 * <p>
	 * 只有主线任务有此种业务;
	 * 
	 * @param questId
	 */
	public void onFinishMainQuest(int questId) {
		// 检查是否可以被完成
		if (!this.checkMainQuestCanFinish(questId)) {
			if (logger.isWarnEnabled()) {
				logger.warn(String.format(
						"Quest can not be finished, human: %s, questId: %d",
						human.toLogString(), questId));
				return;
			}
		}

		Quest quest = this.mainQuestAcceptingMap.get(questId);
		quest.finishQuest();
		this.mainQuestAcceptingMap.remove(questId);
		// 添加到缓存，完成从已接收列表中移除
		questAcceptingCaches.addDelete(questId, quest);
		// 添加到完成任务, 同时添加到缓存
		this.questFinishedMap.add(questId);
		this.questFinishedCaches.addUpdate(questId, quest);
		// 发送任务日志
		GameServerAssist.getLogService().sendQuestLog(human,
				QuestLogReason.FINISH, "", questId);

		// 推送新任务的时候判断是否有要触发的引导，引导对应固定的任务id
		switch (quest.getQuestId()) {
		case 1001:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1001.getIndex());
			break;
		case 1002:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1002.getIndex());
			break;
		case 1003:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1003.getIndex());
			break;
		case 1004:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1004.getIndex());
			break;
		case 1005:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1005.getIndex());
			break;
		case 1006:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1006.getIndex());
			break;
		case 1007:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1007.getIndex());
			break;
		case 1008:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1008.getIndex());
			break;
		case 1009:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1009.getIndex());
			break;
		case 1010:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1010.getIndex());
			break;
		case 1011:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1011.getIndex());
			break;
		case 1012:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1012.getIndex());
			break;
		case 1013:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1013.getIndex());
			break;
		case 1014:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1014.getIndex());
			break;
		case 1015:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1015.getIndex());
			break;
		case 1016:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1016.getIndex());
			break;
		case 1017:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1017.getIndex());
			break;
		case 1018:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1018.getIndex());
			break;
		case 1019:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1019.getIndex());
			break;
		case 1020:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1020.getIndex());
			break;
		case 1021:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1021.getIndex());
			break;
		case 1022:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1022.getIndex());
			break;
		case 1023:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1023.getIndex());
			break;
		case 1024:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1024.getIndex());
			break;
		case 1025:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1025.getIndex());
			break;
		case 1026:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1026.getIndex());
			break;
		case 1027:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1027.getIndex());
			break;
		case 1028:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1028.getIndex());
			break;
		case 1029:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1029.getIndex());
			break;
		case 1030:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1030.getIndex());
			break;
		case 1031:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1031.getIndex());
			break;
		case 1032:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1032.getIndex());
			break;
		case 1033:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1033.getIndex());
			break;
		case 1034:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1034.getIndex());
			break;
		case 1035:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1035.getIndex());
			break;
		case 1036:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1036.getIndex());
			break;
		case 1038:
			human.getHumanGuideManager().showGuide(
					GuideType.QUEST_1038.getIndex());
			break;
		}
	}

	/**
	 * 推送合适的任务给玩家;<br>
	 * FIXME: crayjohn 后期优化考虑通过排序提高效率;
	 */
	public void pushNewSuitableQuests() {
		Map<Integer, QuestInfo> newQuests = new HashMap<Integer, QuestInfo>();
		for (QuestTemplate quest : GameServerAssist.getQuestTemplateManager()
				.getAllMainQuest()) {
			// 看角色等级是否合适
			if (human.getLevel() < quest.getMinGetLevel()
					|| human.getLevel() > quest.getMaxGetLevel()) {
				continue;
			}
			// 任务是否已经完成
			if (isFinishedMainQuest(quest.getId())) {
				continue;
			}
			// 任务是否正在接取
			if (containSuchAcceptingQuest(quest.getId())) {
				continue;
			}
			// 任务的前置任务是否完成
			if (!this.preQuestsFinished(quest)) {
				continue;
			}
			// 添加到角色的正在接取任务列表中
			boolean result = human.getHumanQuestManager().appendNewMainQuest(
					quest.getId());
			// 通知客户端新任务;
			if (result) {
				Quest newQuest = this.mainQuestAcceptingMap.get(quest.getId());
				if (newQuest != null) {
					QuestInfo newQuestInfo = this.converter.convert(newQuest);
					newQuests.put(newQuestInfo.getQuestId(), newQuestInfo);
				}
			}
			if (!newQuests.isEmpty()) {
				GCAppendNewQuests appendQuestsMsg = new GCAppendNewQuests();
				appendQuestsMsg.setQuestList(newQuests.values().toArray(
						new QuestInfo[0]));
				human.sendMessage(appendQuestsMsg);
			}
		}
	}

	/**
	 * 检查主线任务是否可以完成;
	 * 
	 * @param questId
	 * @return
	 */
	private boolean checkMainQuestCanFinish(int questId) {
		if (this.mainQuestAcceptingMap.get(questId) == null) {
			return false;
		}
		if (this.mainQuestAcceptingMap.get(questId).getCurrentState() != QuestState.QUEST_CAN_FINISH) {
			return false;
		}
		// 进行任务自身的验证;
		return this.mainQuestAcceptingMap.get(questId).checkFinish();
	}

	private boolean isAcceptingQuest(int questId) {
		Quest quest = this.mainQuestAcceptingMap.get(questId);
		if (quest == null) {
			return false;
		}
		if (quest.getCurrentState() == QuestState.QUEST_ACCEPTING) {
			return true;
		}
		return false;
	}

	/**
	 * 主线任务是否已经完成;
	 * 
	 * @param questId
	 * @return
	 */
	public boolean isFinishedMainQuest(int questId) {
		if (this.isAcceptingQuest(questId)) {
			return false;
		}
		// 是否已经完成了, 但是没有领取奖励??
		if (finishButNotGetRewardQuest(questId)) {
			return true;
		}
		return this.questFinishedMap.contains(questId);
	}

	private boolean finishButNotGetRewardQuest(int questId) {
		Quest quest = this.mainQuestAcceptingMap.get(questId);
		if (quest == null) {
			return false;
		}
		if (quest.getCurrentState() != QuestState.QUEST_CAN_FINISH) {
			return false;
		}
		return true;
	}

	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();

		// 添加正在进行的任务数据
		for (Quest acceptQuest : this.questAcceptingCaches.getAllUpdateData()) {
			HumanQuestDataEntity questEntity = this.questInstanceToEntityConverter
					.convert(acceptQuest);
			updateList.add(questEntity);
		}

		// 添加已经完成的的任务数据
		for (Quest finishedQuest : this.questFinishedCaches.getAllUpdateData()) {
			convertToQuestFinishEntity(finishedQuest);
			updateList.add(convertToQuestFinishEntity(finishedQuest));
		}
		// 任务宝箱数据
		for (DailyQuestRewardBox box : dailyQuestRewardBoxCaches
				.getAllUpdateData()) {
			HumanDailyQuestRewardBox.Builder boxBuilder = HumanDailyQuestRewardBox
					.newBuilder();
			HumanDailyQuestRewardBoxEntity boxEntity = new HumanDailyQuestRewardBoxEntity(
					boxBuilder);
			boxBuilder.setHumanGuid(human.getHumanGuid());
			com.hifun.soul.proto.common.Quests.DailyQuestRewardBox.Builder stateBuilder = com.hifun.soul.proto.common.Quests.DailyQuestRewardBox
					.newBuilder();
			stateBuilder.setBoxId(box.getTemplate().getId());
			stateBuilder.setState(box.getState().getIndex());
			boxBuilder.setBox(stateBuilder);
			updateList.add(boxEntity);
		}

		return updateList;
	}

	public List<IEntity> getDeleteEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for (Quest quest : this.questAcceptingCaches.getAllDeleteData()) {
			HumanQuestDataEntity questEntity = this.questInstanceToEntityConverter
					.convert(quest);
			updateList.add(questEntity);
		}

		for (DailyQuestRewardBox box : dailyQuestRewardBoxCaches
				.getAllDeleteData()) {
			HumanDailyQuestRewardBox.Builder boxBuilder = HumanDailyQuestRewardBox
					.newBuilder();
			HumanDailyQuestRewardBoxEntity boxEntity = new HumanDailyQuestRewardBoxEntity(
					boxBuilder);
			boxBuilder.setHumanGuid(human.getHumanGuid());
			com.hifun.soul.proto.common.Quests.DailyQuestRewardBox.Builder stateBuilder = com.hifun.soul.proto.common.Quests.DailyQuestRewardBox
					.newBuilder();
			stateBuilder.setBoxId(box.getTemplate().getId());
			stateBuilder.setState(box.getState().getIndex());
			boxBuilder.setBox(stateBuilder);
			updateList.add(boxEntity);
		}
		return updateList;
	}

	/**
	 * 从DB中加载完成任务的数据;
	 * 
	 * @param questId
	 */
	private void loadQuestFinishData(int questId) {
		QuestTemplate questTemplate = GameServerAssist
				.getQuestTemplateManager().getQuestTemplateByQuestId(questId);
		if (questTemplate == null) {
			logger.error(String.format(
					"No such quest template, human: %s, questId: %d",
					human.toLogString(), questId));
			return;
		}
		this.questFinishedMap.add(questId);
	}

	/**
	 * 从DB中加载未完成任务的数据;
	 * 
	 * @param questId
	 * @param questState
	 */
	private void loadQuestData(int questId, int questState, long endTime) {
		QuestTemplate questTemplate = GameServerAssist
				.getQuestTemplateManager().getQuestTemplateByQuestId(questId);
		if (questTemplate == null) {
			logger.error(String.format(
					"No such quest template, human: %s, questId: %d",
					human.toLogString(), questId));
			return;
		}
		if (needLoad(questTemplate, questState)) {
			loadQuest(questId, questState, endTime);
		}

	}

	/**
	 * 判断是否需要加载某个任务;<br>
	 * 主线任务加载{@code QuestState#QUEST_ACCEPTING}
	 * {@code QuestState#QUEST_CAN_FINISH} 加载这两种状态的任务<br>
	 * 支线任务加载{@code QuestState#QUEST_ACCEPTING}
	 * {@code QuestState#QUEST_FINISHED} 这两种状态的任务;<br>
	 * 
	 * @param questTemplate
	 * @param questState
	 * @return false表示不需要加载;
	 */
	private boolean needLoad(QuestTemplate questTemplate, int questState) {
		if ((questTemplate.getQuestType() == QuestType.QUEST_MAIN.getIndex() || questTemplate
				.getQuestType() == QuestType.QUEST_BRANCH.getIndex())
				&& (questState == QuestState.QUEST_ACCEPTING.getStateCode() || questState == QuestState.QUEST_CAN_FINISH
						.getStateCode())) {
			return true;
		}
		// 日常任务，不管状态都加载出来
		if (questTemplate.getQuestType() == QuestType.QUEST_DAILY.getIndex()) {
			return true;
		}
		return false;
	}

	/**
	 * 添加任务到正在进行任务的内存映射中;(主线或者日常)
	 * 
	 * @param questId
	 * @param questState
	 */
	private void loadQuest(int questId, int questState, long endTime) {
		QuestTemplate questTemplate = GameServerAssist
				.getQuestTemplateManager().getQuestTemplateByQuestId(questId);
		Quest quest = new Quest(human, this.human.getEventBus(), questTemplate);
		quest.init();
		quest.setState(QuestState.typeOf(questState));
		quest.setEndTime(endTime);
		if (questState == QuestState.QUEST_ACCEPTING.getIndex()) {
			// 添加事件监听
			quest.addListeners();
		}
		if (questTemplate.getQuestType() == QuestType.QUEST_MAIN.getIndex()
				|| questTemplate.getQuestType() == QuestType.QUEST_BRANCH
						.getIndex()) {
			this.mainQuestAcceptingMap.put(questId, quest);
		} else {
			this.dailyQuestMap.put(questId, quest);
		}
		// TODO: crazyjohn 加入缓存？？
	}

	/**
	 * 记载任务目标计数器信息;
	 * 
	 * @param questId
	 * @param index
	 * @param value
	 * @param counterType
	 * @param questType
	 */
	private void loadQuestAimCounter(int questId, int index, int value,
			int counterType, int questType) {
		Quest quest = null;
		if (questType == QuestType.QUEST_MAIN.getIndex()
				|| questType == QuestType.QUEST_BRANCH.getIndex()) {
			quest = this.mainQuestAcceptingMap.get(questId);
		} else {
			quest = this.dailyQuestMap.get(questId);
		}

		if (quest == null) {
			logger.error(String.format(
					"No such quest, human: %s, questType: %d, questId: %d",
					human.toLogString(), questType, questId));
			return;
		}
		IQuestAimCounter aimCounter = quest.getAimList().get(index);
		if (aimCounter == null) {
			logger.error(String
					.format("QuestAimCounter is null, human: %s, questId: %d, aimIndex: %d, counterType: %d",
							human.toLogString(), questId, index, counterType));
			return;
		}

		if (counterType != aimCounter.getType().getAimType()) {
			logger.error(String
					.format("QuestAimCounterType is not same, human: %s, questId: %d, aimType1: %d, aimType2: %d",
							human.toLogString(), questId, counterType,
							aimCounter.getType().getAimType()));
			return;
		}
		// 设置计数器当前值
		aimCounter.setValue(value);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 加载未完成任务数据
		for (HumanQuestData.Builder eachQuest : humanEntity.getBuilder()
				.getHumanQuestBuilderList()) {
			QuestTemplate questTemplate = GameServerAssist
					.getQuestTemplateManager().getQuestTemplateByQuestId(
							eachQuest.getQuestStateData().getQuestId());
			if (questTemplate == null) {
				continue;
			}
			loadQuestData(eachQuest.getQuestStateData().getQuestId(), eachQuest
					.getQuestStateData().getState(), eachQuest
					.getQuestStateData().getEndTime());
			List<QuestAimCounterData> aimCounterDataList = eachQuest
					.getQuestStateData().getAimCounterList();

			for (QuestAimCounterData eachAim : aimCounterDataList) {
				loadQuestAimCounter(eachQuest.getQuestStateData().getQuestId(),
						eachAim.getAimIndex(), eachAim.getValue(),
						eachAim.getAimType(), questTemplate.getQuestType());
			}
		}

		// 加载任务完成数据
		for (HumanQuestFinishData.Builder eachQuest : humanEntity.getBuilder()
				.getHumanFinishQuestBuilderList()) {
			loadQuestFinishData(eachQuest.getQuestFinishData().getQuestId());
		}

		// 加载日常任务奖励箱子
		for (HumanDailyQuestRewardBox.Builder eachBox : humanEntity
				.getBuilder().getDailyRewardBoxBuilderList()) {
			DailyQuestRewardTemplate boxTemplate = GameServerAssist
					.getQuestTemplateManager().getDailyRewardById(
							eachBox.getBox().getBoxId());
			if (boxTemplate == null) {
				continue;
			}
			DailyQuestRewardBox box = new DailyQuestRewardBox();
			box.setTemplate(boxTemplate);
			box.setState(DailyRewardBoxState
					.typeOf(eachBox.getBox().getState()));
			this.dailyQuestRewardBoxMap.put(boxTemplate.getId(), box);
		}

	}

	/**
	 * 重置日常任务
	 */
	public void resetDailyQuests() {
		// 重置日常任务
		refreshDailyQuests();
		// 重置积分;
		this.human.setDailyQuestScore(0);
		// 重置接受任务次数
		human.setDailyQuestReceivedNum(0);
		// 重置魔晶接受任务次数
		human.setDailyQuestBuyNum(0);
		// 重置日常奖励宝箱状态
		for (DailyQuestRewardBox box : this.dailyQuestRewardBoxMap.values()) {
			if (box != null) {
				this.dailyQuestRewardBoxCaches.addDelete(box.getTemplate()
						.getId(), box);
			}
		}
		this.dailyQuestRewardBoxMap.clear();
		for (DailyQuestRewardTemplate boxTemplate : GameServerAssist
				.getQuestTemplateManager().getDailyRewardTemplateList()) {
			DailyQuestRewardBox box = new DailyQuestRewardBox();
			box.setTemplate(boxTemplate);
			box.setState(DailyRewardBoxState.CLOSED);
			this.dailyQuestRewardBoxMap.put(boxTemplate.getId(), box);
			this.dailyQuestRewardBoxCaches.addUpdate(boxTemplate.getId(), box);
		}

		// 更新日常任务是否有奖励可领取
		updateDailyQuestReward();
	}

	/**
	 * 判断是否有可以刷新的日常任务
	 */
	public boolean canRefreshDailyQuest() {
		// 个数不够，能刷
		if (this.dailyQuestMap.size() < SharedConstants.DAILY_QUEST_NUM) {
			return true;
		}
		for (Quest quest : this.dailyQuestMap.values()) {
			if (quest.getCurrentState() == QuestState.QUEST_FINISHED
					|| quest.getCurrentState() == QuestState.QUEST_CAN_ACCEPT) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 刷新日常任务
	 */
	public void refreshDailyQuests() {
		// 已完成和可接受的任务
		List<Quest> deleteQuests = new ArrayList<Quest>();
		for (Quest quest : this.dailyQuestMap.values()) {
			if (quest.getCurrentState() == QuestState.QUEST_FINISHED
					|| quest.getCurrentState() == QuestState.QUEST_CAN_ACCEPT) {
				deleteQuests.add(quest);
			}
		}
		// 要刷新的任务数量
		int refreshNum = deleteQuests.size();
		// 第一次刷新或之前日常任务数由于等级限制，未达到指定个数
		if (dailyQuestMap.size() < SharedConstants.DAILY_QUEST_NUM) {
			refreshNum += SharedConstants.DAILY_QUEST_NUM
					- dailyQuestMap.size();
		}
		// 移除掉之前的任务
		for (Quest quest : deleteQuests) {
			quest.removeListeners();
			dailyQuestMap.remove(quest.getQuestId());
			this.questAcceptingCaches.addDelete(quest.getQuestId(), quest);
		}
		// 添加刷出来的新任务
		for (QuestTemplate each : GameServerAssist
				.getQuestTemplateManager()
				.getSomeDailyQuests(refreshNum, dailyQuestMap, human.getLevel())) {
			this.appendNewDailyQuest(each.getId());
		}
	}

	/**
	 * 刷新单个日常任务
	 */
	public Quest refreshDailyQuest(int questId) {
		Quest quest = dailyQuestMap.get(questId);
		quest.removeListeners();
		dailyQuestMap.remove(quest.getQuestId());
		// 加入删除的缓存中，避免突然停服，数据没有保存上
		questAcceptingCaches.addDelete(quest.getQuestId(), quest);
		return appendNewDailyQuest(GameServerAssist.getQuestTemplateManager()
				.getSomeDailyQuests(1, dailyQuestMap, human.getLevel()).get(0)
				.getId());
	}

	/**
	 * 结束自动完成的日常任务
	 */
	public void endDailyQuests() {
		long now = GameServerAssist.getSystemTimeService().now();
		for (Quest quest : this.dailyQuestMap.values()) {
			if (quest.getCurrentState() == QuestState.QUEST_AUTO
					&& quest.getEndTime() <= now) {
				quest.quicklyAutoCompleteDailyQuest();
			}
		}
	}

	private Quest appendNewDailyQuest(int questId) {
		// 修复之任务数量不正确的数据
		if (dailyQuestMap.size() >= SharedConstants.DAILY_QUEST_NUM) {
			return null;
		}
		// 检查任务是否可以开启
		QuestTemplate questData = GameServerAssist.getQuestTemplateManager()
				.getQuestTemplateByQuestId(questId);
		Quest quest = new Quest(human, human.getEventBus(), questData);
		quest.init();
		// 日常任务默认不开启
		// quest.startQuest();
		this.dailyQuestMap.put(questId, quest);
		// 放入缓存中
		this.questAcceptingCaches.addUpdate(questId, quest);
		return quest;
	}

	public boolean appendNewMainQuest(int questId) {
		// 是否达到接受任务的最大值
		if (this.mainQuestAcceptingMap.size() >= SharedConstants.CHARCTER_MAX_ACCEPTING_COUNT) {
			if (logger.isWarnEnabled()) {
				logger.warn(String
						.format("Reach to max accepting quest count! human: %s, questId: %d",
								human.toLogString(), questId));
			}
			return false;
		}

		// 检查任务是否可以开启
		if (!checkMainQuestCanStart(questId)) {
			return false;
		}

		QuestTemplate questData = GameServerAssist.getQuestTemplateManager()
				.getQuestTemplateByQuestId(questId);
		Quest quest = new Quest(human, human.getEventBus(), questData);
		quest.init();
		quest.startQuest();
		this.mainQuestAcceptingMap.put(questId, quest);
		// 放入缓存中
		this.questAcceptingCaches.addUpdate(questId, quest);
		return true;
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 设置未完成主线任务数据
		for (Quest quest : this.mainQuestAcceptingMap.values()) {
			HumanQuestData.Builder questBuilder = HumanQuestData.newBuilder();
			questBuilder.setHumanGuid(human.getHumanGuid());
			QuestStateData.Builder stateBuilder = QuestStateData.newBuilder();
			stateBuilder.setQuestId(quest.getQuestId());
			for (IQuestAimCounter counter : quest.getAimList()) {
				QuestAimCounterData.Builder aimBuilder = QuestAimCounterData
						.newBuilder();
				aimBuilder.setAimIndex(counter.getIndex());
				aimBuilder.setAimType(counter.getType().getAimType());
				aimBuilder.setValue(counter.getValue());
				stateBuilder.addAimCounter(aimBuilder);
			}
			// 设置任务当前状态
			stateBuilder.setState(quest.getCurrentState().getIndex());
			questBuilder.setQuestStateData(stateBuilder);
			humanEntity.getBuilder().addHumanQuest(questBuilder);
		}

		// 存储日常任务
		for (Quest quest : this.dailyQuestMap.values()) {
			HumanQuestData.Builder questBuilder = HumanQuestData.newBuilder();
			questBuilder.setHumanGuid(human.getHumanGuid());
			QuestStateData.Builder stateBuilder = QuestStateData.newBuilder();
			stateBuilder.setQuestId(quest.getQuestId());
			for (IQuestAimCounter counter : quest.getAimList()) {
				QuestAimCounterData.Builder aimBuilder = QuestAimCounterData
						.newBuilder();
				aimBuilder.setAimIndex(counter.getIndex());
				aimBuilder.setAimType(counter.getType().getAimType());
				aimBuilder.setValue(counter.getValue());
				stateBuilder.addAimCounter(aimBuilder);
			}
			// 设置任务当前状态
			stateBuilder.setState(quest.getCurrentState().getIndex());
			stateBuilder.setEndTime(quest.getEndTime());
			questBuilder.setQuestStateData(stateBuilder);
			humanEntity.getBuilder().addHumanQuest(questBuilder);
		}

		// 设置完成任务数据
		for (Integer questId : this.questFinishedMap) {
			HumanQuestFinishData.Builder finishQuestBuilder = HumanQuestFinishData
					.newBuilder();
			finishQuestBuilder.setHumanGuid(human.getHumanGuid());
			com.hifun.soul.proto.common.Quests.QuestFinishData.Builder finishDataBuilder = com.hifun.soul.proto.common.Quests.QuestFinishData
					.newBuilder();
			finishDataBuilder.setQuestId(questId);
			finishQuestBuilder.setQuestFinishData(finishDataBuilder);
			humanEntity.getBuilder().addHumanFinishQuest(finishQuestBuilder);
		}
		// 存储日常任务宝箱的状态
		for (DailyQuestRewardBox box : dailyQuestRewardBoxMap.values()) {
			// 进行宝箱的持久化
			HumanDailyQuestRewardBox.Builder boxBuilder = HumanDailyQuestRewardBox
					.newBuilder();
			boxBuilder.setHumanGuid(human.getHumanGuid());
			com.hifun.soul.proto.common.Quests.DailyQuestRewardBox.Builder stateBuilder = com.hifun.soul.proto.common.Quests.DailyQuestRewardBox
					.newBuilder();
			stateBuilder.setBoxId(box.getTemplate().getId());
			stateBuilder.setState(box.getState().getIndex());
			boxBuilder.setBox(stateBuilder);
			humanEntity.getBuilder().addDailyRewardBox(boxBuilder);
		}

	}

	public Collection<Quest> getAcceptingQuests() {
		return this.mainQuestAcceptingMap.values();
	}

	/**
	 * 获取所有主线任务;
	 * 
	 * @return
	 */
	public List<QuestInfo> getAllMainQuest() {
		// 查看模版管理器里是否有合适的任务
		GameServerAssist.getQuestTemplateManager().addSuitableQuestToHuman(
				human);
		List<QuestInfo> result = new ArrayList<QuestInfo>();
		for (Quest each : this.getAcceptingQuests()) {
			// 任务类型是否合适
			if (each.getQuestTemplate().getQuestType() == QuestType.QUEST_DAILY
					.getIndex()) {
				continue;
			}
			QuestInfo questInfo = this.converter.convert(each);
			result.add(questInfo);
		}
		return result;
	}

	/**
	 * 获取所有日常任务;
	 * 
	 * @return
	 */
	public List<QuestInfo> getAllDailyQuests() {
		List<QuestInfo> result = new ArrayList<QuestInfo>();
		for (Quest each : this.dailyQuestMap.values()) {
			// 任务类型是否合适
			if (each.getQuestTemplate().getQuestType() != QuestType.QUEST_DAILY
					.getIndex()) {
				continue;
			}
			QuestInfo questInfo = this.converter.convert(each);
			result.add(questInfo);
		}
		return result;
	}

	public boolean containSuchAcceptingQuest(int id) {
		return this.mainQuestAcceptingMap.containsKey(id);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	/**
	 * 打开日常任务箱子;
	 * 
	 * @param boxId
	 *            宝箱ID;
	 */
	public void onOpenDailyQuestBox(int boxId) {
		DailyQuestRewardBox box = this.dailyQuestRewardBoxMap.get(boxId);
		if (box == null) {
			if (logger.isErrorEnabled()) {
				logger.error("DailyRewardBox should not be null");
			}
			return;
		}
		// 积分是否达到
		if (human.getDailyQuestScore() < box.getTemplate().getScoreLimit()) {
			return;
		}
		// 是否已经开启
		if (box.getState() == DailyRewardBoxState.OPEN) {
			human.sendErrorMessage(LangConstants.DAILY_QUEST_REWARED_HAS_GET);
			return;
		}

		// 先判断背包空余空间是否足够
		int rewardItemCount = box.getTemplate().getDailyRewardItems().size();
		HumanBagManager bagManager = human.getBagManager();
		if (rewardItemCount > bagManager.getFreeSize(BagType.MAIN_BAG)) {
			human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}

		// 取出金币等级参数
		LevelConfigTemplate levelTemplate = GameServerAssist
				.getTemplateService().get(human.getLevel(),
						LevelConfigTemplate.class);

		if (levelTemplate == null) {
			return;
		}
		// 发物品,判断背包是否已满
		for (DailyItemReward itemReward : box.getTemplate()
				.getDailyRewardItems()) {
			if (bagManager.isFull(BagType.MAIN_BAG)) {
				human.sendWarningMessage(LangConstants.BAG_IS_FULL);
				return;
			}
			// 放入背包;
			bagManager.putItems(BagType.MAIN_BAG, itemReward.getItemId(),
					itemReward.getItemCount(),
					ItemLogReason.DAILY_QUEST_REWARD, "");
		}

		// 发钱
		human.getWallet().addMoney(
				CurrencyType.COIN,
				box.getTemplate().getRewardMoney()
						* levelTemplate.getDailyQuestRewardConfig(), true,
				LogReasons.MoneyLogReason.DAILY_QUEST_REWARD, "");
		box.setState(DailyRewardBoxState.OPEN);

		// 更新箱子状态
		GCDailyQuestRewardBoxStateUpdate updateBox = new GCDailyQuestRewardBoxStateUpdate();
		updateBox.setBoxId(boxId);
		updateBox.setState(DailyRewardBoxState.OPEN.getIndex());
		human.sendMessage(updateBox);
		// 添加到缓存
		this.dailyQuestRewardBoxCaches.addUpdate(boxId, box);

		// 更新日常任务是否有奖励可领取
		updateDailyQuestReward();
	}

	@Override
	public void onEvent(IEvent event) {
		switch (event.getType()) {
		case LEVEL_UP_EVENT: {
			// 升级后检查看是否有合适的任务推送
			this.pushNewSuitableQuests();
			break;
		}
		}
	}

	public boolean preQuestsFinished(QuestTemplate quest) {
		// 任务的前置任务是否完成
		for (int preId : quest.getPreQuestIdSet()) {
			if (preId != SharedConstants.QUEST_INVALID_PRE_ID
					&& !this.isFinishedMainQuest(preId)) {
				return false;
			}
		}
		return true;
	}

	public DailyScoreRewardInfo[] getDailyRewardList() {
		List<DailyScoreRewardInfo> result = new ArrayList<DailyScoreRewardInfo>();
		for (DailyQuestRewardBox each : dailyQuestRewardBoxMap.values()) {
			// 设置tip描述
			result.add(each.getTemplate().toInfo(
					buildTipForBox(each.getTemplate()), each.getState()));
		}
		return result.toArray(new DailyScoreRewardInfo[0]);
	}

	/**
	 * 构建宝箱tips;
	 * 
	 * @param each
	 * @return
	 */
	private String buildTipForBox(DailyQuestRewardTemplate each) {
		String coinString = GameServerAssist.getSysLangService().read(
				LangConstants.COIN);
		StringBuilder sb = new StringBuilder("\n");
		sb.append(coinString);
		sb.append(each.getRewardMoney()).append("\n");
		for (DailyItemReward item : each.getDailyRewardItems()) {
			ItemTemplate itemTemplate = GameServerAssist
					.getItemTemplateManager().getItemTemplate(item.getItemId());
			if (itemTemplate != null) {
				sb.append(itemTemplate.getName()).append("*")
						.append(item.getItemCount()).append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * 添加任务更新到数据缓存;
	 * 
	 * @param quest
	 */
	public void addUpdateQuest(Quest quest) {
		this.questAcceptingCaches.addUpdate(quest.getQuestId(), quest);
	}

	private HumanQuestFinishDataEntity convertToQuestFinishEntity(Quest src) {
		HumanQuestFinishDataEntity result = new HumanQuestFinishDataEntity();
		result.getBuilder().setHumanGuid(src.getHuman().getHumanGuid());
		result.getBuilder().getQuestFinishDataBuilder()
				.setQuestId(src.getQuestId());
		return result;
	}

	/**
	 * 通知客户端日常任务是否有可以领取的奖励
	 * 
	 */
	public void updateDailyQuestReward() {
		for (DailyScoreRewardInfo rewardInfo : getDailyRewardList()) {
			if (human.getDailyQuestScore() >= rewardInfo.getScoreLimit()
					&& rewardInfo.getState() == DailyRewardBoxState.CLOSED
							.getIndex()) {
				GCDailyQuestState gcMsg = new GCDailyQuestState();
				gcMsg.setHasReward(true);
				human.sendMessage(gcMsg);
				return;
			}
		}

		GCDailyQuestState gcMsg = new GCDailyQuestState();
		gcMsg.setHasReward(false);
		human.sendMessage(gcMsg);
	}

	@Override
	public void onLogin() {
		// 更新日常任务是否有奖励可领取
		updateDailyQuestReward();
	}

	/**
	 * 根据任务ID获取日常任务
	 */
	public Quest getDailyQuest(int questId) {
		QuestTemplate questTemplate = GameServerAssist
				.getQuestTemplateManager().getQuestTemplateByQuestId(questId);
		if (questTemplate == null
				|| (questTemplate != null && questTemplate.getQuestType() != QuestType.QUEST_DAILY
						.getIndex())) {
			return null;
		}
		return dailyQuestMap.get(questId);
	}

	/**
	 * 判断是否有委托中的任务
	 */
	public boolean hasAutoCompleteQuest() {
		for (int questId : dailyQuestMap.keySet()) {
			if (dailyQuestMap.get(questId).getCurrentState() == QuestState.QUEST_AUTO) {
				return true;
			}
		}
		return false;
	}
}
