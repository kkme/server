package com.hifun.soul.gameserver.stage.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.StageMapState;
import com.hifun.soul.gameserver.stage.msg.CGGetPassStageReward;
import com.hifun.soul.gameserver.stage.msg.GCGetPassStageReward;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StagePassRewardTemplate;

@Component
public class CGGetPassStageRewardHandler implements
		IMessageHandlerWithType<CGGetPassStageReward> {
	private Logger logger = Loggers.STAGE_LOGGER;
	
	@Autowired
	private StageTemplateManager stageTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_PASS_STAGE_REWARD;
	}

	@Override
	public void execute(CGGetPassStageReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		int mapId = message.getMapId();
		
		// 判断是否已经通关并尚未领取奖励
		StageMapState stageState = human.getHumanStageManager().getStageMapState(mapId);
		if(stageState != StageMapState.PASSED){
			logger.error(String.format("stagestate is error! can not get. humanGuid=%s,mapId=%s", human.getHumanGuid(), mapId));
			return;
		}
		StagePassRewardTemplate stagePassRewardTemplate = stageTemplateManager.getStagePassRewardTemplate(mapId);
		if(stagePassRewardTemplate == null){
			logger.error(String.format("stagePassRewardTemplate is null! humanGuid=%s;mapId=%s", human.getHumanGuid(),mapId));
			return;
		}
		// 判断背包是否有足够的空间
		if(human.getBagManager().getFreeSize(BagType.MAIN_BAG) < 2){
			human.sendWarningMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
			return;
		}
		// 设置通关奖励已经领取
		human.getHumanStageManager().setStageMapState(mapId,StageMapState.GETTED);
		// 给予奖励
		human.getBagManager().putItems(BagType.MAIN_BAG, 
				stagePassRewardTemplate.getItem1Id(), 
				stagePassRewardTemplate.getItem1Num(), 						
				ItemLogReason.STAGE_REWARD,
				"");
		human.getBagManager().putItems(BagType.MAIN_BAG, 
				stagePassRewardTemplate.getItem2Id(), 
				stagePassRewardTemplate.getItem2Num(), 						
				ItemLogReason.STAGE_REWARD,
				"");
		// 加钱
		human.getWallet().addMoney(CurrencyType.COIN, stagePassRewardTemplate.getCoin(), true, MoneyLogReason.STAGE_PASS, "");
		// 发送领取成功消息
		GCGetPassStageReward gcMsg = new GCGetPassStageReward();
		gcMsg.setSuccess(true);
		human.sendMessage(gcMsg);
	}

}
