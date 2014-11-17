package com.hifun.soul.gameserver.bag.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;

/**
 * 背包服务
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class BagService implements IInitializeRequired {

	@Override
	public void init() {

	}

	/**
	 * 获取物品列表
	 * 
	 * @param player
	 * @param bagType
	 * @return
	 */
	public CommonItem[] getCommonItemList(HumanBagManager bagManager, BagType bagType) {
		List<Item> items = bagManager.getItems(bagType);
		int size = items.size();
		CommonItem[] commonItems = new CommonItem[size];
		for (int i = 0; i < size; i++) {
			if (items.get(i) != null) {
				commonItems[i] = CommonItemBuilder.converToCommonItem(items
						.get(i));
			}
		}
		return commonItems;
	}

	/**
	 * 获取指定背包位置的物品
	 * 
	 * @param player
	 * @param bagType
	 * @param position
	 * @return
	 */
	public CommonItem getCommonItem(HumanBagManager bagManager, BagType bagType, int position) {
		Item item = bagManager.getItem(bagType, position);
		if (item == null) {
			return null;
		} else {
			return CommonItemBuilder.converToCommonItem(item);
		}
	}

}
