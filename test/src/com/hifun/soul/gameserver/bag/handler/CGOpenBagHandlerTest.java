package com.hifun.soul.gameserver.bag.handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.core.context.ApplicationContext;
//import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.MainBag;
//import com.hifun.soul.gameserver.bag.msg.CGOpenBag;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.MaterialItem;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;
import com.hifun.soul.gameserver.item.template.MaterialItemTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

public class CGOpenBagHandlerTest {
	private MainBag mainBag;
	private Human human;
	private EquipItem equipItem;
	private MaterialItem materialItem;

	@Before
	public void setUp() throws Exception {
		ApplicationContext.initAndScan();
		MinaGameClientSession session = new MinaGameClientSession(null);
		session.setPlayer(new Player(session));
		human = new Human(new Player(session));
		mainBag   = new MainBag(human);
		mainBag.init(40);
		// 初始化好装备物品，不可叠加
		equipItem = new EquipItem(human, "000000000", 1);
		EquipItemTemplate equipTemplate = equipItem.getTemplate();
		equipTemplate.setId(001);
		equipTemplate.setMaxOverlap(1);
		equipTemplate.setType(1);

		// 初始化好材料物品，可叠加
		materialItem = new MaterialItem(human, "111111111", 1);
		MaterialItemTemplate materialTemplate = materialItem.getTemplate();
		materialTemplate.setId(002);
		materialTemplate.setMaxOverlap(10);
		equipTemplate.setType(2);

		materialItem.setOverlapNum(5);
		mainBag.putItem(equipItem,false,null,"");
		mainBag.putItem(materialItem,false,null,"");
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testOpenBag(){
//		CGOpenBag openBagMessage = new CGOpenBag(){
//			@Override
//			public MinaGameClientSession getSession() {
//				MinaGameClientSession session = new MinaGameClientSession(null);
//				session.setPlayer(new Player(session));
//				return session;
//			}
//		};
//		openBagMessage.setBagType((short)BagType.MAIN_BAG.getIndex());
//		CGOpenBagHandler handler = new CGOpenBagHandler();
//		handler.execute(openBagMessage);
	}
}
