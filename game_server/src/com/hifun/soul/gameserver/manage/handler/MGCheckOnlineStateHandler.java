package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.manage.msg.GMCheckOnlineState;
import com.hifun.soul.gameserver.manage.msg.MGCheckOnlineState;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

@Component
public class MGCheckOnlineStateHandler implements IMessageHandlerWithType<MGCheckOnlineState> {

	@Override
	public short getMessageType() {
		return MessageType.MG_CHECK_CHARACTER_ONLINE_STATE;
	}

	@Override
	public void execute(MGCheckOnlineState message) {
		SceneHumanManager humanManager =  GameServerAssist.getGameWorld().getSceneHumanManager();
		Human human = humanManager.getHumanByGuid(message.getHumanGuid());
		GMCheckOnlineState gmMsg = new GMCheckOnlineState();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setOnline(human == null ? false : true);
		message.getSession().write(gmMsg);
	}

}
