package com.hifun.soul.gameserver.player.auth.strategy;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.auth.AccountInfo;
import com.hifun.soul.common.auth.AuthResult;
import com.hifun.soul.common.auth.IAuthResult;
import com.hifun.soul.gamedb.callback.IDBCallback;

public class MockAuthStrategy implements IAuthStrategy {

	@Override
	public void doAuthAction(String userName, String password, String ip,
			IDBCallback<List<?>> dbCallback) {
		List<IAuthResult> results = new ArrayList<IAuthResult>();
		AuthResult aResult = new AuthResult();
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setPassportId(888L);
		aResult.setSucceed(true);
		aResult.setAccountInfo(accountInfo);
		results.add(aResult);
		dbCallback.onSucceed(results);
	}

}
