package com.hifun.soul.gameserver.meditation.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.HumanMeditationManager;
import com.hifun.soul.gameserver.meditation.MeditationTemplateManager;
import com.hifun.soul.gameserver.meditation.msg.CGOpenAssistPosition;
import com.hifun.soul.gameserver.meditation.msg.GCOpenAssistPosition;
import com.hifun.soul.gameserver.meditation.template.MeditationAssistPositionTemplate;

@Component
public class CGOpenAssistPositionHandler implements IMessageHandlerWithType<CGOpenAssistPosition> {

	@Autowired
	private MeditationTemplateManager templateManager;
	
	@Override
	public short getMessageType() {		
		return MessageType.CG_OPEN_ASSIST_POSITION;
	}

	@Override
	public void execute(CGOpenAssistPosition message) {
		Human human  = message.getPlayer().getHuman();
		HumanMeditationManager manager = human.getHumanMeditationManager();
		int currentPositionNum = manager.getAssistPositionNum();
		if(message.getIsRichMan()){
			MeditationAssistPositionTemplate assistPositionTemplate = templateManager.getAssistPositionTemplate(manager.getAssistPositionNum()+1);
			int costCrystal = assistPositionTemplate.getCostCrystalNum();
			if(human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal, MoneyLogReason.MEDITATION_OPEN_POSITION, "")){
				manager.addAssistPosition();
				sendAssistPositionAddMessage(human,manager.getAssistPositionNum()-1);
			}
		}
		else{
			MeditationAssistPositionTemplate assistPositionTemplate = templateManager.getAssistPositionTemplate(currentPositionNum+1);
			if(human.getLevel()<assistPositionTemplate.getOpenLevel()){
				//等级未达到
				human.sendErrorMessage(LangConstants.CHARACTER_LEVEL_LIMIT,assistPositionTemplate.getOpenLevel());
				return;
			}
			if(human.getWallet().costMoney(CurrencyType.COIN, assistPositionTemplate.getCostCoinNum(), MoneyLogReason.MEDITATION_OPEN_POSITION, "")){
				manager.addAssistPosition();
				sendAssistPositionAddMessage(human,manager.getAssistPositionNum()-1);
			}
		}
	}

	private void sendAssistPositionAddMessage(Human human,int positionIndex){
		GCOpenAssistPosition gcMsg = new GCOpenAssistPosition();
		MeditationAssistPositionTemplate assistPositionTemplate = templateManager.getAssistPositionTemplate(human.getHumanMeditationManager().getAssistPositionNum()+1);
		if(assistPositionTemplate != null){
			gcMsg.setLevelLimit(assistPositionTemplate.getOpenLevel());
			gcMsg.setCoinLimit(assistPositionTemplate.getCostCoinNum());
			gcMsg.setCrystalLimit(assistPositionTemplate.getCostCrystalNum());
		}
		gcMsg.setIndex(positionIndex);
		human.sendMessage(gcMsg);
	}
}
