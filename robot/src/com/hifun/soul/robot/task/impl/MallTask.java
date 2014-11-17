package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.mall.MallItemType;
import com.hifun.soul.gameserver.mall.msg.CGBuyMallItem;
import com.hifun.soul.gameserver.mall.msg.CGShowMallItemList;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class MallTask extends LoopExecuteTask {
	
	private MallItemType[] types = MallItemType.values();
	
	private int i = 0;

	public MallTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		switch (i) {
		case 0:
			CGShowMallItemList cgShowMallItemList = new CGShowMallItemList();
			cgShowMallItemList.setMallItemType(types[MathUtils.randomInt(types.length)].getIndex());
			cgShowMallItemList.setPageIndex((short)0);
			getRobot().sendMessage(cgShowMallItemList);
			break;
		case 1:
			CGBuyMallItem cgBuyMallItem = new CGBuyMallItem();
			cgBuyMallItem.setItemId(72110001);
			cgBuyMallItem.setNum(1);
			getRobot().sendMessage(cgBuyMallItem);
			break;
		}
		
		i = (i++)%2;
	}

	@Override
	public void onResponse(IMessage message) {
		
	}

}
