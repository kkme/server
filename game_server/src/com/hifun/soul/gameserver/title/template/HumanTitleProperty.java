package com.hifun.soul.gameserver.title.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 军衔获得的属性加成
 * 
 * @author yandajun
 * 
 */
@ExcelRowBinding
public class HumanTitleProperty {
	@BeanFieldNumber(number = 1)
	private int propertyId;
	@BeanFieldNumber(number = 2)
	private int amendType;
	@BeanFieldNumber(number = 3)
	private int propertyValue;

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getAmendType() {
		return amendType;
	}

	public void setAmendType(int amendType) {
		this.amendType = amendType;
	}

	public int getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}

}
