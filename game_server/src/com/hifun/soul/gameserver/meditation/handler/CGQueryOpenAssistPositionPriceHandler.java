package com.hifun.soul.gameserver.meditation.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.MeditationTemplateManager;
import com.hifun.soul.gameserver.meditation.msg.CGQueryOpenAssistPositionPrice;
import com.hifun.soul.gameserver.meditation.msg.GCOpenAssistPositionPrice;
import com.hifun.soul.gameserver.meditation.template.MeditationAssistPositionTemplate;

@Component
public class CGQueryOpenAssistPositionPriceHandler implements IMessageHandlerWithType<CGQueryOpenAssistPositionPrice> {
	@Autowired
	private MeditationTemplateManager templateManager;
	
	@Override
	public short getMessageType() {	
		return MessageType.CG_QUERY_OPEN_ASSIST_POSITION_PRICE;
	}

	@Override
	public void execute(CGQueryOpenAssistPositionPrice message) {
		Human human  = message.getPlayer().getHuman();
		HumanMeditationManager manager = human.getHumanMeditationManager();
		MeditationAssistPositionTemplate assistPositionTemplate = templateManager.getAssistPositionTemplate(manager.getAssistPositionNum()+1);
		GCOpenAssistPositionPrice gcMsg = new GCOpenAssistPositionPrice();
		gcMsg.setLevelLimit(assistPositionTemplate.getOpenLevel());
		gcMsg.setNormalCurrencyType(CurrencyType.COIN.getIndex());
		gcMsg.setNormalcurrencyNum(assistPositionTemplate.getCostCoinNum());
		gcMsg.setNoLimitCurrencyType(CurrencyType.CRYSTAL.getIndex());
		gcMsg.setNoLimitCurrencyNum(assistPositionTemplate.getCostCrystalNum());
		human.sendMessage(gcMsg);
	}

}
