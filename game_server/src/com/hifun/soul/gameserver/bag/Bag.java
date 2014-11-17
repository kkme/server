package com.hifun.soul.gameserver.bag;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.entity.HumanItemEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;

public abstract class Bag implements IBag {

	protected List<IBagUnit> bagUnits;
	protected Human human;
	/** 物品实体缓存 */
	protected CacheEntry<String, HumanItemEntity> itemCaches = new CacheEntry<String, HumanItemEntity>();
	
	public Bag(Human human){
		this.human = human;
	}

	/**
	 * 背包是否已满
	 */
	public boolean isFull() {
		for (IBagUnit unit : bagUnits) {
			if (unit.isEmpty() == true) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断背包是否能装下某物品
	 * 
	 * @param item
	 * @return
	 */
	public boolean canContain(Item item) {
		return !isFull();
	}

	public int getSize() {
		return bagUnits.size();
	}

	public void clear(ItemLogReason reason, String param) {
		for (IBagUnit unit : bagUnits) {
			unit.removeItem(false,reason,param);
		}
	}

	@Override
	public abstract boolean putItem(Item item, boolean isNotifyUpdate, ItemLogReason reason, String param);

	@Override
	public Item getItem(int index){
		if (index >= bagUnits.size()) {
			throw new IllegalArgumentException("参数超出索引范围");
		}
		return bagUnits.get(index).getItem();
	}

	@Override
	public void removeItem(int index,boolean isNotifyUpdate, ItemLogReason reason, String param){		
			bagUnits.get(index).removeItem(isNotifyUpdate,reason,param);
	}
	
	@Override
	public void removeItem(int index,int removeNum,boolean isNotifyUpdate, ItemLogReason reason, String param){
		if(index>=this.bagUnits.size()){
			throw new IllegalArgumentException("索引参数超出范围。");
		}
		BagUnit unit = (BagUnit)bagUnits.get(index);
		Item item = unit.getItem();
		if(item==null || removeNum>item.getOverlapNum()){
			throw new IllegalArgumentException("指定位置没有足够的物品可移除。");
		}
		if(removeNum==item.getOverlapNum()){
			unit.removeItem(isNotifyUpdate,reason,param);
		}
		else{
			item.setOverlapNum(item.getOverlapNum()-removeNum);	
			unit.sendItemUpdateMessage();
		}
	}

	@Override
	public List<Item> getItems() {
		List<Item> items = new ArrayList<Item>();
		for (IBagUnit unit : bagUnits) {
			if (unit.getItem() != null) {
				items.add(unit.getItem());
			}
		}
		return items;
	}
	
	/**
	 * 获取主背包的空背包单元数
	 * @return
	 */
	public int getFreeSize() {
		int freeSize = 0;
		for (IBagUnit unit : bagUnits) {
			if (unit.isEmpty()) {
				freeSize++;
			}
		}
		return freeSize;
	}

	public List<Item> getItems(int fromIndex, int unitCount) {
		List<Item> items = new ArrayList<Item>();
		if(fromIndex>=bagUnits.size()){
			return items;
		}
		int toIndex = fromIndex+unitCount;
		if(fromIndex+unitCount>=bagUnits.size()){
			toIndex = bagUnits.size();
		}		
		for (int i=fromIndex;i<toIndex;i++) {
			IBagUnit unit = bagUnits.get(i);
			if (unit.getItem() != null) {
				items.add(unit.getItem());
			}
		}
		return items;
	}
	
	@Override
	public abstract BagType getBagType();

	@Override
	public abstract void putItem(Item item, int index,boolean isNotifyUpdate, ItemLogReason reason, String param);

	@Override
	public Item getItemByUuid(String itemUuid) {
		for(IBagUnit unit :bagUnits ){
			if(unit.getItem()!=null && unit.getItem().getUUID().endsWith(itemUuid)){
				return unit.getItem();
			}
		}
		return null;
	}
	
	
	public List<IEntity> getUpdateEntities(){
		List<IEntity> resultList = new ArrayList<IEntity>();
		for(HumanItemEntity entity : itemCaches.getAllUpdateData()){
			resultList.add(entity);
		}
		return resultList;
	}
	
	public List<IEntity> getDeleteEntities(){
		List<IEntity> resultList = new ArrayList<IEntity>();
		for(HumanItemEntity entity : itemCaches.getAllDeleteData()){
			resultList.add(entity);
		}
		return resultList;
	}
	
	/**
	 * 发送物品更新消息
	 * @param index
	 */
	public void sendItemUpdateMessage(int index){
		if(index<0 || index>=bagUnits.size() ){
			return ;
		}
		this.bagUnits.get(index).sendItemUpdateMessage();
	}
	
	/**
	 * 更新物品
	 * @param index
	 */
	public void updateItem(int index){
		if(index<0 || index>=bagUnits.size() ){
			return ;
		}
		this.bagUnits.get(index).updateItem();
	}
		
}
