package com.hifun.soul.gameserver.horoscope.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 星运介绍模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HoroscopeIntroduceTemplateVO extends TemplateObject {

	/** 多语言id */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 品质 */
	@ExcelCellBinding(offset = 3)
	protected int color;

	/** 多语言id */
	@ExcelCellBinding(offset = 4)
	protected int descLangId;

	/** 描述 */
	@ExcelCellBinding(offset = 5)
	protected String desc;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[多语言id]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		if (color == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[品质]color不可以为0");
		}
		this.color = color;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[多语言id]descLangId不可以为0");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[描述]desc不可以为空");
		}
		this.desc = desc;
	}
	

	@Override
	public String toString() {
		return "HoroscopeIntroduceTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",color=" + color + ",descLangId=" + descLangId + ",desc=" + desc + ",]";

	}
}