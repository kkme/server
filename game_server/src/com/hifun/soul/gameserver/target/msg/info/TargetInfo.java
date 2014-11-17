package com.hifun.soul.gameserver.target.msg.info;

public class TargetInfo {
	private int targetId;
	private int targetIcon;
	private String targetName;
	private String targetDesc;
	private String targetTaskDesc;
	private int targetType;
	private int targetParam;
	private int rewardState;
	private TargetRewardInfo[] rewardInfos;

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public int getTargetIcon() {
		return targetIcon;
	}

	public void setTargetIcon(int targetIcon) {
		this.targetIcon = targetIcon;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetDesc() {
		return targetDesc;
	}

	public void setTargetDesc(String targetDesc) {
		this.targetDesc = targetDesc;
	}

	public String getTargetTaskDesc() {
		return targetTaskDesc;
	}

	public void setTargetTaskDesc(String targetTaskDesc) {
		this.targetTaskDesc = targetTaskDesc;
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public int getTargetParam() {
		return targetParam;
	}

	public void setTargetParam(int targetParam) {
		this.targetParam = targetParam;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}

	public TargetRewardInfo[] getRewardInfos() {
		return rewardInfos;
	}

	public void setRewardInfos(TargetRewardInfo[] rewardInfos) {
		this.rewardInfos = rewardInfos;
	}

}
