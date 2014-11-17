package com.hifun.soul.gameserver.sprite.model;

/**
 * 精灵信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpriteInfo {
	/** uuid */
	private String uuid;
	/** 精灵id */
	private int spriteId;
	/** 精灵图标id */
	private int iconId;
	/** 名称 */
	private String name;
	/** 品质 */
	private int quality;
	/** 升级需要的灵气值 */
	private int levelUpAura;
	/** 精灵类型 */
	private int spriteType;
	/** 精灵属性id */
	private int propId;
	/** 精灵属性值 */
	private int propValue;
	/** 精灵属性加成方式 */
	private int amendType;
	/** 是否出战 */
	private boolean isEquip;
	/** 当前等级 */
	private int level;
	/** 放弃该等级精灵返还灵气值 */
	private int dropReturnAura;
	
	public int getSpriteId() {
		return spriteId;
	}
	public void setSpriteId(int spriteId) {
		this.spriteId = spriteId;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
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
	public int getLevelUpAura() {
		return levelUpAura;
	}
	public void setLevelUpAura(int levelUpAura) {
		this.levelUpAura = levelUpAura;
	}
	public int getSpriteType() {
		return spriteType;
	}
	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}
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
	public int getAmendType() {
		return amendType;
	}
	public void setAmendType(int amendType) {
		this.amendType = amendType;
	}
	public boolean getIsEquip() {
		return isEquip;
	}
	public void setIsEquip(boolean isEquip) {
		this.isEquip = isEquip;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getDropReturnAura() {
		return dropReturnAura;
	}
	public void setDropReturnAura(int dropReturnAura) {
		this.dropReturnAura = dropReturnAura;
	}
}
