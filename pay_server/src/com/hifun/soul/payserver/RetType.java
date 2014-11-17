package com.hifun.soul.payserver;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 结果码;
 * 
 * @author crazyjohn
 * 
 */
public enum RetType implements IndexedEnum {
	// 0: 成功
	// 1: 系统繁忙
	// 2: token已过期
	// 3: token不存在
	// 4: 请求参数错误：（这里填写错误的具体参数）
	OK(0, "OK"), SYSTEM_BUSY(1, "SYSTEM_BUSY"), TOKEN_TIMEOUT(2,
			"TOKEN_TIMEOUT"), NO_THIS_TOKEN(3, "NO_THIS_TOKEN"), PARAMS_WRONG(
			4, "PARAMS_WRONG"), NET_ERROR(4, "CAN_NOT_CONNECT_TO_GAME_SERVER");

	private int ret;
	private String desc = "";
	private String param;

	RetType(int ret, String desc) {
		this.ret = ret;
		this.desc = desc;
	}

	@Override
	public int getIndex() {
		return ret;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
