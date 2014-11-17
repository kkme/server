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
@Table(name = "t_legion")
public class LegionEntity extends BaseCommonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column
	private long id;

	@Column
	private String legionName;
	@Column
	private long commanderId;
	@Column
	private String commanderName;
	@Column
	private int legionLevel;
	@Column
	private int memberLimit;
	@Column
	private int memberNum;
	@Column
	private int experience;
	@Column
	private String notice;
	@Column
	private int legionCoin;

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

	public long getCommanderId() {
		return commanderId;
	}

	public void setCommanderId(long commanderId) {
		this.commanderId = commanderId;
	}

	public String getCommanderName() {
		return commanderName;
	}

	public void setCommanderName(String commanderName) {
		this.commanderName = commanderName;
	}

	public int getLegionLevel() {
		return legionLevel;
	}

	public void setLegionLevel(int legionLevel) {
		this.legionLevel = legionLevel;
	}

	public int getMemberLimit() {
		return memberLimit;
	}

	public void setMemberLimit(int memberLimit) {
		this.memberLimit = memberLimit;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	@Override
	public Serializable getId() {
		return this.id;
	}

	public int getLegionCoin() {
		return legionCoin;
	}

	public void setLegionCoin(int legionCoin) {
		this.legionCoin = legionCoin;
	}

}
