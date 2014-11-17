package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.manage.msg.GMQueryOnlineNum;
import com.hifun.soul.gameserver.manage.msg.MGQueryOnlineNum;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

@Component
public class MGQueryOnlineNumHandler implements IMessageHandlerWithType<MGQueryOnlineNum> {

	@Override
	public short getMessageType() {
		return MessageType.MG_QUERY_ONLINE_NUMBER;
	}

	@Override
	public void execute(MGQueryOnlineNum message) {
		SceneHumanManager humanManager =  ApplicationContext.getApplicationContext().getBean(GameWorld.class).getSceneHumanManager();
		GMQueryOnlineNum gmMsg = new GMQueryOnlineNum();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setOnlineNum(humanManager.getAllHumans().size());
		message.getSession().write(gmMsg);
	}

}
