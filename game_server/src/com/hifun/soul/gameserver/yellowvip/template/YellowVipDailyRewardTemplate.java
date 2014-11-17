package com.hifun.soul.gameserver.yellowvip.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 黄钻每日奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class YellowVipDailyRewardTemplate extends YellowVipDailyRewardTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
