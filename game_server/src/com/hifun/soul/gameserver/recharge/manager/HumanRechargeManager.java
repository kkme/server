package com.hifun.soul.gameserver.recharge.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanSingleRechargeRewardEntity;
import com.hifun.soul.gamedb.entity.HumanTotalRechargeRewardEntity;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.event.RechargeEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.recharge.FirstRechargeState;
import com.hifun.soul.gameserver.recharge.RechargeActivityState;
import com.hifun.soul.gameserver.recharge.RechargeActivityType;
import com.hifun.soul.gameserver.recharge.RechargeTemplateManager;
import com.hifun.soul.gameserver.recharge.WeekTotalRechargeRewardState;
import com.hifun.soul.gameserver.recharge.converter.RechargeRewardConverter;
import com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo;
import com.hifun.soul.gameserver.recharge.msg.GCGetFirstRechargeReward;
import com.hifun.soul.gameserver.recharge.msg.GCGetSingleRechargeReward;
import com.hifun.soul.gameserver.recharge.msg.GCGetTotalRechargeReward;
import com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo;
import com.hifun.soul.gameserver.recharge.msg.SingleRechargeRewardInfo;
import com.hifun.soul.gameserver.recharge.msg.TotalRechargeGradeInfo;
import com.hifun.soul.gameserver.recharge.msg.TotalRechargeRewardInfo;
import com.hifun.soul.gameserver.recharge.template.FirstRechargeTemplate;
import com.hifun.soul.gameserver.recharge.template.SingleRechargeReward;
import com.hifun.soul.gameserver.recharge.template.SingleRechargeTemplate;
import com.hifun.soul.gameserver.recharge.template.TotalRechargeReward;
import com.hifun.soul.gameserver.recharge.template.TotalRechargeTemplate;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.vip.RechargeType;
import com.hifun.soul.proto.data.entity.Entity.HumanSingleRechargeReward;
import com.hifun.soul.proto.data.entity.Entity.HumanTotalRechargeReward;

/**
 * 角色充值管理器
 * 
 * @author yandajun
 * 
 */
