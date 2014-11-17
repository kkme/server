package com.hifun.soul.gameserver.turntable.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

@ExcelRowBinding
public class ItemRateData {

	/** 物品id */
	@BeanFieldNumber(number = 1)
	private int itemId;
	/** 物品概率*/
	@BeanFieldNumber(number = 2)
	private int rate;
	/** 索引 */
	private int index;
	/** 是否选中 */
	private boolean isSelected;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
