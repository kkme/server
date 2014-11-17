package com.hifun.soul.gamedb.entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import com.hifun.soul.core.orm.BaseCommonEntity;


@Entity
@Table(name = "t_bulletin")
public class BulletinEntity extends BaseCommonEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment") 
	@Column
	private int id;
	
	/** 公告内容 */
	@Column(name="content")
	private String content;
	
	/** 发布时间 */
	@Column(name="publishTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishTime;
	
	/** 客户端显示时长（s） */
	@Column(name="showTime")
	private int showTime;
	
	/** 公告级别 */
	@Column(name="level")
	private int level;
	
	/** 公告类型 */
	@Column(name="bulletinType")
	private int bulletinType;
	
	/** 是否有效 */
	@Column(name="valid")
	private boolean valid;
	/** 公告有效开始日期 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date startDate;
	/** 公告有效截止日期 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date endDate;
	/** 每日循环发布开始时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date startTime;
	/** 每日循环发布截止时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date endTime;
	/** 上次发布时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastPublishTime;
	/** 发布间隔时间（s） */
	@Column
	private int publishInterval;
	/** 公告发布时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date createTime;
	
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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
	
	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Integer)id;
		
	}
	
}
