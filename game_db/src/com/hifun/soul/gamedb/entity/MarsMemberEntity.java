package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_mars_member")
public class MarsMemberEntity extends BaseCommonEntity {
	@Id
	@Column
	private long id;
	@Column
	private String humanName;
	@Column
	private int humanLevel;
	@Column
	private int occupation;
	@Column
	private int todayKillValue;
	@Column
	private int totalKillValue;
	@Column
	private int rewardCoin;
	@Column
	private int rewardPrestige;
	@Column
	private int rewardState;

	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getHumanLevel() {
		return humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		this.humanLevel = humanLevel;
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public int getTodayKillValue() {
		return todayKillValue;
	}

	public void setTodayKillValue(int todayKillValue) {
		this.todayKillValue = todayKillValue;
	}

	public int getTotalKillValue() {
		return totalKillValue;
	}

	public void setTotalKillValue(int totalKillValue) {
		this.totalKillValue = totalKillValue;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public int getRewardPrestige() {
		return rewardPrestige;
	}

	public void setRewardPrestige(int rewardPrestige) {
		this.rewardPrestige = rewardPrestige;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}

}
