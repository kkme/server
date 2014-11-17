package com.hifun.soul.gameserver.quest.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.quest.msg.CGOpenDailyQuestRewardBox;

/**
 * 打开积分奖励箱子处理器;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenDailyQuestRewardBoxHandler implements
		IMessageHandlerWithType<CGOpenDailyQuestRewardBox> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_DAILY_QUEST_REWARD_BOX;
	}

	@Override
	public void execute(CGOpenDailyQuestRewardBox message) {
		message.getPlayer().getHuman().getHumanQuestManager().onOpenDailyQuestBox(message.getBoxId());
	}

}
