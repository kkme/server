package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团矿战军团buf模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionBufTemplateVO extends TemplateObject {

	/** 名称 */
	@ExcelCellBinding(offset = 1)
	protected String bufName;

	/** 军团拥有矿位小于等于个数 */
	@ExcelCellBinding(offset = 2)
	protected int mineNum;

	/** 攻防加成 */
	@ExcelCellBinding(offset = 3)
	protected int effect;

	/** buf图标 */
	@ExcelCellBinding(offset = 4)
	protected int bufIcon;


	public String getBufName() {
		return this.bufName;
	}

	public void setBufName(String bufName) {
		if (StringUtils.isEmpty(bufName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[名称]bufName不可以为空");
		}
		this.bufName = bufName;
	}
	
	public int getMineNum() {
		return this.mineNum;
	}

	public void setMineNum(int mineNum) {
		if (mineNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[军团拥有矿位小于等于个数]mineNum不可以为0");
		}
		this.mineNum = mineNum;
	}
	
	public int getEffect() {
		return this.effect;
	}

	public void setEffect(int effect) {
		if (effect == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[攻防加成]effect不可以为0");
		}
		this.effect = effect;
	}
	
	public int getBufIcon() {
		return this.bufIcon;
	}

	public void setBufIcon(int bufIcon) {
		if (bufIcon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[buf图标]bufIcon不可以为0");
		}
		this.bufIcon = bufIcon;
	}
	

	@Override
	public String toString() {
		return "LegionBufTemplateVO[bufName=" + bufName + ",mineNum=" + mineNum + ",effect=" + effect + ",bufIcon=" + bufIcon + ",]";

	}
}