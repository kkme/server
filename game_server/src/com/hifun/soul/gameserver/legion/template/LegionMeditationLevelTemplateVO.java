package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团冥想等级模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMeditationLevelTemplateVO extends TemplateObject {

	/** 小思获得冥想力 */
	@ExcelCellBinding(offset = 1)
	protected int littleThinkMeditation;

	/** 深思获得冥想力 */
	@ExcelCellBinding(offset = 2)
	protected int deepThinkMeditation;

	/** 沉思获得冥想力 */
	@ExcelCellBinding(offset = 3)
	protected int weightThinkMeditation;


	public int getLittleThinkMeditation() {
		return this.littleThinkMeditation;
	}

	public void setLittleThinkMeditation(int littleThinkMeditation) {
		if (littleThinkMeditation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[小思获得冥想力]littleThinkMeditation不可以为0");
		}
		this.littleThinkMeditation = littleThinkMeditation;
	}
	
	public int getDeepThinkMeditation() {
		return this.deepThinkMeditation;
	}

	public void setDeepThinkMeditation(int deepThinkMeditation) {
		if (deepThinkMeditation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[深思获得冥想力]deepThinkMeditation不可以为0");
		}
		this.deepThinkMeditation = deepThinkMeditation;
	}
	
	public int getWeightThinkMeditation() {
		return this.weightThinkMeditation;
	}

	public void setWeightThinkMeditation(int weightThinkMeditation) {
		if (weightThinkMeditation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[沉思获得冥想力]weightThinkMeditation不可以为0");
		}
		this.weightThinkMeditation = weightThinkMeditation;
	}
	

	@Override
	public String toString() {
		return "LegionMeditationLevelTemplateVO[littleThinkMeditation=" + littleThinkMeditation + ",deepThinkMeditation=" + deepThinkMeditation + ",weightThinkMeditation=" + weightThinkMeditation + ",]";

	}
}