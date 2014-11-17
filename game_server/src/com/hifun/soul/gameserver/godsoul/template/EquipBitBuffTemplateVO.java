package com.hifun.soul.gameserver.godsoul.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 神魄装备位buff模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EquipBitBuffTemplateVO extends TemplateObject {

	/** 需要装备位同时强化等级 */
	@ExcelCellBinding(offset = 1)
	protected int needEquipBitLevel;

	/** 属性ID */
	@ExcelCellBinding(offset = 2)
	protected int propertyId;

	/** 加成效果 */
	@ExcelCellBinding(offset = 3)
	protected int amendEffect;

	/** 加成方式 */
	@ExcelCellBinding(offset = 4)
	protected int amendMethod;


	public int getNeedEquipBitLevel() {
		return this.needEquipBitLevel;
	}

	public void setNeedEquipBitLevel(int needEquipBitLevel) {
		if (needEquipBitLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[需要装备位同时强化等级]needEquipBitLevel不可以为0");
		}
		this.needEquipBitLevel = needEquipBitLevel;
	}
	
	public int getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(int propertyId) {
		if (propertyId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[属性ID]propertyId不可以为0");
		}
		this.propertyId = propertyId;
	}
	
	public int getAmendEffect() {
		return this.amendEffect;
	}

	public void setAmendEffect(int amendEffect) {
		if (amendEffect == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[加成效果]amendEffect不可以为0");
		}
		this.amendEffect = amendEffect;
	}
	
	public int getAmendMethod() {
		return this.amendMethod;
	}

	public void setAmendMethod(int amendMethod) {
		if (amendMethod == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[加成方式]amendMethod不可以为0");
		}
		this.amendMethod = amendMethod;
	}
	

	@Override
	public String toString() {
		return "EquipBitBuffTemplateVO[needEquipBitLevel=" + needEquipBitLevel + ",propertyId=" + propertyId + ",amendEffect=" + amendEffect + ",amendMethod=" + amendMethod + ",]";

	}
}