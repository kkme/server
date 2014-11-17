package com.hifun.soul.gamedb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name="t_mail_draft")
public class MailDraftEntity extends BaseCommonEntity {
	/** id */	
	@Id
	@Column
	private long id;
	/** 发送人id */
	@Column
	private long sendHumanId;
	/** 发送人名称 */
	@Column
	private String sendHumanName;
	/** 主题 */
	@Column
	private String theme;
	/** 内容 */
	@Column
	private String content;	
	/** 是否有附件 */
	@Column
	private boolean isAttachment;
	/** 物品id */
	@Column
	private String itemIds;
	/** 计划发送时间 */
	@Column
	private Date sendTime;
	/** 接收人id列表 */
	@Column
	private String receiveHumanId;
	/** 发送备注 */
	@Column
	private String sendMemo;
	/** 过期时间 */
	@Column
	private Date expireDate;
	/** 是否有效 */
	@Column
	private boolean valid;
	/** 发送状态 */
	@Column
	private int sendState;
	/** 创建时间 */
	@Column
	private Date createTime;
	/** 上次编辑时间 */
	@Column
	private Date lastEditTime;
	/** 计划发送时间 */
	@Column
	private Date planSendTime;
	/** 是否为定时发送邮件 */
	@Column
	private boolean isTimingMail;	
	
	public long getSendHumanId() {
		return sendHumanId;
	}

	public void setSendHumanId(long sendHumanId) {
		this.sendHumanId = sendHumanId;
	}

	public String getSendHumanName() {
		return sendHumanName;
	}

	public void setSendHumanName(String sendHumanName) {
		this.sendHumanName = sendHumanName;
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

	public boolean isAttachment() {
		return isAttachment;
	}

	public void setAttachment(boolean isAttachment) {
		this.isAttachment = isAttachment;
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

	public String getReceiveHumanId() {
		return receiveHumanId;
	}

	public void setReceiveHumanId(String receiveHumanId) {
		this.receiveHumanId = receiveHumanId;
	}

	public String getSendMemo() {
		return sendMemo;
	}

	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public int getSendState() {
		return sendState;
	}

	public void setSendState(int sendState) {
		this.sendState = sendState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	@Override
	public Serializable getId() {		
		return id;
	}

	@Override
	public void setId(Serializable id) {		
		this.id= (Long)id;
	}

	public Date getPlanSendTime() {
		return planSendTime;
	}

	public void setPlanSendTime(Date planSendTime) {
		this.planSendTime = planSendTime;
	}

	public boolean isTimingMail() {
		return isTimingMail;
	}

	public void setTimingMail(boolean isTimingMail) {
		this.isTimingMail = isTimingMail;
	}

}
