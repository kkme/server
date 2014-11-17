package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 礼包中的物品信息
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class SpreeItemInfo {
	@BeanFieldNumber(number = 1)
	private int itemid;
	@BeanFieldNumber(number = 2)
	private int num;
	@BeanFieldNumber(number = 3)
	private int weight;
	
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
