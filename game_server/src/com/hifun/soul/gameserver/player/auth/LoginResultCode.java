package com.hifun.soul.gameserver.player.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录结果码;
 * 
 * @author crazyjohn
 * 
 */
public enum LoginResultCode {
	/** 登录成功 */
	SUCCESS(0),
	/** 玩家状态异常 */
	STATE_ERROR(1),
	/** 重复登录 */
	RELOGIN(2),
	/** 账号被锁定 */
	LOCKED(3),
	/** 用户名和密码错误 */
	USERNAME_PASSWORD_WRONG(4);
	private int code;
	private static Map<Integer, LoginResultCode> codes = new HashMap<Integer, LoginResultCode>();
	
	static {
		for (LoginResultCode code : LoginResultCode.values()) {
			codes.put(code.getResultCode(), code);
		}
	}

	LoginResultCode(int code) {
		this.code = code;
	}

	public int getResultCode() {
		return code;
	}
	
	public static LoginResultCode typeOf(int type) {
		return codes.get(type);
	}
}
