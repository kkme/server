package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.OnceExecuteTask;

public class PrepareTask extends OnceExecuteTask {

	public PrepareTask(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		// 给金币
		CGChatMsg cgChatMsg1 = new CGChatMsg();
		cgChatMsg1.setContent("!givemoney 1 1000000");
		getRobot().sendMessage(cgChatMsg1);
		// 给魔晶
		CGChatMsg cgChatMsg2 = new CGChatMsg();
		cgChatMsg2.setContent("!givemoney 2 1000000 1");
		getRobot().sendMessage(cgChatMsg2);
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
