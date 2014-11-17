package com.hifun.soul.gameserver.bag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gameserver.bag.msg.GCBagUpdate;
import com.hifun.soul.gameserver.bag.msg.GCMoveItem;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.role.Occupation;

/**
 * 主背包
 * 
 * @author magicstone
 * 
 */
public class MainBag extends Bag {

	private static Logger logger = Loggers.BAG_LOGGER;
	/**
	 * key:item.itemID,value:对应的背包格索引数组
	 */
	private Map<Integer, List<IBagUnit>> itemIndexMap = new HashMap<Integer, List<IBagUnit>>();
	/**
	 * 已穿上的装备物品 key:装备位置 value:对应装备
	 */
	private Map<Integer,EquipItem> equipedItems = new HashMap<Integer, EquipItem>();

	public MainBag(Human human) {
		super(human);
	}
	
	public void init(int size){
		if (size < 0) {
			throw new IllegalArgumentException("背包大小不能为负数");
		}
		
		bagUnits = new ArrayList<IBagUnit>();
		for (int i = 0; i < size; i++) {
			bagUnits.add(new MainBagUnit(human,i,itemCaches,itemIndexMap));
		}
	}

	/**
	 * 添加物品
	 * 
	 * @param item
	 * @param isNotifyUpdate 是否及时通知客户端更新
	 * @return
	 */
	@Override
	public boolean putItem(Item item, boolean isNotifyUpdate, ItemLogReason reason, String param) {
		if (!canContain(item)) {
			return false;
		}
		List<IBagUnit> suitableUnits = findSuitableUnits(item);
		if (suitableUnits.size() <= 0) {
			return false;
		}
		for (IBagUnit unit : suitableUnits) {
			unit.putItem(item,isNotifyUpdate,reason,param);					
		}

		return true;
	}

	@Override
	public void putItem(Item item, int index,boolean isNotifyUpdate, ItemLogReason reason, String param) {
		if (item == null) {
			this.removeItem(index, isNotifyUpdate,reason,param);
			return;
		}
		IBagUnit unit = bagUnits.get(index);	
		((MainBagUnit)unit).putItem(item,true,isNotifyUpdate,reason,param);
	}

	/**
	 * 查找合适的背包单元放置物品
	 * 
	 * @param item
	 * @return
	 */
	protected List<IBagUnit> findSuitableUnits(Item item) {
		List<IBagUnit> suitableUnits = new ArrayList<IBagUnit>();
		if (item == null) {
			return suitableUnits;
		}
		if (item.isOverlapable()) {
			int itemOverlap = item.getOverlapNum();
			if (itemIndexMap.containsKey(item.getItemId())) {
				List<IBagUnit> units = itemIndexMap.get(item.getItemId());
				for (IBagUnit unit : units) {
					MainBagUnit mainBagUnit = (MainBagUnit) unit;
					if (mainBagUnit.getFreeSize() > 0) {
						suitableUnits.add(unit);
						itemOverlap = itemOverlap - mainBagUnit.getFreeSize();
						if (itemOverlap <= 0) {
							break;
						}
					}
				}
				if (itemOverlap > 0) {
					int unitCount = (itemOverlap - 1) / item.getMaxOverlap();
					for (IBagUnit unit : bagUnits) {
						if (unit.isEmpty()) {
							suitableUnits.add(unit);
							unitCount--;
						}
						if (unitCount < 0) {
							break;
						}
					}
				}
			} else {
				int unitCount = (itemOverlap - 1) / item.getMaxOverlap();
				for (IBagUnit unit : bagUnits) {
					if (unit.isEmpty()) {
						suitableUnits.add(unit);
						unitCount--;
					}
					if (unitCount < 0) {
						break;
					}
				}
			}
		} else {
			for (IBagUnit unit : bagUnits) {
				if (unit.isEmpty()) {
					suitableUnits.add(unit);
					break;
				}
			}
		}
		return suitableUnits;

	}

