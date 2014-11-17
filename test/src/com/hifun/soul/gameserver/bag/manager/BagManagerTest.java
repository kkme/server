package com.hifun.soul.gameserver.bag.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.MaterialItem;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;
import com.hifun.soul.gameserver.item.template.MaterialItemTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


public class BagManagerTest {
	
	private HumanBagManager bagManager ;
	private Human human ;
	private EquipItemMock equipItem;
	private MaterialItemMock materialItem;
	
	@BeforeClass
	public static void classBefore(){
		// 初始化DI容器上下文
		ApplicationContext.initAndScan();
	}
	@Before
	public void setUp() throws Exception {	
	
		MinaGameClientSession session = new MinaGameClientSession(null);
		human = new Human(new Player(session));
		bagManager = new HumanBagManager(human);
		//初始化好装备物品，不可叠加
		equipItem = new EquipItemMock(human, "000000000", 1);
		EquipItemTemplate equipTemplate = equipItem.getTemplate();
		equipTemplate.setId(001);
		equipTemplate.setMaxOverlap(1);
		equipTemplate.setType(1);
		
		//初始化好材料物品，可叠加
		materialItem = new MaterialItemMock(human, "111111111", 1);
		MaterialItemTemplate materialTemplate = materialItem.getTemplate();
		materialTemplate.setId(002);
		materialTemplate.setMaxOverlap(10); 
		equipTemplate.setType(2);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPutItem() {
		boolean excuteResult = bagManager.putItem(BagType.MAIN_BAG,equipItem,null,"");		
		//验证是否执行成功
		assertTrue(excuteResult);		
		
		Item item = bagManager.getItem(BagType.MAIN_BAG,equipItem.getItemId()); 		
		assertEquals(equipItem.getUUID(),item.getUUID());
		assertEquals(equipItem.getOverlapNum(),item.getOverlapNum());
		
		item=null;
		item = bagManager.getItem(BagType.MAIN_BAG,0);
		assertEquals(equipItem.getUUID(),item.getUUID());
		assertEquals(equipItem.getOverlapNum(),item.getOverlapNum());
		
		bagManager.clear(BagType.MAIN_BAG,null,"");
		item = bagManager.getItem(BagType.MAIN_BAG,0);
		assertNull(item);
		
		//超出背包索引范围
		item = bagManager.getItem(BagType.MAIN_BAG,10);
		assertNull(item);
		
		excuteResult = false;
		materialItem.setOverlapNum(10);
		excuteResult = bagManager.putItem(BagType.MAIN_BAG,materialItem,null,"");
		assertTrue(excuteResult);	
		
		item = bagManager.getItem(BagType.MAIN_BAG,materialItem.getItemId()); 		
		assertEquals(materialItem.getUUID(),item.getUUID());
		assertEquals(10,item.getOverlapNum());

		//放入null
		excuteResult = false;
		excuteResult = bagManager.putItem(BagType.MAIN_BAG, null,null,"");
		assertTrue(excuteResult);

		excuteResult = false;
		excuteResult = bagManager.putItem(BagType.MAIN_BAG, null,null,"");
		assertTrue(excuteResult);
	}
	
	@Test
	public void testPutItem2(){
		boolean excuteResult = bagManager.putItem(BagType.MAIN_BAG,equipItem,3,null,"");		
		//验证是否执行成功
		assertTrue(excuteResult);	
		
		Item item = bagManager.getItem(BagType.MAIN_BAG,3);
		assertNotNull(item);
		assertEquals(equipItem.getUUID(),item.getUUID());
		
		excuteResult = bagManager.putItem(BagType.MAIN_BAG,materialItem,2,null,"");		
		assertTrue(excuteResult);	
		item = bagManager.getItem(BagType.MAIN_BAG,2);
		assertNotNull(item);
		assertEquals(materialItem.getUUID(),item.getUUID());
		
		//放入null
		assertNotNull(bagManager.getItem(BagType.MAIN_BAG, 3));
		excuteResult = bagManager.putItem(BagType.MAIN_BAG, null,3,null,"");
		assertTrue(excuteResult);
		assertNull(bagManager.getItem(BagType.MAIN_BAG, 3));
	}
	
	@Test
	public void testManage(){
		bagManager.putItem(BagType.MAIN_BAG,equipItem,3,null,"");	
		bagManager.putItem(BagType.MAIN_BAG,materialItem,2,null,"");	
		bagManager.tidy(BagType.MAIN_BAG);
		
		Item item = bagManager.getItem(BagType.MAIN_BAG,0);
		assertNotNull(item);
		assertEquals(equipItem.getUUID(),item.getUUID());
	
		item = bagManager.getItem(BagType.MAIN_BAG,1);
		assertNotNull(item);
		assertEquals(materialItem.getUUID(),item.getUUID());		
	}
	

	
	@Test
	public void testRemoveItem(){
		bagManager.putItem(BagType.MAIN_BAG,equipItem,1,null,"");
		assertEquals(equipItem.getUUID(),bagManager.getItem(BagType.MAIN_BAG,1).getUUID());
		bagManager.removeItem(BagType.MAIN_BAG,1,null,"");
		assertNull(bagManager.getItem(BagType.MAIN_BAG,1));
	}
	
	class EquipItemMock extends EquipItem{
		
		private EquipItemTemplate equipTemplate;
		public EquipItemMock(Human human, String UUID, Integer itemId) {
			super(human, UUID, itemId);
		}

		@Override
		protected void initTemplate() {			
			equipTemplate = new EquipItemTemplate();
		}

		@Override
		public EquipItemTemplate getTemplate() {
			return equipTemplate;
		}

		@Override
		protected void initFeature(Item item) {
			itemFeature = new EquipItemFeature(item);
		}

		@Override
		public boolean isEquip() {
			return true;
		}

		@Override
		public String getName() {
			return equipTemplate.getName();
		}

		@Override
		public String getDesc() {
			return equipTemplate.getDesc();
		}

		@Override
		public int getType() {
			return equipTemplate.getType();
		}

		@Override
		public int getIcon() {
			return equipTemplate.getIcon();
		}

		@Override
		public int getRarity() {
			return equipTemplate.getRarity();
		}

		@Override
		public int getBind() {
			return equipTemplate.getBind();
		}

		@Override
		public int getMaxOverlap() {
			return equipTemplate.getMaxOverlap();
		}
		
		/**
		 * 获取等级限制
		 * @return
		 */
		public int getLimitLevel(){
			return equipTemplate.getLimitLevel();
		}
		
		/**
		 * 获取职业限制
		 * @return
		 */
		public int getLimitOccupation(){
			return equipTemplate.getLimitOccupation();
		}
		/**
		 * 获取性别限制
		 * @return
		 */
		public int getLimitSex(){
			return equipTemplate.getLimitSex();
		}

		
		@Override
		public short getSellCurrencyType() {
			return equipTemplate.getCurrencyType();
		}

		@Override
		public int getSellCurrencyNum() {
			return equipTemplate.getCurrencyNum();
		}

		@Override
		public boolean isOverlapable() {		
			return equipTemplate.isOverlapable();
		}
		
	}
	
	class MaterialItemMock extends MaterialItem{
		private MaterialItemTemplate materialItemTemplate;
		
		
		public MaterialItemMock(Human human, String UUID, Integer itemId){
			super(human, UUID, itemId);
		}
		
		@Override
		protected void initTemplate() {			
			materialItemTemplate = new MaterialItemTemplate();
		}

		@Override
		public MaterialItemTemplate getTemplate() {
			return materialItemTemplate;
		}

		@Override
		protected void initFeature(Item item) {
			itemFeature = new MaterialItemFeature(item);
		}

		@Override
		public boolean isEquip() {
			return false;
		}

		@Override
		public String getName() {
			return materialItemTemplate.getName();
		}

		@Override
		public String getDesc() {
			return materialItemTemplate.getDesc();
		}

		@Override
		public int getType() {
			return materialItemTemplate.getType();
		}

		@Override
		public int getIcon() {
			return materialItemTemplate.getIcon();
		}

		@Override
		public int getRarity() {
			return materialItemTemplate.getRarity();
		}

		@Override
		public int getBind() {
			return materialItemTemplate.getBind();
		}

		@Override
		public int getMaxOverlap() {
			return materialItemTemplate.getMaxOverlap();
		}

		
		@Override
		public short getSellCurrencyType() {
			return materialItemTemplate.getCurrencyType();
		}

		@Override
		public int getSellCurrencyNum() {
			return materialItemTemplate.getCurrencyNum();
		}

		@Override
		public boolean isOverlapable() {
			return materialItemTemplate.isOverlapable();
		}
	}
}
