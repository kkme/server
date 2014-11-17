package com.hifun.soul.gameserver.levy.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.levy.HumanLevyManager;
import com.hifun.soul.gameserver.levy.msg.CGLevy;

@Component
public class CGLevyHandler implements IMessageHandlerWithType<CGLevy> {
	
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_LEVY;
	}

	@Override
	public void execute(CGLevy message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human==null){
			return;
		}
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.MAIN_CITY, true)){
			return;
		}
		HumanLevyManager levyManager = human.getLevyManager();
		levyManager.handleLevy();
	}

}
