package com.hifun.soul.gameserver.godsoul.msg;

/**
 * 神魄系统装备信息
 * 
 * @author yandajun
 * 
 */
public class EquipBitInfo {
	private int equipBitType;
	private int currentLevel;
	private int currentEffect;
	private int nextEffect;
	private int needCrystalNum;
	private int successRate;
	private int amendSuccessRate;
	private int needCoin;
	private int needHumanLevel;
	private boolean isMaxLevel;

	public int getEquipBitType() {
		return equipBitType;
	}

	public void setEquipBitType(int equipBitType) {
		this.equipBitType = equipBitType;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getCurrentEffect() {
		return currentEffect;
	}

	public void setCurrentEffect(int currentEffect) {
		this.currentEffect = currentEffect;
	}

	public int getNextEffect() {
		return nextEffect;
	}

	public void setNextEffect(int nextEffect) {
		this.nextEffect = nextEffect;
	}

	public int getNeedCrystalNum() {
		return needCrystalNum;
	}

	public void setNeedCrystalNum(int needCrystalNum) {
		this.needCrystalNum = needCrystalNum;
	}

	public int getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(int successRate) {
		this.successRate = successRate;
	}

	public int getAmendSuccessRate() {
		return amendSuccessRate;
	}

	public void setAmendSuccessRate(int amendSuccessRate) {
		this.amendSuccessRate = amendSuccessRate;
	}

	public int getNeedCoin() {
		return needCoin;
	}

	public void setNeedCoin(int needCoin) {
		this.needCoin = needCoin;
	}

	public int getNeedHumanLevel() {
		return needHumanLevel;
	}

	public void setNeedHumanLevel(int needHumanLevel) {
		this.needHumanLevel = needHumanLevel;
	}

	public boolean getIsMaxLevel() {
		return isMaxLevel;
	}

	public void setIsMaxLevel(boolean isMaxLevel) {
		this.isMaxLevel = isMaxLevel;
	}

}
