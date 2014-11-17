package com.hifun.soul.gameserver.predict.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 预见加成属性
 * 
 * @author yandajun
 * 
 */
@ExcelRowBinding
public class PredictAttribute {
	@BeanFieldNumber(number = 1)
	private int propertyId;
	@BeanFieldNumber(number = 2)
	private int propertyValue;

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}
}
