
package com.hifun.soul.common.auth;

/**
 * 封装认证结果;
 * 
 * @author crazyjohn
 * 
 */
public interface IAuthResult {

	/**
	 * 是否认证成功;
	 * 
	 * @return true 表示认证成功; false 表示认证失败;
	 */
	public boolean isSucceed();

	/**
	 * 获取失败信息;
	 * 
	 * @return
	 */
	public String getErrorInfo();

	/**
	 * 获取玩家账号信息;
	 * 
	 * @return
	 */
	public AccountInfo getAccoutInfo();

}
