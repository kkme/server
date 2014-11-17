package com.hifun.soul.gamedb.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.hifun.soul.common.auth.AccountInfo;
import com.hifun.soul.common.auth.AuthResult;
import com.hifun.soul.common.auth.IAuthResult;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.enums.AccountState;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.uuid.UUIDService;
import com.hifun.soul.core.uuid.UUIDType;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gamedb.PlayerPermissionType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.entity.AccountEntity;
import com.hifun.soul.gamedb.logger.Loggers;
import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;

/**
 * 根据openId进行查询;
 * 
 * @author crazyjohn
 * 
 */
public class QQOpenIdQuery implements IXQLExecutor {
	/** 腾讯的请求成功码 */
	private static final int RESULT_OK = 0;	
	private static Logger logger = Loggers.LOCAL_LOGGER;
	LocalConfig config;
	protected IDBService dbService;
	UUIDService uuidService;

	public QQOpenIdQuery(LocalConfig config, IDBService dbService,
			UUIDService uuidService) {
		this.config = config;
		this.dbService = dbService;
		this.uuidService = uuidService;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		OpenApiV3 sdk = new OpenApiV3(config.getAppId(), config.getAppKey());
		sdk.setServerName(config.getApiServerName());
		// 指定OpenApi Cgi名字
		String scriptName = "/v3/user/get_info";

		// 指定HTTP请求协议类型
		String protocol = "http";
		String openId = (String) values[0];
		String openKey = (String) values[1];
		// 填充URL请求参数
		HashMap<String, String> qqParams = new HashMap<String, String>();
		qqParams.put("openid", openId);
		qqParams.put("openkey", openKey);
		qqParams.put("pf", config.getQqPf());

		try {
			String resp = sdk.api(scriptName, qqParams, protocol);
			logger.info("Login qq user info: " + resp);
			// 从返回的json窜中解析出结果码, 看看是否成功了;
			JSONObject jsonUserInfo = JSONObject.fromObject(resp);
			int resultCode = (Integer) jsonUserInfo.get("ret");
			if (resultCode == RESULT_OK) {
				// 账户信息是否已经存储了;
				List<?> existAccount = this.dbService
						.findByNamedQueryAndNamedParam(
								DataQueryConstants.QUERY_ACCOUNT_BY_NAME,
								new String[] { "userName" },
								new Object[] { openId });
				AccountEntity accountEntity = null;
				// 账号不存在要执行插入操作;
				if (existAccount == null || existAccount.size() == 0) {
					AccountEntity newAccount = new AccountEntity();
					newAccount.setUserName(openId);
					newAccount.setPassword(openId);
					newAccount.setState(AccountState.NORMAL.getIndex());
					newAccount.setId(uuidService.getNextUUID(UUIDType.ACCOUNT));
					newAccount
							.setPermissionType(PlayerPermissionType.NORMAL_PLAYER
									.getIndex());
					this.dbService.insert(newAccount);
					// 赋值;
					accountEntity = newAccount;
				} else {
					accountEntity = (AccountEntity) existAccount.get(0);
				}
				// 把信息发送回去
				// 转化成IAuthResult
				AuthResult authResult = new AuthResult();
				authResult.setSucceed(true);
				AccountInfo accountInfo = new AccountInfo();
				accountInfo.setLockState(accountEntity.getState());
				accountInfo.setPassportId(accountEntity.getId());
				accountInfo
						.setPermissionType(accountEntity.getPermissionType());
				Properties localProperties = new Properties();
				localProperties.put(SharedConstants.OPEN_ID, openId);
				localProperties.put(SharedConstants.YELLOW_VIP_LEVEL, jsonUserInfo.get(SharedConstants.YELLOW_VIP_LEVEL));
				localProperties.put(SharedConstants.IS_YELLOW_YEAR_VIP, jsonUserInfo.get(SharedConstants.IS_YELLOW_YEAR_VIP));				
				localProperties.put(SharedConstants.IS_YELLOW_HIGH_VIP, jsonUserInfo.get(SharedConstants.IS_YELLOW_HIGH_VIP));
				accountInfo.setLocalProperties(localProperties);
				authResult.setAccountInfo(accountInfo);
				List<AuthResult> authResults = new ArrayList<AuthResult>();
				authResults.add(authResult);
				return authResults;
			} else {
				return new ArrayList<IAuthResult>();
			}
		} catch (OpensnsException e) {
			logger.error(
					String.format("Request Failed. code:%d, msg:%s\n",
							e.getErrorCode(), e.getMessage()), e);
		}
		return new ArrayList<Object>();
	}
}
