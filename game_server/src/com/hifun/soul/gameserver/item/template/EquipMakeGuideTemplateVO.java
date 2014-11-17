package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 装备制作引导
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EquipMakeGuideTemplateVO extends TemplateObject {

	/** 对应的地图和关卡id,以逗号分隔 */
	@ExcelCellBinding(offset = 1)
	protected String guide;


	public String getGuide() {
		return this.guide;
	}

	public void setGuide(String guide) {
		if (StringUtils.isEmpty(guide)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[对应的地图和关卡id,以逗号分隔]guide不可以为空");
		}
		this.guide = guide;
	}
	

	@Override
	public String toString() {
		return "EquipMakeGuideTemplateVO[guide=" + guide + ",]";

	}
}