package com.hifun.soul.gameserver.bag.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 主背包升级花费模板
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class MainBagUpgradeTemplate extends MainBagUpgradeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
