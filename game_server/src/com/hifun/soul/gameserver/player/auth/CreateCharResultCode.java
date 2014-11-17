package com.hifun.soul.gameserver.player.auth;

/**
 * 创建角色结果码;
 * 
 * @author crazyjohn
 * 
 */
public enum CreateCharResultCode {
	/** 创建角色成功 */
	SUCCESS(0),
	/** 创建角色失败 */
	CREATE_CHAR_FAILED(1), 
	/** 用户名存在 */
	USERNAME_EXIST(2), 
	CHAR_FULL(3),;
	private int resultCode;

	CreateCharResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getResultCode() {
		return this.resultCode;
	}
}
