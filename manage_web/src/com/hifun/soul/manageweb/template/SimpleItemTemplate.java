package com.hifun.soul.manageweb.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 
 * 消耗品模版
 * 
 * @author magicstone
 * 
 */
@ExcelRowBinding
public class SimpleItemTemplate extends SimpleItemTemplateVO {

	@Override
	public void check() throws TemplateConfigException {

	}
	
}
