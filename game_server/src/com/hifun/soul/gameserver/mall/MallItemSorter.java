package com.hifun.soul.gameserver.mall;

import java.util.Comparator;

import com.hifun.soul.gameserver.mall.msg.MallItemInfo;

public class MallItemSorter implements Comparator<MallItemInfo>{
	
	@Override
	public int compare(MallItemInfo item1, MallItemInfo item2) {
		if (item1.getItemId() != item2.getItemId()) {
			return item1.getItemId() - item2.getItemId();
		}

		return 0;
	}
	
}
