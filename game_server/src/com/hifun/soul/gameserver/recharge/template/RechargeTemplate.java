package com.hifun.soul.gameserver.recharge.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 充值模板
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class RechargeTemplate extends RechargeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
