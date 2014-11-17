package com.hifun.soul.gameserver.recharge.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 单笔充值奖励物品模板
 * 
 * @author yandajun
 * 
 */
@ExcelRowBinding
public class SingleRechargeReward {
	/** 物品ID */
	@BeanFieldNumber(number = 1)
	private int itemId;
	/** 物品数量 */
	@BeanFieldNumber(number = 2)
	private int itemNum;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

}
