package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_legion_apply")
public class LegionApplyEntity extends BaseCommonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment") 
	@Column
	private long id;
	@Column
	private long applyHumanGuid;
	@Column
	private long applyLegionId;
	@Column
	private String applyHumanName;
	@Column
	private int applyHumanLevel;
	@Column
	private int applyArenaRank;
	@Column
	private int applyStatus;
	@Column
	private long applyTime;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public long getApplyHumanGuid() {
		return applyHumanGuid;
	}

	public void setApplyHumanGuid(long applyHumanGuid) {
		this.applyHumanGuid = applyHumanGuid;
	}

	public long getApplyLegionId() {
		return applyLegionId;
	}

	public void setApplyLegionId(long applyLegionId) {
		this.applyLegionId = applyLegionId;
	}

	public String getApplyHumanName() {
		return applyHumanName;
	}

	public void setApplyHumanName(String applyHumanName) {
		this.applyHumanName = applyHumanName;
	}

	public int getApplyHumanLevel() {
		return applyHumanLevel;
	}

	public void setApplyHumanLevel(int applyHumanLevel) {
		this.applyHumanLevel = applyHumanLevel;
	}

	public int getApplyArenaRank() {
		return applyArenaRank;
	}

	public void setApplyArenaRank(int applyArenaRank) {
		this.applyArenaRank = applyArenaRank;
	}

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}

	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

}
