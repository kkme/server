package com.hifun.soul.gameserver.legion.info;

/**
 * 军团头衔信息
 * 
 * @author yandajun
 * 
 */
public class LegionTitleInfo {
	private int titleId;
	private String titelName;
	private int iconId;
	private int colorId;
	private String needPositionName;
	private int maxNum;
	private int remainNum;
	private int needMedal;
	private boolean isAllProperty;
	private int propertyId;
	private int propertyRate;
	private int validDays;
	private int needBuildingLevel;

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public String getTitelName() {
		return titelName;
	}

	public void setTitelName(String titelName) {
		this.titelName = titelName;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public String getNeedPositionName() {
		return needPositionName;
	}

	public void setNeedPositionName(String needPositionName) {
		this.needPositionName = needPositionName;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}

	public int getNeedMedal() {
		return needMedal;
	}

	public void setNeedMedal(int needMedal) {
		this.needMedal = needMedal;
	}

	public boolean getIsAllProperty() {
		return isAllProperty;
	}

	public void setIsAllProperty(boolean isAllProperty) {
		this.isAllProperty = isAllProperty;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getPropertyRate() {
		return propertyRate;
	}

	public void setPropertyRate(int propertyRate) {
		this.propertyRate = propertyRate;
	}

	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}

	public int getNeedBuildingLevel() {
		return needBuildingLevel;
	}

	public void setNeedBuildingLevel(int needBuildingLevel) {
		this.needBuildingLevel = needBuildingLevel;
	}
}
