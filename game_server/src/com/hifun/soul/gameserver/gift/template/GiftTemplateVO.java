package com.hifun.soul.gameserver.gift.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 天赋模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class GiftTemplateVO extends TemplateObject {

	/** 天赋名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 天赋名称多语言id */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/** 后置天赋id */
	@ExcelCellBinding(offset = 3)
	protected int nextGiftId;

	/** 天赋类型 */
	@ExcelCellBinding(offset = 4)
	protected int type;

	/** 图标id */
	@ExcelCellBinding(offset = 5)
	protected int icon;

	/** 天赋描述 */
	@ExcelCellBinding(offset = 6)
	protected String desc;

	/** 开启等级 */
	@ExcelCellBinding(offset = 7)
	protected int openLevel;

	/** 前置天赋ID */
	@ExcelCellBinding(offset = 8)
	protected int previousGiftId;

	/** 需要前置天赋等级 */
	@ExcelCellBinding(offset = 9)
	protected int previousGiftLevel;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[天赋名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[天赋名称多语言id]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getNextGiftId() {
		return this.nextGiftId;
	}

	public void setNextGiftId(int nextGiftId) {
		if (nextGiftId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[后置天赋id]nextGiftId的值不得小于0");
		}
		this.nextGiftId = nextGiftId;
	}
	
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		if (type == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[天赋类型]type不可以为0");
		}
		this.type = type;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[图标id]icon不可以为0");
		}
		this.icon = icon;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[天赋描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[开启等级]openLevel不可以为0");
		}
		this.openLevel = openLevel;
	}
	
	public int getPreviousGiftId() {
		return this.previousGiftId;
	}

	public void setPreviousGiftId(int previousGiftId) {
		if (previousGiftId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[前置天赋ID]previousGiftId的值不得小于0");
		}
		this.previousGiftId = previousGiftId;
	}
	
	public int getPreviousGiftLevel() {
		return this.previousGiftLevel;
	}

	public void setPreviousGiftLevel(int previousGiftLevel) {
		if (previousGiftLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[需要前置天赋等级]previousGiftLevel的值不得小于0");
		}
		this.previousGiftLevel = previousGiftLevel;
	}
	

	@Override
	public String toString() {
		return "GiftTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",nextGiftId=" + nextGiftId + ",type=" + type + ",icon=" + icon + ",desc=" + desc + ",openLevel=" + openLevel + ",previousGiftId=" + previousGiftId + ",previousGiftLevel=" + previousGiftLevel + ",]";

	}
}