package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.item.msg.CGEquipMake;
import com.hifun.soul.gameserver.item.msg.CGSelectEquipPaperToMake;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class EquipMakeTask extends LoopExecuteTask {

	private int i=0;
	
	public EquipMakeTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		// 简单测试，完全模拟测试需要首先添加物品
		switch (i) {
		case 0:
			CGSelectEquipPaperToMake cgSelectEquipPaperToMake = new CGSelectEquipPaperToMake();
			cgSelectEquipPaperToMake.setBagIndex(0);
			getRobot().sendMessage(cgSelectEquipPaperToMake);
			break;
		case 1:
			CGEquipMake cgEquipMake = new CGEquipMake();
			cgEquipMake.setPaperBagType(BagType.MAIN_BAG.getIndex());
			cgEquipMake.setPaperBagIndex(1);
			getRobot().sendMessage(cgEquipMake);
			break;
		}
		
		i = (i++)%2;
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
