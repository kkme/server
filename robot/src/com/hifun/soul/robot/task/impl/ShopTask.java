package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.shop.msg.CGBuyShopItem;
import com.hifun.soul.gameserver.shop.msg.CGSellShopItem;
import com.hifun.soul.gameserver.shop.msg.CGShowShopItemList;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class ShopTask extends LoopExecuteTask {

	private int i = 0;
	
	public ShopTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		switch (i) {
		case 0:
			CGShowShopItemList cgShowShopItemList = new CGShowShopItemList();
			cgShowShopItemList.setPageIndex((short)0);
			getRobot().sendMessage(cgShowShopItemList);
			break;
		case 1:
			CGBuyShopItem cgBuyShopItem = new CGBuyShopItem();
			cgBuyShopItem.setItemId(73140003);
			cgBuyShopItem.setNum(1);
			getRobot().sendMessage(cgBuyShopItem);
			break;
		case 2:
			CGSellShopItem cgSellShopItem = new CGSellShopItem();
			cgSellShopItem.setBagType(BagType.MAIN_BAG.getIndex());
			cgSellShopItem.setBagIndex(0);
			cgSellShopItem.setNum(1);
			getRobot().sendMessage(cgSellShopItem);
			break;
		}
		
		i = (i++)%3;
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
