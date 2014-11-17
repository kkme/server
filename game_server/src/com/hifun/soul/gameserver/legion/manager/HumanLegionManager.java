package com.hifun.soul.gameserver.legion.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanLegionTaskEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.HumanOffLineEvent;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.LegionTechnologyAmend;
import com.hifun.soul.gameserver.legion.converter.LegionTaskToEntityConverter;
import com.hifun.soul.gameserver.legion.enums.LegionTaskState;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.legion.info.HumanLegionTitleInfo;
import com.hifun.soul.gameserver.legion.info.LegionTaskInfo;
import com.hifun.soul.gameserver.legion.msg.GCSendHumanLegionTitleInfo;
import com.hifun.soul.gameserver.legion.template.LegionHonorTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTaskTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionTask;

public class HumanLegionManager implements IEventListener,
		IHumanPersistenceManager, ICachableComponent, ILoginManager,
		IHumanPropertiesLoadForBattle {
	private Human human;
	private Map<Integer, LegionTask> legionTaskMap = new HashMap<Integer, LegionTask>();
	private GlobalLegionManager globalLegionManager;
	private LegionTemplateManager legionTemplateManager;
	private CacheEntry<Integer, IEntity> cache = new CacheEntry<Integer, IEntity>();
	LegionTaskToEntityConverter taskConverter;

	public HumanLegionManager(Human human) {
		this.human = human;
		this.human.getEventBus().addListener(EventType.HUMAN_OFF_LINE_EVENT,
				this);
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		this.human.registerLoginManager(this);
		this.human.registerCachableManager(this);
		this.human.registerPersistenceManager(this);
		legionTemplateManager = GameServerAssist.getLegionTemplateManager();
		globalLegionManager = GameServerAssist.getGlobalLegionManager();
		taskConverter = new LegionTaskToEntityConverter(human);
	}

	@Override
	public void onLogin() {
		// 下发角色所在军团信息
		globalLegionManager.updateHumanLegionInfo(human);
		// 下发军团头衔消息
		sendHumanLegionTitleInfo();
		// 如果从来没有刷新过任务列表
		for (int taskId = 1; taskId <= SharedConstants.LEGION_TASK_NUM; taskId++) {
			LegionTask task = legionTaskMap.get(taskId);
			if (task == null) {
				task = new LegionTask();
				task.setTaskId(taskId);
				legionTaskMap.put(taskId, task);
				refreshTask(taskId, true);
			}
		}
		// 从来没有刷过主题
		if (human.getLegionTaskThemeType() == 0) {
			refreshTaskTheme();
		}
	}

	/**
	 * 下发军团头衔消息
	 */
	public void sendHumanLegionTitleInfo() {
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		int titleId = human.getLegionTitleId();
		if (titleId == 0) {
			return;
		}
		// 头衔信息
		LegionHonorTemplate titleTemplate = legionTemplateManager
				.getHonorTemplate(human.getLegionTitleId());
		GCSendHumanLegionTitleInfo msg = new GCSendHumanLegionTitleInfo();
		HumanLegionTitleInfo titleInfo = new HumanLegionTitleInfo();
		titleInfo.setTitleId(titleTemplate.getId());
		titleInfo.setTitelName(titleTemplate.getTitleName());
		titleInfo.setIsAllProperty(titleTemplate.isAllProperty());
		titleInfo.setPropertyId(titleTemplate.getPropertyId());
		titleInfo.setPropertyRate(titleTemplate.getAmendEffect());
		titleInfo.setValid(human.isLegionTitleValid());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		long endTime = human.getPropertyManager().getLongPropertyValue(
				HumanLongProperty.LEGION_TITLE_END_TIME);
		String endTimeDesc = format.format(new Date(endTime));
		titleInfo.setEndTime(endTimeDesc);
		msg.setHumanLegionTitleInfo(titleInfo);
		human.sendMessage(msg);
	}

	/**
	 * 重置每日数据
	 */
	public void resetDailyData() {
		// 冥想状态
		human.setIsLegionMeditationed(false);
		// 接受任务次数
		human.setLegionReceivedTaskNum(0);
		// 任务积分排行奖励领取状态
		human.setIsGodLegionTaskRankReward(false);
	}

	@Override
	public void onEvent(IEvent event) {
		// 角色离线事件
		if (event.getType() == EventType.HUMAN_OFF_LINE_EVENT) {
			HumanOffLineEvent offLineEvent = (HumanOffLineEvent) event;
			long time = offLineEvent.getOffLineTime();
			LegionMember legionMember = globalLegionManager
					.getLegionMember(human.getHumanGuid());
			if (legionMember != null) {
				legionMember.setOffLineTime(time);
				globalLegionManager.updateLegionMember(legionMember);
			}
		}
		// 角色升级事件
		else if (event.getType() == EventType.LEVEL_UP_EVENT) {
			LegionMember legionMember = globalLegionManager
					.getLegionMember(human.getHumanGuid());
			if (legionMember != null) {
				legionMember.setLevel(human.getLevel());
				globalLegionManager.updateLegionMember(legionMember);
			}

		}
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for (HumanLegionTask.Builder builder : humanEntity.getBuilder()
				.getLegionTaskBuilderList()) {
			LegionTask legionTask = new LegionTask();
			legionTask.setTaskId(builder.getLegionTask().getTaskId());
			legionTask.setTemplateId(builder.getLegionTask().getTemplateId());
			legionTask.setEndTime(builder.getLegionTask().getEndTime());
			legionTask.setHasReward(builder.getLegionTask().getHasReward());
			legionTask.setTaskState(builder.getLegionTask().getTaskState());
			legionTaskMap.put(builder.getLegionTask().getTaskId(), legionTask);
		}
		// 军团头衔属性加成
		int legionTitleId = human.getLegionTitleId();
		amendLegionTitleProperty(human.getPropertyManager(), legionTitleId);
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for (Integer taskId : legionTaskMap.keySet()) {
			LegionTask legionTask = legionTaskMap.get(taskId);
			HumanLegionTaskEntity legionTaskEntity = taskConverter
					.convert(legionTask);
			humanEntity.getBuilder().addLegionTask(
					legionTaskEntity.getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	public Map<Integer, LegionTask> getLegionTaskMap() {
		return legionTaskMap;
	}

	public LegionTask getLegionTask(int taskId) {
		return legionTaskMap.get(taskId);
	}

	public void updateLegionTask(LegionTask task) {
		cache.addUpdate(task.getTaskId(), taskConverter.convert(task));
	}

	/**
	 * 生成任务信息
	 */
	public LegionTaskInfo generateTaskInfo(LegionTask task) {
		LegionTaskTemplate template = GameServerAssist
				.getLegionTemplateManager().getTaskTemplate(
						task.getTemplateId());
		LegionTaskInfo info = new LegionTaskInfo();
		info.setTaskId(task.getTaskId());
		info.setTaskTheme(template.getShemeName());
		info.setIconId(template.getIconId());
		info.setColorId(template.getQuantityId());
		info.setRewardMedal(template.getRewardMedal());
		info.setRewardLegionExperience(template.getRewardLegionExperience());
		info.setHasTaskReward(task.isHasReward());
		info.setRemainTime((int) (task.getEndTime() - GameServerAssist
				.getSystemTimeService().now()));
		info.setNeedTime(template.getTaskTime());
		return info;
	}

	/**
	 * 刷新任务主题
	 */
	public void refreshTaskTheme() {
		human.setLegionTaskThemeType(RandomUtils.nextInt(globalLegionManager
				.getTaskThemeMap().size()) + 1);
	}

	/**
	 * 判断是否有可以刷新的任务
	 */
	public boolean hasCanRefreshTask() {
		for (LegionTask task : legionTaskMap.values()) {
			if (task.getTaskState() == LegionTaskState.END.getIndex()
					&& !task.isHasReward()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 刷新任务列表
	 */
	public void refreshTaskList(boolean isSystem) {
		for (int taskId = 1; taskId <= SharedConstants.LEGION_TASK_NUM; taskId++) {
			LegionTask task = legionTaskMap.get(taskId);
			if (task == null) {
				task = new LegionTask();
				task.setTaskId(taskId);
				legionTaskMap.put(taskId, task);
				refreshTask(taskId, isSystem);
			} else {
				// 只有任务已结束,并且奖励已领取,才刷新
				if (task.getTaskState() == LegionTaskState.END.getIndex()
						&& !task.isHasReward()) {
					refreshTask(taskId, isSystem);
				}
			}
		}
	}

	/**
	 * 刷新某一个任务
	 */
	public void refreshTask(int taskId, boolean system) {
		List<LegionTaskTemplate> templateList = legionTemplateManager
				.getTaskTemplateList();
		List<Integer> weights = new ArrayList<Integer>();
		for (LegionTaskTemplate template : templateList) {
			if (system) {
				weights.add(template.getSystemRefreshWeight());
			} else {
				weights.add(template.getCrystalRefreshWeight());
			}
		}
		int[] indexs = MathUtils.getRandomUniqueIndex(weights, 1);
		if (indexs != null) {
			for (int index : indexs) {
				LegionTaskTemplate taskTemplate = templateList.get(index);
				LegionTask legionTask = legionTaskMap.get(taskId);
				legionTask.setTemplateId(taskTemplate.getId());
				legionTask.setTaskState(LegionTaskState.END.getIndex());
				legionTask.setHasReward(false);
				legionTask.setEndTime(GameServerAssist.getSystemTimeService()
						.now());
				updateLegionTask(legionTask);
			}
		}
	}

	/**
	 * 系统心跳：军团任务到时结束
	 */
	public void endTaskList() {
		for (Integer taskId : legionTaskMap.keySet()) {
			LegionTask task = legionTaskMap.get(taskId);
			long now = GameServerAssist.getSystemTimeService().now();
			if (task.getEndTime() <= now
					&& task.getTaskState() == LegionTaskState.UNDERWAY
							.getIndex()) {
				task.setEndTime(now);
				task.setTaskState(LegionTaskState.END.getIndex());
				task.setHasReward(true);
			}
		}
	}

	public void setLastResetDailyDataTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_LEGION_DATA_TIME, lastTime);
	}

	public long getLastResetDailyDataTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_LEGION_DATA_TIME);
	}

	public void setLastRefreshTaskTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_REFRESH_LEGION_TASK_TIME, lastTime);
	}

	public long getLastRefreshTaskTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_REFRESH_LEGION_TASK_TIME);
	}

	/**
	 * 获得军团科技收益加成
	 */
	public int getLegionTechnologyAmend(
			LegionTechnologyType legionTechnologyType, int oldValue) {
		LegionTechnologyAmend legionTechnologyAmend = globalLegionManager
				.getTechnologyAmend(human.getHumanGuid(), legionTechnologyType);
		if (legionTechnologyAmend != null) {
			if (legionTechnologyAmend.getAmendMethod() == AmendMethod.ADD) {
				oldValue += legionTechnologyAmend.getAmendValue();
			} else if (legionTechnologyAmend.getAmendMethod() == AmendMethod.MULIPLY) {
				oldValue += oldValue * legionTechnologyAmend.getAmendValue()
						/ SharedConstants.DEFAULT_FLOAT_BASE;
			}
		}
		return oldValue;
	}

	/**
	 * 战斗，军团头衔属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		int legionTitleId = propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.LEGION_TITLE_ID);
		amendLegionTitleProperty(propertyManager, legionTitleId);
	}

	/**
	 * 军团头衔属性加成
	 */
	public void amendLegionTitleProperty(HumanPropertyManager propertyManager,
			int legionTitleId) {
		if (legionTitleId == 0) {
			return;
		}
		LegionHonorTemplate titleTemplate = GameServerAssist
				.getLegionTemplateManager().getHonorTemplate(legionTitleId);
		if (titleTemplate == null) {
			return;
		}
		int propertyId = titleTemplate.getPropertyId();
		int propertyValue = titleTemplate.getAmendEffect();
		AmendMethod amendType = AmendMethod.valueOf(titleTemplate
				.getAmendMethod());
		human.getPropertyManager().amendProperty(human, amendType, propertyId,
				propertyValue, PropertyLogReason.LEGION_TITLE, "");
	}
}
