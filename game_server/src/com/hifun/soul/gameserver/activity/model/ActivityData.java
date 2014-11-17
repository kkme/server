package com.hifun.soul.gameserver.activity.model;

import java.util.Calendar;

import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.activity.Activity;

/**
 * 用于消息传输的活动信息
 * 
 * @author magicstone
 *
 */
public class ActivityData {
	/** 活动id */
	private int id;
	/** 名称 */
	private String name;
	/** 简单描述 */
	private String simpleDesc;
	/** 活动显示类型 */
	private int activityShowType;
	/** 开启等级 */
	private int openLevel;
	/** 开启时间信息 */
	private String activeTimeInfo;
	/** 活动状态 */
	private int state;
	/** 活动图标id */
	private int iconId;
	
	public ActivityData(){}
	
	public ActivityData(Activity activity){
		this.id = activity.getId();
		this.name = activity.getName();
		this.simpleDesc = activity.getSimpleDesc();
		this.activityShowType = activity.getActivityShowType();
		this.openLevel = activity.getOpenLevel();
		this.state = activity.getState().getIndex();
		this.iconId = activity.getIconId();
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		for(KeyValuePair<Long,Long> pair : activity.getActiveTimeSpan()){
			sb.append(TimeUtils.formatHMTime(pair.getKey()+TimeUtils.getBeginOfDay(cal.getTimeInMillis())));
			sb.append("-");
			sb.append(TimeUtils.formatHMTime(pair.getValue()+TimeUtils.getBeginOfDay(cal.getTimeInMillis())));
			sb.append(",");
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		activeTimeInfo = sb.toString();
	}
	
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
	public int getOpenLevel() {
		return openLevel;
	}
	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
	}
	public String getActiveTimeInfo() {
		return activeTimeInfo;
	}
	public void setActiveTimeInfo(String activeTimeInfo) {
		this.activeTimeInfo = activeTimeInfo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

}
