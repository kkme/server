package com.hifun.soul.gameserver.activity;

import java.util.Date;
import java.util.List;

import com.hifun.soul.core.util.KeyValuePair;

/**
 * 活动
 * 
 * @author magicstone
 *
 */
public class Activity {
	/** 活动id */
	private int id;
	/** 名称 */
	private String name;
	/** 详细描述 */
	private String desc;
	/** 简单描述 */
	private String simpleDesc;
	/** 活动类型 */
	private int activityShowType;
	/** 可视等级 */
	private int visibleLevel;
	/** 开启等级 */
	private int openLevel;
	/** 是否定点开放 */
	private boolean isTiming;
	/** 开始日期 */
	private Date startDate;
	/** 结束日期 */
	private Date endDate;
	/** key:开始时间,value:结束时间 */
	private List<KeyValuePair<Long,Long>> activeTimeSpan;
	/** 活动状态 */
	private ActivityState state;
	/** 当天活动的结束时间 */
	private long finishTime;
	/** 当天活动的最后一次开始时间 */
	private long lastBeginTime;
	/** 下一个执行时间段索引 */
	private int nextTimeSpanIndex;
	/** 图标id*/
	private int iconId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}	
	public String getSimpleDesc() {
		return simpleDesc;
	}
	public void setSimpleDesc(String simpleDesc) {
		this.simpleDesc = simpleDesc;
	}
	public int getActivityShowType() {
		return activityShowType;
	}
	public void setActivityShowType(int activityShowType) {
		this.activityShowType = activityShowType;
	}
	public int getVisibleLevel() {
		return visibleLevel;
	}
	public void setVisibleLevel(int visibleLevel) {
		this.visibleLevel = visibleLevel;
	}
	public int getOpenLevel() {
		return openLevel;
	}
	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}
	public boolean isTiming() {
		return isTiming;
	}
	public void setTiming(boolean isTiming) {
		this.isTiming = isTiming;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<KeyValuePair<Long, Long>> getActiveTimeSpan() {
		return activeTimeSpan;
	}
	public void setActiveTimeSpan(List<KeyValuePair<Long, Long>> activeTimeSpan) {
		this.activeTimeSpan = activeTimeSpan;
	}
	public ActivityState getState() {
		return state;
	}
	public void setState(ActivityState state) {
		this.state = state;
	}	
	public long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}
	public long getLastBeginTime() {
		return lastBeginTime;
	}
	public void setLastBeginTime(long lastBeginTime) {
		this.lastBeginTime = lastBeginTime;
	}
	public int getNextTimeSpanIndex() {
		return nextTimeSpanIndex;
	}
	public void setNextTimeSpanIndex(int nextTimeSpanIndex) {
		this.nextTimeSpanIndex = nextTimeSpanIndex;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	public String toString(){
		return "activity [id:"+id+",name:"+name+",desc:"+desc+",simpleDesc:"+simpleDesc+",activityShowType:"+activityShowType+",visibleLevel:"+visibleLevel
				+",openLevel:"+openLevel+",isTiming:" + isTiming +",startDate:"+ startDate + ",endDate:"+ endDate +",activeTimeSpan:"+ activeTimeSpan 
				+",state:"+ state +",finishTime:"+finishTime+",lastBeginTime:"+lastBeginTime+",nextTimeSpanIndex:"+nextTimeSpanIndex;
	}
}
