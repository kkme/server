package com.hifun.soul.gameserver.player.auth.strategy;

import java.util.List;

import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 平台认证策略;
 * 
 * @author Administrator
 * 
 */
public class LocalAuthStrategy implements IAuthStrategy {

	@Override
	public void doAuthAction(String userName, String password, String ip,
			IDBCallback<List<?>> dbCallback) {
		// TODO Auto-generated method stub

	}

}
