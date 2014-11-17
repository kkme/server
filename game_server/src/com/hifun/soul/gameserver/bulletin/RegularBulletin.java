package com.hifun.soul.gameserver.bulletin;

import java.util.Date;
import com.hifun.soul.gamedb.entity.BulletinEntity;


/**
 * 周期性相同间隔时间公告
 * @author magicstone
 *
 */
public class RegularBulletin extends Bulletin {
	/** 开始日期 */
	private Date startDate;
	/** 结束日期 */
	private Date endDate;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	/** 发布时间间隔 */
	private int publishInterval;
	/** 下一次发布时间 */
	private long nextPublishTime;
	
	public RegularBulletin(){
		
	}

	
	public RegularBulletin(BulletinEntity entity){
		super(entity);
		this.nextPublishTime = entity.getPublishTime().getTime();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.startTime = entity.getStartTime();
		this.endTime = entity.getEndTime();
		this.publishInterval = entity.getPublishInterval();
	}
	
	public RegularBulletin(com.hifun.soul.proto.services.Services.Bulletin bulletin){
		super(bulletin);
		this.nextPublishTime = bulletin.getPublishTime();
		this.startDate = new Date(bulletin.getStartDate());
		this.endDate = new Date(bulletin.getEndDate());
		this.startTime = new Date(bulletin.getStartTime());
		this.endTime = new Date(bulletin.getEndTime());
		this.publishInterval = bulletin.getPublishInterval();
	}
	
	/**
	 * 获取开始日期
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * 设置开始日期
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取结束日期
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * 设置结束日期
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 获取开始时间
	 * @return
	 */	
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置公告开始发布时间
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		this.publishTime = this.startTime.getTime();
	}
	/**
	 * 获取结束时间
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置结束时间
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 获取间隔时间
	 * @return
	 */
	public int getPublishInterval() {
		return publishInterval;
	}
	
	/**
	 * 设置间隔时间
	 * @param timeSpan
	 */
	public void setPublishInterval(int publishInterval) {
		this.publishInterval = publishInterval;
	}
	
	public long getNextPublishTime() {
		return nextPublishTime;
	}

	public void setNextPublishTime(long nextPublishTime) {
		this.nextPublishTime = nextPublishTime;
	}

	@Override
	public BulletinType getBulletinType() {
		return BulletinType.REGULAR_BULLETIN;
	}
	
	/**
	 * 转换为周期性公告实体
	 * 
	 * @return
	 */
	@Override
	public BulletinEntity toEntity(){
		BulletinEntity entity  = super.toEntity();
		entity.setStartDate(this.startDate);
		entity.setEndDate(this.endDate);
		entity.setLastPublishTime(new Date(this.nextPublishTime));
		entity.setPublishInterval(this.publishInterval);
		entity.setStartTime(this.startTime);
		entity.setEndTime(this.endTime);
		return entity;		
	}
}
