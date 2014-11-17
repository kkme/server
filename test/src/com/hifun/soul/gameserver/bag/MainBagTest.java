package com.hifun.soul.gameserver.bag;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.MaterialItem;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;
import com.hifun.soul.gameserver.item.template.MaterialItemTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

public class MainBagTest {

	private MainBag mainBag; 
	private Human human;
	private EquipItem equipItem;
	private MaterialItem materialItem;

	@Before
	public void setUp() throws Exception {
		MinaGameClientSession session = new MinaGameClientSession(null);
		human = new Human(new Player(session));
		mainBag = new MainBag(human);
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
	public void testIsFull() {
		MaterialItem mItem = new MaterialItem(human, "111111111",1);
		MaterialItemTemplate materialTemplate = mItem.getTemplate();
		materialTemplate.setId(002);
		materialTemplate.setMaxOverlap(10);

		mItem.setOverlapNum(385);
		boolean canContain = mainBag.canContain(mItem);
		assertTrue(canContain);

		mItem.setOverlapNum(386);
		canContain = mainBag.canContain(mItem);
		assertFalse(canContain);

		mItem.setOverlapNum(1);
		canContain = mainBag.canContain(mItem);
		assertTrue(canContain);

		mItem.setOverlapNum(0);
		canContain = mainBag.canContain(mItem);
		assertTrue(canContain);

		mItem.setOverlapNum(-1);
		canContain = mainBag.canContain(mItem);
		assertTrue(canContain);

		materialTemplate.setId(003);
		materialTemplate.setMaxOverlap(10);

		mItem.setOverlapNum(380);
		canContain = mainBag.canContain(mItem);
		assertTrue(canContain);

		mItem.setOverlapNum(381);
		canContain = mainBag.canContain(mItem);
		assertFalse(canContain);

	}

	@Test
	public void testFindSuitableUnits() {

		MaterialItem mItem = new MaterialItem(human, "111111111",1);
		MaterialItemTemplate materialTemplate = mItem.getTemplate();
		materialTemplate.setId(002);
		materialTemplate.setMaxOverlap(10);

		mItem.setOverlapNum(35);
		boolean canContain = mainBag.canContain(mItem);
		assertTrue(canContain);

		List<IBagUnit> units = mainBag.findSuitableUnits(mItem);
		for (IBagUnit unit : units) {
			MainBagUnit mainBagUnit = (MainBagUnit) unit;
			System.out.println(mainBagUnit.getFreeSize());
		}
	}

	@Test
	public void testPutItem() {
		MaterialItem mItem = new MaterialItem(human, "111111111",1);
		MaterialItemTemplate materialTemplate = mItem.getTemplate();
		materialTemplate.setId(002);
		materialTemplate.setMaxOverlap(10);
		mItem.setOverlapNum(386);
		try {
			mainBag.putItem(mItem,false,null,"");
		} catch (Exception e) {
			assertEquals("背包已满", e.getMessage());
		}
	}

	@Test
	public void testManage() {

		mainBag.clear(null,"");
		materialItem.setOverlapNum(5);
		mainBag.putItem(equipItem, 3,false,null,"");
		mainBag.putItem(materialItem, 2,false,null,"");
		MaterialItem mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 4,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 5,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 6,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 7,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 8,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 9,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 10,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 11,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 12,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 13,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 14,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 15,false,null,"");

		mItem = (MaterialItem)ItemFactory.creatNewItem(human, materialItem.getItemId());
		mItem.setOverlapNum(4);
		mainBag.putItem(mItem, 16,false,null,"");

		// 13*4=52+5=57
		// 6+1

		mainBag.tidy();

		Item item = mainBag.getItem(0);
		assertNotNull(item);
		assertEquals(equipItem.getUUID(), item.getUUID());

		item = mainBag.getItem(6);
		assertNotNull(item);
		assertEquals(materialItem.getItemId(), item.getItemId());
		assertEquals(7, item.getOverlapNum());

		item = mainBag.getItem(7);
		assertNull(item);

	}

	@Test
	public void testAddBagUnit() {
		boolean b = false;
		try {
			mainBag.putItem(equipItem, 40,false,null,"");
		} catch (IndexOutOfBoundsException ex) {
			b = true;
		}
		assertTrue(b);
		
		mainBag.addBagUnit(1);
		mainBag.putItem(equipItem, 40,false,null,"");
	}
}
