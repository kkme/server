package com.hifun.soul.gameserver.mine.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 矿坑出现概率模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class MineFieldRateTemplate extends MineFieldRateTemplateVO {

	@Override
	public void check() throws TemplateConfigException {

	}

}
