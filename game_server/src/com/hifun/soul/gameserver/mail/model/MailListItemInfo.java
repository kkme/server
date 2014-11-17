package com.hifun.soul.gameserver.mail.model;

import java.util.Date;
import com.hifun.soul.gameserver.mail.Mail;

/**
 * 邮件列表项数据
 * 
 * @author magicstone
 *
 */
public class MailListItemInfo {
	/** 邮件Id */
	private long mailId;
	/** 邮件类型 */
	private int mailType;
	/** 邮件主题 */
	private String theme;
	/** 发件人姓名 */
	private String sendHumanName;
	/** 是否已读取 */
	private boolean isRead;
	/** 是否含附件 */
	private boolean isAttachment;
	/** 是否已拾取附件物品 */
	private boolean isPickUp;
	/** 过期天数 */
	private int expireDays;	
	/** 是否已选中(仅用于客户端绑定数据) */
	private boolean checked;
	/** 发送时间 */
	private String sendTime;


	public MailListItemInfo(){}
	
	public MailListItemInfo(Mail mail){
		this.mailId = mail.getMailId();
		this.mailType = mail.getMailType().getIndex();
		this.theme = mail.getTheme();
		this.sendHumanName = mail.getSendHumanName();
		if(mail.getItems() == null || mail.getItems().size()==0){
			this.isAttachment = false;
		}else{
			this.isAttachment = true;
		}
		this.isPickUp = false;
		this.isRead = false;
		this.checked = false;
		Date deadlime = mail.getExpireDate();
		Date now = new Date();
		if(now.after(deadlime)){
			this.expireDays = 0;			
		}
		else{
			long diff = deadlime.getTime()-now.getTime();
			this.expireDays = (int)diff/(24*60*60*1000);
		}
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
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getSendHumanName() {
		return sendHumanName;
	}
	public void setSendHumanName(String sendHumanName) {
		this.sendHumanName = sendHumanName;
	}
	public boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}
	public boolean getIsAttachment() {
		return isAttachment;
	}
	public void setIsAttachment(boolean isAttachment) {
		this.isAttachment = isAttachment;
	}
	public boolean getIsPickUp() {
		return isPickUp;
	}
	public void setIsPickUp(boolean isPickUp) {
		this.isPickUp = isPickUp;
	}
	public int getExpireDays() {
		return expireDays;
	}
	public void setExpireDays(int expireDays) {
		this.expireDays = expireDays;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
}
