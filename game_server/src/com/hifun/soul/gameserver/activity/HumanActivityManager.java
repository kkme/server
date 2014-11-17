package com.hifun.soul.gameserver.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.gameserver.activity.model.ActivityBaseInfo;
import com.hifun.soul.gameserver.activity.model.ActivityData;
import com.hifun.soul.gameserver.activity.msg.GCActivityList;
import com.hifun.soul.gameserver.activity.msg.GCAloneActivityList;
import com.hifun.soul.gameserver.activity.msg.GCUpdateActivityState;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.activity.template.ActivityShowTypeTemplate;
import com.hifun.soul.gameserver.boss.manager.HumanBossManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.legionboss.manager.HumanLegionBossManager;
import com.hifun.soul.gameserver.legionmine.manager.HumanLegionMineWarManager;
import com.hifun.soul.gameserver.matchbattle.manager.HumanMatchBattleManager;

/**
 * 玩家所有的活动管理器
 * 
 * @author magicstone
 * 
 */
public class HumanActivityManager implements ILoginManager, IEventListener {
	/** 所有的活动管理器 */
	private Map<Integer, IHumanActivityManager> activityManagers = new HashMap<Integer, IHumanActivityManager>();
	/** 定时活动管理器 */
	private Map<Integer, ITimingActivityManager> timingActivityManagers = new HashMap<Integer, ITimingActivityManager>();
	/** 当前玩家的所有活动状态 */
	private Map<Integer, ActivityState> activityStateMap = new HashMap<Integer, ActivityState>();
	/** 当前玩家的所有活动状态 */
	private Map<Integer, ActivityState> activityTypeStateMap = new HashMap<Integer, ActivityState>();
	
	private Human human;

