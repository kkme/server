package com.hifun.soul.gameserver.name.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 角色姓氏模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FirstNameTemplateVO extends TemplateObject {

	/** 多语言id */
	@ExcelCellBinding(offset = 1)
	protected int langId;

	/** 姓氏 */
	@ExcelCellBinding(offset = 2)
	protected String firstName;


	public int getLangId() {
		return this.langId;
	}

	public void setLangId(int langId) {
		if (langId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[多语言id]langId不可以为0");
		}
		this.langId = langId;
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		if (StringUtils.isEmpty(firstName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[姓氏]firstName不可以为空");
		}
		this.firstName = firstName;
	}
	

	@Override
	public String toString() {
		return "FirstNameTemplateVO[langId=" + langId + ",firstName=" + firstName + ",]";

	}
}