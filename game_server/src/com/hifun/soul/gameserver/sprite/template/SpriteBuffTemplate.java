package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo;

/**
 * 精灵套装buff;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SpriteBuffTemplate extends SpriteBuffTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public SpriteBuffInfo toInfo() {
		SpriteBuffInfo info = new SpriteBuffInfo();
		info.setActivated(false);
		info.setActivateQuality(this.getQuality());
		info.setAmendType(amendType);
		info.setBuffId(this.getId());
		info.setName(this.getName());
		info.setPropId(this.getPropId());
		info.setPropValue(this.getPropValue());
		return info;
	}

}
