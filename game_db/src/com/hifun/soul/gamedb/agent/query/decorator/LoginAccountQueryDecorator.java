package com.hifun.soul.gamedb.agent.query.decorator;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.auth.AccountInfo;
import com.hifun.soul.common.auth.AuthResult;
import com.hifun.soul.common.auth.IAuthResult;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.entity.AccountEntity;

public class LoginAccountQueryDecorator extends XQLExecutorDecorator {

	public LoginAccountQueryDecorator(IXQLExecutor wrappedExecutor) {
		super(wrappedExecutor);
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		List<?> baseResult = wrappedExecutor.execute(queryName, params, values);
		if (baseResult == null || baseResult.isEmpty()) {
			return new ArrayList<IAuthResult>();
		}
		// 转化成IAuthResult
		AccountEntity account = (AccountEntity) baseResult.get(0);
		AuthResult authResult = new AuthResult();
		authResult.setSucceed(true);
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setLockState(account.getState());
		accountInfo.setPassportId(account.getId());
		accountInfo.setPermissionType(account.getPermissionType());
		authResult.setAccountInfo(accountInfo);
		List<AuthResult> authResults = new ArrayList<AuthResult>();
		authResults.add(authResult);
		return authResults;
	}

}
