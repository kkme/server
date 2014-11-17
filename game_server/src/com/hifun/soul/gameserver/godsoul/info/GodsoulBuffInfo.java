package com.hifun.soul.gameserver.godsoul.info;

public class GodsoulBuffInfo {
	private int buffId;
	private int needUpgradeLevel;
	private int propertyId;
	private int amendEffect;
	private int amendType;
	private boolean valid;

	public int getBuffId() {
		return buffId;
	}

	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}

	public int getNeedUpgradeLevel() {
		return needUpgradeLevel;
	}

	public void setNeedUpgradeLevel(int needUpgradeLevel) {
		this.needUpgradeLevel = needUpgradeLevel;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getAmendEffect() {
		return amendEffect;
	}

	public void setAmendEffect(int amendEffect) {
		this.amendEffect = amendEffect;
	}

	public int getAmendType() {
		return amendType;
	}

	public void setAmendType(int amendType) {
		this.amendType = amendType;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
