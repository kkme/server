package com.hifun.soul.gameserver.crystalexchange.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.crystalexchange.manager.HumanCrystalExchangeManager;
import com.hifun.soul.gameserver.crystalexchange.msg.CGCrystalExchange;
import com.hifun.soul.gameserver.crystalexchange.msg.GCShowCrystalExchangePanel;
import com.hifun.soul.gameserver.crystalexchange.service.CrystalExchangeTemplateManager;
import com.hifun.soul.gameserver.crystalexchange.template.CrystalExchangeConsumeTemplate;
import com.hifun.soul.gameserver.crystalexchange.template.CrystalExchangeRewardTemplate;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.CrystalExchangeEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGCrystalExchangeHandler implements
		IMessageHandlerWithType<CGCrystalExchange> {
	private Logger logger = Loggers.CRYSTALEXCHANGE_LOGGER;

	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private VipPrivilegeTemplateManager templateManager;
	@Autowired
	private CrystalExchangeTemplateManager crystalExchangeTemplateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_CRYSTAL_EXCHANGE;
	}

	@Override
	public void execute(CGCrystalExchange message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(player.getHuman(), GameFuncType.CRYSTAL_EXCHANGE, true)){
			return;
		}
		
		HumanCrystalExchangeManager crystalExchangeManager = human.getHumanCrystalExchangeManager();
		// 判断当前次数是否到上限
		VipPrivilegeTemplate vipTemplate = templateManager.getVipTemplate(human.getVipLevel());
		if(vipTemplate == null){
			return;
		}
		if(crystalExchangeManager.getUseTimes() >= vipTemplate.getMaxExchangeTime()){
			human.sendErrorMessage(LangConstants.CRYSTALEXCHANGE_TIME_LIMIT);
			return;
		}
		
		// 获取消耗和奖励模版
		CrystalExchangeConsumeTemplate consumeTemplate
			= crystalExchangeTemplateManager.getCrystalExchangeConsumeTemplate(crystalExchangeManager.getUseTimes()+1);
		if(consumeTemplate == null){
			consumeTemplate = crystalExchangeTemplateManager.getMaxCostTemplate();
		}
		CrystalExchangeRewardTemplate rewardTemplate 
			= crystalExchangeTemplateManager.getCrystalExchangeRewardTemplate(human.getLevel());
		if(consumeTemplate == null
				|| rewardTemplate == null){
			return;
		}
		
		// 判断魔晶是否足够
		CurrencyType consumeCurrencyType = CurrencyType.indexOf(consumeTemplate.getCurrencyType());
		if(consumeCurrencyType == null){
			return;
		}
		int consumeCurrencyNum = consumeTemplate.getCurrencyNum();
		if(!human.getWallet().isEnough(consumeCurrencyType, consumeCurrencyNum)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, consumeCurrencyType.getDesc());
			return;
		}
		
		// 将魔晶兑换成金币同时更新面板
		if(human.getWallet().costMoney(consumeCurrencyType, consumeCurrencyNum,MoneyLogReason.CRYSTAL_EXCHANGE,"")){
			CurrencyType rewardCurrencyType = CurrencyType.indexOf(rewardTemplate.getCurrencyType());
			if(rewardCurrencyType == null){
				logger.error("crystalexchange reward template error.humanGuid:" + human.getHumanGuid() + "; currencyType:" + consumeCurrencyType.getIndex() + "; currencyNum:" + consumeCurrencyNum);
				return;
			}
			int rewardCurrencyNum = rewardTemplate.getCurrencyNum();
			// 军团科技加成魔晶兑换收益
			rewardCurrencyNum = human.getHumanLegionManager()
					.getLegionTechnologyAmend(
							LegionTechnologyType.CRYSTAL_EXCHANGE,
							rewardCurrencyNum);
			// 加钱添加次数
			human.getWallet().addMoney(rewardCurrencyType, rewardCurrencyNum,true,MoneyLogReason.CRYSTAL_EXCHANGE, "");			
			crystalExchangeManager.addUseTimes();
			
			// 更新面板
			GCShowCrystalExchangePanel gcMsg = new GCShowCrystalExchangePanel();
			int time = crystalExchangeManager.getUseTimes();
			int vipLevel = human.getVipLevel();
			int totalTime = crystalExchangeTemplateManager.getTotalCrystalExchangeTime(vipLevel);
			gcMsg.setUseTimes(totalTime - time);
			gcMsg.setTotalTimes(totalTime);
			gcMsg.setCrystal(crystalExchangeTemplateManager.getCrystalExchangeConsume(time+1,vipLevel));
			gcMsg.setCoin(crystalExchangeTemplateManager.getCrystalExchangeReward(vipLevel,human.getLevel()));
			
			human.sendMessage(gcMsg);
			// 发送事件
			human.getEventBus().fireEvent(new CrystalExchangeEvent());
		}
	}

}
