package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo;

/**
 * 精灵星图模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SpriteStarMapTemplate extends SpriteStarMapTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public SpriteStarMapInfo toInfo() {
		SpriteStarMapInfo info = new SpriteStarMapInfo();
		info.setActivated(false);
		info.setOpenLevel(this.getOpenLevel());
		info.setName(this.getName());
		info.setStarMapId(this.getId());
		info.setNextStarMapId(this.getNextStarMapId());
		return info;
	}

}
