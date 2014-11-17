package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_abattoir_honor")
public class HumanAbattoirPrestigeEntity extends BaseCommonEntity {

	/** id */
	@Id
	@Column
	private long id;

	/** 上次提取时间 */
	@Column
	private long lastExtractTime;

	/** 威望 */
	@Column
	private int honor;

	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public long getLastExtractTime() {
		return lastExtractTime;
	}

	public void setLastExtractTime(long lastExtractTime) {
		this.lastExtractTime = lastExtractTime;
	}

	public int getHonor() {
		return honor;
	}

	public void setHonor(int honor) {
		this.honor = honor;
	}

}
