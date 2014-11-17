package com.hifun.soul.gameserver.timetask.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.GlobalKeyValueEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;
import com.hifun.soul.gameserver.timetask.model.ActivityDailyTask;
import com.hifun.soul.gameserver.timetask.model.DoubleMineInternalTask;
import com.hifun.soul.gameserver.timetask.model.GlobalAbattoirQuitRoomsTask;
import com.hifun.soul.gameserver.timetask.model.GlobalEscortDailyTask;
import com.hifun.soul.gameserver.timetask.model.GlobalEscortEndTask;
import com.hifun.soul.gameserver.timetask.model.GlobalHonorRankTask;
import com.hifun.soul.gameserver.timetask.model.GlobalLegionApplyOutTimeTask;
import com.hifun.soul.gameserver.timetask.model.GlobalLevelRankTask;
import com.hifun.soul.gameserver.timetask.model.GlobalPrisonReleaseSlavesTask;
import com.hifun.soul.gameserver.timetask.model.GlobalPrisonResetDataTask;
import com.hifun.soul.gameserver.timetask.model.GlobalResetFriendRewardTask;
import com.hifun.soul.gameserver.timetask.model.GlobalResetLegionDailyDataTask;
import com.hifun.soul.gameserver.timetask.model.GlobalTitleRankTask;
import com.hifun.soul.gameserver.timetask.model.ResetArenaRankRewardTask;
import com.hifun.soul.gameserver.timetask.model.ResetGlobalMarsDataDailyTask;

/**
 * 全局定时任务管理器
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class GlobalTimeTaskManager implements IHeartBeatable,
		IInitializeRequired {

	@Autowired
	private SystemTimeService timeService;
	@Autowired
	private IDataService dataService;
	private List<IHeartBeatTask> tasks = new ArrayList<IHeartBeatTask>();
	/** 等级排行榜刷新任务 */
	@Autowired
	private GlobalLevelRankTask levelRankTask;
	/** 军衔排行榜刷新任务 */
	@Autowired
	private GlobalTitleRankTask titleRankTask;
	/** 荣誉排行榜刷新任务 */
	@Autowired
	private GlobalHonorRankTask honorRankTask;
	/** 每日活动刷新任务 */
	@Autowired
	private ActivityDailyTask activityDailyTask;
	@Autowired
	private ResetArenaRankRewardTask resetArenaRankRewardTask;
	@Autowired
	private GlobalResetFriendRewardTask resetFriendRewardTask;
	/** 军团申请超时任务 */
	@Autowired
	private GlobalLegionApplyOutTimeTask legionApplyOutTimeTask;
	/** 战俘营重置数据任务 */
	@Autowired
	private GlobalPrisonResetDataTask prisonResetDataTask;
	/** 战俘营到点释放奴隶 */
	@Autowired
	private GlobalPrisonReleaseSlavesTask prisonReleaseSlavesTask;
	/** 角斗场到点退出房间 */
	@Autowired
	private GlobalAbattoirQuitRoomsTask abattoirQuitRoomsTask;
	/** 战神之巅重置每日数据 */
	@Autowired
	private ResetGlobalMarsDataDailyTask resetGlobalMarsDataDailyTask;
	/** 押运到时结束 */
	@Autowired
	private GlobalEscortEndTask globalEscortEndTask;
	/** 押运重置数据 */
	@Autowired
	private GlobalEscortDailyTask globalEscortDailyTask;
	/** 重置全局军团每日数据 */
	@Autowired
	GlobalResetLegionDailyDataTask globalResetLegionDailyDataTask;
	/** 军团矿战刷加倍矿定时任务 */
	@Autowired
	DoubleMineInternalTask doubleMineInternalTask;

	private Logger logger = Loggers.TIMMER_LOGGER;
	private Map<Integer, Long> globalTaskTimeMap = new HashMap<Integer, Long>();

	public GlobalTimeTaskManager() {
	}

	@Override
	public void init() {
		// 等级排行榜刷新任务
		tasks.add(levelRankTask);
		// 军衔排行榜刷新任务
		tasks.add(titleRankTask);
		// 荣誉排行榜刷新任务
		tasks.add(honorRankTask);
		// 每日活动刷新任务
		tasks.add(activityDailyTask);
		// 竞技场排名奖励定时刷新
		tasks.add(resetArenaRankRewardTask);
		// 好友奖励信息
		tasks.add(resetFriendRewardTask);
		// 军团申请超时任务
		tasks.add(legionApplyOutTimeTask);
		// 战俘营重置数据任务
		tasks.add(prisonResetDataTask);
		// 战俘营到点释放奴隶
		tasks.add(prisonReleaseSlavesTask);
		// 角斗场到点退出房间
		tasks.add(abattoirQuitRoomsTask);
		// 战神之巅重置每日数据
		tasks.add(resetGlobalMarsDataDailyTask);
		// 押运到时结束
		tasks.add(globalEscortEndTask);
		// 押运重置每日数据
		tasks.add(globalEscortDailyTask);
		// 重置全局军团每日数据
		tasks.add(globalResetLegionDailyDataTask);
		// 军团矿战刷加倍矿定时任务
		tasks.add(doubleMineInternalTask);
	}

	public void start(IDBService dbService) {
		// 加载全局定时任务上次执行时间
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_GLOBAL_KEY_VALUE);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<GlobalKeyValueEntity> results = (List<GlobalKeyValueEntity>) result;
			for (GlobalKeyValueEntity entity : results) {
				globalTaskTimeMap.put((Integer) entity.getId(),
						Long.parseLong(entity.getValue()));
			}
		}
	}

	public void setLastRunTime(int key, long time) {
		GlobalKeyValueEntity entity = new GlobalKeyValueEntity();
		entity.setId(key);
		entity.setValue(String.valueOf(time));
		if (globalTaskTimeMap.containsKey(key)) {
			dataService.update(entity);
		} else {
			dataService.insert(entity);
		}
		globalTaskTimeMap.put(key, time);
	}

	public long getLastRunTime(int key) {
		if (!globalTaskTimeMap.containsKey(key)) {
			return 0L;
		}
		return globalTaskTimeMap.get(key);
	}

	@Override
	public void heartBeat() {
		long now = timeService.now();
		for (IHeartBeatTask task : tasks) {
			if (task == null) {
				continue;
			}
			if (task.isTimeUp(now)) {
				try {
					task.run();
				} catch (Exception e) {
					logger.error(String.format("%s run error", task.getClass()
							.getSimpleName()), e);
				}
				task.nextRound();
			}
		}
	}

}
