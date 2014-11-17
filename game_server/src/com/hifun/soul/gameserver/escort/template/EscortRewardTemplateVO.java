package com.hifun.soul.gameserver.escort.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 押运奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EscortRewardTemplateVO extends TemplateObject {

	/** 奖励金币 */
	@ExcelCellBinding(offset = 1)
	protected int rewardCoin;

	/** 奖励声望 */
	@ExcelCellBinding(offset = 2)
	protected int rewardHonor;

	/** 押运时长 */
	@ExcelCellBinding(offset = 3)
	protected int rescortTime;


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
	
	public int getRewardHonor() {
		return this.rewardHonor;
	}

	public void setRewardHonor(int rewardHonor) {
		if (rewardHonor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[奖励声望]rewardHonor的值不得小于0");
		}
		this.rewardHonor = rewardHonor;
	}
	
	public int getRescortTime() {
		return this.rescortTime;
	}

	public void setRescortTime(int rescortTime) {
		if (rescortTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[押运时长]rescortTime不可以为0");
		}
		this.rescortTime = rescortTime;
	}
	

	@Override
	public String toString() {
		return "EscortRewardTemplateVO[rewardCoin=" + rewardCoin + ",rewardHonor=" + rewardHonor + ",rescortTime=" + rescortTime + ",]";

	}
}