package com.hifun.soul.gameserver.player.auth.strategy;

import java.util.List;

import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 玩家认证策略;<br>
 * 
 * <pre>
 * 1. 可以使本地认证策略;
 * 2. 可以使不同平台的认证策略;
 * 3. easy to mock and test!
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public interface IAuthStrategy {
	/**
	 * 使用用户名和密码进行认证;
	 * 
	 * @param userName
	 *            用户名;
	 * @param password
	 *            密码;
	 * @param ip
	 *            IP;
	 */
	public void doAuthAction(String userName, String password, String ip, IDBCallback<List<?>> dbCallback);
}
