package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团赏金任务排行奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionTaskRankTemplateVO extends TemplateObject {

	/** 奖励贡献值 */
	@ExcelCellBinding(offset = 1)
	protected int rewardContribution;

	/** 奖励贡勋章 */
	@ExcelCellBinding(offset = 2)
	protected int rewardMedal;


	public int getRewardContribution() {
		return this.rewardContribution;
	}

	public void setRewardContribution(int rewardContribution) {
		if (rewardContribution < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[奖励贡献值]rewardContribution的值不得小于0");
		}
		this.rewardContribution = rewardContribution;
	}
	
	public int getRewardMedal() {
		return this.rewardMedal;
	}

	public void setRewardMedal(int rewardMedal) {
		if (rewardMedal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[奖励贡勋章]rewardMedal的值不得小于0");
		}
		this.rewardMedal = rewardMedal;
	}
	

	@Override
	public String toString() {
		return "LegionTaskRankTemplateVO[rewardContribution=" + rewardContribution + ",rewardMedal=" + rewardMedal + ",]";

	}
}