package com.hifun.soul.gameserver.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.mina.common.IoSession;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 游戏MOCK工具类;
 * 
 * @author crazyjohn
 * 
 */
public class GameMockUtil {
	/**
	 * Mock 角色对象;
	 * 
	 * @author crazyjohn
	 * 
	 */
	public static class MockHuman extends Human {
		List<IMessage> receiveMsgs = new ArrayList<IMessage>();

		public MockHuman(Player player) {
			super(player);
		}

		/**
		 * 获得接受到的所有消息;
		 * 
		 * @return
		 */
		public List<IMessage> getReceiveMessages() {
			return Collections.unmodifiableList(receiveMsgs);
		}
		
		@Override
		public void sendMessage(IMessage msg) {
			this.receiveMsgs.add(msg);
			super.sendMessage(msg);
		}

	}

	/**
	 * 构建一个Mock角色对象;
	 * 
	 * @return
	 */
	public static MockHuman buildHuman() {
		final Player player = buildAGamingPlayer();
		MockHuman human = new MockHuman(player);
		return human;
	}

	public static Player buildAGamingPlayer() {
		MinaGameClientSession session = new MinaGameClientSession(
				GameMockUtil.buildIoSession());
		final Player player = new Player(session);
		player.transferStateTo(PlayerState.CONNECTED);
		player.transferStateTo(PlayerState.AUTHRONIZED);
		player.transferStateTo(PlayerState.ENTERING);
		player.transferStateTo(PlayerState.GAMEING);
		return player;
	}

	public static IoSession buildIoSession() {
		// TODO Auto-generated method stub
		return null;
	}

}
