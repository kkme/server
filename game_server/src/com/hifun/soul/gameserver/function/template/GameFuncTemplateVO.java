package com.hifun.soul.gameserver.function.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 功能模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class GameFuncTemplateVO extends TemplateObject {

	/**  多语言名称 */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected int descLangId;

	/**  描述 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  开放等级 */
	@ExcelCellBinding(offset = 5)
	protected int openLevel;

	/**  开放vip限制 */
	@ExcelCellBinding(offset = 6)
	protected int vipLevel;

	/**  */
	@ExcelCellBinding(offset = 7)
	protected int icon;

	/** 是否显示在小助手 */
	@ExcelCellBinding(offset = 8)
	protected boolean showInHelper;

	/** 是否显示在预见未来 */
	@ExcelCellBinding(offset = 9)
	protected boolean showInPredict;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 多语言名称]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 多语言描述]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 开放等级]openLevel不可以为0");
		}
		this.openLevel = openLevel;
	}
	
	public int getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		if (vipLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 开放vip限制]vipLevel的值不得小于0");
		}
		this.vipLevel = vipLevel;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[]icon不可以为0");
		}
		this.icon = icon;
	}
	
	public boolean isShowInHelper() {
		return this.showInHelper;
	}

	public void setShowInHelper(boolean showInHelper) {
		this.showInHelper = showInHelper;
	}
	
	public boolean isShowInPredict() {
		return this.showInPredict;
	}

	public void setShowInPredict(boolean showInPredict) {
		this.showInPredict = showInPredict;
	}
	

	@Override
	public String toString() {
		return "GameFuncTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",openLevel=" + openLevel + ",vipLevel=" + vipLevel + ",icon=" + icon + ",showInHelper=" + showInHelper + ",showInPredict=" + showInPredict + ",]";

	}
}