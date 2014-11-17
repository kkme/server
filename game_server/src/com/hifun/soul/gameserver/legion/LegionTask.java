package com.hifun.soul.gameserver.legion;

/**
 * 军团任务
 * 
 * @author yandajun
 * 
 */
public class LegionTask {

	private int taskId;
	private int templateId;
	private long endTime;
	private boolean hasReward;
	private int taskState;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public boolean isHasReward() {
		return hasReward;
	}

	public void setHasReward(boolean hasReward) {
		this.hasReward = hasReward;
	}

	public int getTaskState() {
		return taskState;
	}

	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}
}
