package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 关卡奖励物品信息;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class StageRewardItemInfo {
	@BeanFieldNumber(number = 1)
	private int itemTemplateId;
	@BeanFieldNumber(number = 2)
	private int count;

	public int getItemTemplateId() {
		return itemTemplateId;
	}

	public void setItemTemplateId(int itemTemplateId) {
		this.itemTemplateId = itemTemplateId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
