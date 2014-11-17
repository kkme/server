package com.hifun.soul.gameserver.foster.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 培养属性模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FosterAttributeTemplateVO extends TemplateObject {

	/** 培养类型 */
	@ExcelCellBinding(offset = 1)
	protected int fosterMode;

	/**  一级属性id */
	@ExcelCellBinding(offset = 2)
	protected int level1PropertyId;

	/**  单次培养成功下限 */
	@ExcelCellBinding(offset = 3)
	protected int minNumOfPerFoster;

	/**  单次培养成功上限 */
	@ExcelCellBinding(offset = 4)
	protected int maxNumOfPerFoster;


	public int getFosterMode() {
		return this.fosterMode;
	}

	public void setFosterMode(int fosterMode) {
		if (fosterMode == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[培养类型]fosterMode不可以为0");
		}
		this.fosterMode = fosterMode;
	}
	
	public int getLevel1PropertyId() {
		return this.level1PropertyId;
	}

	public void setLevel1PropertyId(int level1PropertyId) {
		if (level1PropertyId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 一级属性id]level1PropertyId不可以为0");
		}
		this.level1PropertyId = level1PropertyId;
	}
	
	public int getMinNumOfPerFoster() {
		return this.minNumOfPerFoster;
	}

	public void setMinNumOfPerFoster(int minNumOfPerFoster) {
		if (minNumOfPerFoster < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 单次培养成功下限]minNumOfPerFoster的值不得小于0");
		}
		this.minNumOfPerFoster = minNumOfPerFoster;
	}
	
	public int getMaxNumOfPerFoster() {
		return this.maxNumOfPerFoster;
	}

	public void setMaxNumOfPerFoster(int maxNumOfPerFoster) {
		if (maxNumOfPerFoster < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 单次培养成功上限]maxNumOfPerFoster的值不得小于0");
		}
		this.maxNumOfPerFoster = maxNumOfPerFoster;
	}
	

	@Override
	public String toString() {
		return "FosterAttributeTemplateVO[fosterMode=" + fosterMode + ",level1PropertyId=" + level1PropertyId + ",minNumOfPerFoster=" + minNumOfPerFoster + ",maxNumOfPerFoster=" + maxNumOfPerFoster + ",]";

	}
}