package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.BasicPlayerLogReason;
import com.hifun.soul.common.LogReasons.OnlineTimeLogReason;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.gameserver.common.log.LogService;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.manage.msg.GMKickOffCharacter;
import com.hifun.soul.gameserver.manage.msg.MGKickOffCharacter;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

@Component
public class MGKickOffCharacterHandler implements IMessageHandlerWithType<MGKickOffCharacter> {

	@Override
	public short getMessageType() {
		return MessageType.MG_KICK_OFF_CHARACTER;
	}

	@Override
	public void execute(MGKickOffCharacter message) {
		GameWorld sceneManager = ApplicationContext.getApplicationContext().getBean(GameWorld.class);
		SceneHumanManager sceneHumanManager = sceneManager.getSceneHumanManager();
		Human human = sceneHumanManager.getHumanByGuid(message.getHumanGuid());
		if (human != null) {
			long now = ApplicationContext.getApplicationContext().getBean(TimeService.class).now();
			long loginTime = human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LOGIN_TIME);
			long onlineTime = now - loginTime;
			LogService logService = ApplicationContext.getApplicationContext().getBean(LogService.class);
			logService.sendBasicPlayerLog(human, BasicPlayerLogReason.GM_KICK, "", human.getPlayer().getSession()
					.getIp(), human.getCrystal(), human.getCoin(), human.getExperience(), human.getEnergy(),onlineTime);
			logService.sendOnlineTimeLog(human, OnlineTimeLogReason.GM_KICK, "", (int)(onlineTime/1000), 0, loginTime, now);
			Player player = human.getPlayer();
			// 断开会话连接
			if (player.getSession() != null) {
				player.getSession().close();
			}
		}
		GMKickOffCharacter gmMsg = new GMKickOffCharacter();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setResult(true);
		message.getSession().write(gmMsg);
	}

}
