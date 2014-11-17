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
@Table(name = "t_legion_honor")
public class LegionHonorEntity extends BaseCommonEntity {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	private long id;

	@Column
	private long legionId;
	@Column
	private int titleId;
	@Column
	private int exchangeNum;

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

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public int getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(int exchangeNum) {
		this.exchangeNum = exchangeNum;
	}

}
