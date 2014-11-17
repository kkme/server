package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 精灵星座模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpriteSignTemplateVO extends TemplateObject {

	/**  星图id */
	@ExcelCellBinding(offset = 1)
	protected int starMapId;

	/**  星座名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  多语言名称id */
	@ExcelCellBinding(offset = 3)
	protected int nameLangId;

	/**  点亮消耗星魂数量 */
	@ExcelCellBinding(offset = 4)
	protected int costStarSoul;

	/**  属性id */
	@ExcelCellBinding(offset = 5)
	protected int propId;

	/**  属性值 */
	@ExcelCellBinding(offset = 6)
	protected int propValue;

	/**  加成方式 */
	@ExcelCellBinding(offset = 7)
	protected int amendType;

	/**  x坐标 */
	@ExcelCellBinding(offset = 8)
	protected int x;

	/**  y坐标 */
	@ExcelCellBinding(offset = 9)
	protected int y;


	public int getStarMapId() {
		return this.starMapId;
	}

	public void setStarMapId(int starMapId) {
		if (starMapId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 星图id]starMapId的值不得小于0");
		}
		this.starMapId = starMapId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 星座名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 多语言名称id]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getCostStarSoul() {
		return this.costStarSoul;
	}

	public void setCostStarSoul(int costStarSoul) {
		if (costStarSoul < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 点亮消耗星魂数量]costStarSoul的值不得小于0");
		}
		this.costStarSoul = costStarSoul;
	}
	
	public int getPropId() {
		return this.propId;
	}

	public void setPropId(int propId) {
		if (propId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 属性id]propId的值不得小于0");
		}
		this.propId = propId;
	}
	
	public int getPropValue() {
		return this.propValue;
	}

	public void setPropValue(int propValue) {
		if (propValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 属性值]propValue的值不得小于0");
		}
		this.propValue = propValue;
	}
	
	public int getAmendType() {
		return this.amendType;
	}

	public void setAmendType(int amendType) {
		if (amendType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 加成方式]amendType的值不得小于0");
		}
		this.amendType = amendType;
	}
	
	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		if (x < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ x坐标]x的值不得小于0");
		}
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		if (y < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ y坐标]y的值不得小于0");
		}
		this.y = y;
	}
	

	@Override
	public String toString() {
		return "SpriteSignTemplateVO[starMapId=" + starMapId + ",name=" + name + ",nameLangId=" + nameLangId + ",costStarSoul=" + costStarSoul + ",propId=" + propId + ",propValue=" + propValue + ",amendType=" + amendType + ",x=" + x + ",y=" + y + ",]";

	}
}