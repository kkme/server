package com.hifun.soul.gameserver.mars.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 战神之巅下注加倍模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MarsBetTemplateVO extends TemplateObject {

	/** 倍数 */
	@ExcelCellBinding(offset = 1)
	protected int multiple;

	/** 消耗魔晶 */
	@ExcelCellBinding(offset = 2)
	protected int costNum;

	/** vip开启等级 */
	@ExcelCellBinding(offset = 3)
	protected int vipLevel;


	public int getMultiple() {
		return this.multiple;
	}

	public void setMultiple(int multiple) {
		if (multiple == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[倍数]multiple不可以为0");
		}
		this.multiple = multiple;
	}
	
	public int getCostNum() {
		return this.costNum;
	}

	public void setCostNum(int costNum) {
		this.costNum = costNum;
	}
	
	public int getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	

	@Override
	public String toString() {
		return "MarsBetTemplateVO[multiple=" + multiple + ",costNum=" + costNum + ",vipLevel=" + vipLevel + ",]";

	}
}