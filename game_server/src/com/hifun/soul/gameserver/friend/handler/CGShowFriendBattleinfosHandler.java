package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendBattleInfo;
import com.hifun.soul.gameserver.friend.manager.HumanFriendManager;
import com.hifun.soul.gameserver.friend.msg.CGShowFriendBattleinfos;
import com.hifun.soul.gameserver.friend.msg.GCShowFriendBattleinfos;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGShowFriendBattleinfosHandler implements
		IMessageHandlerWithType<CGShowFriendBattleinfos> {
	@Autowired
	private SystemTimeService systemTimeService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_FRIEND_BATTLEINFOS;
	}

	@Override
	public void execute(CGShowFriendBattleinfos message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FRIEND, true)) {
			return;
		}
		HumanFriendManager friendManager = human.getHumanFriendManager();
		FriendBattleInfo[] friendBattleInfos = new FriendBattleInfo[friendManager.getFriendBattleInfos().size()];
		int i=0;
		for(FriendBattleInfo friendBattleInfo : friendManager.getFriendBattleInfos()){
			friendBattleInfo.setTimeInterval((int) (systemTimeService.now() - friendBattleInfo.getBattleTime()));
			friendBattleInfos[i] = friendBattleInfo;
			i++;
		}
		GCShowFriendBattleinfos gcMsg = new GCShowFriendBattleinfos();
		gcMsg.setFriendBattleInfos(friendBattleInfos);
		human.sendMessage(gcMsg);
	}

}
