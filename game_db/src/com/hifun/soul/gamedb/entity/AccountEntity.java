package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.proto.data.entity.Entity.Account;
import com.hifun.soul.proto.data.entity.Entity.Account.Builder;

/**
 * 账号实体;<br>
 * 
 * @author crazyjohn
 * 
 */
@Entity
@Table(name = "t_account")
public class AccountEntity extends BaseProtobufEntity<Account.Builder> {
		
	public AccountEntity(Builder builder) {
		super(builder);
	}

	public AccountEntity() {
		this(Account.newBuilder());
	}

	@Id
	@Column
	@Override
	public Long getId() {
		return builder.getPassportId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setPassportId((Long) id);
	}

	@Column
	public String getUserName() {
		return builder.getUserName();
	}

	public void setUserName(String userName) {
		this.builder.setUserName(userName);
	}

	@Column
	public String getPassword() {
		return this.builder.getPassword();
	}

	public void setPassword(String password) {
		this.builder.setPassword(password);
	}

	public int getState() {
		return this.builder.getState();
	}

	@Column
	public void setState(int state) {
		this.builder.setState(state);
	}

	public int getPermissionType() {
		return this.builder.getPermissionType();
	}

	public void setPermissionType(int permissionType) {
		this.builder.setPermissionType(permissionType);
	}
}
