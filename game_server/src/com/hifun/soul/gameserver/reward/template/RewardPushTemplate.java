package com.hifun.soul.gameserver.reward.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 奖励推送模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class RewardPushTemplate extends RewardPushTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
