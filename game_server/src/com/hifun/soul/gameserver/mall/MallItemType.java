package com.hifun.soul.gameserver.mall;

import com.hifun.soul.core.enums.IndexedEnum;


/**
 * 
 * 商城中物品的类型，跟商城面板页签对应
 * 
 * @author magicstone
 *
 */
public enum MallItemType implements IndexedEnum{
	/** 所有 */
	ALL_ITEM(0),
	/** 材料 */
	EQUIP_ITEM(1),
	/** 常用道具 */
	COMMON_ITEM(2),
	/** 限时 */
	LIMIT_ITEM(3),
	/** 新品 */
	NEW_ITEM(4),
	/** 建筑材料 */
	MATERIAL_ITEM(5),
	/** 打折 */
	DISCOUNT_ITEM(6);


	private int index;
	
	private MallItemType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
