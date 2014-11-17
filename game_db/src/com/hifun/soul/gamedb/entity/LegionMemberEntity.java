package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_legion_member")
public class LegionMemberEntity extends BaseCommonEntity {
	@Id
	@Column
	private long id;
	@Column
	private long legionId;
	@Column
	private String memberName;
	@Column
	private int level;
	@Column
	private int position;
	@Column
	private String positionName;
	@Column
	private int totalContribution;
	@Column
	private int todayContribution;
	@Column
	private long offLineTime;
	@Column
	private int todayTaskScore;
	@Column
	private int medal;
	@Column
	private int yesterdayScoreRank;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public int getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(int totalContribution) {
		this.totalContribution = totalContribution;
	}

	public int getTodayContribution() {
		return todayContribution;
	}

	public void setTodayContribution(int todayContribution) {
		this.todayContribution = todayContribution;
	}

	public long getOffLineTime() {
		return offLineTime;
	}

	public void setOffLineTime(long offLineTime) {
		this.offLineTime = offLineTime;
	}

	public int getTodayTaskScore() {
		return todayTaskScore;
	}

	public void setTodayTaskScore(int todayTaskScore) {
		this.todayTaskScore = todayTaskScore;
	}

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
	}

	public int getYesterdayScoreRank() {
		return yesterdayScoreRank;
	}

	public void setYesterdayScoreRank(int yesterdayScoreRank) {
		this.yesterdayScoreRank = yesterdayScoreRank;
	}
}
