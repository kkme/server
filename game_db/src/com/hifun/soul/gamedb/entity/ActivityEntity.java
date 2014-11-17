package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

/**
 * 全局活动状态实体
 * @author magicstone
 *
 */
@Entity
@Table(name = "t_activity")
public class ActivityEntity extends BaseCommonEntity{
	/** 活动id */
	@Id
	@Column
	private int id;
	/** 活动状态 */
	@Column
	private int activityState;
	/** 下一个时间段索引 */
	@Column
	private int timeSpanIndex;
	/** 修改时间 */
	@Column
	private long editTime;
	@Override
	public Serializable getId() {		
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Integer)id;	
	}

	public int getActivityState() {
		return activityState;
	}

	public void setActivityState(int activityState) {
		this.activityState = activityState;
	}

	public int getTimeSpanIndex() {
		return timeSpanIndex;
	}

	public void setTimeSpanIndex(int timeSpanIndex) {
		this.timeSpanIndex = timeSpanIndex;
	}

	public long getEditTime() {
		return editTime;
	}

	public void setEditTime(long editTime) {
		this.editTime = editTime;
	}

}
