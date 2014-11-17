package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.msg.CGShowLatestFriends;
import com.hifun.soul.gameserver.friend.msg.GCShowLatestFriends;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGShowLatestFriendsHandler implements
		IMessageHandlerWithType<CGShowLatestFriends> {
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LATEST_FRIENDS;
	}

	@Override
	public void execute(CGShowLatestFriends message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FRIEND, true)) {
			return;
		}
		GCShowLatestFriends gcMsg = new GCShowLatestFriends();
		gcMsg.setFriendInfos(friendService.getLatestFriendInfos(human.getHumanGuid()));
		human.sendMessage(gcMsg);
	}

}