	/**
	 * 根据物品Id获取指定量的物品
	 * 
	 * @param itemId 物品Id
	 * @param count 获取的数量
	 * @return 若物品是可叠加的，返回的列表长度不一定为count值，但是{@link Item#getOverlapNum()}的和正好是count值
	 */
	public List<Item> getItemsByItemId(int itemId, int count) {
		List<Item> items = new ArrayList<Item>();
		if (itemIndexMap.containsKey(itemId)) {
			List<IBagUnit> units = itemIndexMap.get(itemId);
			for (IBagUnit unit : units) {
				if(count <= 0){
					break;
				}
				if (unit.getItem().getOverlapNum() <= count) {
					items.add(unit.getItem());
					count -= unit.getItem().getOverlapNum();
				} else {
					Item item = ItemFactory.creatNewItem(human, unit.getItem().getItemId());
					item.setOverlapNum(count);
					items.add(item);
					break;
				}
			}
		}
		return items;
	}

	@Override
	public void removeItem(int index,boolean isNotifyUpdate, ItemLogReason reason, String param) {
		IBagUnit unit = bagUnits.get(index);
		Item item = unit.getItem();
		if (item != null) {	
			unit.removeItem(isNotifyUpdate,reason,param);
		}		
	}

	/**
	 * 移除物品
	 * 
	 * @param itemId 物品的itemId
	 * @param removeNum 移除数量
	 * @param isNotifyUpdate
	 * @param reason
	 * @param param
	 */
	public boolean removeItemByItemId(int itemId,int removeNum,boolean isNotifyUpdate, ItemLogReason reason, String param) {
		if (itemIndexMap.containsKey(itemId)) {
			List<IBagUnit> units = itemIndexMap.get(itemId);
				int totalCount = 0;
				for(IBagUnit unit:units){
					totalCount +=unit.getItem().getOverlapNum();
				}
				if(totalCount<removeNum){
					logger.error("背包中没有足够多的物品可以移除。");
					return false;
				}
				for (int i = units.size() - 1; i >= 0; i--) {
					MainBagUnit mainBagUnit = (MainBagUnit) units.get(i);
					int unitContains = mainBagUnit.getItem().getOverlapNum();
					if (removeNum >= unitContains) {
						mainBagUnit.removeItem(isNotifyUpdate,reason,param);
						removeNum -= unitContains;
						if (removeNum == 0) {
							break;
						}
					} else {
						mainBagUnit.removeItem(removeNum,isNotifyUpdate,reason,param);
						break;
					}
				}
			return true;
		}
		else{
			logger.error("背包中没有要移除的物品。");
			return false;
		}
	}
	
