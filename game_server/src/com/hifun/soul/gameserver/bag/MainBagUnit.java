package com.hifun.soul.gameserver.bag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.entity.HumanItemEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;


public class MainBagUnit extends BagUnit {
	Map<Integer, List<IBagUnit>> itemIndexMap;
	public MainBagUnit(Human human,int index,CacheEntry<String, HumanItemEntity> itemCaches,Map<Integer, List<IBagUnit>> itemIndexMap){
		super(human,index);
		this.itemCaches = itemCaches;
		this.itemIndexMap = itemIndexMap;
	}
	@Override
	public boolean isEmpty() {
		if (item == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 放置物品
	 * @param item2
	 * @param replace
	 * @param isNotifyUpdate
	 * @param reason
	 * @param param
	 */
	public void putItem(Item item2,boolean replace,boolean isNotifyUpdate,ItemLogReason reason, String param){
		if(item2==null){
			removeItem(false,reason,param);
			return;
		}
		if (item2.isOverlapable() && !replace) {
			if (this.item != null && !this.item.getItemId().equals(item2.getItemId())) {
				removeItem(false,reason,param);
			}
			if (this.item == null) {
				if (item2.getOverlapNum() > item2.getMaxOverlap()) {
					this.item = ItemFactory.creatNewItem(human, item2.getItemId());
					this.item.setOverlapNum(item.getMaxOverlap());
					item2.setOverlapNum(item2.getOverlapNum()
							- item2.getMaxOverlap());
				} else {
					this.item = item2;
				}				
			} else {
				if (item.getOverlapNum() + item2.getOverlapNum() > item
						.getMaxOverlap()) {
					item2.setOverlapNum(item.getOverlapNum()
							+ item2.getOverlapNum() - item.getMaxOverlap());
					item.setOverlapNum(item.getMaxOverlap());
				} else {
					item.setOverlapNum(item.getOverlapNum()
							+ item2.getOverlapNum());
				}
			}			
		} else {
			if (this.item != null) {
				removeItem(false,reason,param);
			}
			this.item = item2;			
		}
		if(this.item!=null){
			this.item.setBagIndex(index);
			this.item.setBagType(BagType.MAIN_BAG);
			this.item.setHuman(human);
			this.addToUpdateCache(this.item);
			if(this.itemIndexMap.get(this.item.getItemId())==null){
				List<IBagUnit> bagUnits = new ArrayList<IBagUnit>();
				bagUnits.add(this);
				this.itemIndexMap.put(this.item.getItemId(), bagUnits);
			}
			else{
				List<IBagUnit> units = itemIndexMap.get(this.item.getItemId());
				if(!units.contains(this)){
					units.add(this);
				}
			}
		}		
		if(isNotifyUpdate){
			sendItemUpdateMessage();
		}
		
		// 发送日志
		if(reason != null){
			sendItemLogMsg(reason,param);
		}
	}

	@Override
	public void putItem(Item item2, boolean isNotifyUpdate,ItemLogReason reason, String param) {
		this.putItem(item2, false, isNotifyUpdate, reason, param);
	}

	public Item getItem() {
		return item;
	}

	
	/**
	 * 移除单元格内所有物品
	 * 
	 * @param isNotifyUpdate
	 */
	@Override
	public void removeItem(boolean isNotifyUpdate,ItemLogReason reason, String param) {
		// 发送日志
		if(reason != null){
			sendItemLogMsg(reason,param);
		}
		if(this.item!=null){
			this.itemIndexMap.get(this.item.getItemId()).remove(this);
			if(this.itemIndexMap.get(this.item.getItemId()).size()==0){
				this.itemIndexMap.remove(this.item.getItemId());
			}			
			this.addToDeleteCache(this.item);
			this.item = null;
			if(isNotifyUpdate){
				sendItemUpdateMessage();
			}
		}		
	}
	
	/**
	 * 从背包格中移除一定量的物品
	 * @param count
	 */
	public void removeItem(int count,ItemLogReason reason, String param) {
		removeItem(count,false,reason,param);
	}
	
	/**
	 * 从背包格中移除一定量的物品
	 * @param count
	 * @param isNotifyUpdate
	 */
	public void removeItem(int count,boolean isNotifyUpdate,ItemLogReason reason, String param) {
		if(this.item != null){
			if(this.item.getOverlapNum()>count){				
				this.item.setOverlapNum(item.getOverlapNum()-count);
				if(isNotifyUpdate){
					sendItemUpdateMessage();
				}
				this.addToUpdateCache(this.item);
				// 发送日志
				sendItemLogMsg(reason,param);
			}
			else{
				removeItem(isNotifyUpdate,reason,param);				
			}
		}
	}


	public boolean isFull() {
		if (this.item == null) {
			return false;
		} else {
			return item.getOverlapNum() >= item.getMaxOverlap();
		}
	}

	/**
	 * 获取背包单元还可叠加的物品数量
	 * 
	 * @return 背包单元还可叠加的数量。当背包单元为空时返回-1，当背包单元已满时返回0
	 */
	public int getFreeSize() {
		if (item == null) {
			return -1;
		} else {
			return item.getMaxOverlap() - item.getOverlapNum();
		}
	}
	
	/**
	 * 获取背包格索引
	 * @return
	 */
	@Override
	public int getIndex(){
		return this.index;
	}
	
	public BagType getBagType(){
		return BagType.MAIN_BAG;
	}
	
	/**
	 * 发送log日志
	 * 
	 * @param reason
	 * @param param
	 */
	private void sendItemLogMsg(ItemLogReason reason, String param){
		if(reason != null
				&& item != null){
			// 发送物品更新日志
			GameServerAssist.getLogService().sendItemLog(human, 
					reason, 
					param, 
					this.item.getBagType().getIndex(), 
					this.item.getBagIndex(), 
					this.item.getItemId(), 
					this.item.getUUID(), 
					this.item.getOverlapNum()
					);
		}
	}
	
	@Override
	public void logicRemoveItem(boolean isNotifyUpdate, ItemLogReason reason, String param) {
		if(this.item!=null){
			this.itemIndexMap.get(this.item.getItemId()).remove(this);
			if(this.itemIndexMap.get(this.item.getItemId()).size()==0){
				this.itemIndexMap.remove(this.item.getItemId());
			}			
			this.item = null;
			if(isNotifyUpdate){
				sendItemUpdateMessage();
			}
		}		
	}
	
}
