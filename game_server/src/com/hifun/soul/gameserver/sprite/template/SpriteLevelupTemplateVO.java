package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 精灵升级模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpriteLevelupTemplateVO extends TemplateObject {

	/**  精灵id */
	@ExcelCellBinding(offset = 1)
	protected int spriteId;

	/**  精灵等级 */
	@ExcelCellBinding(offset = 2)
	protected int level;

	/**  需要的玩家等级 */
	@ExcelCellBinding(offset = 3)
	protected int needHumanLevel;

	/**  属性id */
	@ExcelCellBinding(offset = 4)
	protected int propId;

	/**  本级属性加成值 */
	@ExcelCellBinding(offset = 5)
	protected int propValue;

	/**  加成方式 */
	@ExcelCellBinding(offset = 6)
	protected int amendType;

	/**  升级消耗灵气值 */
	@ExcelCellBinding(offset = 7)
	protected int costAura;

	/**  放弃返回的灵气值 */
	@ExcelCellBinding(offset = 8)
	protected int dropReturnAura;


	public int getSpriteId() {
		return this.spriteId;
	}

	public void setSpriteId(int spriteId) {
		if (spriteId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 精灵id]spriteId的值不得小于0");
		}
		this.spriteId = spriteId;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 精灵等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getNeedHumanLevel() {
		return this.needHumanLevel;
	}

	public void setNeedHumanLevel(int needHumanLevel) {
		if (needHumanLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 需要的玩家等级]needHumanLevel的值不得小于0");
		}
		this.needHumanLevel = needHumanLevel;
	}
	
	public int getPropId() {
		return this.propId;
	}

	public void setPropId(int propId) {
		if (propId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 属性id]propId的值不得小于0");
		}
		this.propId = propId;
	}
	
	public int getPropValue() {
		return this.propValue;
	}

	public void setPropValue(int propValue) {
		if (propValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 本级属性加成值]propValue的值不得小于0");
		}
		this.propValue = propValue;
	}
	
	public int getAmendType() {
		return this.amendType;
	}

	public void setAmendType(int amendType) {
		if (amendType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 加成方式]amendType的值不得小于0");
		}
		this.amendType = amendType;
	}
	
	public int getCostAura() {
		return this.costAura;
	}

	public void setCostAura(int costAura) {
		if (costAura < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 升级消耗灵气值]costAura的值不得小于0");
		}
		this.costAura = costAura;
	}
	
	public int getDropReturnAura() {
		return this.dropReturnAura;
	}

	public void setDropReturnAura(int dropReturnAura) {
		if (dropReturnAura < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 放弃返回的灵气值]dropReturnAura的值不得小于0");
		}
		this.dropReturnAura = dropReturnAura;
	}
	

	@Override
	public String toString() {
		return "SpriteLevelupTemplateVO[spriteId=" + spriteId + ",level=" + level + ",needHumanLevel=" + needHumanLevel + ",propId=" + propId + ",propValue=" + propValue + ",amendType=" + amendType + ",costAura=" + costAura + ",dropReturnAura=" + dropReturnAura + ",]";

	}
}