public class HumanRechargeManager implements IHumanPersistenceManager,
		ILoginManager, IEventListener, ICachableComponent {
	private Human human;
	private RechargeTemplateManager rechargeTemplateManager;
	/** 首充奖励 */
	private List<FirstRechargeRewardInfo> firstRechargeRewardList = new ArrayList<FirstRechargeRewardInfo>();
	/** 单笔充值奖励 */
	private Map<Integer, SingleRechargeGradeInfo> singleRechargeRewardMap = new TreeMap<Integer, SingleRechargeGradeInfo>();
	/** 单笔充值领奖数据:档位ID - 剩余领奖次数 */
	private Map<Integer, Integer> singleRewardNumMap = new HashMap<Integer, Integer>();
	/** 累计充值奖励 */
	private Map<Integer, TotalRechargeGradeInfo> totalRechargeRewardMap = new TreeMap<Integer, TotalRechargeGradeInfo>();

	/** 缓存 */
	private CacheEntry<Integer, IEntity> cache = new CacheEntry<Integer, IEntity>();

	public HumanRechargeManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
		this.human.registerPersistenceManager(this);
		rechargeTemplateManager = GameServerAssist.getRechargeTemplateManager();
		this.human.getEventBus().addListener(EventType.RECHARGE_EVENT, this);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 加载角色单笔充值领奖数据
		for (HumanSingleRechargeReward.Builder builder : humanEntity
				.getBuilder().getSingleRechargeRewardBuilderList()) {
			if (builder.getSingleRechargeReward().getGradeId() == 0) {
				continue;
			}
			singleRewardNumMap.put(builder.getSingleRechargeReward()
					.getGradeId(), builder.getSingleRechargeReward()
					.getRemainRewardNum());
		}
		// 加载角色累计充值领奖数据
		for (HumanTotalRechargeReward.Builder builder : humanEntity
				.getBuilder().getTotalRechargeRewardBuilderList()) {
			if (builder.getTotalRechargeReward().getGradeId() == 0) {
				continue;
			}
			TotalRechargeGradeInfo totalRechargeGradeInfo = new TotalRechargeGradeInfo();
			totalRechargeGradeInfo.setRemainRewardNum(builder
					.getTotalRechargeReward().getRemainRewardNum());
			totalRechargeGradeInfo.setRewardState(builder
					.getTotalRechargeReward().getRewardState());
			totalRechargeRewardMap.put(builder.getTotalRechargeReward()
					.getGradeId(), totalRechargeGradeInfo);
		}
	}

	@Override
	public void onLogin() {
		// 初始化首充奖励信息
		initFirstRechargeRewardInfo();
		// 初始化单笔充值奖励信息
		initSingleRechargeRewardInfo();
		// 初始化累计充值奖励信息
		initTotalRechargeRewardInfo();
	}

	/**
	 * 初始化首充奖励信息
	 */
	private void initFirstRechargeRewardInfo() {
		Map<Integer, FirstRechargeTemplate> firstRechargeTemplates = rechargeTemplateManager
				.getFirstRechargeRewards();
		for (Integer id : firstRechargeTemplates.keySet()) {
			FirstRechargeTemplate firstTemplate = firstRechargeTemplates
					.get(id);
			int itemId = firstTemplate.getItemId();
			int itemNum = firstTemplate.getItemNum();
			SimpleCommonItem commonItem = CommonItemBuilder
					.genSimpleCommonItem(itemId);
			if (commonItem == null) {
				continue;
			}
			FirstRechargeRewardInfo rewardInfo = new FirstRechargeRewardInfo();
			rewardInfo.setItemNum(itemNum);
			rewardInfo.setCommonItem(commonItem);
			firstRechargeRewardList.add(rewardInfo);
		}
	}

	/**
	 * 初始化单笔充值奖励信息
	 */
	private void initSingleRechargeRewardInfo() {
		Map<Integer, SingleRechargeTemplate> singleRechargeTemplates = rechargeTemplateManager
				.getSingleRechargeRewards();
		for (Integer gradeId : singleRechargeTemplates.keySet()) {
			SingleRechargeTemplate singleTemplate = singleRechargeTemplates
					.get(gradeId);
			SingleRechargeGradeInfo singleRechargeGradeInfo = new SingleRechargeGradeInfo();
			singleRechargeGradeInfo.setGradeId(gradeId);
			singleRechargeGradeInfo.setRechargeNum(singleTemplate
					.getRechargeNum());
			Integer remainRewardNum = singleRewardNumMap.get(gradeId);
			if (remainRewardNum == null) {
				remainRewardNum = 0;
			}
			singleRechargeGradeInfo.setRemainRewardNum(remainRewardNum);
			SingleRechargeReward[] singleRewards = singleTemplate
					.getRechargeRewards();
			SingleRechargeRewardInfo[] singleRewardInfos = new SingleRechargeRewardInfo[singleRewards.length];
			for (int i = 0; i < singleRewards.length; i++) {
				int itemId = singleRewards[i].getItemId();
				int itemNum = singleRewards[i].getItemNum();
				SimpleCommonItem commonItem = CommonItemBuilder
						.genSimpleCommonItem(itemId);
				if (commonItem == null) {
					continue;
				}
				SingleRechargeRewardInfo singleRewardInfo = new SingleRechargeRewardInfo();
				singleRewardInfo.setItemNum(itemNum);
				singleRewardInfo.setCommonItem(commonItem);
				singleRewardInfos[i] = singleRewardInfo;
			}
			singleRechargeGradeInfo.setRewardInfos(singleRewardInfos);
			singleRechargeRewardMap.put(gradeId, singleRechargeGradeInfo);
		}
	}

	/**
	 * 初始化累计充值奖励信息
	 */
	private void initTotalRechargeRewardInfo() {
		Map<Integer, TotalRechargeTemplate> totalRechargeTemplates = rechargeTemplateManager
				.getTotalRechargeRewards();
		for (Integer gradeId : totalRechargeTemplates.keySet()) {
			TotalRechargeTemplate totalTemplate = totalRechargeTemplates
					.get(gradeId);
			TotalRechargeGradeInfo totalRechargeGradeInfo = new TotalRechargeGradeInfo();
			totalRechargeGradeInfo.setGradeId(gradeId);
			totalRechargeGradeInfo.setRechargeNum(totalTemplate
					.getRechargeNum());
			// 库中的数据
			TotalRechargeGradeInfo gradeInfoData = totalRechargeRewardMap
					.get(gradeId);
			if (gradeInfoData == null) {
				totalRechargeGradeInfo.setRemainRewardNum(0);
				totalRechargeGradeInfo
						.setRewardState(WeekTotalRechargeRewardState.NOT_REACH
								.getIndex());
			} else {
				totalRechargeGradeInfo.setRemainRewardNum(gradeInfoData
						.getRemainRewardNum());
				totalRechargeGradeInfo.setRewardState(gradeInfoData
						.getRewardState());
			}

			TotalRechargeReward[] totalRewards = totalTemplate
					.getRechargeRewards();
			TotalRechargeRewardInfo[] totalRewardInfos = new TotalRechargeRewardInfo[totalRewards.length];
			for (int i = 0; i < totalRewards.length; i++) {
				int itemId = totalRewards[i].getItemId();
				int itemNum = totalRewards[i].getItemNum();
				SimpleCommonItem commonItem = CommonItemBuilder
						.genSimpleCommonItem(itemId);
				if (commonItem == null) {
					continue;
				}
				TotalRechargeRewardInfo totalRewardInfo = new TotalRechargeRewardInfo();
				totalRewardInfo.setItemNum(itemNum);
				totalRewardInfo.setCommonItem(commonItem);
				totalRewardInfos[i] = totalRewardInfo;
			}
			totalRechargeGradeInfo.setRewardInfos(totalRewardInfos);
			totalRechargeRewardMap.put(gradeId, totalRechargeGradeInfo);
		}
	}

	/**
	 * 获取首充奖励
	 */
	public List<FirstRechargeRewardInfo> getFirstRechargeRewardList() {
		return firstRechargeRewardList;
	}

	/**
	 * 是否有首充奖励
	 */
	public boolean hasFirsetRechargeReward() {
		return human.getFirstRechargeState() == FirstRechargeState.NO_REWARD
				.getIndex();
	}

	/**
	 * 领取首充奖励
	 */
	public void getFirstRechargeReward() {
		// 将奖励物品放到背包中
		for (FirstRechargeRewardInfo firstRewardInfo : firstRechargeRewardList) {
			for (int i = 0; i < firstRewardInfo.getItemNum(); i++) {
				SimpleCommonItem commonItem = firstRewardInfo.getCommonItem();
				Item item = ItemFactory.creatNewItem(human,
						commonItem.getItemId());
				if (!human.getBagManager().putItem(BagType.MAIN_BAG, item,
						ItemLogReason.FIRST_RECHARGE_REWARD, "")) {
					return;
				}
			}
		}
		// 设置首充状态
		human.setFirstRechargeState(FirstRechargeState.FINISHED.getIndex());
		// 发送消息
		GCGetFirstRechargeReward msg = new GCGetFirstRechargeReward();
		human.sendMessage(msg);
	}

	/**
	 * 获取单笔充值奖励
	 */
	public List<SingleRechargeGradeInfo> getSingleRechargeRewardList() {
		List<SingleRechargeGradeInfo> singleRechargeRewardList = new ArrayList<SingleRechargeGradeInfo>();
		for (int gradeId : singleRechargeRewardMap.keySet()) {
			singleRechargeRewardList.add(singleRechargeRewardMap.get(gradeId));
		}
		return singleRechargeRewardList;
	}

	/**
	 * 获取累计充值奖励
	 */
	public List<TotalRechargeGradeInfo> getTotalRechargeRewardList() {
		List<TotalRechargeGradeInfo> totalRechargeRewardList = new ArrayList<TotalRechargeGradeInfo>();
		for (int gradeId : totalRechargeRewardMap.keySet()) {
			totalRechargeRewardList.add(totalRechargeRewardMap.get(gradeId));
		}
		return totalRechargeRewardList;
	}

	/**
	 * 是否有单笔充值奖励
	 */
	public boolean hasSingleRechargeReward() {
		for (Integer gradeId : singleRechargeRewardMap.keySet()) {
			if (singleRechargeRewardMap.get(gradeId).getRemainRewardNum() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 领取某一档位单笔充值奖励
	 */
	public void getSingleRechargeReward(int gradeId) {
		// 将奖励物品放到背包中
		SingleRechargeGradeInfo singleGradeRewardInfo = singleRechargeRewardMap
				.get(gradeId);
		if (singleGradeRewardInfo == null) {
			return;
		}
		if (singleGradeRewardInfo.getRemainRewardNum() <= 0) {
			human.sendErrorMessage(LangConstants.SINGLE_RECHARGE_FINISHED);
			return;
		}
		for (SingleRechargeRewardInfo singleRewardInfo : singleGradeRewardInfo
				.getRewardInfos()) {
			SimpleCommonItem commonItem = singleRewardInfo.getCommonItem();
			if (!human.getBagManager().putItems(BagType.MAIN_BAG,
					commonItem.getItemId(), singleRewardInfo.getItemNum(),
					ItemLogReason.SINGLE_RECHARGE_REWARD, "")) {
				return;
			}
		}
		// 修改单笔充值领奖次数
		singleGradeRewardInfo.setRemainRewardNum(singleGradeRewardInfo
				.getRemainRewardNum() - 1);
		// 数据库缓存
		singleRewardNumMap.put(gradeId,
				singleGradeRewardInfo.getRemainRewardNum());
		HumanSingleRechargeRewardEntity entity = RechargeRewardConverter
				.toSingleRechargeReward(human.getHumanGuid(), gradeId,
						singleRewardNumMap.get(gradeId));
		cache.addUpdate(gradeId, entity);

		// 发送消息
		GCGetSingleRechargeReward msg = new GCGetSingleRechargeReward();
		human.sendMessage(msg);
	}

	/**
	 * 是否有累计充值奖励
	 */
	public boolean hasTotalRechargeReward() {
		for (Integer gradeId : totalRechargeRewardMap.keySet()) {
			if (totalRechargeRewardMap.get(gradeId).getRemainRewardNum() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 领取某一档位累计充值奖励
	 */
	public void getTotalRechargeReward(int gradeId) {
		// 将奖励物品放到背包中
		TotalRechargeGradeInfo totalGradeRewardInfo = totalRechargeRewardMap
				.get(gradeId);
		if (totalGradeRewardInfo == null) {
			return;
		}
		if (totalGradeRewardInfo.getRemainRewardNum() <= 0) {
			human.sendErrorMessage(LangConstants.TOTAL_RECHARGE_FINISHED);
			return;
		}
		for (TotalRechargeRewardInfo totalRewardInfo : totalGradeRewardInfo
				.getRewardInfos()) {
			SimpleCommonItem commonItem = totalRewardInfo.getCommonItem();
			if (!human.getBagManager().putItems(BagType.MAIN_BAG,
					commonItem.getItemId(), totalRewardInfo.getItemNum(),
					ItemLogReason.TOTAL_RECHARGE_REWARD, "")) {
				return;
			}
		}
		// 修改累计充值领奖次数
		totalGradeRewardInfo.setRemainRewardNum(totalGradeRewardInfo
				.getRemainRewardNum() - 1);
		// 修改周累充领奖状态
		totalGradeRewardInfo
				.setRewardState(WeekTotalRechargeRewardState.FINISHED
						.getIndex());
		// 数据库缓存
		HumanTotalRechargeRewardEntity entity = RechargeRewardConverter
				.toTotalRechargeReward(human.getHumanGuid(),
						totalGradeRewardInfo);
		cache.addUpdate(gradeId, entity);

		// 发送消息
		GCGetTotalRechargeReward msg = new GCGetTotalRechargeReward();
		human.sendMessage(msg);
	}

	/**
	 * 监听充值事件
	 */
	@Override
	public void onEvent(IEvent event) {
		if (event.getType() != EventType.RECHARGE_EVENT) {
			return;
		}
		RechargeEvent rechargeEvent = (RechargeEvent) event;
		// 只有正常充值才有活动
		if (rechargeEvent.getRecharge().getRechargeType() != RechargeType.NORMAL_RECHARGE) {
			return;
		}
		// 首充
		firstRecharge();
		// 单笔充值
		singleRecharge(rechargeEvent);
		// 累计充值
		totalRecharge(rechargeEvent);
	}

	/**
	 * 首充(正常充值次数为1次)
	 */
	private void firstRecharge() {
		if (human.getNormalRechargeTimes() <= 1) {
			human.setFirstRechargeState(FirstRechargeState.NO_REWARD.getIndex());
		}
	}

	/**
	 * 单笔充值(达到某一档充值金额，将该档位的领奖次数+1)
	 */
	private void singleRecharge(RechargeEvent rechargeEvent) {
		// 是否在活动时间范围内
		RechargeActivityState activityState = GameServerAssist
				.getRechargeTemplateManager().getRechargeActivityState(
						RechargeActivityType.SINGLE_RECHARGE);
		// 还没有到时间，或没有活动
		if (activityState != RechargeActivityState.IN_TIME) {
			return;
		}
		int rechargeNum = rechargeEvent.getRecharge().getRechargeNum();
		int reachGradeId = 0;
		for (int gradeId : singleRechargeRewardMap.keySet()) {
			SingleRechargeGradeInfo gradeInfo = singleRechargeRewardMap
					.get(gradeId);
			if (rechargeNum < gradeInfo.getRechargeNum()) {
				break;
			}
			reachGradeId = gradeId;
		}
		if (reachGradeId != 0) {
			SingleRechargeGradeInfo reachGradeInfo = singleRechargeRewardMap
					.get(reachGradeId);
			reachGradeInfo.setRemainRewardNum(reachGradeInfo
					.getRemainRewardNum() + 1);
			// 数据库缓存
			singleRewardNumMap.put(reachGradeId,
					reachGradeInfo.getRemainRewardNum());
			HumanSingleRechargeRewardEntity entity = RechargeRewardConverter
					.toSingleRechargeReward(human.getHumanGuid(), reachGradeId,
							singleRewardNumMap.get(reachGradeId));
			cache.addUpdate(reachGradeId, entity);
		}
	}

	/**
	 * 累计充值(一周累计充值达到某一充值金额，将满足档位的领奖次数+1)
	 */
	private void totalRecharge(RechargeEvent rechargeEvent) {
		// 是否在活动时间范围内
		RechargeActivityState activityState = GameServerAssist
				.getRechargeTemplateManager().getRechargeActivityState(
						RechargeActivityType.TOTAL_RECHARGE);
		if (activityState != RechargeActivityState.IN_TIME) {
			return;
		}
		int rechargeNum = rechargeEvent.getRecharge().getRechargeNum();
		human.setWeekTotalRechargeNum(human.getWeekTotalRechargeNum()
				+ rechargeNum);
		for (int gradeId : totalRechargeRewardMap.keySet()) {
			TotalRechargeGradeInfo gradeInfo = totalRechargeRewardMap
					.get(gradeId);
			if (human.getWeekTotalRechargeNum() >= gradeInfo.getRechargeNum()) {
				if (gradeInfo.getRewardState() == WeekTotalRechargeRewardState.NOT_REACH
						.getIndex()) {
					// 状态是未达到的，领奖次数+1，状态置为达到未领取
					gradeInfo
							.setRemainRewardNum(gradeInfo.getRemainRewardNum() + 1);
					gradeInfo
							.setRewardState(WeekTotalRechargeRewardState.NOT_GET
									.getIndex());
					// 数据库缓存
					HumanTotalRechargeRewardEntity entity = RechargeRewardConverter
							.toTotalRechargeReward(human.getHumanGuid(),
									gradeInfo);
					cache.addUpdate(gradeId, entity);
				}
			} else {
				break;
			}
		}
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 保存单笔充值领奖数据
		for (int gradeId : singleRewardNumMap.keySet()) {
			HumanSingleRechargeRewardEntity entity = RechargeRewardConverter
					.toSingleRechargeReward(human.getHumanGuid(), gradeId,
							singleRewardNumMap.get(gradeId));
			humanEntity.getBuilder().addSingleRechargeReward(
					entity.getBuilder());
		}
		// 保存累计充值领奖数据
		for (int gradeId : totalRechargeRewardMap.keySet()) {
			TotalRechargeGradeInfo totalRechargeGradeInfo = totalRechargeRewardMap
					.get(gradeId);
			HumanTotalRechargeRewardEntity entity = RechargeRewardConverter
					.toTotalRechargeReward(human.getHumanGuid(),
							totalRechargeGradeInfo);
			humanEntity.getBuilder()
					.addTotalRechargeReward(entity.getBuilder());
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

	public long getLastTotalRechargeWeeklyResetTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_WEEK_TOTAL_RECHARGE_DATA_TIME);
	}

	public void setLastTotalRechargeWeeklyResetTime(long lastResetTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_WEEK_TOTAL_RECHARGE_DATA_TIME,
				lastResetTime);
	}

	/**
	 * 重置每周累计充值数据
	 */
	public void resetTotalRechargeWeeklyData() {
		human.setWeekTotalRechargeNum(0);
		for (int gradeId : totalRechargeRewardMap.keySet()) {
			totalRechargeRewardMap.get(gradeId).setRewardState(
					WeekTotalRechargeRewardState.NOT_REACH.getIndex());
		}
	}
}
