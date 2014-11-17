package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_escort")
public class EscortEntity extends BaseCommonEntity {
	/**
	 * 押运者ID
	 */
	@Id
	@Column
	private long id;
	@Column
	private int monsterType;
	@Column
	private long ownerId;
	@Column
	private String ownerName;
	@Column
	private int ownerLevel;
	@Column
	private long helperId;
	@Column
	private String helperName;
	@Column
	private int helperLevel;
	@Column
	private int beRobbedNum;
	@Column
	private int encourageRate;
	@Column
	private int escortTime;
	@Column
	private long endTime;
	@Column
	private int escortRewardCoin;
	@Column
	private int helpLevelDiff;
	@Column
	private int escortRewardState;
	@Column
	private int escortState;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
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

	public int getBeRobbedNum() {
		return beRobbedNum;
	}

	public void setBeRobbedNum(int beRobbedNum) {
		this.beRobbedNum = beRobbedNum;
	}

	public int getEncourageRate() {
		return encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getEscortTime() {
		return escortTime;
	}

	public void setEscortTime(int escortTime) {
		this.escortTime = escortTime;
	}

	public int getEscortRewardCoin() {
		return escortRewardCoin;
	}

	public void setEscortRewardCoin(int escortRewardCoin) {
		this.escortRewardCoin = escortRewardCoin;
	}

	public int getHelpLevelDiff() {
		return helpLevelDiff;
	}

	public void setHelpLevelDiff(int helpLevelDiff) {
		this.helpLevelDiff = helpLevelDiff;
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

}
