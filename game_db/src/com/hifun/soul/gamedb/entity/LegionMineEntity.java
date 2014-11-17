package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_legion_mine")
public class LegionMineEntity extends BaseCommonEntity {
	@Id
	@Column
	private int id;
	@Column
	private boolean isDouble;

	@Override
	public void setId(Serializable id) {
		this.id = (Integer) id;
	}

	@Override
	public Serializable getId() {
		return this.id;
	}

	public boolean isDouble() {
		return isDouble;
	}

	public void setDouble(boolean isDouble) {
		this.isDouble = isDouble;
	}
}
