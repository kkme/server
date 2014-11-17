package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_escort_help")
public class EscortHelpEntity extends BaseCommonEntity {
	/**
	 * 角色ID
	 */
	@Id
	@Column
	private long id;
	@Column
	private int remainHelpNum;
	@Column
	private int rewardCoin;
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

	public int getRemainHelpNum() {
		return remainHelpNum;
	}

	public void setRemainHelpNum(int remainHelpNum) {
		this.remainHelpNum = remainHelpNum;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public int getRewardState() {
		return rewardState;
	}

	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}

}
