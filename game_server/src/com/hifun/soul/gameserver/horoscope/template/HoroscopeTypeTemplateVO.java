package com.hifun.soul.gameserver.horoscope.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 运魂类型模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HoroscopeTypeTemplateVO extends TemplateObject {

	/**  开启等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/**  格子说明多语言 */
	@ExcelCellBinding(offset = 2)
	protected int descLangId;

	/**  格子说明 */
	@ExcelCellBinding(offset = 3)
	protected String desc;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 开启等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 格子说明多语言]descLangId不可以为0");
		}
		if (descLangId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 格子说明多语言]descLangId的值不得小于1");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 格子说明]desc不可以为空");
		}
		this.desc = desc;
	}
	

	@Override
	public String toString() {
		return "HoroscopeTypeTemplateVO[level=" + level + ",descLangId=" + descLangId + ",desc=" + desc + ",]";

	}
}