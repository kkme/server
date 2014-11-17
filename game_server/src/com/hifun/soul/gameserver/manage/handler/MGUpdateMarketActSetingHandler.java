package com.hifun.soul.gameserver.manage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.manage.msg.GMUpdateMarketActSetting;
import com.hifun.soul.gameserver.manage.msg.MGUpdateMarketActSetting;
import com.hifun.soul.gameserver.marketact.setting.MarketActivitySetting;

@Component
public class MGUpdateMarketActSetingHandler implements IMessageHandlerWithType<MGUpdateMarketActSetting> {

	@Autowired
	private MarketActivitySetting marketActSetting;
	@Override
	public short getMessageType() {
		return MessageType.MG_UPDATE_MARKET_ACT_SETING;
	}

	@Override
	public void execute(MGUpdateMarketActSetting message) {
		boolean result = marketActSetting.updateSetting(message.getMarketActType(), message.isEnable(), message.getRoleLevel(), message.getVipLevel());
		GMUpdateMarketActSetting gmMsg = new GMUpdateMarketActSetting();
		gmMsg.setResult(result);
		gmMsg.setContextId(message.getContextId());
		message.getSession().write(gmMsg);
	}

}
