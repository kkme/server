package com.hifun.soul.gameserver.rechargetx;


public class RechargeHandlerFactory {
	private static QQRechargeHandler qqRechargeHandler;
	
	/**
	 * 腾讯开放平台充值管理
	 * @return
	 */
	public static IRechargeHandler getQQRechargeHandler() {
		if(qqRechargeHandler == null){
			qqRechargeHandler = new QQRechargeHandler();
		}
		return qqRechargeHandler;
	}
}
