package com.hifun.soul.gameserver.stagestar.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 关卡评星与cd时间减免模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageStarCdTemplateVO extends TemplateObject {

	/**  减免cd时间(分钟) */
	@ExcelCellBinding(offset = 1)
	protected int reduceCd;


	public int getReduceCd() {
		return this.reduceCd;
	}

	public void setReduceCd(int reduceCd) {
		if (reduceCd == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 减免cd时间(分钟)]reduceCd不可以为0");
		}
		this.reduceCd = reduceCd;
	}
	

	@Override
	public String toString() {
		return "StageStarCdTemplateVO[reduceCd=" + reduceCd + ",]";

	}
}