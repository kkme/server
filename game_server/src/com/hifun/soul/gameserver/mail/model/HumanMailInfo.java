package com.hifun.soul.gameserver.mail.model;

import com.hifun.soul.gamedb.entity.ReceivedMailEntity;
import com.hifun.soul.gamedb.entity.SentMailEntity;

/**
 * 邮件联合查询辅助类，含邮件相关实体的全部信息
 * 
 * @author magicstone
 *
 */
public class HumanMailInfo {
	private SentMailEntity sentEntity;
	private ReceivedMailEntity receivedEntity;
	
	public HumanMailInfo() {

	}

	public HumanMailInfo(SentMailEntity sent, ReceivedMailEntity received) {
		this.sentEntity = sent;
		this.receivedEntity = received;
	}

	public SentMailEntity getSentEntity() {
		return sentEntity;
	}

	public void setSentEntity(SentMailEntity sentEntity) {
		this.sentEntity = sentEntity;
	}

	public ReceivedMailEntity getReceiveEntity() {
		return receivedEntity;
	}

	public void setReceiveEntity(ReceivedMailEntity receiveEntity) {
		this.receivedEntity = receiveEntity;
	}

}
