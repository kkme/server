package com.hifun.soul.gameserver.foster.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.foster.manager.HumanFosterManager;
import com.hifun.soul.gameserver.foster.msg.CGSaveFoster;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGSaveFosterHandler implements IMessageHandlerWithType<CGSaveFoster> {
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {		
		return MessageType.CG_SAVE_FOSTER;
	}

	@Override
	public void execute(CGSaveFoster message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.FOSTER, true)) {
			return;
		}
		HumanFosterManager fosterManager = human.getHumanFosterManager();
		fosterManager.saveFoster();
	}

}
