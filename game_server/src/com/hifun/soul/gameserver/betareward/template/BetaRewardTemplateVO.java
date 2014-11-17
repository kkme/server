package com.hifun.soul.gameserver.betareward.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 封测用户奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BetaRewardTemplateVO extends TemplateObject {

	/**  奖励id */
	@ExcelCellBinding(offset = 1)
	protected int rewardId;


	public int getRewardId() {
		return this.rewardId;
	}

	public void setRewardId(int rewardId) {
		if (rewardId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 奖励id]rewardId不可以为0");
		}
		if (rewardId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 奖励id]rewardId的值不得小于1");
		}
		this.rewardId = rewardId;
	}
	

	@Override
	public String toString() {
		return "BetaRewardTemplateVO[rewardId=" + rewardId + ",]";

	}
}