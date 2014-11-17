package com.hifun.soul.gameserver.shop;

import java.util.Comparator;

import com.hifun.soul.gameserver.item.assist.CommonItem;

public class ShopItemSorter implements Comparator<CommonItem>{
	
	@Override
	public int compare(CommonItem item1, CommonItem item2) {
		if (item1.getType() != item2.getType()) {
			return item1.getType() - item2.getType();
		}

		if (item1.getItemId() != item2.getItemId()) {
			return item1.getItemId() - item2.getItemId();
		}

		return 0;
	}
	
}
