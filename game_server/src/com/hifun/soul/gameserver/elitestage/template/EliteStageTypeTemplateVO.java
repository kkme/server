package com.hifun.soul.gameserver.elitestage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 精英副本类型模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EliteStageTypeTemplateVO extends TemplateObject {

	/** 副本名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 副本名称多语言 */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/** 副本描述 */
	@ExcelCellBinding(offset = 3)
	protected String desc;

	/** 描述多语言 */
	@ExcelCellBinding(offset = 4)
	protected int descLangId;

	/** 开启等级 */
	@ExcelCellBinding(offset = 5)
	protected int openLevel;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[副本名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[副本名称多语言]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[副本描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[描述多语言]descLangId不可以为0");
		}
		this.descLangId = descLangId;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[开启等级]openLevel不可以为0");
		}
		this.openLevel = openLevel;
	}
	

	@Override
	public String toString() {
		return "EliteStageTypeTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",desc=" + desc + ",descLangId=" + descLangId + ",openLevel=" + openLevel + ",]";

	}
}