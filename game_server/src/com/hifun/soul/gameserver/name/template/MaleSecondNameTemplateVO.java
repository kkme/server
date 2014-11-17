package com.hifun.soul.gameserver.name.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 男性角色名字模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MaleSecondNameTemplateVO extends TemplateObject {

	/** 多语言id */
	@ExcelCellBinding(offset = 1)
	protected int langId;

	/** 男性玩家名字 */
	@ExcelCellBinding(offset = 2)
	protected String secondName;


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
	
	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		if (StringUtils.isEmpty(secondName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[男性玩家名字]secondName不可以为空");
		}
		this.secondName = secondName;
	}
	

	@Override
	public String toString() {
		return "MaleSecondNameTemplateVO[langId=" + langId + ",secondName=" + secondName + ",]";

	}
}