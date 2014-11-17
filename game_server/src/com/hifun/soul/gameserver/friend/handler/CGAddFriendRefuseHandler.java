package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.msg.CGAddFriendRefuse;
import com.hifun.soul.gameserver.friend.msg.GCRemoveApply;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 拒绝好友添加请求
 */
@Component
public class CGAddFriendRefuseHandler implements
		IMessageHandlerWithType<CGAddFriendRefuse> {
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ADD_FRIEND_REFUSE;
	}

	@Override
	public void execute(CGAddFriendRefuse message) {
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
		long fromRoleId = message.getFromRoleId();
		// 判断玩家是否自己发出过申请
		if(!friendService.isFriendApplying(human.getHumanGuid(),fromRoleId)){
			return;
		}
		// 拒绝别人的好友请求
		friendService.refuseApplying(human.getHumanGuid(),fromRoleId);
		// 更新自己的好友申请列表
		GCRemoveApply gcMsg = new GCRemoveApply();
		gcMsg.setFromRoleId(fromRoleId);
		human.sendMessage(gcMsg);
	}

}
