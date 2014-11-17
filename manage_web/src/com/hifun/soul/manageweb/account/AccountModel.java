package com.hifun.soul.manageweb.account;

import com.hifun.soul.proto.data.entity.Entity.Account;

public class AccountModel {
	
	public AccountModel(){}
	
	public AccountModel(Account account)
	{
		this.passportId = account.getPassportId();
		this.password = account.getPassword();
		this.userName = account.getUserName();
		this.permissionType = account.getPermissionType();
		this.state = account.getState();
	}
	
	public Account.Builder toAccountBuiler()
	{
		Account.Builder builder = Account.newBuilder();
		builder.setPassportId(passportId);
		builder.setPassword(password);
		builder.setUserName(userName);
		builder.setPermissionType(permissionType);
		builder.setState(state);
		return builder;
	}
	
	private long passportId;
	private String password;
	private String userName;
	private int permissionType;
	private int state;
	
	
	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
