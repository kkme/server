package com.hifun.soul.gameserver.arena.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.arena.manager.HumanArenaManager;
import com.hifun.soul.gameserver.arena.msg.CGAddArenaBattleTime;
import com.hifun.soul.gameserver.arena.msg.GCAddArenaBattleTime;
import com.hifun.soul.gameserver.arena.service.ArenaTemplateManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.vip.service.VipPrivilegeTemplateManager;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGAddArenaBattleTimeHandler implements
		IMessageHandlerWithType<CGAddArenaBattleTime> {
	@Autowired
	private VipPrivilegeTemplateManager vipTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private ArenaTemplateManager arenaTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_ADD_ARENA_BATTLE_TIME;
	}

	@Override
	public void execute(CGAddArenaBattleTime message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ARENA, true)){
			return;
		}
		
		HumanArenaManager arenaManager = human.getHumanArenaManager();
		// 获取竞技场相关配置模版
		int buyTimeCost = arenaTemplateManager.getBuyTimeCost(arenaManager.getMaxArenaBuyTime()+1);
		
		// 竞技场购买最大次数受到vip等级的限制
		VipPrivilegeTemplate vipTemplate = vipTemplateManager.getVipTemplate(human.getVipLevel());
		if(vipTemplate == null){
			return;
		}
		if(arenaManager.getMaxArenaBuyTime() >= vipTemplate.getMaxArenaBuyTimes()){
			human.sendErrorMessage(LangConstants.CAN_NOT_BUY_MORE_TIMES);
			return;
		}
		
		// 判断魔晶是否足够
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, buyTimeCost)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return;
		}
		
		// 消除魔晶石增加竞技场战斗次数
		if(human.getWallet().costMoney(CurrencyType.CRYSTAL, buyTimeCost, MoneyLogReason.ARENA_BUY_BATTLE_TIME, "")){
			arenaManager.addArenaBattleTime();
			arenaManager.addMaxArenaBuyTime();
		}
		
		// 通知消息
		GCAddArenaBattleTime gcMsg = new GCAddArenaBattleTime();
		gcMsg.setCrystal(arenaTemplateManager.getBuyTimeCost(arenaManager.getMaxArenaBuyTime()+1));
		gcMsg.setTimes(human.getArenaBattleTime());
		human.sendMessage(gcMsg);
	}

}
