package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.sprite.model.SpriteInfo;

/**
 * 精灵升级模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SpriteLevelupTemplate extends SpriteLevelupTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public void setSpriteInfo(SpriteInfo sprite) {
		sprite.setPropValue(this.getPropValue());
		sprite.setLevelUpAura(this.getCostAura());
		sprite.setDropReturnAura(this.getDropReturnAura());
	}

}
