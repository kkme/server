package com.hifun.soul.gameserver.escort.info;

/**
 * 押运信息
 * 
 * @author yandajun
 * 
 */
public class EscortInfo {
	/** 押运ID(同ownerID) */
	private long escortId;
	private int monsterModelId;
	private int monsterType;
	private long ownerId;
	private String ownerName;
	private int ownerLevel;
	private String ownerLegionName;
	private long helperId;
	private String helperName;
	private int helperLevel;
	private int remainBeRobbedNum;
	private int maxBeRobbedNum;
	private int escortRemainTime;
	private int encourageRate;
	private int escortRewardCoin;
	private int helpRewardCoin;
	private int helpLevelDiff;
	private int robRewardCoin;
	private int escortTime;
	private long endTime;
	private int escortRewardState;
	private int escortState;
	private boolean isRobing;

	public long getEscortId() {
		return escortId;
	}

	public void setEscortId(long escortId) {
		this.escortId = escortId;
	}

	public int getMonsterModelId() {
		return monsterModelId;
	}

	public void setMonsterModelId(int monsterModelId) {
		this.monsterModelId = monsterModelId;
	}

	public int getMonsterType() {
		return monsterType;
	}

	public void setMonsterType(int monsterType) {
		this.monsterType = monsterType;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getOwnerLevel() {
		return ownerLevel;
	}

	public void setOwnerLevel(int ownerLevel) {
		this.ownerLevel = ownerLevel;
	}

	public String getOwnerLegionName() {
		return ownerLegionName;
	}

	public void setOwnerLegionName(String ownerLegionName) {
		this.ownerLegionName = ownerLegionName;
	}

	public long getHelperId() {
		return helperId;
	}

	public void setHelperId(long helperId) {
		this.helperId = helperId;
	}

	public String getHelperName() {
		return helperName;
	}

	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}

	public int getHelperLevel() {
		return helperLevel;
	}

	public void setHelperLevel(int helperLevel) {
		this.helperLevel = helperLevel;
	}

	public int getRemainBeRobbedNum() {
		return remainBeRobbedNum;
	}

	public void setRemainBeRobbedNum(int remainBeRobbedNum) {
		this.remainBeRobbedNum = remainBeRobbedNum;
	}

	public int getMaxBeRobbedNum() {
		return maxBeRobbedNum;
	}

	public void setMaxBeRobbedNum(int maxBeRobbedNum) {
		this.maxBeRobbedNum = maxBeRobbedNum;
	}

	public int getEscortRemainTime() {
		return escortRemainTime;
	}

	public void setEscortRemainTime(int escortRemainTime) {
		this.escortRemainTime = escortRemainTime;
	}

	public int getEncourageRate() {
		return encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
	}

	public int getEscortRewardCoin() {
		return escortRewardCoin;
	}

	public void setEscortRewardCoin(int escortRewardCoin) {
		this.escortRewardCoin = escortRewardCoin;
	}

	public int getHelpRewardCoin() {
		return helpRewardCoin;
	}

	public void setHelpRewardCoin(int helpRewardCoin) {
		this.helpRewardCoin = helpRewardCoin;
	}

	public int getHelpLevelDiff() {
		return helpLevelDiff;
	}

	public void setHelpLevelDiff(int helpLevelDiff) {
		this.helpLevelDiff = helpLevelDiff;
	}

	public int getRobRewardCoin() {
		return robRewardCoin;
	}

	public void setRobRewardCoin(int robRewardCoin) {
		this.robRewardCoin = robRewardCoin;
	}

	public int getEscortTime() {
		return escortTime;
	}

	public void setEscortTime(int escortTime) {
		this.escortTime = escortTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getEscortRewardState() {
		return escortRewardState;
	}

	public void setEscortRewardState(int escortRewardState) {
		this.escortRewardState = escortRewardState;
	}

	public int getEscortState() {
		return escortState;
	}

	public void setEscortState(int escortState) {
		this.escortState = escortState;
	}

	public boolean isRobing() {
		return isRobing;
	}

	public void setRobing(boolean isRobing) {
		this.isRobing = isRobing;
	}

}
