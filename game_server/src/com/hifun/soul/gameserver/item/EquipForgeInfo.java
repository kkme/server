package com.hifun.soul.gameserver.item;

public class EquipForgeInfo {
	/** 装备身上带的特殊属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] equipSpecialAttributes;
	/** 装备洗练的尚未保存特殊属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] newEquipSpecialAttributes;
	/** 随机属性区间 */
	private com.hifun.soul.gameserver.item.template.GemAttribute[] attributes;
	/** 洗炼模式(1:免费洗炼;2:灵石洗炼;3:魔晶洗炼) */
	private int forgeType;
	/** 免费次数 */
	private int freeTimes;
	/** 灵石洗炼需要灵石图标 */
	private int stoneIcon;
	/** 灵石洗炼需要灵石名称 */
	private String stoneName;
	/** 灵石洗炼需要灵石数量 */
	private int stoneNum;
	/** 魔晶洗炼需要魔晶数量 */
	private int crystalNum;
	/** 灵石id */
	private int stoneId;
	
	public com.hifun.soul.core.util.KeyValuePair<Integer, Integer>[] getEquipSpecialAttributes() {
		return equipSpecialAttributes;
	}
	public void setEquipSpecialAttributes(
			com.hifun.soul.core.util.KeyValuePair<Integer, Integer>[] equipSpecialAttributes) {
		this.equipSpecialAttributes = equipSpecialAttributes;
	}
	public com.hifun.soul.gameserver.item.template.GemAttribute[] getAttributes() {
		return attributes;
	}
	public void setAttributes(
			com.hifun.soul.gameserver.item.template.GemAttribute[] attributes) {
		this.attributes = attributes;
	}
	public int getForgeType() {
		return forgeType;
	}
	public void setForgeType(int forgeType) {
		this.forgeType = forgeType;
	}
	public int getFreeTimes() {
		return freeTimes;
	}
	public void setFreeTimes(int freeTimes) {
		this.freeTimes = freeTimes;
	}
	public int getStoneIcon() {
		return stoneIcon;
	}
	public void setStoneIcon(int stoneIcon) {
		this.stoneIcon = stoneIcon;
	}
	public String getStoneName() {
		return stoneName;
	}
	public void setStoneName(String stoneName) {
		this.stoneName = stoneName;
	}
	public int getStoneNum() {
		return stoneNum;
	}
	public void setStoneNum(int stoneNum) {
		this.stoneNum = stoneNum;
	}
	public int getCrystalNum() {
		return crystalNum;
	}
	public void setCrystalNum(int crystalNum) {
		this.crystalNum = crystalNum;
	}
	public com.hifun.soul.core.util.KeyValuePair<Integer, Integer>[] getNewEquipSpecialAttributes() {
		return newEquipSpecialAttributes;
	}
	public void setNewEquipSpecialAttributes(
			com.hifun.soul.core.util.KeyValuePair<Integer, Integer>[] newEquipSpecialAttributes) {
		this.newEquipSpecialAttributes = newEquipSpecialAttributes;
	}
	public int getStoneId() {
		return stoneId;
	}
	public void setStoneId(int stoneId) {
		this.stoneId = stoneId;
	}
	
}
