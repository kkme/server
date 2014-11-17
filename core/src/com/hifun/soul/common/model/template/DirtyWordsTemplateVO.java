package com.hifun.soul.common.model.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.annotation.NotTranslate;
import com.hifun.soul.core.util.StringUtils;

/**
 * 脏话模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class DirtyWordsTemplateVO extends TemplateObject {

	/** 要过滤的词 */
	@NotTranslate
	@ExcelCellBinding(offset = 1)
	protected String dirtyWords;


	public String getDirtyWords() {
		return this.dirtyWords;
	}

	public void setDirtyWords(String dirtyWords) {
		if (StringUtils.isEmpty(dirtyWords)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[要过滤的词]dirtyWords不可以为空");
		}
		this.dirtyWords = dirtyWords;
	}
	

	@Override
	public String toString() {
		return "DirtyWordsTemplateVO[dirtyWords=" + dirtyWords + ",]";

	}
}