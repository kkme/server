package com.hifun.soul.gameserver.human.quest.daily;

import java.util.ArrayList;
import java.util.List;

public class DailyScoreRewardInfo {
	private int boxId;
	private int state;
	/** 积分限制 */
	protected int scoreLimit;

	/** 日常奖励金币 */
	protected int rewardMoney;
	
	/** 宝箱tip */
	private String tip;

	/** 日常任务物品奖励列表 */
	protected List<com.hifun.soul.gameserver.human.quest.daily.DailyItemReward> dailyRewardItems;

	public int getScoreLimit() {
		return this.scoreLimit;
	}

	public void setScoreLimit(int scoreLimit) {

		this.scoreLimit = scoreLimit;
	}

	public int getRewardMoney() {
		return this.rewardMoney;
	}

	public void setRewardMoney(int rewardMoney) {

		this.rewardMoney = rewardMoney;
	}

	public DailyItemReward[] getDailyRewardItems() {
		return this.dailyRewardItems.toArray(new DailyItemReward[0]);
	}

	public void setDailyRewardItems(DailyItemReward[] dailyRewardItems) {
		List<DailyItemReward> result = new ArrayList<DailyItemReward>();
		for (DailyItemReward reward : dailyRewardItems) {
			result.add(reward);
		}
		this.dailyRewardItems = result;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
