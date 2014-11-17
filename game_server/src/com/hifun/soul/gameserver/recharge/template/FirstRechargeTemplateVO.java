package com.hifun.soul.gameserver.recharge.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 首充奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FirstRechargeTemplateVO extends TemplateObject {

	/** 物品ID */
	@ExcelCellBinding(offset = 1)
	protected int itemId;

	/** 物品数量 */
	@ExcelCellBinding(offset = 2)
	protected int itemNum;


	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[物品ID]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(int itemNum) {
		if (itemNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[物品数量]itemNum不可以为0");
		}
		this.itemNum = itemNum;
	}
	

	@Override
	public String toString() {
		return "FirstRechargeTemplateVO[itemId=" + itemId + ",itemNum=" + itemNum + ",]";

	}
}