	public HumanActivityManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);		
		init();
		registerEventListener();
		
	}

	private void registerEventListener() {
		human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}

	private void init() {
		// 注册具体的活动管理器
		//问答活动
		AnswerQuestionManager answerQuestionManager = new AnswerQuestionManager(human);
		activityManagers.put(answerQuestionManager.getActivityType().getIndex(), answerQuestionManager);
		//boss战活动
		HumanBossManager humanBossManager = human.getHumanBossManager();
		activityManagers.put(humanBossManager.getActivityType().getIndex(), humanBossManager);
		//匹配战活动
		HumanMatchBattleManager humanMatchBattleManager = human.getHumanMatchBattleManager();
		activityManagers.put(humanMatchBattleManager.getActivityType().getIndex(), humanMatchBattleManager);
		//军团boss战活动
		HumanLegionBossManager humanLegionBossManager = human.getHumanLegionBossManager();
		activityManagers.put(humanLegionBossManager.getActivityType().getIndex(), humanLegionBossManager);
		//军团矿战活动
		HumanLegionMineWarManager humanLegionMineWarManager = human.getHumanLegionMineWarManager();
		activityManagers.put(humanLegionMineWarManager.getActivityType().getIndex(), humanLegionMineWarManager);
	}

	public Human getOwner() {
		return human;
	}

	public IHumanActivityManager getActivityManager(ActivityType activityType) {
		return this.activityManagers.get(activityType.getIndex());
	}

	@Override
	public void onEvent(IEvent event) {
		switch (event.getType()) {
		case LEVEL_UP_EVENT:
			// 升级后检查是否有新的活动开启
			doLevelUpAction();
			break;
		}

	}

	/**
	 * 角色升级事件处理
	 */
	private void doLevelUpAction() {		
		int humanLevel = human.getLevel();
		List<ActivityBaseInfo> activityBaseInfoList = new ArrayList<ActivityBaseInfo>();
		Map<Integer,ActivityBaseInfo> activityTypeBaseInfoMap = new HashMap<Integer, ActivityBaseInfo>();
		//检查是否新的活动类型开启
		for(Integer typeId : GameServerAssist.getActivityTemplateManager().getShowTypeTemplates().keySet()){
			if(typeId == ActivityShowType.ALONE_ACTIVITY.getIndex()){
				continue;
			}			
			ActivityShowTypeTemplate typeTemplate = GameServerAssist.getActivityTemplateManager().getShowTypeTemplate(typeId);
			if (humanLevel >= typeTemplate.getOpenLevel()) {
				if(activityTypeStateMap.get(typeId) == ActivityState.LOCK || activityTypeStateMap.get(typeId) == ActivityState.UNVISABLE){
					activityTypeStateMap.put(typeId, ActivityState.READY);
					ActivityBaseInfo baseInfo = new ActivityBaseInfo(typeTemplate);
					baseInfo.setState(ActivityState.READY.getIndex());
					activityTypeBaseInfoMap.put(baseInfo.getId(), baseInfo);
				}
			}
			else if(humanLevel >= typeTemplate.getVisibleLevel()){
				activityTypeStateMap.put(typeId, ActivityState.LOCK);
				ActivityBaseInfo baseInfo = new ActivityBaseInfo(typeTemplate);
				baseInfo.setState(ActivityState.LOCK.getIndex());
				activityTypeBaseInfoMap.put(baseInfo.getId(), baseInfo);
			}			
		}
		//检查是否新的活动开启
		Map<Integer, Activity> aliveActivities = GameServerAssist.getGlobalActivityManager().getActivities();
		for (Integer id : aliveActivities.keySet()) {
			Activity activity = aliveActivities.get(id);
			if (humanLevel >= activity.getOpenLevel()) {
				// 活动功能开启
				if (ActivityState.LOCK == activityStateMap.get(id)) {
					ActivityState state = activity.getState();
					if(!activity.isTiming()){
						state = ActivityState.OPEN;
					}
					activityStateMap.put(id, state);
					sendUpdateActivityStateMessage(activity.getId(),activity.getActivityShowType(),state.getIndex());					
				}
				else if(ActivityState.UNVISABLE == activityStateMap.get(id)){
					ActivityState state = activity.getState();
					activityStateMap.put(id, state);
					ActivityBaseInfo baseInfo = new ActivityBaseInfo(activity);
					baseInfo.setState(ActivityState.READY.getIndex());
					activityBaseInfoList.add(baseInfo);
					if(state == ActivityState.OPEN && baseInfo.getActivityType() != ActivityShowType.ALONE_ACTIVITY.getIndex()){
						if(activityTypeBaseInfoMap.containsKey(baseInfo.getActivityType())){
							activityTypeBaseInfoMap.get(baseInfo.getActivityType()).setState(state.getIndex());
						}
						else
						{
							sendUpdateActivityStateMessage(activity.getId(),0,state.getIndex());		
						}
					}
				}
				continue;
			} else if (humanLevel >= activity.getVisibleLevel()) {
				activityStateMap.put(id, ActivityState.LOCK);
				if(activity.getActivityShowType() != ActivityShowType.ALONE_ACTIVITY.getIndex()){
					continue;
				}
				// 活动可见
				if (ActivityState.UNVISABLE == activityStateMap.get(id)) {
					ActivityBaseInfo baseInfo = new ActivityBaseInfo(activity);
					baseInfo.setState(ActivityState.LOCK.getIndex());
					activityBaseInfoList.add(baseInfo);
				}
				continue;
			} else {
				// 活动不可见
				activityStateMap.put(id, ActivityState.UNVISABLE);
			}
		}
		activityBaseInfoList.addAll(activityTypeBaseInfoMap.values());
		GCAloneActivityList gcAloneActivityMsg = new GCAloneActivityList();
		ActivityBaseInfo[] baseInfoArray = new ActivityBaseInfo[0];
		gcAloneActivityMsg.setActivityBaseInfoList(activityBaseInfoList.toArray(baseInfoArray));
		human.sendMessage(gcAloneActivityMsg);
	}
	
	/**
	 * 发送活动状态更新消息
	 * @param activityId
	 * @param activityType
	 * @param state
	 */
	private void sendUpdateActivityStateMessage(int activityId,int activityType,int state){
		GCUpdateActivityState gcMsg = new GCUpdateActivityState();
		gcMsg.setId(activityId);
		gcMsg.setActivityType(activityType);
		gcMsg.setState(state);
		human.sendMessage(gcMsg);
	}


	@Override
	public void onLogin() {
		int humanLevel = human.getLevel();
		List<ActivityBaseInfo> activityBaseInfoList = new ArrayList<ActivityBaseInfo>();
		Map<Integer,ActivityBaseInfo> activityTypeBaseInfoMap = new HashMap<Integer, ActivityBaseInfo>();
		for(Integer typeId : GameServerAssist.getActivityTemplateManager().getShowTypeTemplates().keySet()){
			if(typeId == ActivityShowType.ALONE_ACTIVITY.getIndex()){
				continue;
			}
			activityTypeStateMap.put(typeId, ActivityState.UNVISABLE);
			ActivityShowTypeTemplate typeTemplate = GameServerAssist.getActivityTemplateManager().getShowTypeTemplate(typeId);
			if (humanLevel >= typeTemplate.getOpenLevel()) {
				activityTypeStateMap.put(typeId, ActivityState.READY);
				ActivityBaseInfo aloneActivity = new ActivityBaseInfo(typeTemplate);
				aloneActivity.setState(ActivityState.READY.getIndex());
				activityTypeBaseInfoMap.put(aloneActivity.getId(), aloneActivity);
			}
			else if(humanLevel >= typeTemplate.getVisibleLevel()){
				activityTypeStateMap.put(typeId, ActivityState.LOCK);
				ActivityBaseInfo aloneActivity = new ActivityBaseInfo(typeTemplate);
				aloneActivity.setState(ActivityState.LOCK.getIndex());
				activityTypeBaseInfoMap.put(aloneActivity.getId(), aloneActivity);
			}			
		}
		Map<Integer, Activity> aliveActivities = GameServerAssist.getGlobalActivityManager().getActivities();
		for (Integer id : aliveActivities.keySet()) {
			Activity activity = aliveActivities.get(id);
			if (humanLevel >= activity.getOpenLevel()) {
				// 活动功能开启
				activityStateMap.put(id, activity.getState());
				if (ActivityShowType.ALONE_ACTIVITY.getIndex() == activity
						.getActivityShowType()) {
					ActivityBaseInfo aloneActivity = new ActivityBaseInfo(activity);
					aloneActivity.setState(activity.getState().getIndex());
					activityBaseInfoList.add(aloneActivity);
				}
				else if(activity.getState() == ActivityState.OPEN){
					activityTypeBaseInfoMap.get(activity.getActivityShowType()).setState(activity.getState().getIndex());
					sendUpdateActivityStateMessage(activity.getId(),activity.getActivityShowType(),activity.getState().getIndex());
				}
				continue;
			} else if (humanLevel >= activity.getVisibleLevel()) {
				// 活动可见
				activityStateMap.put(id, ActivityState.LOCK);
				if (ActivityShowType.ALONE_ACTIVITY.getIndex() == activity
						.getActivityShowType()) {
					ActivityBaseInfo aloneActivity = new ActivityBaseInfo(activity);
					aloneActivity.setState(ActivityState.LOCK.getIndex());
					activityBaseInfoList.add(aloneActivity);
				}
				continue;
			} else {
				// 活动不可见
				activityStateMap.put(id, ActivityState.UNVISABLE);
			}
		}
		activityBaseInfoList.addAll(activityTypeBaseInfoMap.values());
		GCAloneActivityList gcAloneActivityMsg = new GCAloneActivityList();
		ActivityBaseInfo[] baseInfoArray = new ActivityBaseInfo[0];
		gcAloneActivityMsg.setActivityBaseInfoList(activityBaseInfoList.toArray(baseInfoArray));
		human.sendMessage(gcAloneActivityMsg);
	}

	/**
	 * 更新活动状态（开启或关闭）
	 * 
	 * @param activityId
	 */
	public void updateActivityState(int activityId, ActivityState state) {
		ITimingActivityManager timingActivityManager = null;
		int showType = GameServerAssist.getGlobalActivityManager().getActivity(ActivityType.indexOf(activityId)).getActivityShowType();
		if (timingActivityManagers.containsKey(activityId)) {
			timingActivityManager = timingActivityManagers.get(activityId);
		}
		if(activityStateMap.get(activityId) == ActivityState.UNVISABLE || activityStateMap.get(activityId) == ActivityState.LOCK){
			return;
		}
		this.activityStateMap.put(activityId,state);
		if(state == ActivityState.OPEN ) {
			if (timingActivityManager != null) {
				timingActivityManager.onStart();				
			}
			//发活动状态通知
			sendUpdateActivityStateMessage(activityId,showType,state.getIndex());
		}
		else if(state == ActivityState.FINISH || state == ActivityState.READY){	
			this.activityStateMap.put(activityId,state);
			if (timingActivityManager != null) {
				timingActivityManager.onStop();					
			}
			//发活动状态通知:检查是否该类型的所有活动状态都不为open
			boolean isAllFinished = true;
			for(Activity act : GameServerAssist.getGlobalActivityManager().getActivities(showType)){
				if(activityStateMap.containsKey(act.getId())){
					if(activityStateMap.get(act.getId())==ActivityState.OPEN){
						isAllFinished = false;
						break;
					}
				}
			}
			if(isAllFinished){
				sendUpdateActivityStateMessage(activityId,showType,state.getIndex());
			}
		}	
		
	}

	/**
	 * 参与活动
	 * @param activityId
	 */
	public void enterActivity(int activityId) {		
		if (this.activityManagers.containsKey(activityId)) {
			Activity activity = GameServerAssist.getGlobalActivityManager().getActivities().get(activityId);
			if(activity.getOpenLevel()>human.getLevel()){
				return;
			}			
			if (activity.isTiming()) {
				if (ActivityState.OPEN != activity.getState()) {
					return;
				}
				if (ActivityState.OPEN != this.activityStateMap.get(activityId)) {
					return;
				}
			}
			IHumanActivityManager humanActivityManager = this.activityManagers.get(activityId);
			if (humanActivityManager != null) {
				humanActivityManager.onOpenClick();
			}
		}
	}
	
	/**
	 * 发送开启的活动列表信息
	 * @param activityShowType
	 */
	public void sendActivityListMessage(int activityShowType){
		List<ActivityData> activityDataList = new ArrayList<ActivityData>();
		for(Activity activity : GameServerAssist.getGlobalActivityManager().getActivities(activityShowType)){
			ActivityState state = this.activityStateMap.get(activity.getId());
			if(state != ActivityState.UNVISABLE){
				ActivityData actData = new ActivityData(activity);
				actData.setState(state.getIndex());
				activityDataList.add(actData);
				
			}
		}
		ActivityData[] activities = new ActivityData[0];
		activities = activityDataList.toArray(activities);
		GCActivityList gcMsg = new GCActivityList();
		gcMsg.setActivities(activities);
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 获取活动状态
	 */
	public int getActivityState(ActivityType activityType) {
		return activityStateMap.get(activityType.getIndex()).getIndex();
	}
}
