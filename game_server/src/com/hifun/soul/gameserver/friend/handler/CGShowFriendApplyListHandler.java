package com.hifun.soul.gameserver.friend.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.msg.CGShowFriendApplyList;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 好友申请列表
 */
@Component
public class CGShowFriendApplyListHandler implements
		IMessageHandlerWithType<CGShowFriendApplyList> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_FRIEND_APPLY_LIST;
	}

	@Override
	public void execute(CGShowFriendApplyList message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		final Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FRIEND, true)) {
			return;
		}
		human.getHumanFriendManager().sendFriendApplyInfos();
	}

}
