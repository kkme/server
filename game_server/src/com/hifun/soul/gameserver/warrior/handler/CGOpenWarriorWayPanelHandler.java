package com.hifun.soul.gameserver.warrior.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.warrior.manager.HumanWarriorManager;
import com.hifun.soul.gameserver.warrior.msg.CGOpenWarriorWayPanel;

@Deprecated
public class CGOpenWarriorWayPanelHandler implements IMessageHandlerWithType<CGOpenWarriorWayPanel> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {		
		return MessageType.CG_OPEN_WARRIOR_WAY_PANEL;
	}

	@Override
	public void execute(CGOpenWarriorWayPanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}		
		Human human = player.getHuman();
		if(human == null){
			return;
		}		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.WARRIOR_WAY, true)){
			return;
		}
		HumanWarriorManager warriorManager = human.getHumanWarriorManager();
		warriorManager.sendOpenWarriorWayPanelMessage();
		human.getHumanGuideManager().showGuide(GuideType.OPEN_WARRIOR_PANEL.getIndex());
	}

}
