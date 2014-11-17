package com.hifun.soul.gameserver.rechargetx;

public class RechargeInfo {
	private int id;
	private String name;
	private String desc;
	private int icon;
	private int crystal;
	private int price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getCrystal() {
		return crystal;
	}
	public void setCrystal(int crystal) {
		this.crystal = crystal;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
