package com.hifun.soul.gameserver.compass;

/**
 * 腾讯的汇报接口类型;
 * 
 * @author crazyjohn
 * 
 */
public enum QQCompassInterfaceType {
	/** 登录接口 */
	LOGIN("login"),
	/** 注册接口 */
	REGISTER("register"),
	/** 消费接口 */
	CONSUME("consume"),
	/** 充值接口 */
	RECHARGE("recharge");

	private String desc;

	QQCompassInterfaceType(String desc) {
		this.desc = desc;
	}

	public String getInterfaceDesc() {
		return this.desc;
	}
}
