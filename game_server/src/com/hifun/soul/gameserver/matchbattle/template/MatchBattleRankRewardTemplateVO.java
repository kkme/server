package com.hifun.soul.gameserver.matchbattle.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 匹配战排行奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MatchBattleRankRewardTemplateVO extends TemplateObject {

	/** 奖励物品id */
	@ExcelCellBinding(offset = 1)
	protected int rewardItemId;


	public int getRewardItemId() {
		return this.rewardItemId;
	}

	public void setRewardItemId(int rewardItemId) {
		if (rewardItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[奖励物品id]rewardItemId不可以为0");
		}
		this.rewardItemId = rewardItemId;
	}
	

	@Override
	public String toString() {
		return "MatchBattleRankRewardTemplateVO[rewardItemId=" + rewardItemId + ",]";

	}
}