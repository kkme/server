package com.hifun.soul.gameserver.boss.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * boss排名奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BossRankRewardTemplateVO extends TemplateObject {

	/**  排名下限 */
	@ExcelCellBinding(offset = 1)
	protected int minRank;

	/**  排名上限 */
	@ExcelCellBinding(offset = 2)
	protected int maxRank;

	/**  礼包id */
	@ExcelCellBinding(offset = 3)
	protected int giftId;


	public int getMinRank() {
		return this.minRank;
	}

	public void setMinRank(int minRank) {
		if (minRank == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 排名下限]minRank不可以为0");
		}
		if (minRank < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 排名下限]minRank的值不得小于0");
		}
		this.minRank = minRank;
	}
	
	public int getMaxRank() {
		return this.maxRank;
	}

	public void setMaxRank(int maxRank) {
		if (maxRank == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 排名上限]maxRank不可以为0");
		}
		if (maxRank < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 排名上限]maxRank的值不得小于0");
		}
		this.maxRank = maxRank;
	}
	
	public int getGiftId() {
		return this.giftId;
	}

	public void setGiftId(int giftId) {
		if (giftId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 礼包id]giftId不可以为0");
		}
		if (giftId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 礼包id]giftId的值不得小于0");
		}
		this.giftId = giftId;
	}
	

	@Override
	public String toString() {
		return "BossRankRewardTemplateVO[minRank=" + minRank + ",maxRank=" + maxRank + ",giftId=" + giftId + ",]";

	}
}