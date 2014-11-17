package com.hifun.soul.manageweb.mail;

import java.util.Date;
import java.util.List;

import com.hifun.soul.proto.services.Services.MailObject;

public class MailModel {
	// 邮件id
	private long mailId;
	// 邮件类型
	private int mailType;
	// 接收人id
	private String receiveHumanIds;
	// 邮件主题
	private String theme;
	// 内容
	private String content;
	// 发件人id
	private long sendHumanId;
	// 发件人姓名
	private String sendHumanName;
	// 是否有有效期
	private boolean hasExpireDate;
	// 邮件过期时间
	private Date expireDate;
	// 附件1
	private int itemId;
	// 附件2
	private int itemId2;
	// 附件1数量
	private int itemNum;
	// 附件2数量
	private int itemNum2;
	// 备注
	private String sendMemo;
	// 发送时间
	private Date sendTime;
	// 是否为定时邮件
	private boolean isTimingMail;
	// 是否有效
	private boolean valid;
	// 发送状态
	private int sendState;
	// 计划发送时间
	private Date planSendTime;
	// 创建时间
	private Date createTime;
	// 最近更新时间
	private Date lastEditTime;

	public MailModel() {
	}

	public MailModel(MailObject mailObject) {
		this.mailId = mailObject.getMailId();
		this.mailType = mailObject.getMailType();
		this.receiveHumanIds = mailObject.getReceiveHumanIds();
		this.theme = mailObject.getTheme();
		this.content = mailObject.getContent();
		this.sendHumanId = mailObject.getSendHumanId();
		this.sendHumanName = mailObject.getSendHumanName();
		this.expireDate = new Date(mailObject.getExpireDate());
		List<Integer> itemIdList = mailObject.getItemIdList();
		if (itemIdList != null && itemIdList.size() > 0) {
			this.itemId = itemIdList.get(0);
			if (itemIdList.size() > 1) {
				this.itemId2 = itemIdList.get(1);
			}
		}
		List<Integer> itemNumList = mailObject.getItemNumList();
		if(itemNumList !=null && itemNumList.size()>0){
			this.itemNum = itemNumList.get(0);
			if(itemNumList.size()>1){
				this.itemNum2 = itemNumList.get(1);
			}
		}
		this.sendMemo = mailObject.getSendMemo();
		this.sendTime = new Date(mailObject.getSendTime());
		if (this.itemId != 0) {
			this.hasExpireDate = true;
		} else {
			this.hasExpireDate = false;
		}
		this.isTimingMail = mailObject.getIsTimingMail();
		if (isTimingMail) {
			this.planSendTime = new Date(mailObject.getPlanSendTime());
		}
		this.valid = mailObject.getValid();
		this.sendState = mailObject.getSendState();
	}

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	public int getMailType() {
		return mailType;
	}

	public void setMailType(int mailType) {
		this.mailType = mailType;
	}

	public String getReceiveHumanIds() {
		return receiveHumanIds;
	}

	public void setReceiveHumanIds(String receiveHumanIds) {
		this.receiveHumanIds = receiveHumanIds;
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

	public boolean isHasExpireDate() {
		return hasExpireDate;
	}

	public void setHasExpireDate(boolean hasExpireDate) {
		this.hasExpireDate = hasExpireDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getSendMemo() {
		return sendMemo;
	}

	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getItemId2() {
		return itemId2;
	}

	public void setItemId2(int itemId2) {
		this.itemId2 = itemId2;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public int getItemNum2() {
		return itemNum2;
	}

	public void setItemNum2(int itemNum2) {
		this.itemNum2 = itemNum2;
	}

	public boolean isTimingMail() {
		return isTimingMail;
	}

	public void setTimingMail(boolean isTimingMail) {
		this.isTimingMail = isTimingMail;
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

	public Date getPlanSendTime() {
		return planSendTime;
	}

	public void setPlanSendTime(Date planSendTime) {
		this.planSendTime = planSendTime;
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

}
