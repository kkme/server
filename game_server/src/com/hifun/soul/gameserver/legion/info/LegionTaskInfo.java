package com.hifun.soul.gameserver.legion.info;

/**
 * 军团任务信息
 * 
 * @author yandajun
 * 
 */
public class LegionTaskInfo {
	private int taskId;
	private String taskTheme;
	private int iconId;
	private int colorId;
	private int rewardMedal;
	private int rewardLegionExperience;
	private boolean hasTaskReward;
	private int remainTime;
	private int needTime;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskTheme() {
		return taskTheme;
	}

	public void setTaskTheme(String taskTheme) {
		this.taskTheme = taskTheme;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public int getRewardMedal() {
		return rewardMedal;
	}

	public void setRewardMedal(int rewardMedal) {
		this.rewardMedal = rewardMedal;
	}

	public int getRewardLegionExperience() {
		return rewardLegionExperience;
	}

	public void setRewardLegionExperience(int rewardLegionExperience) {
		this.rewardLegionExperience = rewardLegionExperience;
	}

	public boolean getHasTaskReward() {
		return hasTaskReward;
	}

	public void setHasTaskReward(boolean hasTaskReward) {
		this.hasTaskReward = hasTaskReward;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public int getNeedTime() {
		return needTime;
	}

	public void setNeedTime(int needTime) {
		this.needTime = needTime;
	}
}
