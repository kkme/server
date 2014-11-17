package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;


/**
 * 
 * 宝石的属性
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class GemAttribute {

	@BeanFieldNumber(number = 1)
	private int propKey;
	
	@BeanFieldNumber(number = 2)
	private int propValue;
	
	@BeanFieldNumber(number = 3)
	private int propMaxValue;

	public int getPropKey() {
		return propKey;
	}

	public void setPropKey(int propKey) {
		this.propKey = propKey;
	}	

	public int getPropValue() {
		return propValue;
	}

	public void setPropValue(int propValue) {
		this.propValue = propValue;
	}

	public int getPropMaxValue() {
		return propMaxValue;
	}

	public void setPropMaxValue(int propMaxValue) {
		this.propMaxValue = propMaxValue;
	}
	
}
