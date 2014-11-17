package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.model.human.CharacterInfo;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.msg.CGOpenPrivateChatPanel;
import com.hifun.soul.gameserver.friend.msg.GCOpenPrivateChatPanel;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGOpenPrivateChatPanelHandler implements
		IMessageHandlerWithType<CGOpenPrivateChatPanel> {
	@Autowired
	private GameWorld gameWorld;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_PRIVATE_CHAT_PANEL;
	}

	@Override
	public void execute(CGOpenPrivateChatPanel message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FRIEND, true)) {
			return;
		}
		Human friend = gameWorld.getSceneHumanManager().getHumanByGuid(message.getRoleId());
		if(friend == null){
			human.sendErrorMessage(LangConstants.PLAYER_OFFLINE);
			return;
		}
		CharacterInfo characterInfo = new CharacterInfo();
		characterInfo.setGuid(friend.getHumanGuid());
		characterInfo.setLevel(friend.getLevel());
		characterInfo.setName(friend.getName());
		characterInfo.setOccupation(friend.getOccupation().getIndex());
		GCOpenPrivateChatPanel gcMsg = new GCOpenPrivateChatPanel();
		gcMsg.setCharacterInfo(characterInfo);
		human.sendMessage(gcMsg);
	}

}
