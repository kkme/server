package com.hifun.soul.gameserver.quest.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.quest.msg.CGFinishQuest;

/**
 * 处理完成任务;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGFinishQuestHandler implements
		IMessageHandlerWithType<CGFinishQuest> {

	@Override
	public short getMessageType() {
		return MessageType.CG_FINISH_QUEST;
	}

	@Override
	public void execute(CGFinishQuest message) {
		// 处理完成任务, 领取奖励
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		human.getHumanQuestManager().onFinishMainQuest(message.getQuestId());
	}

}
