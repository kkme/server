package com.hifun.soul.gameserver.sprite.model;

/**
 * 精灵装备位信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpriteEuqipSlotInfo {
	private static final String INVALIDE_ID = "-1";
	/** 装备位索引 */
	private int index;
	/** 装备位是否有装备 */
	private boolean equip;
	/** 装备的精灵id */
	private String spriteUUID;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean getEquip() {
		return equip;
	}

	public void setEquip(boolean equip) {
		this.equip = equip;
	}

	public boolean isEmpty() {
		return !this.getEquip();
	}

	/**
	 * 添加装备到装备位上;
	 * 
	 * @param sprite
	 */
	public void attachSprite(SpriteInfo sprite) {
		sprite.setIsEquip(true);
		this.setEquip(true);
		this.setSpriteUUID(sprite.getUuid());
	}

	/**
	 * 卸载装备位上的装备;
	 * 
	 * @param sprite
	 */
	public void unAttachSprite(SpriteInfo sprite) {
		sprite.setIsEquip(false);
		this.setEquip(false);
		this.setSpriteUUID(INVALIDE_ID);
	}

	public String getSpriteUUID() {
		return spriteUUID;
	}

	public void setSpriteUUID(String spriteUUID) {
		this.spriteUUID = spriteUUID;
	}

}
