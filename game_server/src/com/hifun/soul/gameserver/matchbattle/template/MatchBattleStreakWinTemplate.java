package com.hifun.soul.gameserver.matchbattle.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 匹配战连胜奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class MatchBattleStreakWinTemplate extends MatchBattleStreakWinTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
	
	}

}
