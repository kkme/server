package com.hifun.soul.gameserver.mine.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 矿场消费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class MineConsumeTemplate extends MineConsumeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
