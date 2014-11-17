package com.hifun.soul.gameserver.functionhelper.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.functionhelper.msg.CGShowFuncHelpInfos;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGShowFuncHelpInfosHandler implements IMessageHandlerWithType<CGShowFuncHelpInfos> {
	@Autowired
	private GameFuncService gameFuncService;
	@Override
	public short getMessageType() {		
		return MessageType.CG_SHOW_FUNC_HELP_INFOS;
	}

	@Override
	public void execute(CGShowFuncHelpInfos message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		final Human human = player.getHuman();
		if(human == null){
			return;
		}
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.FUNCTION_HELPER, true)){
			return;
		}
		GameServerAssist.getFunctionHelperService().sendFuncHelpInfos(human,message.getId());
	}

}
