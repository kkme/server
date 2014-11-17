package com.hifun.soul.gameserver.horoscope;

/**
 * 运魂装备位信息
 * 
 * @author magicstone
 *
 */
public class HoroscopeSoulInfo {

	private int horoscopeSoulType;
	
	private int level;
	
	private String desc;
	
	private boolean open;

	public int getHoroscopeSoulType() {
		return horoscopeSoulType;
	}

	public void setHoroscopeSoulType(int horoscopeSoulType) {
		this.horoscopeSoulType = horoscopeSoulType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
}
