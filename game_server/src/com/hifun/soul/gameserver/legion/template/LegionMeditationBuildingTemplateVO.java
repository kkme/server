package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团冥想建筑模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMeditationBuildingTemplateVO extends TemplateObject {

	/** 升至下级需要军团等级 */
	@ExcelCellBinding(offset = 1)
	protected int needLegionLevel;

	/** 升至下级需要军团资金 */
	@ExcelCellBinding(offset = 2)
	protected int needLegionCoin;

	/** 加成效果 */
	@ExcelCellBinding(offset = 3)
	protected int amendEffect;

	/** 加成方式 */
	@ExcelCellBinding(offset = 4)
	protected int amendMethod;


	public int getNeedLegionLevel() {
		return this.needLegionLevel;
	}

	public void setNeedLegionLevel(int needLegionLevel) {
		if (needLegionLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[升至下级需要军团等级]needLegionLevel不可以为0");
		}
		this.needLegionLevel = needLegionLevel;
	}
	
	public int getNeedLegionCoin() {
		return this.needLegionCoin;
	}

	public void setNeedLegionCoin(int needLegionCoin) {
		if (needLegionCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[升至下级需要军团资金]needLegionCoin的值不得小于0");
		}
		this.needLegionCoin = needLegionCoin;
	}
	
	public int getAmendEffect() {
		return this.amendEffect;
	}

	public void setAmendEffect(int amendEffect) {
		if (amendEffect < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[加成效果]amendEffect的值不得小于0");
		}
		this.amendEffect = amendEffect;
	}
	
	public int getAmendMethod() {
		return this.amendMethod;
	}

	public void setAmendMethod(int amendMethod) {
		if (amendMethod == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[加成方式]amendMethod不可以为0");
		}
		this.amendMethod = amendMethod;
	}
	

	@Override
	public String toString() {
		return "LegionMeditationBuildingTemplateVO[needLegionLevel=" + needLegionLevel + ",needLegionCoin=" + needLegionCoin + ",amendEffect=" + amendEffect + ",amendMethod=" + amendMethod + ",]";

	}
}