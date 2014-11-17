package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 精灵招募模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpriteRecruitTemplateVO extends TemplateObject {

	/**  消耗精魂类型 */
	@ExcelCellBinding(offset = 1)
	protected int soulType;

	/**  消耗精魂数量 */
	@ExcelCellBinding(offset = 2)
	protected int soulNum;

	/**  开启角色等级 */
	@ExcelCellBinding(offset = 3)
	protected int openLevel;


	public int getSoulType() {
		return this.soulType;
	}

	public void setSoulType(int soulType) {
		if (soulType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 消耗精魂类型]soulType的值不得小于0");
		}
		this.soulType = soulType;
	}
	
	public int getSoulNum() {
		return this.soulNum;
	}

	public void setSoulNum(int soulNum) {
		if (soulNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 消耗精魂数量]soulNum的值不得小于0");
		}
		this.soulNum = soulNum;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 开启角色等级]openLevel的值不得小于0");
		}
		this.openLevel = openLevel;
	}
	

	@Override
	public String toString() {
		return "SpriteRecruitTemplateVO[soulType=" + soulType + ",soulNum=" + soulNum + ",openLevel=" + openLevel + ",]";

	}
}