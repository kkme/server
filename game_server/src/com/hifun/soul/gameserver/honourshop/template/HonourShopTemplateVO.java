package com.hifun.soul.gameserver.honourshop.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 荣誉商店
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HonourShopTemplateVO extends TemplateObject {

	/**  道具ID */
	@ExcelCellBinding(offset = 1)
	protected int itemId;

	/**  购买需要荣誉 */
	@ExcelCellBinding(offset = 2)
	protected int needHonour;

	/**  分页签 */
	@ExcelCellBinding(offset = 3)
	protected int itemType;

	/** 可见等级 */
	@ExcelCellBinding(offset = 4)
	protected int visibleLevel;


	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 道具ID]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public int getNeedHonour() {
		return this.needHonour;
	}

	public void setNeedHonour(int needHonour) {
		if (needHonour < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 购买需要荣誉]needHonour的值不得小于0");
		}
		this.needHonour = needHonour;
	}
	
	public int getItemType() {
		return this.itemType;
	}

	public void setItemType(int itemType) {
		if (itemType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 分页签]itemType的值不得小于0");
		}
		this.itemType = itemType;
	}
	
	public int getVisibleLevel() {
		return this.visibleLevel;
	}

	public void setVisibleLevel(int visibleLevel) {
		if (visibleLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[可见等级]visibleLevel的值不得小于0");
		}
		this.visibleLevel = visibleLevel;
	}
	

	@Override
	public String toString() {
		return "HonourShopTemplateVO[itemId=" + itemId + ",needHonour=" + needHonour + ",itemType=" + itemType + ",visibleLevel=" + visibleLevel + ",]";

	}
}