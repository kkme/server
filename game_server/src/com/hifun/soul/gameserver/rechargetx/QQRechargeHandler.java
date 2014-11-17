package com.hifun.soul.gameserver.rechargetx;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.rechargetx.template.RechargeTXTemplate;

public class QQRechargeHandler implements IRechargeHandler {
	private IDataService dataService = GameServerAssist.getDataService();
	private Logger logger = Loggers.RECHARGE_LOGGER;
	
	@Override
	public boolean canRecharge(int id) {
		RechargeTXTemplate template = GameServerAssist.getRechargeTXTemplateManager().getRechargeTXTemplate(id);
		if(template == null){
			return false;
		}
		return true;
	}

	@Override
	public void doRecharge(long humanGUID, int id, String jsonValue,
			IDBCallback<List<?>> iMainThreadDBCallback) {
		RechargeTXTemplate template = GameServerAssist.getRechargeTXTemplateManager().getRechargeTXTemplate(id);
		if(template == null){
			logger.error(String.format("can not find RechargeTXTemplate, id:%s", id));
			return;
		}
		// 以下是调用腾讯接口必须需要用到的参数
		// openid --客户端可以获取到发给服务器，从jsonValue取
		// openkey --客户端可以获取到发给服务器，从jsonValue取
		// appid --相对某个应用是固定值，在LocalConfig获取
		// sig --这个不需要传，腾讯的api自己生成
		// pf --相对某个应用是固定值，在LocalConfig获取
		// pfkey --客户端可以获取到发给服务器，从jsonValue取
		// ts --与腾讯通信的时候在生成
		// payitem --格式为：物品id*价格*数量，通过配置模版生成
		// goodsmeta --格式为: 名称*描述，通过配置模版生成
		// goodsurl --cdn的路径地址，在LocalConfig获取并+File.separator+客户端资源存放路径+模版配置的物品图标
		// zoneid --通过LocalConfig获取
		// port --gameServer开放的端口号
		String openId = getOpenIdFromJson(jsonValue);
		String openKey = getOpenKeyFromJson(jsonValue);
		String pfKey = getPfKeyFormJson(jsonValue);
		String payItem = id + "*" + template.getPrice() + "*" + 1;
		String goodSmeta = template.getName() + "*" + template.getDesc();
		String icon = String.valueOf(template.getIcon());
		String port = GameServerAssist.getGameServerConfig().getPorts();
		dataService.query(DataQueryConstants.QUERY_RECHARGE,
				new String[] { "openId", "openKey", "pfKey", "payItem", "goodSmeta", "icon", "port", "humanGUID" }, 
				new String[] { openId, openKey, pfKey, payItem, goodSmeta, icon, port, String.valueOf(humanGUID) },
				iMainThreadDBCallback);
	}

	private String getOpenKeyFromJson(String jsonValue) {
		JSONObject json = JSONObject.fromObject(jsonValue);
		return (String) json.get("openkey");
	}

	private String getOpenIdFromJson(String jsonValue) {
		JSONObject json = JSONObject.fromObject(jsonValue);
		return (String) json.get("openid");
	}
	
	private String getPfKeyFormJson(String jsonValue) {
		JSONObject json = JSONObject.fromObject(jsonValue);
		return (String) json.get("pfkey");
	}

	@Override
	public void doRechargeConfirm(long humanGUID, String jsonValue,
			IDBCallback<List<?>> iMainThreadDBCallback) {
		dataService.query(DataQueryConstants.QUERY_RECHARGE_CONFIRM, 
				new String[] {"humanGUID", "jsonValue"},
				new String[] {String.valueOf(humanGUID), jsonValue},
				iMainThreadDBCallback);
	}

	@Override
	public void checkPlateFormLoginState(String jsonValue,
			IDBCallback<List<?>> iMainThreadDBCallback) {
		String openId = getOpenIdFromJson(jsonValue);
		String openKey = getOpenKeyFromJson(jsonValue);
		dataService.query(DataQueryConstants.QUERY_PLATE_LOGIN_STATE,
				new String[] { "openId", "openKey"}, 
				new String[] { openId, openKey},
				iMainThreadDBCallback);
	}
}
