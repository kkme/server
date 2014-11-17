package com.hifun.soul.gameserver.gem.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
/**
 * 宝石合成模板
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class GemUpgradeTemplate extends GemUpgradeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {

	}

}
