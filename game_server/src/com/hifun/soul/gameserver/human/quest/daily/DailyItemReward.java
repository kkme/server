package com.hifun.soul.gameserver.human.quest.daily;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 日常任务物品奖励;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class DailyItemReward {
	/** 奖励物品ID */
	@BeanFieldNumber(number = 1)
	private int itemId;
	/** 奖励物品数量 */
	@BeanFieldNumber(number = 2)
	private int itemCount;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

}
