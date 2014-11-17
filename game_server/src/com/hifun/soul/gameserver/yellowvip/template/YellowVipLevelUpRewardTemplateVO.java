package com.hifun.soul.gameserver.yellowvip.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 黄钻升级奖励模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class YellowVipLevelUpRewardTemplateVO extends TemplateObject {

	/** 金币数 */
	@ExcelCellBinding(offset = 1)
	protected int coin;

	/** 物品id */
	@ExcelCellBinding(offset = 2)
	protected int itemId;

	/** 物品数量 */
	@ExcelCellBinding(offset = 3)
	protected int itemCount;


	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		if (coin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[金币数]coin不可以为0");
		}
		this.coin = coin;
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[物品id]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public int getItemCount() {
		return this.itemCount;
	}

	public void setItemCount(int itemCount) {
		if (itemCount == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[物品数量]itemCount不可以为0");
		}
		this.itemCount = itemCount;
	}
	

	@Override
	public String toString() {
		return "YellowVipLevelUpRewardTemplateVO[coin=" + coin + ",itemId=" + itemId + ",itemCount=" + itemCount + ",]";

	}
}