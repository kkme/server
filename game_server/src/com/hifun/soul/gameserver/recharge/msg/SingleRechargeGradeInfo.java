package com.hifun.soul.gameserver.recharge.msg;

/**
 * 单笔充值档位奖励信息
 * 
 * @author yandajun
 * 
 */
public class SingleRechargeGradeInfo {
	private int gradeId;
	private int rechargeNum;
	private SingleRechargeRewardInfo[] rewardInfos;
	private int remainRewardNum;

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

	public SingleRechargeRewardInfo[] getRewardInfos() {
		return rewardInfos;
	}

	public void setRewardInfos(SingleRechargeRewardInfo[] rewardInfos) {
		this.rewardInfos = rewardInfos;
	}

	public int getRemainRewardNum() {
		return remainRewardNum;
	}

	public void setRemainRewardNum(int remainRewardNum) {
		this.remainRewardNum = remainRewardNum;
	}

}
