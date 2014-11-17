package com.hifun.soul.gameserver.gift.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 天赋升级模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class GiftLevelTemplateVO extends TemplateObject {

	/** 天赋ID */
	@ExcelCellBinding(offset = 1)
	protected int giftId;

	/** 天赋等级 */
	@ExcelCellBinding(offset = 2)
	protected int giftLevel;

	/** 升到下级需要人物等级 */
	@ExcelCellBinding(offset = 3)
	protected int needLevel;

	/** 属性ID */
	@ExcelCellBinding(offset = 4)
	protected int propertyId;

	/** 属性值 */
	@ExcelCellBinding(offset = 5)
	protected int propertyValue;

	/** 加成方式 */
	@ExcelCellBinding(offset = 6)
	protected int amendMethod;

	/** 升到下级消耗天赋点 */
	@ExcelCellBinding(offset = 7)
	protected int costGiftPoint;

	/** 升到下级消耗道具ID */
	@ExcelCellBinding(offset = 8)
	protected int costItemId;

	/** 升到下级消耗道具数量 */
	@ExcelCellBinding(offset = 9)
	protected int costItemNum;


	public int getGiftId() {
		return this.giftId;
	}

	public void setGiftId(int giftId) {
		if (giftId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[天赋ID]giftId不可以为0");
		}
		this.giftId = giftId;
	}
	
	public int getGiftLevel() {
		return this.giftLevel;
	}

	public void setGiftLevel(int giftLevel) {
		if (giftLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[天赋等级]giftLevel的值不得小于0");
		}
		this.giftLevel = giftLevel;
	}
	
	public int getNeedLevel() {
		return this.needLevel;
	}

	public void setNeedLevel(int needLevel) {
		if (needLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[升到下级需要人物等级]needLevel的值不得小于0");
		}
		this.needLevel = needLevel;
	}
	
	public int getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(int propertyId) {
		if (propertyId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[属性ID]propertyId的值不得小于0");
		}
		this.propertyId = propertyId;
	}
	
	public int getPropertyValue() {
		return this.propertyValue;
	}

	public void setPropertyValue(int propertyValue) {
		if (propertyValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[属性值]propertyValue的值不得小于0");
		}
		this.propertyValue = propertyValue;
	}
	
	public int getAmendMethod() {
		return this.amendMethod;
	}

	public void setAmendMethod(int amendMethod) {
		if (amendMethod < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[加成方式]amendMethod的值不得小于0");
		}
		this.amendMethod = amendMethod;
	}
	
	public int getCostGiftPoint() {
		return this.costGiftPoint;
	}

	public void setCostGiftPoint(int costGiftPoint) {
		if (costGiftPoint < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[升到下级消耗天赋点]costGiftPoint的值不得小于0");
		}
		this.costGiftPoint = costGiftPoint;
	}
	
	public int getCostItemId() {
		return this.costItemId;
	}

	public void setCostItemId(int costItemId) {
		if (costItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[升到下级消耗道具ID]costItemId不可以为0");
		}
		this.costItemId = costItemId;
	}
	
	public int getCostItemNum() {
		return this.costItemNum;
	}

	public void setCostItemNum(int costItemNum) {
		if (costItemNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[升到下级消耗道具数量]costItemNum的值不得小于0");
		}
		this.costItemNum = costItemNum;
	}
	

	@Override
	public String toString() {
		return "GiftLevelTemplateVO[giftId=" + giftId + ",giftLevel=" + giftLevel + ",needLevel=" + needLevel + ",propertyId=" + propertyId + ",propertyValue=" + propertyValue + ",amendMethod=" + amendMethod + ",costGiftPoint=" + costGiftPoint + ",costItemId=" + costItemId + ",costItemNum=" + costItemNum + ",]";

	}
}