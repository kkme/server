package com.hifun.soul.aspect.qq;

public enum CompassInterfaceType {
	/** 登录接口 */
	LOGIN("login"),
	/** 注册接口 */
	REGISTER("register"),
	/** 消费接口 */
	CONSUME("consume");

	private String desc;

	CompassInterfaceType(String desc) {
		this.desc = desc;
	}

	public String getInterfaceDesc() {
		return this.desc;
	}
}
