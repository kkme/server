package com.hifun.soul.gameserver.item.assist;

import com.hifun.soul.core.util.KeyValuePair;


/**
 * 
 * 宝石物品的信息，绑定到装备上面
 * 
 * @author magicstone
 *
 */
public class GemItemInfo {

	/** 宝石的id */
	private int itemId;
	/** 宝石的属性 */
	private KeyValuePair<Integer,Integer>[] equipGemAttributes;
	/** 宝石的位置(默认为-1) */
	private int index = -1;
	/** 宝石的品质 */
	private int rarity = 0;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public KeyValuePair<Integer, Integer>[] getEquipGemAttributes() {
		return equipGemAttributes;
	}
	public void setEquipGemAttributes(KeyValuePair<Integer, Integer>[] equipGemAttributes) {
		this.equipGemAttributes = equipGemAttributes;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getRarity() {
		return rarity;
	}
	public void setRarity(int rarity) {
		this.rarity = rarity;
	}
	
}
