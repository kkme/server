package com.hifun.soul.gamedb.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.util.HttpUtil;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;

/**
 * QQ充值;
 * @author magicstone
 */
public class QQRechargeQuery implements IXQLExecutor {
	/** 腾讯的请求成功码 */
	private static final int RESULT_OK = 0;	
	private Logger logger = Loggers.RECHARGE_LOGGER;
	private LocalConfig config;

	public QQRechargeQuery(LocalConfig config) {
		this.config = config;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		logger.info("begin to execute QQRechargeQuery");
		List<Object> results = new ArrayList<Object>();
		OpenApiV3 sdk = new OpenApiV3(config.getAppId(), config.getAppKey());
		sdk.setServerName(config.getApiServerName());
		// 指定OpenApi Cgi名字
		String scriptName = "/v3/pay/buy_goods";
		// 指定HTTP请求协议类型
		String protocol = "https";
		String openId = (String) values[0];
		String openKey = (String) values[1];
		String pfKey = (String) values[2];
		String payItem = (String) values[3];
		String goodSmeta = (String) values[4];
		String goodSurl = new StringBuffer().append(config.getCdnPath())
				.append("/")
				.append(config.getRechargeIconPath()).toString();
		String port = (String) values[6];
		long humanGUID = Long.valueOf((String) values[7]);
		// 填充URL请求参数
		HashMap<String, String> qqParams = new HashMap<String, String>();
		qqParams.put("openid", openId);
		qqParams.put("openkey", openKey);
		qqParams.put("pf", config.getQqPf());
		qqParams.put("pfkey", pfKey);
		qqParams.put("payitem", payItem);
		qqParams.put("goodsmeta", goodSmeta);
		qqParams.put("goodsurl", goodSurl);
		qqParams.put("zoneid", String.valueOf(config.getZoneId()));
		qqParams.put("ts", String.valueOf(Calendar.getInstance().getTime().getTime()/1000));
		try {
			logger.info("QQRechargeQuery qqParams : " + qqParams + "\n");
			String resp = sdk.api(scriptName, qqParams, protocol);
			logger.info("QQRechargeQuery recieve response from QQ : " + resp);
			// 从返回的json窜中解析出结果码, 看看是否成功了;
			JSONObject jsonUserInfo = JSONObject.fromObject(resp);
			int resultCode = (Integer) jsonUserInfo.get("ret");
			if (resultCode == RESULT_OK) {
				// 收到腾讯服务器的反馈之后要将充值申请数据存储到payServer
				// 模拟发送http请求
				String token = (String) jsonUserInfo.get("token");
				StringBuffer url = new StringBuffer(config.getPayUrlPath());
				url.append("?method=register&").append("openId=").append(openId)
				.append("&").append("token=").append(token)
				.append("&").append("port=").append(port)
				.append("&").append("humanGUID=").append(humanGUID)
				.append("&").append("openKey=").append(openKey)
				;
				try{
					String responseWebService = HttpUtil.getUrl(url.toString());
					logger.info("QQRechargeQuery recieve response from WebService : " + responseWebService);
					JSONObject jsonWebServiceInfo = JSONObject.fromObject(responseWebService);
					int statusCode = (Integer) jsonWebServiceInfo.get("ret");
					if(statusCode == RESULT_OK){
						results.add(true);
						String urlParams = (String) jsonUserInfo.get("url_params");
						results.add(urlParams);
					}
					return results;
				}
				catch(Exception e){
					return results;
				}
			} else {
				return results;
			}
		} 
		catch (OpensnsException e) {
			logger.error(
					String.format("QQRechargeQuery Request Failed. code:%d, msg:%s\n",
							e.getErrorCode(), e.getMessage()), e);
		}
		catch (Exception e) {
			logger.error(
					String.format("QQRechargeQuery exception:%s\n", e));
		}
		return results;
	}
}
