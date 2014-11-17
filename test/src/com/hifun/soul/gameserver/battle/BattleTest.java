package com.hifun.soul.gameserver.battle;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.context.PackagePathType;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mock.GameMockUtil;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 战斗测试;
 * 
 * @author crazyjohn
 * 
 */
public class BattleTest {
	Battle battle;

	@Before
	public void setUp() throws Exception {
		ApplicationContext.initAndScan(PackagePathType.PACKAGE_GAME_SERVER
				.getPackagePaths());
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * 测试开启战斗
	 */
	@Test
	public void testBattleStart() {
		MinaGameClientSession session = new MinaGameClientSession(
				GameMockUtil.buildIoSession());
		final Player player = new Player(session);
		player.transferStateTo(PlayerState.CONNECTED);
		player.transferStateTo(PlayerState.AUTHRONIZED);
		player.transferStateTo(PlayerState.ENTERING);
		player.transferStateTo(PlayerState.GAMEING);
		final List<IMessage> receiveMsgs = new ArrayList<IMessage>();
		Human human = new Human(player) {
			@Override
			public void sendMessage(IMessage msg) {
				// 是否是战斗开始消息
				receiveMsgs.add(msg);
			}
		};
		Monster monster = new Monster(new MonsterTemplate());
		battle = Battle.buildBattle(human, monster);
		//battle.sendBattleInfo();
		// 判断角色状态
		Assert.assertEquals(player.getState(), PlayerState.BATTLING);
		// 判断收到的消息
		Assert.assertEquals(MessageType.GC_START_BATTLE_INFO, receiveMsgs
				.get(0).getType());
		Assert.assertEquals(MessageType.GC_START_BATTLE_ACTION, receiveMsgs
				.get(1).getType());
		// 是否是玩家的回合
		//Assert.assertEquals(human, battle.getNextActionUnit());
		
		human.finishCurrentAction();
		
		// 是否是怪物的回合
		//Assert.assertEquals(monster, battle.getNextActionUnit());
		
		monster.finishCurrentAction();
		
		
		// 怪是否在战斗
		Assert.assertTrue(monster.isInBattleState());
		
	}

}
