package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.sprite.model.SpriteSignInfo;

/**
 * 精灵星座模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SpriteSignTemplate extends SpriteSignTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public SpriteSignInfo toInfo() {
		SpriteSignInfo info = new SpriteSignInfo();
		info.setAmendType(this.getAmendType());
		info.setCostStarSoul(this.getCostStarSoul());
		info.setLight(false);
		info.setName(this.getName());
		info.setPropId(this.getPropId());
		info.setPropValue(this.getPropValue());
		info.setSignId(this.getId());
		info.setStarMapId(this.getStarMapId());
		info.setX(this.getX());
		info.setY(this.getY());
		return info;
	}

}
