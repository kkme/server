package com.hifun.soul.gameserver.horoscope;

/**
 * 占星师
 * 
 * @author magicstone
 *
 */
public class StargazerInfo {

	private int stargazerId;
	
	private String name;
	
	private String desc;
	
	private int icon;
	
	private short costCurrencyType;
	
	private int costCurrencyNum;
	
	private boolean open;

	public int getStargazerId() {
		return stargazerId;
	}

	public void setStargazerId(int stargazerId) {
		this.stargazerId = stargazerId;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
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

	public short getCostCurrencyType() {
		return costCurrencyType;
	}

	public void setCostCurrencyType(short costCurrencyType) {
		this.costCurrencyType = costCurrencyType;
	}

	public int getCostCurrencyNum() {
		return costCurrencyNum;
	}

	public void setCostCurrencyNum(int costCurrencyNum) {
		this.costCurrencyNum = costCurrencyNum;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
	
}
