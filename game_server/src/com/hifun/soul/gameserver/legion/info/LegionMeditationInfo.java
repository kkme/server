package com.hifun.soul.gameserver.legion.info;

/**
 * 军团冥想殿信息
 * 
 * @author yandajun
 * 
 */
public class LegionMeditationInfo {
	private int meditationType;
	private String meditationName;
	private int vipLevel;
	private int currencyType;
	private int currencyNum;
	private int meditation;
	private int medal;

	public int getMeditationType() {
		return meditationType;
	}

	public void setMeditationType(int meditationType) {
		this.meditationType = meditationType;
	}

	public String getMeditationName() {
		return meditationName;
	}

	public void setMeditationName(String meditationName) {
		this.meditationName = meditationName;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public int getCurrencyNum() {
		return currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		this.currencyNum = currencyNum;
	}

	public int getMeditation() {
		return meditation;
	}

	public void setMeditation(int meditation) {
		this.meditation = meditation;
	}

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
	}

}
