package com.hifun.soul.gameserver.sprite.model;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.util.KeyUtil;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.sprite.template.SpriteLevelupTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplate;

/**
 * 精灵的简单信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpritePubInfo {
	/** 出生等级 */
	private static final int FIRST_LEVEL = 1;
	/** 精灵id */
	private int spriteId;
	/** 精灵图标id */
	private int iconId;
	/** 名称 */
	private String name;
	/** 品质 */
	private int quality;
	/** 魂类型 */
	private int soulType;
	/** 魂数量 */
	private int soulNum;
	/** 是否已经猜拳赢了当前精灵(只在对酒界面有用) */
	private boolean win;
	/** 基础属性id */
	private int propId;
	/** 基础属性值 */
	private int propValue;
	/** 所在对酒页 */
	private int pubPageId;

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public int getPropValue() {
		return propValue;
	}

	public void setPropValue(int propValue) {
		this.propValue = propValue;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public boolean getWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getSpriteId() {
		return spriteId;
	}

	public void setSpriteId(int spriteId) {
		this.spriteId = spriteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getSoulType() {
		return soulType;
	}

	public void setSoulType(int soulType) {
		this.soulType = soulType;
	}

	public int getSoulNum() {
		return soulNum;
	}

	public void setSoulNum(int soulNum) {
		this.soulNum = soulNum;
	}

	public int getPubPageId() {
		return pubPageId;
	}

	public void setPubPageId(int pubPageId) {
		this.pubPageId = pubPageId;
	}

	/**
	 * 转化成刚出生的SpriteInfo;
	 * 
	 * @return
	 */
	public SpriteInfo toNewSpriteInfo() {
		SpriteInfo result = new SpriteInfo();
		result.setIconId(this.getIconId());
		int level = SharedConstants.SPRITE_DEFAULT_LEVEL;
		SpriteLevelupTemplate levelUpTemplate = GameServerAssist
				.getSpriteTemplateManager().getSpriteLevelUpTemplate(
						this.getSpriteId(), level);
		SpriteTemplate spriteTemplate = GameServerAssist.getTemplateService()
				.get(this.getSpriteId(), SpriteTemplate.class);
		result.setIsEquip(false);
		result.setLevelUpAura(levelUpTemplate.getCostAura());
		result.setName(this.getName());
		result.setPropId(levelUpTemplate.getPropId());
		result.setPropValue(levelUpTemplate.getPropValue());
		result.setAmendType(levelUpTemplate.getAmendType());
		result.setQuality(this.getQuality());
		result.setSpriteId(this.getSpriteId());
		// 生成uuid
		result.setUuid(KeyUtil.UUIDKey());
		result.setSpriteType(spriteTemplate.getSpriteType());
		result.setLevel(FIRST_LEVEL);
		result.setDropReturnAura(levelUpTemplate.getDropReturnAura());
		return result;
	}
}
