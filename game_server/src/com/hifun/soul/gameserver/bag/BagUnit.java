package com.hifun.soul.gameserver.bag;

import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.entity.HumanItemEntity;
import com.hifun.soul.gameserver.bag.msg.GCItemUpdate;
import com.hifun.soul.gameserver.bag.msg.GCRemoveItem;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;

public abstract class BagUnit implements IBagUnit {
	/** 物品实体缓存 */
	protected CacheEntry<String, HumanItemEntity> itemCaches = null;
	
	protected Human human;
	protected Item item;
	protected int index;
	public BagUnit(Human human,int index){
		this.human = human;
		this.index = index;
	}
	/**
	 * 发送物品更新消息
	 */
	@Override
	public void sendItemUpdateMessage(){
		short bagType = (short)getBagType().getIndex();
		if(item==null){
			GCRemoveItem gcRemoveMsg = new GCRemoveItem();
			gcRemoveMsg.setBagType(bagType);
			gcRemoveMsg.setBagIndex(getIndex());					
			human.sendMessage(gcRemoveMsg);
			return;
		}
		GCItemUpdate gcUpdateMsg = new GCItemUpdate();
		CommonItem commonItem = CommonItemBuilder.converToCommonItem(item);
		gcUpdateMsg.setItem(commonItem);
		human.sendMessage(gcUpdateMsg);		
	}
	
	/**
	 * 添加物品到待删除缓存
	 * 
	 * @param item
	 */
	protected void addToDeleteCache(Item item){
		HumanItem.Builder humanItem = ItemFactory.converItemToHumanItem(human.getHumanGuid(), this.item, BagType.MAIN_BAG);
		HumanItemEntity itemEntity =  new HumanItemEntity(humanItem);
		itemCaches.addDelete(itemEntity.getId(),itemEntity);
	}
	
	/**
	 * 添加物品到待更新缓存
	 * 
	 * @param item
	 */
	protected void addToUpdateCache(Item item){
		HumanItem.Builder humanItem = ItemFactory.converItemToHumanItem(human.getHumanGuid(), this.item, BagType.MAIN_BAG);
		HumanItemEntity itemEntity =  new HumanItemEntity(humanItem);
		itemCaches.addUpdate(itemEntity.getId(),itemEntity);
	}
	
	@Override
	public int getIndex(){
		return this.index;
	}
	
	protected abstract BagType getBagType();
	
	/**
	 * 更新物品
	 */
	@Override
	public void updateItem(){
		if(this.item!=null){
			addToUpdateCache(this.item);
		}
	}
}
