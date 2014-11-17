package com.hifun.soul.gameserver.player.auth.local;

import java.util.List;

import com.hifun.soul.gamedb.callback.IDBCallback;

/**
 * 平台认证接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ILocalHandler {

	public void doAuthAction(String jsonValue,
			IDBCallback<List<?>> iMainThreadDBCallback);

}
