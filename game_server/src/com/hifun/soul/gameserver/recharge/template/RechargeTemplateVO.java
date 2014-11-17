package com.hifun.soul.gameserver.recharge.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 充值奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RechargeTemplateVO extends TemplateObject {

	/** 充值的魔晶数 */
	@ExcelCellBinding(offset = 1)
	protected int rechargeNum;

	/** 奖励魔晶数 */
	@ExcelCellBinding(offset = 2)
	protected int rewardNum;


	public int getRechargeNum() {
		return this.rechargeNum;
	}

	public void setRechargeNum(int rechargeNum) {
		if (rechargeNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[充值的魔晶数]rechargeNum不可以为0");
		}
		this.rechargeNum = rechargeNum;
	}
	
	public int getRewardNum() {
		return this.rewardNum;
	}

	public void setRewardNum(int rewardNum) {
		if (rewardNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[奖励魔晶数]rewardNum的值不得小于0");
		}
		this.rewardNum = rewardNum;
	}
	

	@Override
	public String toString() {
		return "RechargeTemplateVO[rechargeNum=" + rechargeNum + ",rewardNum=" + rewardNum + ",]";

	}
}