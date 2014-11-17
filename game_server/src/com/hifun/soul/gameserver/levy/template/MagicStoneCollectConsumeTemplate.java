package com.hifun.soul.gameserver.levy.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 收集魔法石花费模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class MagicStoneCollectConsumeTemplate extends MagicStoneCollectConsumeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
	}

}
