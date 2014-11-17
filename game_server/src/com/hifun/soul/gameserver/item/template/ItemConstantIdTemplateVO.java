package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 道具常量模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ItemConstantIdTemplateVO extends TemplateObject {

	/** 物品id */
	@ExcelCellBinding(offset = 1)
	protected int itemId;


	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[物品id]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	

	@Override
	public String toString() {
		return "ItemConstantIdTemplateVO[itemId=" + itemId + ",]";

	}
}