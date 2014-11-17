package com.hifun.soul.gameserver.human.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 购买体力值花费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class BuyEnergyCostTemplate extends BuyEnergyCostTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
