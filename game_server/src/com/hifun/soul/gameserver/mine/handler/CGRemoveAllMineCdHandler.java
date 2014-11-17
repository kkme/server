package com.hifun.soul.gameserver.mine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.mine.msg.CGRemoveAllMineCd;
import com.hifun.soul.gameserver.mine.msg.GCRemoveAllMineCd;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGRemoveAllMineCdHandler implements
		IMessageHandlerWithType<CGRemoveAllMineCd> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_REMOVE_ALL_MINE_CD;
	}

	@Override
	public void execute(CGRemoveAllMineCd message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开启
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.REMOVE_ALL_MINE_CD, true)){
			return;
		}
		
		// 计算所有矿场的cd需要多少的花费
		HumanMineManager mineManager = human.getHumanMineManager();
		int needCostNum = mineManager.getRemoveAllMineCdCost();
		
		// 通知客户端
		if(needCostNum <= 0){
			human.sendGenericMessage(LangConstants.CAN_NOT_SPEED);
		}
		else{
			GCRemoveAllMineCd gcMsg = new GCRemoveAllMineCd();
			gcMsg.setCostType(CurrencyType.CRYSTAL.getIndex());
			gcMsg.setCostNum(needCostNum);
			human.sendMessage(gcMsg);
		}
	}

}
