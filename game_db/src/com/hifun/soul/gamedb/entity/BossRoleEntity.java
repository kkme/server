package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_boss_role")
public class BossRoleEntity extends BaseCommonEntity {
	
	/** id(与角色ID一致) */
	@Id
	@Column
	private long id;
	
	/** 排名 */
	@Column
	private int rank;
	
	/** 角色名称 */
	@Column
	private String name;
	
	/** 伤害值 */
	@Column
	private int damage;
	
	/** 充能值 */
	@Column
	private int chargedstrikeRate;
	
	/** 鼓舞值 */
	@Column
	private int encourageRate;
	
	/** 击杀奖励 */
	@Column
	private boolean hasKillReward;
	
	/** 排名奖励 */
	@Column
	private boolean hasRankReward;
	
	/** 伤害奖励 */
	@Column
	private boolean hasDamageReward;
	
	/** 是否参与 */
	@Column
	private boolean isJoin;

	/** 阶段奖励 */
	@Column
	private int stageReward;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getChargedstrikeRate() {
		return chargedstrikeRate;
	}

	public void setChargedstrikeRate(int chargedstrikeRate) {
		this.chargedstrikeRate = chargedstrikeRate;
	}

	public int getEncourageRate() {
		return encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
	}

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

	public boolean getHasKillReward() {
		return hasKillReward;
	}

	public void setHasKillReward(boolean hasKillReward) {
		this.hasKillReward = hasKillReward;
	}

	public boolean getHasRankReward() {
		return hasRankReward;
	}

	public void setHasRankReward(boolean hasRankReward) {
		this.hasRankReward = hasRankReward;
	}

	public boolean getHasDamageReward() {
		return hasDamageReward;
	}

	public void setHasDamageReward(boolean hasDamageReward) {
		this.hasDamageReward = hasDamageReward;
	}

	public boolean getIsJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public int getStageReward() {
		return stageReward;
	}

	public void setStageReward(int stageReward) {
		this.stageReward = stageReward;
	}
	
}
