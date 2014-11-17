package com.hifun.soul.gameserver.stage.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.stage.msg.CGGetAllBattleReward;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;

@Component
public class CGGetAllBattleRewardHandler implements 
	IMessageHandlerWithType<CGGetAllBattleReward>{
	protected Logger logger = Loggers.STAGE_LOGGER;
	
	@Autowired
	protected StageTemplateManager stageTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_ALL_BATTLE_REWARD;
	}

	@Override
	public void execute(CGGetAllBattleReward message) {
		// editby: crazyjohn 20130320去掉全部拿走功能;
//		Player player = message.getPlayer();
//		if(player == null){
//			return;
//		}
//		
//		Human human = player.getHuman();
//		if(human == null){
//			return;
//		}
//		
//		HumanStageManager stageManager = human.getHumanStageManager();
//		if(stageManager.getStageId() <= 0
//				|| stageManager.getRewardItemIds().length <= 0){
//			return;
//		}
//		
//		StageTemplate stageTemplate = stageTemplateManager.getStageTemplate(stageManager.getStageId());
//		if(stageTemplate == null){
//			logger.error(String.format("can not find stage.stageId=%s", stageManager.getStageId()));
//			return;
//		}
//		
//		CurrencyType costCurrencyType = CurrencyType.indexOf(stageTemplate.getCostCurrencyType());
//		if(costCurrencyType == null){
//			logger.error(String.format("can not find cost currencyType.currencyType=%s", stageTemplate.getCostCurrencyType()));
//			return;
//		}
//		
//		// 判断是否有足够的货币
//		if(!human.getWallet().isEnough(costCurrencyType, stageTemplate.getCostCurrencyNum())){
//			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,costCurrencyType.getDesc());
//			return;
//		}
//		
//		if(human.getWallet().costMoney(costCurrencyType, stageTemplate.getCostCurrencyNum(), MoneyLogReason.GET_ALL_STAGE_REWARD, "")){
//			// 选中的奖品信息
//			int[] selectItemIds = stageManager.getRewardItemIds();
//			stageManager.updateItemRateDatasSelected(selectItemIds);
//			// 下发消息
//			GCBattleLottery gcBattleLottery = new GCBattleLottery();
//			gcBattleLottery.setCoin(stageTemplate.getRewardCurrencyNum());
//			gcBattleLottery.setExperience(stageTemplate.getRewardExperience());
//			gcBattleLottery.setItems(CommonItemBuilder.genSimpleCommonItems(selectItemIds));
//			gcBattleLottery.setSelectItemIds(selectItemIds);
//			gcBattleLottery.setIsNew(true);
//			human.sendMessage(gcBattleLottery);
//		}
	}

}
