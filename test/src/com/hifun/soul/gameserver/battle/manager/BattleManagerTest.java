package com.hifun.soul.gameserver.battle.manager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.context.PackagePathType;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gamedb.GameUtil;
import com.hifun.soul.gameserver.battle.callback.IBattleCallback;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mock.GameMockUtil;
import com.hifun.soul.gameserver.mock.GameMockUtil.MockHuman;
import com.hifun.soul.gameserver.monster.IMonsterFactory;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;

/**
 * 战斗管理器测试;
 * 
 * @author crazyjohn
 * 
 */
public class BattleManagerTest {
	/** 怪物工厂 */
	IMonsterFactory monsterFactory;
	IBattleManager battleManager;

	@Before
	public void setUp() throws Exception {
		ApplicationContext.initAndScan(PackagePathType.PACKAGE_GAME_SERVER
				.getPackagePaths());
		monsterFactory = new IMonsterFactory() {

			@Override
			public Monster createMonster(int monsterId) {
				MonsterTemplate template = new MonsterTemplate();
				template.setId(monsterId);
				Monster monster = new Monster(template);
				return monster;
			}

		};

	}

	/**
	 * 测试跟怪开始战斗;
	 */
	@Test
	public void testStartBattleWithMonster() {
		battleManager = new BattleManager() {
			@Override
			public boolean startBattleWithMapMonster(Human human,
					Monster monster, IBattleCallback callback) {
				Assert.assertEquals(1, monster.getTemplate().getId());
				return super.startBattleWithMapMonster(human, monster, callback);
			}
		};
		MockHuman human = GameMockUtil.buildHuman();
		battleManager.startBattleWithMapMonster(human,
				monsterFactory.createMonster(1), GameUtil.buildBattleCallback());
		Assert.assertEquals(1, this.battleManager.getSize());
		
		// 收到战斗面板消息
		Assert.assertEquals(MessageType.GC_START_BATTLE_INFO, human.getReceiveMessages().get(0).getType());
		// 收到战斗行动消息
		Assert.assertEquals(MessageType.GC_START_BATTLE_ACTION, human.getReceiveMessages().get(1).getType());
	}

	@After
	public void tearDown() throws Exception {
	}

}
