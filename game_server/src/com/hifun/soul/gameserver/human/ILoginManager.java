package com.hifun.soul.gameserver.human;

/**
 * 
 * 登陆时如果有什么需要特殊处理实现该接口
 * 
 * 在onLoad方法，也就是加载完数据库数据进入场景是调用
 * 
 * @author magicstone
 *
 */
public interface ILoginManager {

	public void onLogin();
}
