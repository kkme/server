package com.hifun.soul.gameserver.bulletin;

import java.util.Date;
import com.hifun.soul.gamedb.entity.BulletinEntity;

/**
 * 普通公告
 * @author magicstone
 *
 */
public abstract class Bulletin {
	/** 唯一标识 */
	protected int id;
	/** 公告内容 */
	protected String content;
	/** 发布时间 */
	protected long publishTime;
	/** 客户端显示时长 (单位：秒)*/
	protected int showTime;
	/** 公告级别 （决定客户端的显示方式）*/
	protected int level;
	/** 是否有效 */
	protected boolean valid;
	
	public Bulletin(){
		
	}	
	
	
	public Bulletin(BulletinEntity entity){
		this.id = entity.getId();
		this.content = entity.getContent();
		this.publishTime = entity.getPublishTime().getTime();
		this.showTime = entity.getShowTime();
		this.level = entity.getLevel();
		this.valid = entity.isValid();
	}
	
	public Bulletin(com.hifun.soul.proto.services.Services.Bulletin bulletin){
		this.id = bulletin.getId();
		this.content = bulletin.getContent();
		this.publishTime = bulletin.getPublishTime();
		this.showTime = bulletin.getShowTime();
		this.level = bulletin.getLevel();
		this.valid = bulletin.getValid();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(long publishTime) {
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
	
	public boolean getValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	/**
	 * 获取公告类型
	 * 
	 * @return
	 */
	public abstract BulletinType getBulletinType(); 
	
	
	public BulletinEntity toEntity(){	
		BulletinEntity entity = new BulletinEntity();
		entity.setId(this.id);
		entity.setBulletinType(this.getBulletinType().getIndex());
		entity.setContent(this.content);
		entity.setLevel(this.level);
		entity.setPublishTime(new Date(this.publishTime));
		entity.setShowTime(this.showTime);
		entity.setValid(this.valid);
		return entity;
	}
	
}
