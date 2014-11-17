package com.hifun.soul.gameserver.prison.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 战俘营经验模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class PrisonExperienceTemplateVO extends TemplateObject {

	/** 单日经验上限 */
	@ExcelCellBinding(offset = 1)
	protected int experienceLimit;

	/** 互动单次经验 */
	@ExcelCellBinding(offset = 2)
	protected int interactExperience;

	/** 每分钟产出经验 */
	@ExcelCellBinding(offset = 3)
	protected int expPerMinute;


	public int getExperienceLimit() {
		return this.experienceLimit;
	}

	public void setExperienceLimit(int experienceLimit) {
		if (experienceLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[单日经验上限]experienceLimit的值不得小于0");
		}
		this.experienceLimit = experienceLimit;
	}
	
	public int getInteractExperience() {
		return this.interactExperience;
	}

	public void setInteractExperience(int interactExperience) {
		if (interactExperience < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[互动单次经验]interactExperience的值不得小于0");
		}
		this.interactExperience = interactExperience;
	}
	
	public int getExpPerMinute() {
		return this.expPerMinute;
	}

	public void setExpPerMinute(int expPerMinute) {
		if (expPerMinute < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[每分钟产出经验]expPerMinute的值不得小于0");
		}
		this.expPerMinute = expPerMinute;
	}
	

	@Override
	public String toString() {
		return "PrisonExperienceTemplateVO[experienceLimit=" + experienceLimit + ",interactExperience=" + interactExperience + ",expPerMinute=" + expPerMinute + ",]";

	}
}