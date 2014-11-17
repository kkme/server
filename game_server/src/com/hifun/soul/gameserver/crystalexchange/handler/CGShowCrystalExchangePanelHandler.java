package com.hifun.soul.gameserver.crystalexchange.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.crystalexchange.msg.CGShowCrystalExchangePanel;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowCrystalExchangePanelHandler implements
	IMessageHandlerWithType<CGShowCrystalExchangePanel> {
	
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_CRYSTAL_EXCHANGE_PANEL;
	}

	@Override
	public void execute(CGShowCrystalExchangePanel message) {
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
		
		human.getHumanCrystalExchangeManager().onOpenClick();
	}

}
