package com.hifun.soul.gameserver.vip.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 魔晶抽奖次数消耗模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class TurntableCostTemplate extends TurntableCostTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
