package com.hifun.soul.gamedb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.hifun.soul.core.orm.BaseCommonEntity;


@Entity
@Table(name = "t_mail_receive")
public class ReceivedMailEntity extends BaseCommonEntity{
	
	@Id
	@Column(name="id")
	private long id;
	
	/** 邮件Id */
	@Column(name="mailId")
	private long mailId;
	
	/** 接收人Id */
	@Column(name="receiveHumanId")
	private long receiveHumanId;

	/** 是否已读取 */
	@Column(name="isRead")
	private boolean isRead;	
	
	/** 是否已拾取附件物品 */
	@Column(name="isPickUp")
	private boolean isPickUp;

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	public long getReceiveHumanId() {
		return receiveHumanId;
	}

	public void setReceiveHumanId(long receiveHumanId) {
		this.receiveHumanId = receiveHumanId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean isPickUp() {
		return isPickUp;
	}

	public void setPickUp(boolean isPickUp) {
		this.isPickUp = isPickUp;
	}

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id=(Long)id;
	}
	
}
