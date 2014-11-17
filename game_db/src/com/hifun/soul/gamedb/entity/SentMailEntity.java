package com.hifun.soul.gamedb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_mail_send")
public class SentMailEntity extends BaseCommonEntity {
	/** 邮件id */
	@Id
	@Column(name="Id")
	private long mailId;	
	
	/** 邮件类型 */
	@Column(name="mailType")
	private int mailType;
	
	/** 收件人id列表 */
	@Column(name="receiveHumanId")
	private String receiveHumanId;
	
	/** 邮件主题 */
	@Column(name="theme")
	private String theme;
	
	/** 邮件内容 */
	@Column(name="content")
	private String content;
	
	/** 发件人id */
	@Column(name="sendHumanId")
	private Long sendHumanId;
	
	/** 发件人姓名 */
	@Column(name="sendHumanName")
	private String sendHumanName;
	
	/** 邮件过期时间*/
	@Column(name="expireDate")
	private Date expireDate;
	
	/** 邮件附带物品 */
	@Column(name="itemIds")
	private String itemIds;
	
	/** 发送时间 */
	@Column(name="sendTime")
	private Date sendTime;
	
	/** 是否含有附件 */
	@Column(name="isAttachment")
	private boolean isAttachment;
	
	/** 备注信息 */
	@Column(name="sendMemo")
	private String sendMemo;
	
	/** 邮件附带物品数量 */
	@Column(name="itemNums")
	private String itemNums;
	
	@Override
	public Serializable getId() {		
		return mailId;
	}
	@Override
	public void setId(Serializable id) {
		this.mailId = (Long)id;
	}
	
	public int getMailType() {
		return mailType;
	}
	public void setMailType(int mailType) {
		this.mailType = mailType;
	}
	public String getReceiveHumanId() {
		return receiveHumanId;
	}
	public void setReceiveHumanId(String receiveHumanId) {
		this.receiveHumanId = receiveHumanId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getSendHumanId() {
		return sendHumanId;
	}
	public void setSendHumanId(Long sendHumanId) {
		this.sendHumanId = sendHumanId;
	}
	public String getSendHumanName() {
		return sendHumanName;
	}
	public void setSendHumanName(String sendHumanName) {
		this.sendHumanName = sendHumanName;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public boolean isAttachment() {
		return isAttachment;
	}
	public void setAttachment(boolean isAttachment) {
		this.isAttachment = isAttachment;
	}
	public String getSendMemo() {
		return sendMemo;
	}
	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}
	public String getItemNums() {
		return itemNums;
	}
	public void setItemNums(String itemNums) {
		this.itemNums = itemNums;
	}
	
}
