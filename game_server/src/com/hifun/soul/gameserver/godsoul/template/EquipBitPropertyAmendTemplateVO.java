package com.hifun.soul.gameserver.godsoul.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 神魄装备位属性加成模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EquipBitPropertyAmendTemplateVO extends TemplateObject {

	/** 装备位类型 */
	@ExcelCellBinding(offset = 1)
	protected int equipBitType;

	/** 加成比例 */
	@ExcelCellBinding(offset = 2)
	protected int amendValue;


	public int getEquipBitType() {
		return this.equipBitType;
	}

	public void setEquipBitType(int equipBitType) {
		if (equipBitType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[装备位类型]equipBitType不可以为0");
		}
		this.equipBitType = equipBitType;
	}
	
	public int getAmendValue() {
		return this.amendValue;
	}

	public void setAmendValue(int amendValue) {
		if (amendValue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[加成比例]amendValue不可以为0");
		}
		this.amendValue = amendValue;
	}
	

	@Override
	public String toString() {
		return "EquipBitPropertyAmendTemplateVO[equipBitType=" + equipBitType + ",amendValue=" + amendValue + ",]";

	}
}