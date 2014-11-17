package com.hifun.soul.gameserver.foster;

public class FosterMode {
	private int id;
	private String name;
	private String desc;
	private int type;
	private int costCurrencyType;
	private int costCurrencyNum;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCostCurrencyType() {
		return costCurrencyType;
	}
	public void setCostCurrencyType(int costCurrencyType) {
		this.costCurrencyType = costCurrencyType;
	}
	public int getCostCurrencyNum() {
		return costCurrencyNum;
	}
	public void setCostCurrencyNum(int costCurrencyNum) {
		this.costCurrencyNum = costCurrencyNum;
	}
	
}
