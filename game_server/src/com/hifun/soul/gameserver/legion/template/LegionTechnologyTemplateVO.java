package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团科技模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionTechnologyTemplateVO extends TemplateObject {

	/** 军团科技类型 */
	@ExcelCellBinding(offset = 1)
	protected int technologyType;

	/** 军团科技等级 */
	@ExcelCellBinding(offset = 2)
	protected int technologyLevel;

	/** 需要建筑等级 */
	@ExcelCellBinding(offset = 3)
	protected int needBuildingLevel;

	/** 升至下一级需要金币 */
	@ExcelCellBinding(offset = 4)
	protected int upNeedCoin;

	/** 加成效果值 */
	@ExcelCellBinding(offset = 5)
	protected int amendEffect;

	/** 加成方式 */
	@ExcelCellBinding(offset = 6)
	protected int amendMethod;


	public int getTechnologyType() {
		return this.technologyType;
	}

	public void setTechnologyType(int technologyType) {
		if (technologyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[军团科技类型]technologyType不可以为0");
		}
		this.technologyType = technologyType;
	}
	
	public int getTechnologyLevel() {
		return this.technologyLevel;
	}

	public void setTechnologyLevel(int technologyLevel) {
		if (technologyLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[军团科技等级]technologyLevel不可以为0");
		}
		this.technologyLevel = technologyLevel;
	}
	
	public int getNeedBuildingLevel() {
		return this.needBuildingLevel;
	}

	public void setNeedBuildingLevel(int needBuildingLevel) {
		if (needBuildingLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[需要建筑等级]needBuildingLevel不可以为0");
		}
		this.needBuildingLevel = needBuildingLevel;
	}
	
	public int getUpNeedCoin() {
		return this.upNeedCoin;
	}

	public void setUpNeedCoin(int upNeedCoin) {
		if (upNeedCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[升至下一级需要金币]upNeedCoin的值不得小于0");
		}
		this.upNeedCoin = upNeedCoin;
	}
	
	public int getAmendEffect() {
		return this.amendEffect;
	}

	public void setAmendEffect(int amendEffect) {
		if (amendEffect == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[加成效果值]amendEffect不可以为0");
		}
		this.amendEffect = amendEffect;
	}
	
	public int getAmendMethod() {
		return this.amendMethod;
	}

	public void setAmendMethod(int amendMethod) {
		if (amendMethod == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[加成方式]amendMethod不可以为0");
		}
		this.amendMethod = amendMethod;
	}
	

	@Override
	public String toString() {
		return "LegionTechnologyTemplateVO[technologyType=" + technologyType + ",technologyLevel=" + technologyLevel + ",needBuildingLevel=" + needBuildingLevel + ",upNeedCoin=" + upNeedCoin + ",amendEffect=" + amendEffect + ",amendMethod=" + amendMethod + ",]";

	}
}