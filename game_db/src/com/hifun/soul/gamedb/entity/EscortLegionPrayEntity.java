package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_escort_legion_pray")
public class EscortLegionPrayEntity extends BaseCommonEntity {
	/**
	 * 军团ID
	 */
	@Id
	@Column
	private long id;
	@Column
	private long prayMemberId;
	@Column
	private String prayMemberName;
	@Column
	private long endTime;

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public long getPrayMemberId() {
		return prayMemberId;
	}

	public void setPrayMemberId(long prayMemberId) {
		this.prayMemberId = prayMemberId;
	}

	public String getPrayMemberName() {
		return prayMemberName;
	}

	public void setPrayMemberName(String prayMemberName) {
		this.prayMemberName = prayMemberName;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
