package com.hifun.soul.gameserver.loginreward.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 登录特殊奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LoginSpecialRewardTemplateVO extends TemplateObject {

	/**  礼包Id */
	@ExcelCellBinding(offset = 1)
	protected int itemId;


	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 礼包Id]itemId的值不得小于0");
		}
		this.itemId = itemId;
	}
	

	@Override
	public String toString() {
		return "LoginSpecialRewardTemplateVO[itemId=" + itemId + ",]";

	}
}