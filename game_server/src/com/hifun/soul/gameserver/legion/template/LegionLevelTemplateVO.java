package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团等级模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionLevelTemplateVO extends TemplateObject {

	/** 升至下级需要经验 */
	@ExcelCellBinding(offset = 1)
	protected int needExperience;

	/** 成员数限制 */
	@ExcelCellBinding(offset = 2)
	protected int memberLimit;


	public int getNeedExperience() {
		return this.needExperience;
	}

	public void setNeedExperience(int needExperience) {
		this.needExperience = needExperience;
	}
	
	public int getMemberLimit() {
		return this.memberLimit;
	}

	public void setMemberLimit(int memberLimit) {
		if (memberLimit == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[成员数限制]memberLimit不可以为0");
		}
		this.memberLimit = memberLimit;
	}
	

	@Override
	public String toString() {
		return "LegionLevelTemplateVO[needExperience=" + needExperience + ",memberLimit=" + memberLimit + ",]";

	}
}