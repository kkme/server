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
import com.hifun.soul.gameserver.manage.msg.GMKickOffAllCharacter;
import com.hifun.soul.gameserver.manage.msg.MGKickOffAllCharacter;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;

/**
 * 将所有玩家踢下线
 * 
 * @author magicstone
 * 
 */
@Component
public class MGKickOffAllCharacterHandler implements IMessageHandlerWithType<MGKickOffAllCharacter> {

	@Override
	public short getMessageType() {
		return MessageType.MG_KICK_OFF_ALL_CHARACTER;
	}

	@Override
	public void execute(MGKickOffAllCharacter message) {
		GameWorld sceneManager = ApplicationContext.getApplicationContext().getBean(GameWorld.class);		
		LogService logService = ApplicationContext.getApplicationContext().getBean(LogService.class);
		long now = ApplicationContext.getApplicationContext().getBean(TimeService.class).now();			
		for(Player player : sceneManager.getAllPlayers()){
			Human human = player.getHuman();
			if (human != null && player.getSession() != null) {
				long loginTime = human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LOGIN_TIME);
				long onlineTime = now - loginTime;
				logService.sendBasicPlayerLog(human, BasicPlayerLogReason.GM_KICK, "", player.getSession()
						.getIp(), human.getCrystal(), human.getCoin(), human.getExperience(), human.getEnergy(),onlineTime);
				logService.sendOnlineTimeLog(human, OnlineTimeLogReason.GM_KICK, "", (int)(onlineTime/1000), 0, loginTime, now);
			}
			if(player.getSession() != null){
				player.getSession().close();
			}
		}
		GMKickOffAllCharacter gmMsg = new GMKickOffAllCharacter();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setResult(true);
		message.getSession().write(gmMsg);

	}

}
