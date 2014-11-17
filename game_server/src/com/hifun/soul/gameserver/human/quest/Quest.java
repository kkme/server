package com.hifun.soul.gameserver.human.quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventBus;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.MainQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.counter.AbstractDailyQuestAimCounter;
import com.hifun.soul.gameserver.human.quest.aim.counter.IQuestAimCounter;
import com.hifun.soul.gameserver.human.quest.daily.DailyQuestAimType;
import com.hifun.soul.gameserver.human.quest.logic.IQuestLogic;
import com.hifun.soul.gameserver.human.quest.template.QuestTemplate;
import com.hifun.soul.gameserver.quest.msg.GCUpdateQuestCounter;

/**
 * 任务业务对象;
 * 
 * @author crazyjohn
 * 
 */
public class Quest implements IInitializeRequired, IEventListener {
	private Human human;
	/** 任务模版数据 */
	private QuestTemplate questTemplate;
	/** 任务检查逻辑 */
	protected IQuestLogic questCheckerLogic;
	/** 任务目标计数器 */
	private List<IQuestAimCounter> aimCounterList;
	/** 任务状态 */
	private QuestState currentState = QuestState.QUEST_CAN_ACCEPT;
	/** 任务结束时间 */
	private long endTime;
	/** 关心的事件以及处理器的注册表 */
	private Map<EventType, Set<IQuestAimCounter>> listeners = new HashMap<EventType, Set<IQuestAimCounter>>();
	/** 事件总线 */
	private IEventBus eventBus;

	public Quest(Human human, IEventBus eventBus, QuestTemplate questTemplate) {
		this.human = human;
		this.questTemplate = questTemplate;
		this.questCheckerLogic = this.questTemplate.getQuestCheckerLogic();
		this.eventBus = eventBus;
	}

	public void setState(QuestState state) {
		this.currentState = state;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Override
	public void init() {

		// 构建任务计数器
		aimCounterList = new ArrayList<IQuestAimCounter>();
		List<AimInfo> aimList = questTemplate.getAimInfoList();
		int index = 0;
		// 主线任务目标设置
		if (questTemplate.getQuestType() == QuestType.QUEST_MAIN.getIndex()
				|| questTemplate.getQuestType() == QuestType.QUEST_BRANCH
						.getIndex()) {
			for (AimInfo aim : aimList) {
				aimCounterList.add(MainQuestAimType.typeOf(aim.getAimType())
						.createAimCounter(aim, index));
				index++;
			}
		} else {
			// 日常任务目标设置;
			for (AimInfo aim : aimList) {
				aimCounterList.add(DailyQuestAimType.typeOf(aim.getAimType())
						.createAimCounter(aim, index));
				index++;
			}
		}

		// 添加事件监听
		for (IQuestAimCounter counter : this.aimCounterList) {
			for (EventType eachEventType : counter.getEventTypeSet()) {
				if (!this.listeners.containsKey(counter.getEventTypeSet())) {
					this.listeners.put(eachEventType,
							new HashSet<IQuestAimCounter>());
				}
				this.listeners.get(eachEventType).add(counter);
			}

		}

	}

	/**
	 * 接受任务
	 */
	public void startQuest() {
		this.currentState = QuestState.QUEST_ACCEPTING;
		// 初始化计数器
		for (IQuestAimCounter counter : this.aimCounterList) {
			counter.init(human);
		}
		// 添加事件监听
		addListeners();
	}

	/**
	 * 自动完成日常任务
	 */
	public void autoCompleteDailyQuest() {
		this.currentState = QuestState.QUEST_AUTO;
		this.endTime = questTemplate.getAutoCompleteTime() * TimeUtils.MIN
				+ GameServerAssist.getSystemTimeService().now();
	}

	/**
	 * 加速自动完成日常任务
	 */
	public void quicklyAutoCompleteDailyQuest() {
		this.endTime = questTemplate.getAutoCompleteTime() * TimeUtils.MIN
				+ GameServerAssist.getSystemTimeService().now();
		// 调用所有目标都完成的逻辑
		whenAllQuestAimFinished();
	}

	/**
	 * 取消日常任务
	 */
	public void abortDailyQuest() {
		this.currentState = QuestState.QUEST_CAN_ACCEPT;
		this.endTime = GameServerAssist.getSystemTimeService().now();
		// 移除事件监听器
		removeListeners();
		// 初始化计数器
		for (IQuestAimCounter counter : this.aimCounterList) {
			counter.init(human);
		}
	}

	public void addListeners() {
		for (EventType type : this.listeners.keySet()) {
			this.eventBus.addListener(type, this);
		}
	}

	public void removeListeners() {
		for (EventType type : this.listeners.keySet()) {
			this.eventBus.removeListener(type, this);
		}

	}

	@Override
	public void onEvent(IEvent event) {
		List<QuestAimInfo> changedCounters = onQuestEvent(event);
		boolean needUpdate = false;
		if (changedCounters.size() > 0) {
			// 通知客户端任务计数器更新
			GCUpdateQuestCounter questCounterMsg = new GCUpdateQuestCounter();
			questCounterMsg.setQuestId(this.getQuestId());
			questCounterMsg.setQuestAims(changedCounters
					.toArray(new QuestAimInfo[0]));
			human.sendMessage(questCounterMsg);
			needUpdate = true;
		}
		// 任务目标是否已经达成
		if (isAllAimCounterFinished()) {
			// 任务目标完成以后的逻辑
			this.whenAllQuestAimFinished();
			needUpdate = true;
		}
		if (needUpdate) {
			// 任务数据已脏, 添加到缓存中
			human.getHumanQuestManager().addUpdateQuest(this);
		}

	}

	/**
	 * 任务的所有目标都已经达成;
	 */
	private void whenAllQuestAimFinished() {
		this.questCheckerLogic.whenAllAimFinished(this, human);
	}

	private List<QuestAimInfo> onQuestEvent(IEvent event) {
		List<QuestAimInfo> results = new ArrayList<QuestAimInfo>();
		for (IQuestAimCounter counter : this.aimCounterList) {
			if (counter.onQuestEvent(event)) {
				QuestAimInfo changedData = new QuestAimInfo();
				changedData.setAimIndex(counter.getIndex());
				changedData.setCurrentValue(counter.getValue());
				// 设置目标值
				if (counter instanceof AbstractDailyQuestAimCounter) {
					changedData.setAimValue(counter.getAim().getParam1());
				} else {
					changedData.setAimValue(counter.getAim().getParam2());
				}
				results.add(changedData);
			}
		}
		return results;
	}

	/**
	 * 任务目标是否已经全部完成;
	 * 
	 * @return
	 */
	public boolean isAllAimCounterFinished() {
		boolean result = true;
		for (IQuestAimCounter counter : this.aimCounterList) {
			if (!counter.isFinished()) {
				result = false;
			}
		}
		return result;
	}

	public void finishQuest() {
		this.questCheckerLogic.finishQuest(this, human);
		this.currentState = QuestState.QUEST_FINISHED;
	}

	public boolean checkFinish() {
		return this.questCheckerLogic.checkFinish(this, human);
	}

	public QuestState getCurrentState() {
		return this.currentState;
	}

	public List<IQuestAimCounter> getAimList() {
		return Collections.unmodifiableList(aimCounterList);
	}

	public Human getHuman() {
		return this.human;
	}

	public int getQuestId() {
		return this.questTemplate.getId();
	}

	public QuestTemplate getQuestTemplate() {
		return questTemplate;
	}

}
