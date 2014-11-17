package com.hifun.soul.gameserver.matchbattle.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
/**
 * 匹配战配置模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class MatchBattleConfigTemplate extends MatchBattleConfigTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
