package com.hifun.soul.gameserver.player.auth.local;

import org.springframework.stereotype.Component;

import com.hifun.soul.gameserver.rechargetx.IRechargeHandler;

/**
 * 平台处理器工厂;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class LocalHandlerFactory {

	/**
	 * 平台登录管理
	 * @param localType
	 * @return
	 */
	public ILocalHandler createLocalFactory(int localType) {
		return LocalType.typeOf(localType).createLocalHandler();
	}
	
	/**
	 * 平台充值管理
	 * @param localType
	 * @return
	 */
	public IRechargeHandler getRechargeHandler(int localType) {
		if(LocalType.typeOf(localType) == null){
			return null;
		}
		return LocalType.typeOf(localType).getRechargeHandler();
	}

}
