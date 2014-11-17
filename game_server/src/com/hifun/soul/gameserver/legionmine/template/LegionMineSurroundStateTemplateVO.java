package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团矿战矿位包围状态模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineSurroundStateTemplateVO extends TemplateObject {

	/** 包围状态描述 */
	@ExcelCellBinding(offset = 1)
	protected String surroundStateDesc;

	/** 被包围比例 */
	@ExcelCellBinding(offset = 2)
	protected double surroundRate;

	/** 攻防增加 */
	@ExcelCellBinding(offset = 3)
	protected int effect;


	public String getSurroundStateDesc() {
		return this.surroundStateDesc;
	}

	public void setSurroundStateDesc(String surroundStateDesc) {
		if (StringUtils.isEmpty(surroundStateDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[包围状态描述]surroundStateDesc不可以为空");
		}
		this.surroundStateDesc = surroundStateDesc;
	}
	
	public double getSurroundRate() {
		return this.surroundRate;
	}

	public void setSurroundRate(double surroundRate) {
		if (surroundRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[被包围比例]surroundRate不可以为0");
		}
		this.surroundRate = surroundRate;
	}
	
	public int getEffect() {
		return this.effect;
	}

	public void setEffect(int effect) {
		if (effect == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[攻防增加]effect不可以为0");
		}
		this.effect = effect;
	}
	

	@Override
	public String toString() {
		return "LegionMineSurroundStateTemplateVO[surroundStateDesc=" + surroundStateDesc + ",surroundRate=" + surroundRate + ",effect=" + effect + ",]";

	}
}