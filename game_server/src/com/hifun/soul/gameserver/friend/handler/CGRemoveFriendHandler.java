package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.msg.CGRemoveFriend;
import com.hifun.soul.gameserver.friend.msg.GCRemoveFriend;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGRemoveFriendHandler implements IMessageHandlerWithType<CGRemoveFriend> {
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_REMOVE_FRIEND;
	}

	@Override
	public void execute(CGRemoveFriend message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FRIEND, true)) {
			return;
		}
		long roleId = message.getRoleId();
		// 判断是否是好友关系
		if(!friendService.isFriend(human.getHumanGuid(),roleId)){
			return;
		}
		// 删除好友关系
		friendService.removeFriendInfo(human, human.getHumanGuid(), roleId);
		// 更新好友信息
		GCRemoveFriend gcMsg = new GCRemoveFriend();
		gcMsg.setRoleId(roleId);
		human.sendMessage(gcMsg);
	}

}
