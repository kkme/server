package com.hifun.soul.gameserver.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.ActivityEntity;
import com.hifun.soul.gamedb.service.DataService;
import com.hifun.soul.gameserver.boss.service.BossWarService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionboss.service.LegionBossService;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.matchbattle.service.MatchBattleService;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

/**
 * 全局活动管理器
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class GlobalActivityManager implements IHeartBeatable {
	private static Logger logger = Loggers.ACTIVITY_LOGGER;
	@Autowired
	private DataService dataService;
	@Autowired
	private ActivityTemplateManager templateManager;
	@Autowired
	private TimeService timeService;
	/** BOSS战服务 */
	@Autowired
	private BossWarService bossWarService;
	/** 军团BOSS战服务 */
	@Autowired
	private LegionBossService legionBossWarService;
	/** 军团矿战管理 */
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;
	/** 押运管理器 */
	@Autowired
	private GlobalEscortManager globalEscortManager;
	/** 匹配战服务 */
	private MatchBattleService matchBattleService;
	/** 所有有效活动 */
	private Map<Integer, Activity> activities = new HashMap<Integer, Activity>();
	/** 定时活动 */
	private Map<Integer, Activity> timingActivities = new HashMap<Integer, Activity>();
	/** key:活动id，value：定时活动管理器单例 */
	private Map<Integer, ITimingActivityManager> timingActivityManagers = new HashMap<Integer, ITimingActivityManager>();

	private Set<Integer> timingActivityStateSet = new HashSet<Integer>();

	public void initData(IDBService dbService) {
		activities = templateManager.getAllActivities();
		long now = timeService.now();
		for (Integer id : activities.keySet()) {
			Activity act = activities.get(id);
			if (act.isTiming()) {
				act.setNextTimeSpanIndex(this.getTimeSpanIndex(now, act.getActiveTimeSpan()));
				timingActivities.put(id, act);
			}
		}
		// 加载数据库中保存的状态
		List<?> activityStateList = dbService.findByNamedQuery(DataQueryConstants.QUERY_ACTIVITY_STATE);
		if (activityStateList != null && !activityStateList.isEmpty()) {
			long beginOfToday = TimeUtils.getBeginOfDay(now);
			for (int i = 0; i < activityStateList.size(); i++) {
				ActivityEntity entity = (ActivityEntity) activityStateList.get(i);
				int id = (Integer) entity.getId();
				if (!activities.containsKey(id) || activities.get(id) == null) {
					continue;
				}
				timingActivityStateSet.add(id);
				Activity act = activities.get(id);
				// 检查当前轮回是否已经被提前结束过
				if (entity.getEditTime() > beginOfToday && entity.getTimeSpanIndex() == act.getNextTimeSpanIndex()) {
					act.setNextTimeSpanIndex(act.getNextTimeSpanIndex() + 1);
				}
			}
		}
		registerGlobalTimingActivityManagers();
	}

	/**
	 * 注册全局定时活动管理器
	 */
	private void registerGlobalTimingActivityManagers() {
		// 注册全局定时活动管理器
		timingActivityManagers.put(ActivityType.BOSS_WAR.getIndex(), bossWarService);
		matchBattleService = GameServerAssist.getMatchBattleService();
		timingActivityManagers.put(ActivityType.MATCH_BATTLE.getIndex(), matchBattleService);
		timingActivityManagers.put(ActivityType.LEGION_BOSS.getIndex(), legionBossWarService);
		timingActivityManagers.put(ActivityType.LEGION_MINE.getIndex(), globalLegionMineWarManager);
		timingActivityManagers.put(ActivityType.ESCORT_AMEND.getIndex(), globalEscortManager);
	}

	public Map<Integer, Activity> getActivities() {
		return activities;
	}

	/**
	 * 根据活动类型获取活动状态
	 * 
	 * @param activityType
	 * @return
	 */
	public ActivityState getActivityState(ActivityType activityType) {
		Activity activity = activities.get(activityType.getIndex());
		if (activity == null) {
			return ActivityState.FINISH;
		}
		return activity.getState();
	}

	/**
	 * gm命令调用
	 * 
	 * @param activityType
	 * @return
	 */
	public Activity getActivity(ActivityType activityType) {
		return activities.get(activityType.getIndex());
	}

	/**
	 * 提前终止活动
	 */
	public void abortAcivity(ActivityType activityType) {
		if (!timingActivities.containsKey(activityType.getIndex())) {
			return;
		}
		Activity activity = timingActivities.get(activityType.getIndex());
		if (activity != null) {
			long now = timeService.now();
			long timeFormToday = now - TimeUtils.getTodayBegin(timeService);
			logger.info("activity abort,id:" + activity.getId() + " currentTimeSpan:" + activity.getNextTimeSpanIndex());
			if (timeFormToday > activity.getFinishTime()) {
				activity.setState(ActivityState.FINISH);
			} else {
				activity.setState(ActivityState.READY);
			}			
			int timeSpanIndex = activity.getNextTimeSpanIndex();
			activity.setNextTimeSpanIndex(timeSpanIndex + 1);
			saveActivityState(activity, now);
			this.onActivityClose(activity);
		}
	}

	private void saveActivityState(Activity activity, long endTime) {
		ActivityEntity entity = new ActivityEntity();
		entity.setId(activity.getId());
		entity.setActivityState(activity.getState().getIndex());
		entity.setTimeSpanIndex(activity.getNextTimeSpanIndex() - 1);
		entity.setEditTime(endTime);
		if (timingActivityStateSet.contains(activity.getId())) {
			dataService.update(entity);
		} else {
			dataService.insert(entity);
			this.timingActivityStateSet.add(activity.getId());
		}
	}

	/**
	 * 获取有效的活动列表
	 * 
	 * @param showType
	 *            活动显示类型
	 * @return
	 */
	public List<Activity> getActivities(int activityShowType) {
		List<Activity> activityList = new ArrayList<Activity>();
		for (Integer id : activities.keySet()) {
			if (activities.get(id).getActivityShowType() == activityShowType) {
				activityList.add(activities.get(id));
			}
		}
		return activityList;
	}

	@Override
	public void heartBeat() {
		long now = timeService.now();
		long timeFromToday = now - TimeUtils.getBeginOfDay(now);
		for (Integer id : timingActivities.keySet()) {
			Activity activity = activities.get(id);
			if (activity.getState() == ActivityState.FINISH) {
				continue;
			}
			if (activity.getNextTimeSpanIndex() >= activity.getActiveTimeSpan().size()) {
				continue;
			}
			KeyValuePair<Long, Long> openTimeSpan = activity.getActiveTimeSpan().get(activity.getNextTimeSpanIndex());
			if (openTimeSpan == null) {
				continue;
			}
			long beginTime = openTimeSpan.getKey();
			long endTime = openTimeSpan.getValue();
			if (timeFromToday >= endTime) {
				// 结束
				if (timeFromToday >= activity.getFinishTime()) {
					activity.setState(ActivityState.FINISH);
				} else {
					activity.setState(ActivityState.READY);
				}
				activity.setNextTimeSpanIndex(activity.getNextTimeSpanIndex() + 1);
				onActivityClose(activity);
				saveActivityState(activity, now);
				logger.info("activity close,id:" + activity.getId() + " nextTimeSpan:" + activity.getNextTimeSpanIndex());
				continue;
			}
			if (timeFromToday >= beginTime && timeFromToday < endTime) {
				if (activity.getState() == ActivityState.READY) {
					// 开始
					activity.setState(ActivityState.OPEN);
					onActivityOpen(activity.getId());
					logger.info("activity start,id:" + activity.getId() + " nextTimeSpan:" + activity.getNextTimeSpanIndex());
				}
			}
		}
	}

	/**
	 * 活动开始
	 * 
	 * @param activityId
	 */
	private void onActivityOpen(int activityId) {
		if (timingActivityManagers.get(activityId) != null) {
			timingActivityManagers.get(activityId).onStart();
		}
		SceneHumanManager humanManager = GameServerAssist.getGameWorld()
				.getSceneHumanManager();
		for (Human human : humanManager.getAllHumans()) {
			human.getHumanActivityManager().updateActivityState(activityId, ActivityState.OPEN);
		}
	}

	/**
	 * 活动结束
	 * 
	 * @param activityId
	 */
	private void onActivityClose(Activity activity) {		
		logger.debug("closing activity data:"+activity.toString());
		int activityId = activity.getId();
		if (timingActivityManagers.get(activityId) != null) {
			timingActivityManagers.get(activityId).onStop();
		}
		SceneHumanManager humanManager = GameServerAssist.getGameWorld()
				.getSceneHumanManager();
		for (Human human : humanManager.getAllHumans()) {
			human.getHumanActivityManager().updateActivityState(activityId, activity.getState());
		}
	}

	/**
	 * 刷新有效活动列表
	 */
	public void refreshALiveActivity(long now) {
		Map<Integer, Activity> tempActivities = new HashMap<Integer, Activity>();
		for (Integer id : activities.keySet()) {
			Activity activity = activities.get(id);
			if (now > activity.getEndDate().getTime() + TimeUtils.DAY) {
				if (timingActivities.containsKey(id)) {
					timingActivities.remove(id);
				}
				if (timingActivityStateSet.contains(activity.getId())) {
					timingActivityStateSet.remove(activity.getId());
				}
				continue;
			}
			if (activity.isTiming()) {
				activity.setState(ActivityState.READY);
				logger.info("refreshALiveActivity before timeActivityId:" + id + ",timeSpanSize:"
						+ activity.getActiveTimeSpan().size()+",nextTimeSpanIndex:"+activity.getNextTimeSpanIndex());
				int actTimeSpanIndex = this.getTimeSpanIndex(now, activity.getActiveTimeSpan());
				long timeFromTodayBegin = now - TimeUtils.getBeginOfDay(now);
				//小于今天的开始时间需要重置
				if(timeFromTodayBegin<activity.getActiveTimeSpan().get(0).getKey() || activity.getNextTimeSpanIndex() < actTimeSpanIndex){
					activity.setNextTimeSpanIndex(actTimeSpanIndex);
				}
				logger.info("refreshALiveActivity after timeActivityId:" + id + ",timeSpanSize:"
						+ activity.getActiveTimeSpan().size()+",nextTimeSpanIndex:"+activity.getNextTimeSpanIndex());
				
			} else {
				activity.setState(ActivityState.OPEN);
			}
			tempActivities.put(id, activity);
		}
		this.activities = tempActivities;
	}

	/**
	 * 获取活动的剩余时间
	 * 
	 * @param activityType
	 * @return
	 */
	public long getRemainTime(ActivityType activityType) {
		if (!this.activities.containsKey(activityType.getIndex())) {
			return 0;
		}
		Activity activity = this.activities.get(activityType.getIndex());
		long now = GameServerAssist.getSystemTimeService().now();
		if (!activity.isTiming()) {
			return TimeUtils.DAY - (now - TimeUtils.getBeginOfDay(now));
		}
		if (ActivityState.OPEN != activity.getState()) {
			return 0;
		}
		KeyValuePair<Long, Long> openTimeSpan = activity.getActiveTimeSpan().get(activity.getNextTimeSpanIndex());
		return openTimeSpan.getValue() - (now - TimeUtils.getBeginOfDay(now));
	}

	/**
	 * 根据时间获取时间段索引
	 * 
	 * @param now
	 * @param timeSpanList
	 * @return
	 */
	private int getTimeSpanIndex(long now, List<KeyValuePair<Long, Long>> timeSpanList) {
		long timeFromToday = now - TimeUtils.getBeginOfDay(now);
		logger.info("TimeFromToday: " + timeFromToday);
		int result = 0;
		for (int i = 0; i < timeSpanList.size(); i++) {
			KeyValuePair<Long, Long> pair = timeSpanList.get(i);
			if(timeFromToday >= pair.getValue()){
				result++;
			}
		}
		return result;
	}	
	
	/**
	 * 获取定时活动下次开始时间距离当前时间的差值
	 * <pre>注：活动状态必须为ready（未开始）状态，否则返回0</pre>
	 * @param type 活动类型
	 * @return
	 */
	public long getNextOpenTimeDiff(ActivityType type){
		if(!activities.containsKey(type.getIndex())){
			return 0L;
		}
		Activity act = activities.get(type.getIndex());
		if(!act.isTiming()){
			return 0L;
		}
		if(act.getState() == ActivityState.LOCK || act.getState() == ActivityState.UNVISABLE 
				|| act.getState() == ActivityState.OPEN){
			return 0L;
		}
		long now = timeService.now();
		long timeFromToday = now-TimeUtils.getBeginOfDay(now);
		long nextStartTime = 0L;
		long nextOpenTimeDiff = 0L;
		if(act.getNextTimeSpanIndex()>=act.getActiveTimeSpan().size()){
			nextStartTime = act.getActiveTimeSpan().get(0).getKey();
			nextOpenTimeDiff = nextStartTime - timeFromToday + TimeUtils.DAY;
		}else{
			nextStartTime = act.getActiveTimeSpan().get(act.getNextTimeSpanIndex()).getKey();
			nextOpenTimeDiff = nextStartTime - timeFromToday; 
		}
		return nextOpenTimeDiff>0 ? nextOpenTimeDiff : 0L;
	}
}
