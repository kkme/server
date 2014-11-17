package com.hifun.soul.gameserver.recharge.msg;

/**
 * 单笔充值档位奖励信息
 * 
 * @author yandajun
 * 
 */
public class TotalRechargeGradeInfo {
	private int gradeId;
	private int rechargeNum;
	private TotalRechargeRewardInfo[] rewardInfos;
	private int remainRewardNum;
	private int rewardState;

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public int getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(int rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public TotalRechargeRewardInfo[] getRewardInfos() {
		return rewardInfos;
	}

	public void setRewardInfos(TotalRechargeRewardInfo[] rewardInfos) {
		this.rewardInfos = rewardInfos;
	}

	public int getRemainRewardNum() {
		return remainRewardNum;
	}

	public void setRemainRewardNum(int remainRewardNum) {
		this.remainRewardNum = remainRewardNum;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}

}
