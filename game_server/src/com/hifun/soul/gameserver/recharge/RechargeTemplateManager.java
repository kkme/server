package com.hifun.soul.gameserver.recharge;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.recharge.template.FirstRechargeTemplate;
import com.hifun.soul.gameserver.recharge.template.RechargeActivityTimeTemplate;
import com.hifun.soul.gameserver.recharge.template.RechargeTemplate;
import com.hifun.soul.gameserver.recharge.template.SingleRechargeTemplate;
import com.hifun.soul.gameserver.recharge.template.TotalRechargeTemplate;

/**
 * 充值管理器
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class RechargeTemplateManager implements IInitializeRequired {
	private static Logger logger = Loggers.RECHARGE_ACTIVITY_LOGGER;
	private List<RechargeTemplate> rechargeTemplates = new ArrayList<RechargeTemplate>();
	/** 充值活动时间模板 */
	private Map<Integer, RechargeActivityTimeTemplate> rechargeActivityTemplates = new HashMap<Integer, RechargeActivityTimeTemplate>();
	/** 首充奖励模板 */
	private Map<Integer, FirstRechargeTemplate> firstRechargeTemplates = new HashMap<Integer, FirstRechargeTemplate>();
	/** 单笔充值奖励模板 */
	private Map<Integer, SingleRechargeTemplate> singleRechargeTemplates = new HashMap<Integer, SingleRechargeTemplate>();
	/** 累计充值奖励模板 */
	private Map<Integer, TotalRechargeTemplate> totalRechargeTemplates = new HashMap<Integer, TotalRechargeTemplate>();

	@Autowired
	private TemplateService templateService;

	@Override
	public void init() {
		Map<Integer, RechargeTemplate> templates = templateService
				.getAll(RechargeTemplate.class);
		rechargeActivityTemplates = templateService
				.getAll(RechargeActivityTimeTemplate.class);
		firstRechargeTemplates = templateService
				.getAll(FirstRechargeTemplate.class);
		singleRechargeTemplates = templateService
				.getAll(SingleRechargeTemplate.class);
		totalRechargeTemplates = templateService
				.getAll(TotalRechargeTemplate.class);
		for (Integer id : templates.keySet()) {
			rechargeTemplates.add(templates.get(id));
		}
		Collections.sort(rechargeTemplates, new Comparator<RechargeTemplate>() {
			@Override
			public int compare(RechargeTemplate o1, RechargeTemplate o2) {
				return o1.getRechargeNum() < o2.getRechargeNum() ? -1 : (o1
						.getRechargeNum() == o2.getRechargeNum() ? 0 : 1);
			}
		});
	}

	/**
	 * 根据充值数量获取对应的充值奖励
	 * 
	 * @param rechargeNum
	 * @return
	 */
	public int getRechargeReward(int rechargeNum) {
		int reward = 0;
		for (RechargeTemplate template : rechargeTemplates) {
			if (template.getRechargeNum() <= rechargeNum) {
				reward = template.getRewardNum();
			} else {
				break;
			}
		}
		return reward;
	}

	/**
	 * 获得首充奖励模板
	 * 
	 * @return
	 */
	public Map<Integer, FirstRechargeTemplate> getFirstRechargeRewards() {
		return firstRechargeTemplates;
	}

	/**
	 * 获得单笔充值奖励模板
	 * 
	 * @return
	 */
	public Map<Integer, SingleRechargeTemplate> getSingleRechargeRewards() {
		return singleRechargeTemplates;
	}

	/**
	 * 获得累计充值奖励模板
	 * 
	 * @return
	 */
	public Map<Integer, TotalRechargeTemplate> getTotalRechargeRewards() {
		return totalRechargeTemplates;
	}

	/**
	 * 获得充值活动时间模板
	 * 
	 */
	public RechargeActivityTimeTemplate getRechargeActivityTimeTemplate(
			RechargeActivityType rechargeActivityType) {
		return rechargeActivityTemplates.get(rechargeActivityType.getIndex());
	}

	/**
	 * 充值活动状态
	 */
	public RechargeActivityState getRechargeActivityState(
			RechargeActivityType rechargeActivityType) {
		RechargeActivityTimeTemplate template = getRechargeActivityTimeTemplate(rechargeActivityType);
		if (template == null) {
			return RechargeActivityState.NO_ACTIVITY;
		}
		String startDate = template.getStartDate();
		String endDate = template.getEndDate();
		try {
			long startTime = TimeUtils.getYMDTime(startDate);
			long endTime = TimeUtils.getYMDTime(endDate) + TimeUtils.DAY - 1;
			if (endTime < startTime) {
				logger.error("recharge activity config is wrong, the order of start date and end date is illegal. [id="
						+ RechargeActivityType.SINGLE_RECHARGE.getIndex() + "]");
			}
			long now = System.currentTimeMillis();
			// 还没有到时间，活动不显示
			if (now < startTime) {
				return RechargeActivityState.PRE_TIME;
			} else if (now >= startTime && now <= endTime) {
				return RechargeActivityState.IN_TIME;
			} else {
				return RechargeActivityState.OUT_TIME;
			}
		} catch (ParseException e) {
			logger.error(
					"recharge activity config is wrong, the date format is illegal [id="
							+ RechargeActivityType.SINGLE_RECHARGE.getIndex()
							+ "]", e);
			e.printStackTrace();
		}
		return RechargeActivityState.NO_ACTIVITY;
	}
}
