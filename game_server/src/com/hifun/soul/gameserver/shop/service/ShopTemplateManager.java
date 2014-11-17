package com.hifun.soul.gameserver.shop.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.shop.ShopItemSorter;
import com.hifun.soul.gameserver.shop.SpecialShopItem;
import com.hifun.soul.gameserver.shop.template.ShopTemplate;
import com.hifun.soul.gameserver.shop.template.SpecialShopTemplate;


@Scope("singleton")
@Component
public class ShopTemplateManager implements IInitializeRequired{
	@Autowired
	private TemplateService templateService;
	/** key：itemId */
	private Map<Integer,CommonItem> shopTemplateMap = new HashMap<Integer,CommonItem>();
	/** 商店出售的所有物品 */
	private List<CommonItem> commonItems = new ArrayList<CommonItem>();
	/** 排序 */
	private ShopItemSorter shopItemSorter = new ShopItemSorter();
	/** 魔晶可以刷新物品 */
	private Map<Integer,SpecialShopItem> crystalItems = new HashMap<Integer,SpecialShopItem>();
	/** 金币可以刷新物品 */
	private Map<Integer,SpecialShopItem> coinItems = new HashMap<Integer,SpecialShopItem>();
	
	@Override
	public void init() {
		Map<Integer,ShopTemplate> items = templateService.getAll(ShopTemplate.class);
		for(ShopTemplate shopTemplate : items.values()){
			Integer itemId = shopTemplate.getItemId();
			CommonItem commonItem = CommonItemBuilder.genCommonItem(itemId);
			if(commonItem != null){
				commonItem.setShopCurrencyType(shopTemplate.getCurrencyType());
				commonItem.setShopCurrencyNum(shopTemplate.getNum());
				commonItem.setCanSeeLevelLimit(shopTemplate.getLevelLimit());
				commonItem.setCanSeeOccupationLimit(shopTemplate.getOccupationLimit());
				commonItems.add(commonItem);
				shopTemplateMap.put(itemId, commonItem);
			}
		}
		
		for(SpecialShopTemplate template : templateService.getAll(SpecialShopTemplate.class).values()){
			if(template == null){
				continue;
			}
			
			SpecialShopItem specialShopItem = new SpecialShopItem();
			specialShopItem.setId(template.getId());
			CommonItem commonItem = CommonItemBuilder.genCommonItem(template.getItemId());
			commonItem.setCanSeeLevelLimit(template.getLevelLimit());
			commonItem.setCanSeeOccupationLimit(template.getOccupationLimit());
			specialShopItem.setCommonItem(commonItem);
			specialShopItem.setCurrencyType(template.getCurrencyType());
			specialShopItem.setCurrencyNum(template.getCurrencyNum());
			specialShopItem.setIsBuy(false);
			specialShopItem.setItemId(template.getItemId());
			specialShopItem.setItemNum(template.getItemNum());
			specialShopItem.setRate(template.getRate());
			specialShopItem.setRefreshType(template.getRefreshType());
			crystalItems.put(template.getId(), specialShopItem);
			
			if(specialShopItem.getRefreshType() == CurrencyType.COIN.getIndex()){
				coinItems.put(template.getId(), specialShopItem);
			}
		}
		
	}
	
	public CommonItem getShopItem(Integer itemId) {
		return shopTemplateMap.get(itemId);
	}
	
	/**
	 * 根据玩家的等级和职业找到玩家可见的物品列表
	 * 
	 * @param level
	 * @param occupation
	 * @return
	 */
	public List<CommonItem> getAllItems(int level, int occupation) {
		List<CommonItem> items = new ArrayList<CommonItem>();
		for(CommonItem commonItem : commonItems){
			if(canSee(commonItem, level, occupation)){
				items.add(commonItem);
			}
		}
		return items;
	}
	
	/**
	 * 判断物品对玩家是否可见
	 * 
	 * @param commonItem
	 * @param level
	 * @param occupation
	 * @return
	 */
	public boolean canSee(CommonItem commonItem, int level, int occupation) {
		if(level >= commonItem.getCanSeeLevelLimit()
				&& (occupation == commonItem.getCanSeeOccupationLimit()
					|| Occupation.typeOf(commonItem.getCanSeeOccupationLimit()) == null)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 对目标list进行排序
	 * @param displayList
	 */
	public void sortShopItemList(List<CommonItem> items)
	{
		Collections.sort(items,getShopItemSorter());
	}
	
	public ShopItemSorter getShopItemSorter() {
		return shopItemSorter;
	}
	
	public SpecialShopItem getSpecialShopItem(int specialShopItemId){
		return crystalItems.get(specialShopItemId);
	}
	
	public List<SpecialShopItem> getCommonSpecialShopItems(int level, int occupation) {
		List<SpecialShopItem> items = new ArrayList<SpecialShopItem>();
		for(SpecialShopItem item : coinItems.values()){
			if(canSee(item.getCommonItem(), level, occupation)){
				items.add(item);
			}
		}
		return items;
	}
	
	public List<SpecialShopItem> getAllSpecialShopItems(int level, int occupation) {
		List<SpecialShopItem> items = new ArrayList<SpecialShopItem>();
		for(SpecialShopItem item : crystalItems.values()){
			if(canSee(item.getCommonItem(), level, occupation)){
				items.add(item);
			}
		}
		return items;
	}
}
