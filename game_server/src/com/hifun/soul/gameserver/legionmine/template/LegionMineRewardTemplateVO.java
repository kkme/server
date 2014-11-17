package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团矿战奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineRewardTemplateVO extends TemplateObject {

	/** 排名下限 */
	@ExcelCellBinding(offset = 1)
	protected int rankLowest;

	/** 排名上限 */
	@ExcelCellBinding(offset = 2)
	protected int rankHighest;

	/** 奖励金币 */
	@ExcelCellBinding(offset = 3)
	protected int rewarCoin;

	/** 奖励威望 */
	@ExcelCellBinding(offset = 4)
	protected int rewardPrestige;

	/** 物品ID */
	@ExcelCellBinding(offset = 5)
	protected int itemId;

	/** 物品数量 */
	@ExcelCellBinding(offset = 6)
	protected int itemNum;


	public int getRankLowest() {
		return this.rankLowest;
	}

	public void setRankLowest(int rankLowest) {
		if (rankLowest == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[排名下限]rankLowest不可以为0");
		}
		this.rankLowest = rankLowest;
	}
	
	public int getRankHighest() {
		return this.rankHighest;
	}

	public void setRankHighest(int rankHighest) {
		if (rankHighest == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[排名上限]rankHighest不可以为0");
		}
		this.rankHighest = rankHighest;
	}
	
	public int getRewarCoin() {
		return this.rewarCoin;
	}

	public void setRewarCoin(int rewarCoin) {
		if (rewarCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[奖励金币]rewarCoin的值不得小于0");
		}
		this.rewarCoin = rewarCoin;
	}
	
	public int getRewardPrestige() {
		return this.rewardPrestige;
	}

	public void setRewardPrestige(int rewardPrestige) {
		if (rewardPrestige < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[奖励威望]rewardPrestige的值不得小于0");
		}
		this.rewardPrestige = rewardPrestige;
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[物品ID]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(int itemNum) {
		if (itemNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[物品数量]itemNum的值不得小于0");
		}
		this.itemNum = itemNum;
	}
	

	@Override
	public String toString() {
		return "LegionMineRewardTemplateVO[rankLowest=" + rankLowest + ",rankHighest=" + rankHighest + ",rewarCoin=" + rewarCoin + ",rewardPrestige=" + rewardPrestige + ",itemId=" + itemId + ",itemNum=" + itemNum + ",]";

	}
}