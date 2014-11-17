package com.hifun.soul.gameserver.sprite.model;

/**
 * 精灵背包格子信息;
 * 
 * @author crazyjohn
 * 
 */
public class SpriteBagCellInfo {
	/** 非法精灵id */
	private static final String INVALIDE_ID = "-1";
	/** 精灵背包格子索引 */
	private int index;
	/** 背包格子是否有开启 */
	private boolean open;
	/** 背包格子是否有精灵 */
	private boolean equip;
	/** 背包格子精灵id */
	private String spriteUUID;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean getEquip() {
		return equip;
	}

	public void setEquip(boolean equip) {
		this.equip = equip;
	}

	/**
	 * 把精灵放入精灵格子中;
	 * 
	 * @param sprite
	 */
	public void attachSprite(SpriteInfo sprite) {
		setSpriteUUID(sprite.getUuid());
		setEquip(true);
	}

	/**
	 * 是否是空格子;
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return !this.getEquip();
	}

	/**
	 * 把精灵从格子中拿走;
	 * 
	 * @param sprite
	 */
	public void unAttachSprite(SpriteInfo sprite) {
		this.setSpriteUUID(INVALIDE_ID);
		this.setEquip(false);
	}

	public String getSpriteUUID() {
		return spriteUUID;
	}

	public void setSpriteUUID(String spriteUUID) {
		this.spriteUUID = spriteUUID;
	}

}
