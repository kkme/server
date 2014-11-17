package com.hifun.soul.manageweb.bulletin;

import java.util.Date;

public class BulletinModel {

	private int id;

	/** 公告内容 */
	private String content;
	/** 发布时间 */
	private Date publishTime;
	/** 客户端显示时长（s） */
	private int showTime;
	/** 公告级别 */
	private int level;
	/** 公告类型 */
	private int bulletinType;
	/** 是否有效 (0无效，1有效 )[默认值=1]*/
	private int validState=1;
	/** 公告有效开始日期 */
	private Date startDate;
	/** 公告有效截止日期 */
	private Date endDate;
	/** 每日循环发布开始时间 */
	private Date startTime;
	/** 每日循环发布截止时间 */
	private Date endTime;
	/** 上次发布时间 */
	private Date lastPublishTime;
	/** 发布间隔时间（s） */
	private int publishInterval;
	/** 公告创建时间 */
	private Date createTime;
	/** 公告发布时间字符串 */
	private String publishTimeString;
	/** 每日循环发布开始时间字符串 */
	private String startTimeString;
	/** 每日循环发布截止时间字符串 */
	private String endTimeString;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public int getShowTime() {
		return showTime;
	}

	public void setShowTime(int showTime) {
		this.showTime = showTime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBulletinType() {
		return bulletinType;
	}

	public void setBulletinType(int bulletinType) {
		this.bulletinType = bulletinType;
	}

	public int getValidState() {
		return validState;
	}

	public void setValidState(int validState) {
		this.validState = validState;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getLastPublishTime() {
		return lastPublishTime;
	}

	public void setLastPublishTime(Date lastPublishTime) {
		this.lastPublishTime = lastPublishTime;
	}

	public int getPublishInterval() {
		return publishInterval;
	}

	public void setPublishInterval(int publishInterval) {
		this.publishInterval = publishInterval;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPublishTimeString() {
		return publishTimeString;
	}

	public void setPublishTimeString(String publishTimeString) {
		this.publishTimeString = publishTimeString;
	}

	public String getStartTimeString() {
		return startTimeString;
	}

	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}

	public String getEndTimeString() {
		return endTimeString;
	}

	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;

	}

}
