package com.hifun.soul.gameserver.yellowvip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 黄钻新手奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class YellowVipOnceRewardTemplateVO extends TemplateObject {

	/** 奖励物品 */
	@ExcelCellBinding(offset = 1)
	protected int itemId;


	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[奖励物品]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	

	@Override
	public String toString() {
		return "YellowVipOnceRewardTemplateVO[itemId=" + itemId + ",]";

	}
}