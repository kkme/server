package com.hifun.soul.gameserver.human;

import com.hifun.soul.gameserver.function.GameFuncType;

/**
 * 在登陆时需要添加功能提示的管理器
 * 
 * @author magicstone
 * 
 */
public interface INotifyManager {
	/**
	 * 功能类型
	 */
	GameFuncType getFuncType();

	/**
	 * 发送功能提示
	 */
	void sendNotify();

	/**
	 * 是否提示
	 */
	boolean isNotify();
}
