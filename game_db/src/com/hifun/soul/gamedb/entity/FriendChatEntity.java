package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.proto.common.FriendChats.FriendChat;
import com.hifun.soul.proto.common.FriendChats.FriendChat.Builder;

/**
 * 好友留言实体
 * 
 * @author magicstone
 * 
 */
@Entity
@Table(name = "t_friend_chat")
public class FriendChatEntity extends BaseProtobufEntity<FriendChat.Builder> {

	protected FriendChatEntity(Builder builder) {
		super(builder);
	}

	public FriendChatEntity() {
		this(FriendChat.newBuilder());
	}

	@Id
	@Column
	@Override
	public Long getId() {
		return builder.getId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setId((Long)id);
	}

	@Column
	public String getFromRoleName() {
		return builder.getFromRoleName();
	}
	
	public void setFromRoleName(String fromRoleName) {
		this.builder.setFromRoleName(fromRoleName);
	}
	
	@Column
	public Long getFromRoleId() {
		return builder.getFromRoleId();
	}
	
	public void setFromRoleId(Long fromRoleId) {
		this.builder.setFromRoleId(fromRoleId);
	}
	
	@Column
	public Long getToRoleId() {
		return builder.getToRoleId();
	}
	
	public void setToRoleId(Long toRoleId) {
		this.builder.setToRoleId(toRoleId);
	}
	
	@Column
	public String getContent() {
		return builder.getContent();
	}
	
	public void setContent(String content) {
		this.builder.setContent(content);
	}
	
	@Column
	public Long getChatDate() {
		return builder.getChatDate();
	}
	
	public void setChatDate(Long chatDate) {
		this.builder.setChatDate(chatDate);
	}
}
