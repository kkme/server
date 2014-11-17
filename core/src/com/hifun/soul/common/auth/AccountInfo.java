package com.hifun.soul.common.auth;

import java.util.Properties;

/**
 * 玩家账号相关信息;
 * 
 * @author crazyjohn
 * 
 */
public class AccountInfo {
	/** 通行证号 */
	private long passportId;
	/** 账号是否被锁了 */
	private int lockState;
	private int permissionType;
	/** 平台数据信息 */
	private Properties localProperties;

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public int getLockState() {
		return lockState;
	}

	public void setLockState(int lockState) {
		this.lockState = lockState;
	}

	public int getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}

	public Properties getLocalProperties() {
		return localProperties;
	}

	public void setLocalProperties(Properties localProperties) {
		this.localProperties = localProperties;
	}

}