	/**
	 * 移除物品
	 * 
	 * @param itemId 物品的itemId
	 * @param removeNum 移除数量
	 * @param isNotifyUpdate
	 * @param reason
	 * @param param
	 */
	public boolean removeItemByUuid(String itemUuid, boolean isNotifyUpdate, ItemLogReason reason, String param) {
		for (IBagUnit unit : this.bagUnits) {
			if(unit.getItem()==null){
				continue;
			}
			if (unit.getItem().getUUID().equals(itemUuid)) {				
				unit.removeItem(isNotifyUpdate, reason, param);
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear(ItemLogReason reason, String param) {
		super.clear(reason,param);
		this.itemIndexMap.clear();
	}

	/**
	 * 整理背包
	 */
	public void tidy() {		
		List<Item> items = new ArrayList<Item>();
		for(IBagUnit unit : bagUnits){
			if(!unit.isEmpty()){
				items.add(unit.getItem());
			}
		}
		Collections.sort(items, new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				if(o1.isEquip() && o2.isEquip()){					
					EquipItemFeature equipFeature1 = (EquipItemFeature)o1.getFeature();
					EquipItemFeature equipFeature2 = (EquipItemFeature)o2.getFeature();
					if(equipFeature1.isEquiped() && !equipFeature2.isEquiped()){
						return -1;
					}
					else if(!equipFeature1.isEquiped() && equipFeature2.isEquiped()){
						return 1;
					}
				}
				if(o1.getType() == o2.getType()){
					
					return o1.getItemId() - o2.getItemId();
				}
				return o1.getType() - o2.getType();
			}
		});
		// 重新建立对应关系
		clear(ItemLogReason.TIDY,"");
		for (Item item : items) {			
			this.putItem(item,false,ItemLogReason.TIDY,"");			
		}
	}

	/**
	 * 背包扩容
	 * 
	 * @return
	 */
	public boolean addBagUnit(int addCount) {
		for (int i = 0; i < addCount; i++) {
			bagUnits.add(new MainBagUnit(human,bagUnits.size(),this.itemCaches,this.itemIndexMap));
		}
		return true;
	}

	@Override
	public boolean canContain(Item item) {
		if (item == null) {
			return true;
		}
		if (item.isOverlapable()) {// 判断当前背包容量能否装下可叠加的物品
			int itemOverlap = item.getOverlapNum();
			if (itemIndexMap.containsKey(item.getItemId())) {
				List<IBagUnit> units = itemIndexMap.get(item.getItemId());
				for (IBagUnit unit : units) {
					MainBagUnit mainBagUnit = (MainBagUnit) unit;
					if (mainBagUnit.getFreeSize() > 0) {
						if (mainBagUnit.getFreeSize() >= itemOverlap) {
							return true;
						} else {
							itemOverlap = itemOverlap - mainBagUnit.getFreeSize();
						}
					}
				}
				if (itemOverlap > 0) {
					int unitCount = (itemOverlap - 1) / item.getMaxOverlap();
					for (IBagUnit unit : bagUnits) {
						if (unit.isEmpty()) {
							unitCount--;
						}
						if (unitCount < 0) {
							return true;
						}
					}
				}
			} else {
				int unitCount = (itemOverlap - 1) / item.getMaxOverlap();
				for (IBagUnit unit : bagUnits) {
					if (unit.isEmpty()) {
						unitCount--;
					}
					if (unitCount < 0) {
						return true;
					}
				}
			}
			return false;
		} else {
			return super.canContain(item);
		}
	}

	/**
	 * 是否有足够的物品
	 * 
	 * @param itemId
	 * @param count
	 * @return
	 */
	public boolean isEnough(Integer itemId, int count) {
		if (itemIndexMap.containsKey(itemId)) {
			List<IBagUnit> units = itemIndexMap.get(itemId);
			int itemCount = 0;
			for (IBagUnit unit : units) {
				itemCount += unit.getItem().getOverlapNum();
			}
			if (itemCount >= count) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public BagType getBagType() {
		return BagType.MAIN_BAG;
	}

	/**
	 * 根据ItemId获取背包中拥有的数量
	 * 
	 * @param itemId
	 * @return
	 */
	public int getItemCount(int itemId) {
		int count=0;
		if (itemIndexMap.containsKey(itemId)) {
			List<IBagUnit> units = itemIndexMap.get(itemId);
			for (IBagUnit unit : units) {
				count+=unit.getItem().getOverlapNum();
			}
		}
		return count;
	}

	@Override
	public void removeItem(int index, int removeNum, boolean isNotifyUpdate, ItemLogReason reason, String param) {
		if(index>=this.bagUnits.size()){
			throw new IllegalArgumentException("索引超出背包大小范围。");
		}
		IBagUnit unit = bagUnits.get(index);
		Item item = unit.getItem();
		if(item==null || removeNum>item.getOverlapNum()){
			throw new IllegalArgumentException("指定背包位置没有足够的物品可移除。");
		}
		if(removeNum==item.getOverlapNum()){
			this.removeItem(index,isNotifyUpdate,reason,param);
		}
		else{
			MainBagUnit mainBagUnit = (MainBagUnit)unit;
			mainBagUnit.removeItem(removeNum, isNotifyUpdate,reason,param);			
		}
	}

	public void sendBagUpdateMessage() {
		List<Item> itemList = this.getItems();
		CommonItem[] items = new CommonItem[itemList.size()];
		for (int i = 0; i < itemList.size(); i++) {
			items[i] = CommonItemBuilder.converToCommonItem(itemList.get(i));
		}
		GCBagUpdate gcMsg = new GCBagUpdate();
		gcMsg.setItems(items);
		human.sendMessage(gcMsg);
	}

	@Override
	public void moveItem(int sourceIndex, int targetIndex, ItemLogReason reason, String param) {
		if(sourceIndex == targetIndex){
			return;
		}
		if(sourceIndex>=bagUnits.size() || targetIndex >= bagUnits.size()){
			return;
		}		
		IBagUnit sourceBagUnit = this.bagUnits.get(sourceIndex);
		IBagUnit targetBagUnit = this.bagUnits.get(targetIndex);
		Item sourceItem = sourceBagUnit.getItem();
		if(sourceItem == null){
			return;
		}
		Item targetItem = targetBagUnit.getItem();		
		sourceBagUnit.logicRemoveItem(false, reason, param);
		targetBagUnit.logicRemoveItem(false, reason, param);
		targetBagUnit.putItem(sourceItem, false, reason, param);
		sourceBagUnit.putItem(targetItem, false, reason, param);
		GCMoveItem gcMsg = new GCMoveItem();
		gcMsg.setFromBagType((short)this.getBagType().getIndex());
		gcMsg.setFromBagIndex(sourceIndex);
		gcMsg.setToBagType((short)this.getBagType().getIndex());
		gcMsg.setToBagIndex(targetIndex);
		this.human.sendMessage(gcMsg);
	}
	
	/**
	 * 装备物品
	 * @param index
	 * @param isNotifyUpdate
	 * @param reason
	 * @param param
	 * @return
	 */
	public boolean equipItem(int index, boolean isNotifyUpdate,PropertyLogReason reason,String param){
		if(!canEquip(index)){
			return false;
		}
		EquipItem equipItem = (EquipItem)getItem(index);		
		int position = equipItem.getEquipPosition();
		if(equipedItems.containsKey(position)){			
			EquipItem equipedItem = equipedItems.get(position);
			equipedItem.setEquiped(false);
			equipedItems.remove(position);
			this.updateItem(equipedItem.getBagIndex());
			if(isNotifyUpdate){
				this.bagUnits.get(equipedItem.getBagIndex()).sendItemUpdateMessage();
			}
			((EquipItemFeature)equipedItem.getFeature()).amendHumanProperty(false,human.getHumanPropertiesManager(),reason,param);
		}
		equipItem.setEquiped(true);
		equipedItems.put(position, equipItem);
		this.updateItem(index);
		if(isNotifyUpdate){
			this.bagUnits.get(index).sendItemUpdateMessage();			
		}
		((EquipItemFeature)equipItem.getFeature()).amendHumanProperty(true,human.getHumanPropertiesManager(),reason,param);
		return true;
	}
	
	public boolean takeOffEquipItem(int index, boolean isNotifyUpdate,PropertyLogReason reason,String param){
		Item item = getItem(index);
		if(item == null || !(item instanceof EquipItem)){
			return false;
		}		
		EquipItem equipItem = (EquipItem)getItem(index);
		int position = equipItem.getEquipPosition();
		if(!equipedItems.containsKey(position)){			
			return false;
		}
		EquipItem equipedItem = equipedItems.get(position);
		equipedItem.setEquiped(false);
		equipedItems.remove(position);
		this.updateItem(equipedItem.getBagIndex());
		if(isNotifyUpdate){
			this.bagUnits.get(index).sendItemUpdateMessage();
		}
		((EquipItemFeature)equipItem.getFeature()).amendHumanProperty(false,human.getHumanPropertiesManager(),reason,param);
		return true;
	}
	
	public boolean canEquip(int index){
		Item item = getItem(index);
		if(item == null || !(item instanceof EquipItem)){
			return false;
		}
		EquipItem equipItem = (EquipItem)item;
		int limitLevel = equipItem.getLimitLevel();
		if (human.getLevel() < limitLevel) {
			human.sendWarningMessage(LangConstants.CHARACTER_LEVEL_LIMIT,limitLevel);
			return false;
		}
		Occupation occupation = human.getOccupation();
		int limitO = equipItem.getLimitOccupation();
		if ((limitO | occupation.getType()) != limitO) {
			human.sendWarningMessage(LangConstants.EQUIP_OCCUPATION_NOT_MATCH);
			return false;
		}
		return true;
	}
	
	public List<Item> getEquipedItems(){
		List<Item> equipedItemList = new ArrayList<Item>();
		for(Item item : equipedItems.values()){
			equipedItemList.add(item);
		}
		return equipedItemList;
	}
	
	public Item getEquipItem(int position){
		return equipedItems.get(position);
	}
	
	/**
	 *  在指定背包中搜寻物品,若在某个背包单元格中搜到该物品则停止搜寻，返回该单元格的物品
	 * @param itemId	
	 * @return
	 */
	public Item searchItem(int itemId) {
		Item item = null;
		if (itemIndexMap.containsKey(itemId)) {
			List<IBagUnit> units = itemIndexMap.get(itemId);
			item = units.get(0).getItem();
		}
		return item;
	}
}
