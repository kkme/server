package com.hifun.soul.robot.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.chat.ChatType;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class ChatTask extends LoopExecuteTask {
	
	private List<String> chats = new ArrayList<String>();

	public ChatTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		chats.add("what is your name");
		chats.add("hello babys");
		chats.add("哥布林工程师");
		chats.add("半人马战士");
		chats.add("亡灵战士");
		chats.add("徒手异鬼");
		chats.add("剑盾炎魔");
		chats.add("被一群贪婪哥布林占领的春晓之森");
		chats.add("被一群遭放逐的半人马控制的烈阳沙漠 ");
		chats.add("被曝尸荒野的亡灵阻隔的寒鸦旷野");
		chats.add("被无数冰封的异鬼盘踞的凛冬峡湾");
		chats.add("被罪恶的火焰领主统治的炼狱火山");
		
		CGChatMsg cgChatMsg = new CGChatMsg();
		cgChatMsg.setContent(chats.get(MathUtils.randomInt(chats.size())));
		cgChatMsg.setChatType(ChatType.WORLD.getIndex());
		getRobot().sendMessage(cgChatMsg);
	}

	@Override
	public void onResponse(IMessage message) {
	}

}
