package com.hifun.soul.gameserver.shop.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanSpecialShopItemEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.shop.SpecialShopItem;
import com.hifun.soul.gameserver.shop.SpecialShopRefreshType;
import com.hifun.soul.gameserver.shop.converter.SpecialShopItemToEntityConverter;
import com.hifun.soul.gameserver.shop.msg.GCShowSpecialShopPanel;
import com.hifun.soul.gameserver.shop.service.SpecialShopNotifyService;
import com.hifun.soul.gameserver.timetask.TimeTaskType;
import com.hifun.soul.gameserver.vip.template.RefreshSpecialShopTemplate;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialShopItem;

public class HumanSpecialShopManager implements ICachableComponent,IHumanPersistenceManager {

	private Human _human;
	private Map<Integer,Boolean> specialShopItemMap = new HashMap<Integer,Boolean>();
	private CacheEntry<Integer, SpecialShopItem> specialShopItemCaches = new CacheEntry<Integer, SpecialShopItem>();
	private SpecialShopItemToEntityConverter converter;
	
	public HumanSpecialShopManager(Human human) {
		this._human = human;
		converter = new SpecialShopItemToEntityConverter(_human);
		
		this._human.registerCachableManager(this);
		this._human.registerPersistenceManager(this);
	}
	
	public Human getOwner(){
		return _human;
	}
	
	public Collection<SpecialShopItem> getSpecialShopItems() {
		List<SpecialShopItem> specialShopItems = new ArrayList<SpecialShopItem>();
		for(Integer specialItemId : specialShopItemMap.keySet()){
			SpecialShopItem specialShopItem  = GameServerAssist.getShopTemplateManager().getSpecialShopItem(specialItemId);
			if(specialShopItem != null){
				specialShopItem.setIsBuy(specialShopItemMap.get(specialItemId));
				specialShopItems.add(specialShopItem);
			}
		}
		return specialShopItems;
	}
	
	public SpecialShopItem getSpecialShopItem(int specialItemId) {
		SpecialShopItem specialShopItem  = GameServerAssist.getShopTemplateManager().getSpecialShopItem(specialItemId);
		if(specialShopItem == null){
			return null;
		}
		Boolean isBuy = specialShopItemMap.get(specialItemId);
		if(isBuy == null){
			isBuy = true;
		}
		specialShopItem.setIsBuy(isBuy);
		return specialShopItem;
	}
	
