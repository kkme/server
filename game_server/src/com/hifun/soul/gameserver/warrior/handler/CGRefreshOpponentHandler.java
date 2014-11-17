package com.hifun.soul.gameserver.warrior.handler;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.warrior.msg.CGRefreshOpponent;
@Deprecated
public class CGRefreshOpponentHandler implements IMessageHandlerWithType<CGRefreshOpponent> {
//	@Autowired
//	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {		
		return MessageType.CG_REFRESH_OPPONENT;
	}

	@Override
	public void execute(CGRefreshOpponent message) {
//		Player player = message.getPlayer();
//		if(player == null){
//			return;
//		}		
//		Human human = player.getHuman();
//		if(human == null){
//			return;
//		}		
//		// 判断功能是否开放
//		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.WARRIOR_WAY, true)){
//			return;
//		}
//		HumanWarriorManager warriorManager = human.getHumanWarriorManager();
//		if(!warriorManager.canChallenge()){
//			return;
//		}
//		if(warriorManager.refreshOpponent(false)){
//			warriorManager.sendUpdateOpponentMessage();
//		}			
	}

}
