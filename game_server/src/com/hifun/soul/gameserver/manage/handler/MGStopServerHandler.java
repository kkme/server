package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.BasicPlayerLogReason;
import com.hifun.soul.common.LogReasons.OnlineTimeLogReason;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.gameserver.common.config.GameServerConfig;
import com.hifun.soul.gameserver.common.log.LogService;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.manage.msg.MGStopServer;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

@Component
public class MGStopServerHandler implements IMessageHandlerWithType<MGStopServer> {

	@Override
	public short getMessageType() {		
		return MessageType.MG_STOP_SERVER;
	}

	@Override
	public void execute(MGStopServer message) {
		//先开启登陆墙
		GameServerConfig config = (GameServerConfig) ApplicationContext
				.getApplicationContext().getDefaultListableBeanFactory()
				.getSingleton(GameServerConfig.class.getSimpleName());
		config.setLoginWallEnabled(true);
		//将所有玩家踢下线
		GameWorld sceneManager = ApplicationContext.getApplicationContext().getBean(GameWorld.class);
		SceneHumanManager sceneHumanManager = sceneManager.getSceneHumanManager();
		LogService logService = ApplicationContext.getApplicationContext().getBean(LogService.class);
		long now = ApplicationContext.getApplicationContext().getBean(TimeService.class).now();
		for (Human human : sceneHumanManager.getAllHumans()) {
			if (human != null && human.getPlayer().getSession() != null) {
				long loginTime = human.getPropertyManager().getLongPropertyValue(HumanLongProperty.LOGIN_TIME);
				long onlineTime = now - loginTime;
				logService.sendBasicPlayerLog(human, BasicPlayerLogReason.GM_KICK, "", human.getPlayer().getSession()
						.getIp(), human.getCrystal(), human.getCoin(), human.getExperience(), human.getEnergy(),onlineTime);
				logService.sendOnlineTimeLog(human, OnlineTimeLogReason.GM_KICK, "", (int)(onlineTime/1000), 0, loginTime, now);
				human.getPlayer().getSession().close();
			}
		}
		//结束进程
		System.exit(0);
	}

}