	/**
	 * 刷新神秘商店的物品
	 * @return
	 */
	public void setLastRefreshTime(long lastTime) {
		_human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_REFRESH_SPECIAL_SHOP_TIME, lastTime);
	}

	public long getLastRefreshTime() {
		return _human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_REFRESH_SPECIAL_SHOP_TIME);
	}
	
	/**
	 * 重置神秘商店每日重置次数
	 * @return
	 */
	public void setLastResetTime(long lastTime) {
		_human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_SPECIAL_SHOP_BUY_TIME, lastTime);
	}

	public long getLastResetTime() {
		return _human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_SPECIAL_SHOP_BUY_TIME);
	}
	
	/**
	 * 神秘商店刷新次数
	 * @return
	 */
	public int getSpecialShopRefreshTime() {
		return _human.getHumanPropertiesManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.SPECIAL_SHOP_REFRESH_TIME);
	}
	
	public void setSpecialShopRefreshTime(int refreshTime) {
		_human.getHumanPropertiesManager()
			 .getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			 .setPropertyValue(HumanIntProperty.SPECIAL_SHOP_REFRESH_TIME, refreshTime);
	}
	
	/**
	 * 重置神秘商店购买次数
	 */
	public void resetSpecialShopBuyTime() {
		// 重置已经花费的魔晶刷新神秘商店的次数
		setSpecialShopRefreshTime(0);
	}
	
	/**
	 * 判断是否还可以执行魔晶刷新神秘商店操作
	 * @return
	 */
	public boolean canRefreshSpecialShop() {
		RefreshSpecialShopTemplate template = GameServerAssist.getTemplateService().get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, RefreshSpecialShopTemplate.class);
		return template.getTime() > getSpecialShopRefreshTime();
	}
	
	public void updateSpecialShopItem(int specialItemId, SpecialShopItem specialShopItem) {
		specialShopItemMap.put(specialItemId, specialShopItem.getIsBuy());
		// 更新缓存
		specialShopItemCaches.addUpdate(specialItemId, specialShopItem);
	}
	
	/**
	 * 更新神秘商店面板
	 * 
	 * @param specialShopService
	 */
	public void updateSpecialShopPanel(SpecialShopNotifyService specialShopService) {
		GCShowSpecialShopPanel gcMsg = new GCShowSpecialShopPanel();
		gcMsg.setSpecialShopItemList(getSpecialShopItems().toArray(new SpecialShopItem[0]));
		gcMsg.setSpecialShopNotifyList(specialShopService.getSpecialShopNotify());
		RefreshSpecialShopTemplate template = GameServerAssist.getTemplateService().get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, RefreshSpecialShopTemplate.class);
		if(template != null){
			gcMsg.setCrystal(template.getCrystal());
			gcMsg.setRefreshTime(template.getTime()-getSpecialShopRefreshTime());
		}
		gcMsg.setRefreshHours(GameServerAssist.getTimeTaskTemplateManager()
				.getHours(TimeTaskType.REFRESH_SPECIAL_SHOP.getIndex()));
		_human.sendMessage(gcMsg);
	}
	
	/**
	 * 刷新神秘商店
	 * 
	 */
	public void refreshSpecialShop(SpecialShopRefreshType type){
		// 首先清空当前神秘商店中物品
		for(Integer specialShopItemId : specialShopItemMap.keySet()){
			SpecialShopItem specialShopItem = getSpecialShopItem(specialShopItemId);
			if(specialShopItem != null){
				specialShopItemCaches.addDelete(specialShopItem.getId(), specialShopItem);
			}
		}
		specialShopItemMap.clear();
		List<SpecialShopItem> specialShopItems = null;
		switch(type){
		case COIN:
			specialShopItems = GameServerAssist.getShopTemplateManager().getCommonSpecialShopItems(_human.getLevel(), _human.getOccupation().getIndex());
			break;
		case CRYSTAL:
			specialShopItems = GameServerAssist.getShopTemplateManager().getAllSpecialShopItems(_human.getLevel(), _human.getOccupation().getIndex());
			break;
		}
		if(specialShopItems != null){
			List<Integer> weights = new ArrayList<Integer>();
			for(SpecialShopItem specialShopItem : specialShopItems){
				weights.add(specialShopItem.getRate());
			}
			
			int[] indexs = MathUtils.getRandomUniqueIndex(weights, SharedConstants.SPECIAL_SHOP_ITEM_NUM);
			if(indexs != null){
				for(int index : indexs){
					SpecialShopItem newSpecialShopItem = specialShopItems.get(index);
					newSpecialShopItem.setIsBuy(false);
					if(newSpecialShopItem != null){
						updateSpecialShopItem(newSpecialShopItem.getId(),newSpecialShopItem);
					}
				}
			}
		}
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for(HumanSpecialShopItem.Builder builder : humanEntity.getBuilder().getSpecialShopItemBuilderList()){
			specialShopItemMap.put(builder.getSpecialShopItem().getId(), builder.getSpecialShopItem().getIsBuy());
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for(Integer specialShopItemId : specialShopItemMap.keySet()){
			SpecialShopItem specialShopItem = getSpecialShopItem(specialShopItemId);
			if(specialShopItem != null){
				humanEntity.getBuilder().addSpecialShopItem(this.converter.convert(specialShopItem).getBuilder());
			}
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		
		for(SpecialShopItem specialShopItem : specialShopItemCaches.getAllUpdateData()){
			HumanSpecialShopItemEntity entity = this.converter.convert(specialShopItem);
			updateList.add(entity);
		}
		
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		
		for(SpecialShopItem specialShopItem : specialShopItemCaches.getAllDeleteData()){
			HumanSpecialShopItemEntity entity = this.converter.convert(specialShopItem);
			deleteList.add(entity);
		}
		
		return deleteList;
	}
	
	
}
