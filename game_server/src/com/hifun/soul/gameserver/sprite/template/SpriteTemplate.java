package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;

/**
 * 精灵模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SpriteTemplate extends SpriteTemplateVO {

	@Override
	public void check() throws TemplateConfigException {

	}

	/**
	 * 跟精灵简单信息的转化;
	 * 
	 * @return
	 */
	public SpritePubInfo toSimpleInfo() {
		SpritePubInfo info = new SpritePubInfo();
		info.setIconId(this.getIconId());
		info.setName(this.getName());
		info.setQuality(this.getQuality());
		info.setSoulNum(this.getSpriteSoulNum());
		info.setSoulType(this.getSpriteSoulType());
		info.setSpriteId(this.getId());
		info.setPubPageId(this.getPubPageId());
		// 默认精灵等级加成属性
		SpriteLevelupTemplate levelUpTemplate = GameServerAssist
				.getSpriteTemplateManager().getSpriteLevelUpTemplate(
						this.getId(), SharedConstants.SPRITE_DEFAULT_LEVEL);
		info.setPropId(levelUpTemplate.getPropId());
		info.setPropValue(levelUpTemplate.getPropValue());
		return info;
	}

}
