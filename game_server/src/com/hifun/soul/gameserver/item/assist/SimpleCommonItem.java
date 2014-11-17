package com.hifun.soul.gameserver.item.assist;

public class SimpleCommonItem {
	/** UUID */
	private String UUID;
	/** 道具Id */
	private int itemId;
	/** 道具名称 */
	private String name;
	/** 道具描述 */
	private String desc;
	/** 道具图标 */
	private int icon;
	
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
}
