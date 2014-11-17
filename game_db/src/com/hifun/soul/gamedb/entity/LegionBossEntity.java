package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_legion_boss")
public class LegionBossEntity extends BaseCommonEntity {
	
	/** id */
	@Id
	@Column
	private int id;
	
	/** 剩余血量 */
	@Column
	private int remainBlood;
	
	/** boss状态 */
	@Column
	private int bossState;
	
	/** 参与玩家数量 */
	@Column
	private int joinPeopleNum;
	
	/** 击杀者id */
	@Column
	private long killId;
	
	public int getRemainBlood() {
		return remainBlood;
	}

	public void setRemainBlood(int remainBlood) {
		this.remainBlood = remainBlood;
	}

	public int getBossState() {
		return bossState;
	}

	public void setBossState(int bossState) {
		this.bossState = bossState;
	}

	public int getJoinPeopleNum() {
		return joinPeopleNum;
	}

	public void setJoinPeopleNum(int joinPeopleNum) {
		this.joinPeopleNum = joinPeopleNum;
	}

	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id=(Integer)id;
	}

	public long getKillId() {
		return killId;
	}

	public void setKillId(long killId) {
		this.killId = killId;
	}
	
}
