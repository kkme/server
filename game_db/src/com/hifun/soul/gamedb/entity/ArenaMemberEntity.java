package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_arena_member")
public class ArenaMemberEntity extends BaseCommonEntity {
	
	@Id
	@Column
	private long id;
	
	/** 排名 */
	@Column
	private int rank;
	
	/** 头像 */
	@Column
	private int icon;
	
	/** 名称 */
	@Column
	private String name;
	
	/** 等级 */
	@Column
	private int level;
	
	/** 排名奖励id */
	@Column
	private int rankRewardId;
	
	/** 排名奖励领取情况 */
	@Column
	private int rankRewardState;
	
	/** 职业 */
	@Column
	private int occupation;
	
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id=(Long)id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRankRewardId() {
		return rankRewardId;
	}

	public void setRankRewardId(int rankRewardId) {
		this.rankRewardId = rankRewardId;
	}

	public int getRankRewardState() {
		return rankRewardState;
	}

	public void setRankRewardState(int rankRewardState) {
		this.rankRewardState = rankRewardState;
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}
	
}
