package com.hifun.soul.gameserver.recharge;

import com.hifun.soul.gameserver.vip.RechargeType;

/**
 * 充值信息类
 * 
 * @author yandajun
 * 
 */
public class Recharge {
	/** 充值数量 */
	private int rechargeNum;
	/** 充值类型 */
	private RechargeType rechargeType;

	public int getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(int rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public RechargeType getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(RechargeType rechargeType) {
		this.rechargeType = rechargeType;
	}

}
