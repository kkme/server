package com.hifun.soul.gameserver.godsoul.info;

import com.hifun.soul.gameserver.item.assist.CommonItem;

public class MagicPaperInfo {
	private int paperId;
	private int currentLevel;
	private int propertyId;
	private int amendType;
	private int currentEffect;
	private int currentEquipBitMaxLevel;
	private int nextEffect;
	private int nextEquipBitMaxLevel;
	private CommonItem costItem;
	private int costItemNum;
	private boolean isMaxLevel;
	private int hasItemNum;

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public int getAmendType() {
		return amendType;
	}

	public void setAmendType(int amendType) {
		this.amendType = amendType;
	}

	public int getCurrentEffect() {
		return currentEffect;
	}

	public void setCurrentEffect(int currentEffect) {
		this.currentEffect = currentEffect;
	}

	public int getCurrentEquipBitMaxLevel() {
		return currentEquipBitMaxLevel;
	}

	public void setCurrentEquipBitMaxLevel(int currentEquipBitMaxLevel) {
		this.currentEquipBitMaxLevel = currentEquipBitMaxLevel;
	}

	public int getNextEffect() {
		return nextEffect;
	}

	public void setNextEffect(int nextEffect) {
		this.nextEffect = nextEffect;
	}

	public int getNextEquipBitMaxLevel() {
		return nextEquipBitMaxLevel;
	}

	public void setNextEquipBitMaxLevel(int nextEquipBitMaxLevel) {
		this.nextEquipBitMaxLevel = nextEquipBitMaxLevel;
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

	public boolean getIsMaxLevel() {
		return isMaxLevel;
	}

	public void setIsMaxLevel(boolean isMaxLevel) {
		this.isMaxLevel = isMaxLevel;
	}

	public int getHasItemNum() {
		return hasItemNum;
	}

	public void setHasItemNum(int hasItemNum) {
		this.hasItemNum = hasItemNum;
	}
}
