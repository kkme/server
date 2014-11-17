package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团矿战战斗胜利奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineBattleRewardTemplateVO extends TemplateObject {

	/** 玩家等级下限 */
	@ExcelCellBinding(offset = 1)
	protected int minLevel;

	/** 物品ID */
	@ExcelCellBinding(offset = 2)
	protected int itemId;

	/** 物品数量 */
	@ExcelCellBinding(offset = 3)
	protected int itemNum;

	/** 概率 */
	@ExcelCellBinding(offset = 4)
	protected int rewardRate;


	public int getMinLevel() {
		return this.minLevel;
	}

	public void setMinLevel(int minLevel) {
		if (minLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[玩家等级下限]minLevel不可以为0");
		}
		this.minLevel = minLevel;
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	
	public int getRewardRate() {
		return this.rewardRate;
	}

	public void setRewardRate(int rewardRate) {
		this.rewardRate = rewardRate;
	}
	

	@Override
	public String toString() {
		return "LegionMineBattleRewardTemplateVO[minLevel=" + minLevel + ",itemId=" + itemId + ",itemNum=" + itemNum + ",rewardRate=" + rewardRate + ",]";

	}
}