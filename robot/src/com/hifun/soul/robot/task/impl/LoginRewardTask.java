package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.loginreward.msg.CGGetReward;
import com.hifun.soul.gameserver.loginreward.msg.CGGetSpecialReward;
import com.hifun.soul.gameserver.loginreward.msg.CGShowLoginRewardPanel;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class LoginRewardTask extends LoopExecuteTask {

	private int i=0;
	
	public LoginRewardTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		switch(i){
		case 0:
			CGShowLoginRewardPanel cgShowLoginRewardPanel = new CGShowLoginRewardPanel();
			getRobot().sendMessage(cgShowLoginRewardPanel);
			break;
		case 1:
			CGGetReward cgGetReward = new CGGetReward();
			getRobot().sendMessage(cgGetReward);
			break;
		case 2:
			CGGetSpecialReward cgGetSpecialReward = new CGGetSpecialReward();
			cgGetSpecialReward.setSpecialRewardType(1);
			getRobot().sendMessage(cgGetSpecialReward);
			break;
		}
		
		i = (i++)%3;
	}

	@Override
	public void onResponse(IMessage message) {
		
	}

}
