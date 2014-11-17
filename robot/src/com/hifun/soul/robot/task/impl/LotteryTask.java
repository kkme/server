package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.turntable.msg.CGLottery;
import com.hifun.soul.gameserver.turntable.msg.CGShowTurntablePanel;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class LotteryTask extends LoopExecuteTask {

	public LotteryTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		int i = MathUtils.randomInt(2);
		switch (i) {
		case 0:
			CGLottery cgLottery = new CGLottery();
			getRobot().sendMessage(cgLottery);
			break;

		case 1:
			CGShowTurntablePanel cgShowTurntablePanel = new CGShowTurntablePanel();
			getRobot().sendMessage(cgShowTurntablePanel);
			break;
		}

	}

	@Override
	public void onResponse(IMessage message) {
	}

}
