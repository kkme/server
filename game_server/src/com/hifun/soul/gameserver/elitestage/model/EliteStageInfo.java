package com.hifun.soul.gameserver.elitestage.model;

/**
 * 精英副本信息
 * @author magicstone
 *
 */
public class EliteStageInfo {
	/** 关卡id */
	private int stageId;
	/** 怪物名称 */
	private String monsterName;
	/** 怪物图标id */
	private int monsterIconId;	
	/** 开启等级 */
	private int openLevel;
	/** 开启状态：0表示未开启，1表示已开启，2表示锁定 */
	private int openState;
	/** 是否已挑战 */
	private boolean challengeState;
	/** 奖励金币 */
	private int coinNum;
	/** 奖励经验 */
	private int exp;
	/** 奖励科技点 */
	private int techPoint;
	/** 奖励物品名称 */
	private String[] itemNames;
	/** 消耗体力值 */
	private int energyNum;
	/** 关卡星级 */
	private int star;
	/** 是否战胜过该关卡 */
	private boolean passState; 
	/** 副本类型信息 */
	private EliteStageTypeInfo typeInfo;
	
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public String getMonsterName() {
		return monsterName;
	}
	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}
	public int getMonsterIconId() {
		return monsterIconId;
	}
	public void setMonsterIconId(int monsterIconId) {
		this.monsterIconId = monsterIconId;
	}
	public int getOpenLevel() {
		return openLevel;
	}
	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}
	public int getOpenState() {
		return openState;
	}
	public void setOpenState(int openState) {
		this.openState = openState;
	}
	public boolean getChallengeState() {
		return challengeState;
	}
	public void setChallengeState(boolean challengeState) {
		this.challengeState = challengeState;
	}
	public int getCoinNum() {
		return coinNum;
	}
	public void setCoinNum(int coinNum) {
		this.coinNum = coinNum;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getTechPoint() {
		return techPoint;
	}
	public void setTechPoint(int techPoint) {
		this.techPoint = techPoint;
	}
	public String[] getItemNames() {
		return itemNames;
	}
	public void setItemNames(String[] itemNames) {
		this.itemNames = itemNames;
	}
	public int getEnergyNum() {
		return energyNum;
	}
	public void setEnergyNum(int energyNum) {
		this.energyNum = energyNum;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public boolean getPassState() {
		return passState;
	}
	public void setPassState(boolean passState) {
		this.passState = passState;
	}
	public EliteStageTypeInfo getTypeInfo() {
		return typeInfo;
	}
	public void setTypeInfo(EliteStageTypeInfo typeInfo) {
		this.typeInfo = typeInfo;
	}	
}
