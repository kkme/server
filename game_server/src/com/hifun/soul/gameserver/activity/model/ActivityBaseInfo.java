package com.hifun.soul.gameserver.activity.model;

import com.hifun.soul.gameserver.activity.Activity;
import com.hifun.soul.gameserver.activity.ActivityState;
import com.hifun.soul.gameserver.activity.template.ActivityShowTypeTemplate;

public class ActivityBaseInfo {
	/** 活动id */
	private int id;
	/** 活动类型：0表示该对象是一个分类 */
	private int activityType;
	/** 活动图标id */
	private int iconId;
	/** 活动名称 */
	private String name;
	/** 活动状态 */
	private int state;
	
	public ActivityBaseInfo(){}
	
	public ActivityBaseInfo(Activity activity){
		this.id = activity.getId();
		this.activityType = activity.getActivityShowType();
		this.iconId = activity.getIconId();
		this.name = activity.getName();
		this.state = activity.getState().getIndex();
	}
	
	public ActivityBaseInfo(ActivityShowTypeTemplate showTypeTemplate){
		this.id = showTypeTemplate.getId();
		this.activityType = 0;
		this.iconId = showTypeTemplate.getIconId();
		this.name = showTypeTemplate.getName();
		this.state = ActivityState.UNVISABLE.getIndex();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
