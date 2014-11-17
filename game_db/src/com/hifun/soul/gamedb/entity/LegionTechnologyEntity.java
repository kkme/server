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
@Table(name = "t_legion_technology")
public class LegionTechnologyEntity extends BaseCommonEntity {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	private long id;

	@Column
	private long legionId;
	@Column
	private int technologyType;
	@Column
	private int technologyLevel;
	@Column
	private int currentCoin;

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

	public int getTechnologyType() {
		return technologyType;
	}

	public void setTechnologyType(int technologyType) {
		this.technologyType = technologyType;
	}

	public int getTechnologyLevel() {
		return technologyLevel;
	}

	public void setTechnologyLevel(int technologyLevel) {
		this.technologyLevel = technologyLevel;
	}

	public int getCurrentCoin() {
		return currentCoin;
	}

	public void setCurrentCoin(int currentCoin) {
		this.currentCoin = currentCoin;
	}

}
