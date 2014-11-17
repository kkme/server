package com.hifun.soul.gameserver.recharge.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;

/**
 * 累计充值奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class TotalRechargeTemplateVO extends TemplateObject {

	/** 充值金额 */
	@ExcelCellBinding(offset = 1)
	protected int rechargeNum;

	/** 充值奖励物品 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.recharge.template.TotalRechargeReward[].class, collectionNumber = "2,3;4,5;6,7;8,9")
	protected com.hifun.soul.gameserver.recharge.template.TotalRechargeReward[] rechargeRewards;


	public int getRechargeNum() {
		return this.rechargeNum;
	}

	public void setRechargeNum(int rechargeNum) {
		if (rechargeNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[充值金额]rechargeNum不可以为0");
		}
		this.rechargeNum = rechargeNum;
	}
	
	public com.hifun.soul.gameserver.recharge.template.TotalRechargeReward[] getRechargeRewards() {
		return this.rechargeRewards;
	}

	public void setRechargeRewards(com.hifun.soul.gameserver.recharge.template.TotalRechargeReward[] rechargeRewards) {
		if (rechargeRewards == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[充值奖励物品]rechargeRewards不可以为空");
		}	
		this.rechargeRewards = rechargeRewards;
	}
	

	@Override
	public String toString() {
		return "TotalRechargeTemplateVO[rechargeNum=" + rechargeNum + ",rechargeRewards=" + rechargeRewards + ",]";

	}
}