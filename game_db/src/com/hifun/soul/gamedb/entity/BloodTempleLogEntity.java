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
@Table(name = "t_blood_temple_log")
public class BloodTempleLogEntity extends BaseCommonEntity {

	/** id */
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
	private int firstLevel;

	@Column
	private long secondId;

	@Column
	private int result;

	@Column
	private long lootTime;

	public long getLootTime() {
		return lootTime;
	}

	public void setLootTime(long lootTime) {
		this.lootTime = lootTime;
	}

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

	public int getFirstLevel() {
		return firstLevel;
	}

	public void setFirstLevel(int firstLevel) {
		this.firstLevel = firstLevel;
	}

	public long getSecondId() {
		return secondId;
	}

	public void setSecondId(long secondId) {
		this.secondId = secondId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
