package com.hifun.soul.manageserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.proto.services.Services.User;
import com.hifun.soul.proto.services.Services.User.Builder;

/**
 * 用户实体;
 * 
 * @author crazyjohn
 * 
 */
@Entity
@Table(name = "t_user_info")
public class UserEntity extends BaseProtobufEntity<User.Builder> {

	public UserEntity(Builder builder) {
		super(builder);
	}
	
	public UserEntity() {
		this(User.newBuilder());
	}

	@Id
	@Column
	@Override
	public Integer getId() {
		return this.builder.getId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setId((Integer) id);
	}

	public void setUserName(String userName) {
		this.builder.setUserName(userName);
	}

	@Column
	public String getUserName() {
		return this.builder.getUserName();
	}

	public void setPassword(String password) {
		this.builder.setPassword(password);
	}

	@Column
	public String getPassword() {
		return this.builder.getPassword();
	}

	@Column
	public String getPermissions() {
		return builder.getPermissions();
	}

	public void setPermissions(String permissions) {
		this.builder.setPermissions(permissions);
	}
}
