package com.hifun.soul.gameserver.betareward.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 封测用户模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BetaUserTemplateVO extends TemplateObject {

	/**  封测账号id */
	@ExcelCellBinding(offset = 1)
	protected String passPortId;

	/**  封测账号等级 */
	@ExcelCellBinding(offset = 2)
	protected int level;


	public String getPassPortId() {
		return this.passPortId;
	}

	public void setPassPortId(String passPortId) {
		if (StringUtils.isEmpty(passPortId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 封测账号id]passPortId不可以为空");
		}
		this.passPortId = passPortId;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 封测账号等级]level的值不得小于0");
		}
		this.level = level;
	}
	

	@Override
	public String toString() {
		return "BetaUserTemplateVO[passPortId=" + passPortId + ",level=" + level + ",]";

	}
}