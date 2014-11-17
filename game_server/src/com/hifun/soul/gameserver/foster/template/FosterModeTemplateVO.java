package com.hifun.soul.gameserver.foster.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 培养模式模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FosterModeTemplateVO extends TemplateObject {

	/** 培养模式名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 随机范围下限系数 */
	@ExcelCellBinding(offset = 2)
	protected int randomLowRatio;

	/** 开启模式的vip等级 */
	@ExcelCellBinding(offset = 3)
	protected int openVipLevel;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[培养模式名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getRandomLowRatio() {
		return this.randomLowRatio;
	}

	public void setRandomLowRatio(int randomLowRatio) {
		if (randomLowRatio < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[随机范围下限系数]randomLowRatio的值不得小于0");
		}
		this.randomLowRatio = randomLowRatio;
	}
	
	public int getOpenVipLevel() {
		return this.openVipLevel;
	}

	public void setOpenVipLevel(int openVipLevel) {
		if (openVipLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[开启模式的vip等级]openVipLevel的值不得小于0");
		}
		this.openVipLevel = openVipLevel;
	}
	

	@Override
	public String toString() {
		return "FosterModeTemplateVO[name=" + name + ",randomLowRatio=" + randomLowRatio + ",openVipLevel=" + openVipLevel + ",]";

	}
}