package com.hifun.soul.gameserver.meditation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 冥想协助收益模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MeditationAssistRewardTemplateVO extends TemplateObject {

	/** 获得的科技点数 */
	@ExcelCellBinding(offset = 1)
	protected int techPoint;


	public int getTechPoint() {
		return this.techPoint;
	}

	public void setTechPoint(int techPoint) {
		if (techPoint == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[获得的科技点数]techPoint不可以为0");
		}
		this.techPoint = techPoint;
	}
	

	@Override
	public String toString() {
		return "MeditationAssistRewardTemplateVO[techPoint=" + techPoint + ",]";

	}
}