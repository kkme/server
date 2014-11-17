package com.hifun.soul.gameserver.honourshop.sorter;

import java.util.Comparator;

import com.hifun.soul.gameserver.honourshop.msg.HonourShopItemInfo;

public class HonourShopItemSorter implements Comparator<HonourShopItemInfo> {

	@Override
	public int compare(HonourShopItemInfo item1, HonourShopItemInfo item2) {
		if (item1.getNeedHonour() != item2.getNeedHonour()) {
			return item1.getNeedHonour() - item2.getNeedHonour();
		}

		if (item1.getItemId() != item2.getItemId()) {
			return item1.getItemId() - item2.getItemId();
		}

		return 0;
	}

}
