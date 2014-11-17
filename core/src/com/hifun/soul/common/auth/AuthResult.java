package com.hifun.soul.common.auth;

/**
 * 认证结果Bean;
 * 
 * @author crazyjohn
 * 
 */
public class AuthResult implements IAuthResult {
	private boolean isSucceed;
	private String errorInfo;
	private AccountInfo accountInfo;

	@Override
	public boolean isSucceed() {
		return isSucceed;
	}

	@Override
	public String getErrorInfo() {
		return errorInfo;
	}

	@Override
	public AccountInfo getAccoutInfo() {
		return accountInfo;
	}

	public void setSucceed(boolean isSucceed) {
		this.isSucceed = isSucceed;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

}
