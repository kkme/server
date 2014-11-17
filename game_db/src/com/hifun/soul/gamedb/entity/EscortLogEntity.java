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
@Table(name = "t_escort_log")
public class EscortLogEntity extends BaseCommonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column
	private long id;
	@Column
	private long firstId;
	@Column
	private String firstName;
	@Column
	private long secondId;
	@Column
	private String secondName;
	@Column
	private String monserName;
	@Column
	private int robCoin;
	@Column
	private long insertTime;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public long getFirstId() {
		return firstId;
	}

	public void setFirstId(long firstId) {
		this.firstId = firstId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public long getSecondId() {
		return secondId;
	}

	public void setSecondId(long secondId) {
		this.secondId = secondId;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getMonserName() {
		return monserName;
	}

	public void setMonserName(String monserName) {
		this.monserName = monserName;
	}

	public int getRobCoin() {
		return robCoin;
	}

	public void setRobCoin(int robCoin) {
		this.robCoin = robCoin;
	}

	public long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}

}
