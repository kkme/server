package com.hifun.soul.gameserver.costnotify;

public class CostNotifyInfo {

	private int costNotifyType;
	
	private boolean open;
	
	private String name;
	
	private String desc;

	public int getCostNotifyType() {
		return costNotifyType;
	}

	public void setCostNotifyType(int costNotifyType) {
		this.costNotifyType = costNotifyType;
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
	
}
