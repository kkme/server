package com.hifun.soul.gameserver.shop;

public class SpecialShopNotify {

	private int id;
	private int specialShopItemId;
	private int itemId;
	private String itemName;
	private int itemNum;
	private String roleName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSpecialShopItemId() {
		return specialShopItemId;
	}
	public void setSpecialShopItemId(int specialShopItemId) {
		this.specialShopItemId = specialShopItemId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
