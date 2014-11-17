package com.hifun.soul.gameserver.player.auth.local;

import java.util.List;

import net.sf.json.JSONObject;

import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;

/**
 * QQ平台处理器;
 * 
 * @author crazyjohn
 * 
 */

public class QQLocalHandler implements ILocalHandler {
	
	private IDataService dataService = GameServerAssist.getDataService();

	@Override
	public void doAuthAction(String jsonValue, IDBCallback<List<?>> dbCallback) {
		String openId = getOpenIdFromJson(jsonValue);
		String openKey = getOpenKeyFromJson(jsonValue);
		dataService.query(DataQueryConstants.QUERY_ACCOUNT_BY_OPENID,
				new String[] { "openId", "openKey" }, new String[] { openId, openKey },
				dbCallback);
	}

	private String getOpenKeyFromJson(String jsonValue) {
		JSONObject json = JSONObject.fromObject(jsonValue);
		return (String) json.get("openkey");
	}

	private String getOpenIdFromJson(String jsonValue) {
		JSONObject json = JSONObject.fromObject(jsonValue);
		return (String) json.get("openid");
	}

}
