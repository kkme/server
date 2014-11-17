package com.hifun.soul.gameserver.manage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.manage.msg.GMQueryPlayerStateStatistic;
import com.hifun.soul.gameserver.manage.msg.MGQueryPlayerStateStatistic;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 玩家状态统计信息
 * 
 * @author magicstone
 * 
 */
@Component
public class MGQueryPlayerStateStatisticHandler implements
		IMessageHandlerWithType<MGQueryPlayerStateStatistic> {

	@Override
	public short getMessageType() {
		return MessageType.MG_QUERY_PLAYER_STATE_STATISTIC;
	}

	@Override
	public void execute(MGQueryPlayerStateStatistic message) {
		int connectedCount = 0;
		int authronizedCount = 0;
		int enteringCount = 0;
		int gameingCount = 0;
		int battlingCount = 0;
		int exitingCount = 0;
		int otherCount = 0;
		for (Player player : ApplicationContext.getApplicationContext()
				.getBean(GameWorld.class).getAllPlayers()) {
			if (player == null) {
				continue;
			}
			PlayerState playerState = (PlayerState) player.getState();
			switch (playerState) {
			case CONNECTED:
				connectedCount++;
				break;
			case AUTHRONIZED:
				authronizedCount++;
				break;
			case ENTERING:
				enteringCount++;
				break;
			case GAMEING:
				gameingCount++;
				break;
			case BATTLING:
				battlingCount++;
				break;
			case EXITING:
				exitingCount++;
				break;
			case NONE:
			default:
				otherCount++;
				break;
			}
		}
		GMQueryPlayerStateStatistic gmMsg = new GMQueryPlayerStateStatistic();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setConnectedCount(connectedCount);
		gmMsg.setAuthronizedCount(authronizedCount);
		gmMsg.setEnteringCount(enteringCount);
		gmMsg.setGameingCount(gameingCount);
		gmMsg.setBattlingCount(battlingCount);
		gmMsg.setExitingCount(exitingCount);
		gmMsg.setOtherCount(otherCount);
		message.getSession().write(gmMsg);
	}

}
