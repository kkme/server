package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团赏金建筑模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionTaskBuildingTemplateVO extends TemplateObject {

	/** 升至下级需要军团等级 */
	@ExcelCellBinding(offset = 1)
	protected int needLegionLevel;

	/** 升至下级需要军团资金 */
	@ExcelCellBinding(offset = 2)
	protected int needLegionCoin;

	/** 可接任务数 */
	@ExcelCellBinding(offset = 3)
	protected int taskNum;


	public int getNeedLegionLevel() {
		return this.needLegionLevel;
	}

	public void setNeedLegionLevel(int needLegionLevel) {
		if (needLegionLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[升至下级需要军团等级]needLegionLevel的值不得小于0");
		}
		this.needLegionLevel = needLegionLevel;
	}
	
	public int getNeedLegionCoin() {
		return this.needLegionCoin;
	}

	public void setNeedLegionCoin(int needLegionCoin) {
		if (needLegionCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[升至下级需要军团资金]needLegionCoin的值不得小于0");
		}
		this.needLegionCoin = needLegionCoin;
	}
	
	public int getTaskNum() {
		return this.taskNum;
	}

	public void setTaskNum(int taskNum) {
		if (taskNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[可接任务数]taskNum不可以为0");
		}
		this.taskNum = taskNum;
	}
	

	@Override
	public String toString() {
		return "LegionTaskBuildingTemplateVO[needLegionLevel=" + needLegionLevel + ",needLegionCoin=" + needLegionCoin + ",taskNum=" + taskNum + ",]";

	}
}