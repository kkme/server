package com.hifun.soul.gameserver.player.auth.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.service.IDataService;

/**
 * 本地DB认证策略;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class DBAuthStrategy implements IAuthStrategy {
	@Autowired
	private IDataService dataService;

	@Override
	public void doAuthAction(String userName, String password, String ip,
			IDBCallback<List<?>> dbCallback) {
		dataService.query(DataQueryConstants.QUERY_ACCOUNT_BY_NAME_AND_PWD,
				new String[] { "userName", "password" }, new String[] {
						userName, password }, dbCallback);
	}
}
