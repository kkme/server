package com.hifun.soul.gameserver.reward.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 奖励推送模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RewardPushTemplateVO extends TemplateObject {

	/** 奖励名称 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 图标id */
	@ExcelCellBinding(offset = 2)
	protected int iconId;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[奖励名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[图标id]iconId不可以为0");
		}
		this.iconId = iconId;
	}
	

	@Override
	public String toString() {
		return "RewardPushTemplateVO[name=" + name + ",iconId=" + iconId + ",]";

	}
}