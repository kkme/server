package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.onlinereward.msg.CGGetOnlineReward;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class OnlineRewardTask extends LoopExecuteTask {

	public OnlineRewardTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		CGGetOnlineReward cgGetOnlineReward = new CGGetOnlineReward();
		getRobot().sendMessage(cgGetOnlineReward);
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
