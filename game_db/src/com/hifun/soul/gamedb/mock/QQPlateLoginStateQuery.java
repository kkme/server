package com.hifun.soul.gamedb.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;

/**
 * QQ登录状态保持;
 * @author magicstone
 */
public class QQPlateLoginStateQuery implements IXQLExecutor {
	/** 腾讯的请求成功码 */
	private static final int RESULT_OK = 0;	
	private Logger logger = Loggers.RECHARGE_LOGGER;
	private LocalConfig config;

	public QQPlateLoginStateQuery(LocalConfig config) {
		this.config = config;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		logger.info("begin to execute QQPlateLoginStateQuery");
		List<Object> results = new ArrayList<Object>();
		OpenApiV3 sdk = new OpenApiV3(config.getAppId(), config.getAppKey());
		sdk.setServerName(config.getApiServerName());
		// 指定OpenApi Cgi名字
		String scriptName = "/v3/user/is_login";
		// 指定HTTP请求协议类型
		String protocol = "https";
		String openId = (String) values[0];
		String openKey = (String) values[1];
		// 填充URL请求参数
		HashMap<String, String> qqParams = new HashMap<String, String>();
		qqParams.put("openid", openId);
		qqParams.put("openkey", openKey);
		qqParams.put("pf", config.getQqPf());
		try {
			logger.info("QQPlateLoginStateQuery qqParams : " + qqParams + "\n");
			String resp = sdk.api(scriptName, qqParams, protocol);
			// 从返回的json窜中解析出结果码, 看看是否成功了;
			JSONObject jsonUserInfo = JSONObject.fromObject(resp);
			int resultCode = (Integer) jsonUserInfo.get("ret");
			if (resultCode != RESULT_OK) {
				logger.info("QQPlateLoginStateQuery recieve response from QQ : " + resp);
			}
		} 
		catch (OpensnsException e) {
			logger.error(
					String.format("QQPlateLoginStateQuery Request Failed. code:%d, msg:%s\n",
							e.getErrorCode(), e.getMessage()), e);
		}
		catch (Exception e) {
			logger.error(
					String.format("QQPlateLoginStateQuery exception:%s\n", e));
		}
		return results;
	}
}
