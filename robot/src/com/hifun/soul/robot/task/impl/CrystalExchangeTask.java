package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.common.msg.CGMessage;
import com.hifun.soul.gameserver.crystalexchange.msg.CGCrystalExchange;
import com.hifun.soul.gameserver.crystalexchange.msg.CGShowCrystalExchangePanel;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class CrystalExchangeTask extends LoopExecuteTask {

	public CrystalExchangeTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		CGMessage[] msgs = new CGMessage[]{new CGShowCrystalExchangePanel(),new CGCrystalExchange()};
		getRobot().sendMessage(msgs[MathUtils.randomInt(msgs.length)]);
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
