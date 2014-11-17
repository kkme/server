package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_legion_mine_member")
public class LegionMineMemberEntity extends BaseCommonEntity {
	@Id
	@Column
	private long id;

	@Column
	private int mineIndex;
	@Column
	private int occupyValue;
	@Column
	private long occupyTime;
	@Column
	private int encourageRate;
	@Column
	private int attackRate;
	@Column
	private int defenseRate;
	@Column
	private boolean isQuit;
	@Column
	private boolean isJoin;
	@Column
	private int rank;
	@Column
	private boolean isLegionWin;

	public int getMineIndex() {
		return mineIndex;
	}

	public void setMineIndex(int mineIndex) {
		this.mineIndex = mineIndex;
	}

	public int getOccupyValue() {
		return occupyValue;
	}

	public void setOccupyValue(int occupyValue) {
		this.occupyValue = occupyValue;
	}

	public long getOccupyTime() {
		return occupyTime;
	}

	public void setOccupyTime(long occupyTime) {
		this.occupyTime = occupyTime;
	}

	public int getEncourageRate() {
		return encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
	}

	public int getAttackRate() {
		return attackRate;
	}

	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}

	public int getDefenseRate() {
		return defenseRate;
	}

	public void setDefenseRate(int defenseRate) {
		this.defenseRate = defenseRate;
	}

	public boolean isQuit() {
		return isQuit;
	}

	public void setQuit(boolean isQuit) {
		this.isQuit = isQuit;
	}

	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean isLegionWin() {
		return isLegionWin;
	}

	public void setLegionWin(boolean isLegionWin) {
		this.isLegionWin = isLegionWin;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	@Override
	public Serializable getId() {
		return this.id;
	}

}
