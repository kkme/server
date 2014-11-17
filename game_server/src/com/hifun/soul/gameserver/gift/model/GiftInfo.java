package com.hifun.soul.gameserver.gift.model;

import com.hifun.soul.gameserver.item.assist.CommonItem;

public class GiftInfo {
	private int id;
	private String name;
	private int type;
	private int icon;
	private int nextGiftId;
	private int previousGiftId;
	private int currentPropertyId;
	private int currentPropertyValue;
	private int costGiftPoint;
	private int activeState;
	private int propertyValueType;
	private String desc;
	private int currentLevel;
	private int maxLevel;
	private int openLevel;
	private int nextPropertyId;
	private int nextPropertyValue;
	private CommonItem costItem;
	private int costItemNum;
	private int bagItemNum;
	private int upgradeNeedLevel;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getNextGiftId() {
		return nextGiftId;
	}

	public void setNextGiftId(int nextGiftId) {
		this.nextGiftId = nextGiftId;
	}

	public int getPreviousGiftId() {
		return previousGiftId;
	}

	public void setPreviousGiftId(int previousGiftId) {
		this.previousGiftId = previousGiftId;
	}

	public int getCurrentPropertyId() {
		return currentPropertyId;
	}

	public void setCurrentPropertyId(int currentPropertyId) {
		this.currentPropertyId = currentPropertyId;
	}

	public int getCurrentPropertyValue() {
		return currentPropertyValue;
	}

	public void setCurrentPropertyValue(int currentPropertyValue) {
		this.currentPropertyValue = currentPropertyValue;
	}

	public int getCostGiftPoint() {
		return costGiftPoint;
	}

	public void setCostGiftPoint(int costGiftPoint) {
		this.costGiftPoint = costGiftPoint;
	}

	public int getActiveState() {
		return activeState;
	}

	public void setActiveState(int activeState) {
		this.activeState = activeState;
	}

	public int getPropertyValueType() {
		return propertyValueType;
	}

	public void setPropertyValueType(int propertyValueType) {
		this.propertyValueType = propertyValueType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getOpenLevel() {
		return openLevel;
	}

	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}

	public int getNextPropertyId() {
		return nextPropertyId;
	}

	public void setNextPropertyId(int nextPropertyId) {
		this.nextPropertyId = nextPropertyId;
	}

	public int getNextPropertyValue() {
		return nextPropertyValue;
	}

	public void setNextPropertyValue(int nextPropertyValue) {
		this.nextPropertyValue = nextPropertyValue;
	}

	public CommonItem getCostItem() {
		return costItem;
	}

	public void setCostItem(CommonItem costItem) {
		this.costItem = costItem;
	}

	public int getCostItemNum() {
		return costItemNum;
	}

	public void setCostItemNum(int costItemNum) {
		this.costItemNum = costItemNum;
	}

	public int getBagItemNum() {
		return bagItemNum;
	}

	public void setBagItemNum(int bagItemNum) {
		this.bagItemNum = bagItemNum;
	}

	public int getUpgradeNeedLevel() {
		return upgradeNeedLevel;
	}

	public void setUpgradeNeedLevel(int upgradeNeedLevel) {
		this.upgradeNeedLevel = upgradeNeedLevel;
	}

}
