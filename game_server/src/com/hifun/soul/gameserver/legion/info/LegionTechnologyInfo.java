package com.hifun.soul.gameserver.legion.info;

/**
 * 军团科技信息
 * 
 * @author yandajun
 * 
 */
public class LegionTechnologyInfo {
	private int technologyType;
	private String technologyName;
	private int iconId;
	private String amendDesc;
	private int technologyLevel;
	private int upNeedCoin;
	private int upCurrentCoin;
	private int amendEffect;
	private int amendMethod;

	public int getTechnologyType() {
		return technologyType;
	}

	public void setTechnologyType(int technologyType) {
		this.technologyType = technologyType;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getAmendDesc() {
		return amendDesc;
	}

	public void setAmendDesc(String amendDesc) {
		this.amendDesc = amendDesc;
	}

	public int getTechnologyLevel() {
		return technologyLevel;
	}

	public void setTechnologyLevel(int technologyLevel) {
		this.technologyLevel = technologyLevel;
	}

	public int getUpNeedCoin() {
		return upNeedCoin;
	}

	public void setUpNeedCoin(int upNeedCoin) {
		this.upNeedCoin = upNeedCoin;
	}

	public int getUpCurrentCoin() {
		return upCurrentCoin;
	}

	public void setUpCurrentCoin(int upCurrentCoin) {
		this.upCurrentCoin = upCurrentCoin;
	}

	public int getAmendEffect() {
		return amendEffect;
	}

	public void setAmendEffect(int amendEffect) {
		this.amendEffect = amendEffect;
	}

	public int getAmendMethod() {
		return amendMethod;
	}

	public void setAmendMethod(int amendMethod) {
		this.amendMethod = amendMethod;
	}
}
