package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 精灵buff模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpriteBuffTemplateVO extends TemplateObject {

	/**  buff名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/**  多语言名称id */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/**  属性id */
	@ExcelCellBinding(offset = 3)
	protected int propId;

	/**  属性值 */
	@ExcelCellBinding(offset = 4)
	protected int propValue;

	/**  加成方式 */
	@ExcelCellBinding(offset = 5)
	protected int amendType;

	/**  激活星图的套装品质限制 */
	@ExcelCellBinding(offset = 6)
	protected int quality;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ buff名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 多语言名称id]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getPropId() {
		return this.propId;
	}

	public void setPropId(int propId) {
		if (propId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 属性id]propId的值不得小于0");
		}
		this.propId = propId;
	}
	
	public int getPropValue() {
		return this.propValue;
	}

	public void setPropValue(int propValue) {
		if (propValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 属性值]propValue的值不得小于0");
		}
		this.propValue = propValue;
	}
	
	public int getAmendType() {
		return this.amendType;
	}

	public void setAmendType(int amendType) {
		if (amendType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 加成方式]amendType的值不得小于0");
		}
		this.amendType = amendType;
	}
	
	public int getQuality() {
		return this.quality;
	}

	public void setQuality(int quality) {
		if (quality < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 激活星图的套装品质限制]quality的值不得小于0");
		}
		this.quality = quality;
	}
	

	@Override
	public String toString() {
		return "SpriteBuffTemplateVO[name=" + name + ",nameLangId=" + nameLangId + ",propId=" + propId + ",propValue=" + propValue + ",amendType=" + amendType + ",quality=" + quality + ",]";

	}
}