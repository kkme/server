package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.technology.msg.CGShowTechnologyInfo;
import com.hifun.soul.gameserver.technology.msg.CGUpgradeTechnology;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class TechnologyTask extends LoopExecuteTask {

	public TechnologyTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		int i = MathUtils.randomInt(2);
		int technologyId = 1;
		switch (i) {
		case 0:
			CGShowTechnologyInfo cgShowTechnologyInfo = new CGShowTechnologyInfo();
			cgShowTechnologyInfo.setTechnologyId(technologyId);
			getRobot().sendMessage(cgShowTechnologyInfo);
			break;
		case 1:
			CGUpgradeTechnology cgUpgradeTechnology = new CGUpgradeTechnology();
			cgUpgradeTechnology.setTechnologyId(technologyId);
			getRobot().sendMessage(cgUpgradeTechnology);
			break;
		}
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
