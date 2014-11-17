package com.hifun.soul.gamedb.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.LocalConfig;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.entity.QQRechargeEntity;
import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;

/**
 * QQ充值确认;
 * @author magicstone
 */
public class QQRechargeConfirmQuery implements IXQLExecutor {
	private Logger logger = Loggers.RECHARGE_LOGGER;
	private IDBService dbService;
	private LocalConfig config;

	public QQRechargeConfirmQuery(LocalConfig config, IDBService dbService) {
		this.config = config;
		this.dbService = dbService;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		logger.info("begin to execute QQRechargeConfirmQuery");
		List<Object> results = new ArrayList<Object>();
		OpenApiV3 sdk = new OpenApiV3(config.getAppId(), config.getAppKey());
		sdk.setServerName(config.getApiServerName());
		// 指定OpenApi Cgi名字
		String scriptName = "/v3/pay/confirm_delivery";
		// 指定HTTP请求协议类型
		String protocol = "https";
		// 解析参数
		long humanGUID = Long.valueOf(String.valueOf(values[0]));
		JSONObject jsonValue = JSONObject.fromObject(values[1]);
		String openId = jsonValue.getString("openid");
		String openKey = jsonValue.getString("openkey");
		String ts = String.valueOf(Calendar.getInstance().getTime().getTime());
		String payItem = jsonValue.getString("payitem");
		String tokenId = jsonValue.getString("token");
		String billno = jsonValue.getString("billno");
		String zoneId = String.valueOf(config.getZoneId());
		String amt = jsonValue.getString("amt");
		String payamtCoins = jsonValue.getString("payamt_coins");
		String pubacctPayamtCoins = jsonValue.getString("pubacct_payamt_coins");
		// 先去查一下数据库中有没有openId和billno都一样的记录，避免一次充值重复添加
		List<?> rechargeEntitys = this.dbService
				.findByNamedQueryAndNamedParam(
						DataQueryConstants.QUERY_RECHARGE_BY_OPENID_AND_BILLNO,
						new String[] { "openId", "billno" },
						new Object[] { openId, billno });
		if(rechargeEntitys != null
				&& rechargeEntitys.size() > 0){
			logger.error(String.format("it is a repeat recharge! opeId:%s;billno:%s", openId, billno));
			return results;
		}
		// 填充URL请求参数
		HashMap<String, String> qqParams = new HashMap<String, String>();
		qqParams.put("openid", openId);
		qqParams.put("openkey", openKey);
		qqParams.put("pf", config.getQqPf());
		qqParams.put("ts", ts);
		qqParams.put("payitem", payItem);
		qqParams.put("token_id", tokenId);
		qqParams.put("billno", billno);
		qqParams.put("zoneid", zoneId);
		qqParams.put("provide_errno", "0");
		qqParams.put("amt", amt);
		qqParams.put("payamt_coins", payamtCoins);
		try {
			logger.info("QQRechargeConfirmQuery qqParams : " + qqParams + "\n");
			String resp = sdk.api(scriptName, qqParams, protocol);
			logger.info("QQRechargeConfirmQuery recieve response from QQ : " + resp);
			// 从返回的json窜中解析出结果码, 看看是否成功了;
			JSONObject jsonUserInfo = JSONObject.fromObject(resp);
			int resultCode = (Integer) jsonUserInfo.get("ret");
			if (QQRechargeConfirmResult.checkIsSuccess(resultCode)) {
				// 腾讯已经扣款成功,将该条充值流水插入到数据库,默认是还没有给玩家发魔晶
				QQRechargeEntity qqRechargeEntity = new QQRechargeEntity();
				qqRechargeEntity.setId(openId+"&"+billno);
				qqRechargeEntity.setAmt(amt);
				qqRechargeEntity.setBillno(billno);
				qqRechargeEntity.setHumanId(humanGUID);
				qqRechargeEntity.setOpenId(openId);
				qqRechargeEntity.setPayamtCoins(payamtCoins);
				qqRechargeEntity.setPayItem(payItem);
				qqRechargeEntity.setPubacctPayamtCoins(pubacctPayamtCoins);
				qqRechargeEntity.setSended(false);
				qqRechargeEntity.setToken(tokenId);
				qqRechargeEntity.setTs(ts);
				qqRechargeEntity.setJsonValue(jsonValue.toString());
				dbService.insert(qqRechargeEntity);
				// 从数据库取该记录确定已经存进去了
				List<?> newRechargeEntitys = this.dbService
						.findByNamedQueryAndNamedParam(
								DataQueryConstants.QUERY_RECHARGE_BY_OPENID_AND_BILLNO,
								new String[] { "openId", "billno" },
								new Object[] { openId, billno });
				if(newRechargeEntitys == null
						|| newRechargeEntitys.size() == 0){
					logger.error(String.format("db record recharge error! humanGUID:%s,ts:%s,jsonValue:%s", humanGUID, ts, jsonValue));
					return results;
				}
				// 这种情况要是出现就是openId和billno不能唯一确定一条充值记录，出现就见鬼了
				if(newRechargeEntitys.size() > 1){
					logger.error(String.format("db record recharge error! find more than one record. openId:%s,billno:%s", openId, billno));
					return results;
				}
				// 标记成功
				QQRechargeConfirmResult confirmResult = new QQRechargeConfirmResult();
				confirmResult.setSuccess(true);
				confirmResult.setNeedConfirmAgain(false);
				confirmResult.setResultCode(String.valueOf(resultCode));
				confirmResult.setObject(newRechargeEntitys.get(0));
				results.add(confirmResult);
				return results;
				
			}
			// 通知过早订单尚未挂起，请稍候再试(可等待几秒后，重复通知)。
			else if(QQRechargeConfirmResult.checkIsNeedConfirmAgain(resultCode)) {
				QQRechargeConfirmResult confirmResult = new QQRechargeConfirmResult();
				confirmResult.setSuccess(false);
				confirmResult.setNeedConfirmAgain(true);
				confirmResult.setResultCode(String.valueOf(resultCode));
				confirmResult.setObject(null);
				results.add(confirmResult);
				return results;
			}
			else{
				return results;
			}
		} 
		catch (OpensnsException e) {
			logger.error(
					String.format("QQRechargeConfirmQuery Request Failed. code:%d, msg:%s\n",
							e.getErrorCode(), e.getMessage()), e);
		}
		catch (Exception e) {
			logger.error(
					String.format("QQRechargeConfirmQuery exception:%s\n", e));
		}
		return results;
	}
}
