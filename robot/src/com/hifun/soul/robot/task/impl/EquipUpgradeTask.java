package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.item.msg.CGEquipUpgrade;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class EquipUpgradeTask extends LoopExecuteTask {

	public EquipUpgradeTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		CGEquipUpgrade cgEquipUpgrade = new CGEquipUpgrade();
		cgEquipUpgrade.setEquipBagIndex(MathUtils.randomInt(10));
		cgEquipUpgrade.setEquipBagType(BagType.MAIN_BAG.getIndex());
		cgEquipUpgrade.setGuardStoneId(0);
		cgEquipUpgrade.setFortuneStoneId(0);
		
		getRobot().sendMessage(cgEquipUpgrade);
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
