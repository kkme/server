package com.hifun.soul.gameserver.legion.info;

/**
 * 角色军团头衔信息
 * 
 * @author yandajun
 * 
 */
public class HumanLegionTitleInfo {
	private int titleId;
	private String titelName;
	private boolean isAllProperty;
	private int propertyId;
	private int propertyRate;
	private String endTime;
	private boolean valid;

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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
