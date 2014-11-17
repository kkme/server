package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_escort_rob_rank")
public class EscortRobRankEntity extends BaseCommonEntity {
	/**
	 * 角色ID
	 */
	@Id
	@Column
	private long id;
	@Column
	private String robberName;
	@Column
	private int yesterdayRobCoin;
	@Column
	private int todayRobCoin;
	@Column
	private int rewardState;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public String getRobberName() {
		return robberName;
	}

	public void setRobberName(String robberName) {
		this.robberName = robberName;
	}

	public int getYesterdayRobCoin() {
		return yesterdayRobCoin;
	}

	public void setYesterdayRobCoin(int yesterdayRobCoin) {
		this.yesterdayRobCoin = yesterdayRobCoin;
	}

	public int getTodayRobCoin() {
		return todayRobCoin;
	}

	public void setTodayRobCoin(int todayRobCoin) {
		this.todayRobCoin = todayRobCoin;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}

}
