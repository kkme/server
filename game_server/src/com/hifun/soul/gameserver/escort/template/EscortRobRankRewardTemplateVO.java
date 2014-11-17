package com.hifun.soul.gameserver.escort.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 拦截排行奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EscortRobRankRewardTemplateVO extends TemplateObject {

	/** 奖励金币 */
	@ExcelCellBinding(offset = 1)
	protected int rewardCoin;


	public int getRewardCoin() {
		return this.rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		if (rewardCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[奖励金币]rewardCoin不可以为0");
		}
		this.rewardCoin = rewardCoin;
	}
	

	@Override
	public String toString() {
		return "EscortRobRankRewardTemplateVO[rewardCoin=" + rewardCoin + ",]";

	}
}