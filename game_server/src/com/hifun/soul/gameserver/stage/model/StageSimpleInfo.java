package com.hifun.soul.gameserver.stage.model;

/**
 * 关卡简单信息
 * @author magicstone
 */
public class StageSimpleInfo {
	private int stageId;
	private int x;
	private int y;
	private String stageName;
	private String stageDesc;
	private int minLevel;
	private boolean pass;
	private int monsterIcon;
	private int mapId;
	private int star;
	private boolean autoBattle;
	private String monsterName;
	private int monsterLevel;
	private int monsterOccupation;
	private int rewardExperience;
	private short rewardCurrencyType;
	private int rewardCurrencyNum;
	private int monsterFirstAttack;
	
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public int getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	public boolean getPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public int getMonsterIcon() {
		return monsterIcon;
	}
	public void setMonsterIcon(int monsterIcon) {
		this.monsterIcon = monsterIcon;
	}
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public boolean getAutoBattle() {
		return autoBattle;
	}
	public void setAutoBattle(boolean autoBattle) {
		this.autoBattle = autoBattle;
	}
	public String getMonsterName() {
		return monsterName;
	}
	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}
	public int getMonsterLevel() {
		return monsterLevel;
	}
	public void setMonsterLevel(int monsterLevel) {
		this.monsterLevel = monsterLevel;
	}
	public int getMonsterOccupation() {
		return monsterOccupation;
	}
	public void setMonsterOccupation(int monsterOccupation) {
		this.monsterOccupation = monsterOccupation;
	}
	public int getRewardExperience() {
		return rewardExperience;
	}
	public void setRewardExperience(int rewardExperience) {
		this.rewardExperience = rewardExperience;
	}
	public short getRewardCurrencyType() {
		return rewardCurrencyType;
	}
	public void setRewardCurrencyType(short rewardCurrencyType) {
		this.rewardCurrencyType = rewardCurrencyType;
	}
	public int getRewardCurrencyNum() {
		return rewardCurrencyNum;
	}
	public void setRewardCurrencyNum(int rewardCurrencyNum) {
		this.rewardCurrencyNum = rewardCurrencyNum;
	}
	public int getMonsterFirstAttack() {
		return monsterFirstAttack;
	}
	public void setMonsterFirstAttack(int monsterFirstAttack) {
		this.monsterFirstAttack = monsterFirstAttack;
	}
}
