package com.hifun.soul.gameserver.functionhelper;

public class FuncHelperInfo {
	private int id;
	private String name;
	private String desc;
	private int icon;
	private int openLevel;
	private boolean openState;
	private FuncRevenue[] funcRevenues;
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
	public int getOpenLevel() {
		return openLevel;
	}
	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}
	public boolean getOpenState() {
		return openState;
	}
	public void setOpenState(boolean openState) {
		this.openState = openState;
	}
	public FuncRevenue[] getFuncRevenues() {
		return funcRevenues;
	}
	public void setFuncRevenues(FuncRevenue[] funcRevenues) {
		this.funcRevenues = funcRevenues;
	}
	
}
