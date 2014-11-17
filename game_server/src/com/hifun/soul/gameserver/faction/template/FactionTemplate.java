package com.hifun.soul.gameserver.faction.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.faction.model.FactionSimpleInfo;

/**
 * 阵营模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class FactionTemplate extends FactionTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public FactionSimpleInfo toInfo() {
		FactionSimpleInfo info = new FactionSimpleInfo();
		info.setFactionDesc(this.getDesc());
		info.setFactionIconId(this.getIconId());
		info.setFactionType(this.getId());
		return info;
	}

}
