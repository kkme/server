package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.msg.CGAddFriendAgree;
import com.hifun.soul.gameserver.friend.msg.GCAddFriendInfo;
import com.hifun.soul.gameserver.friend.msg.GCRemoveApply;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 同意添加好友
 */
@Component
public class CGAddFriendAgreeHandler implements
		IMessageHandlerWithType<CGAddFriendAgree> {
	@Autowired
	private GameWorld sceneManager;
	@Autowired
	private FriendService friendService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ADD_FRIEND_AGREE;
	}

	@Override
	public void execute(CGAddFriendAgree message) {
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
		final long fromRoleId = message.getFromRoleId();
		// 判断玩家是否自己发出过申请
		if(!friendService.isFriendApplying(human.getHumanGuid(),fromRoleId)){
			human.sendGenericMessage(LangConstants.NO_APPLYED);
			return;
		}
		// 判断自己的好友是否达到上限
		if(!friendService.canAddFriends(human.getHumanGuid(),1)){
			human.sendGenericMessage(LangConstants.SELF_MAX_FRIEND_NUM);
			return;
		}
		// 判断是否已经是好友
		if(friendService.isFriend(human.getHumanGuid(),fromRoleId)){
			human.sendGenericMessage(LangConstants.IS_IN_FRIENDS);
			return;
		}
		// 判断对方的好友是否已经达到上限
		if(!friendService.canAddFriends(fromRoleId,1)){
			human.sendGenericMessage(LangConstants.OTHER_MAX_FRIEND_NUM);
			return;
		}
		// 新建好友关系
		friendService.addFriendInfo(human, human.getHumanGuid(), fromRoleId);
		// 更新自己的好友信息
		GCAddFriendInfo selfAddMsg = new GCAddFriendInfo();
		FriendInfo friendInfo = friendService.getFriendInfo(human.getHumanGuid(), fromRoleId);
		if(friendInfo == null){
			return;
		}
		selfAddMsg.setFriendInfo(friendService.getFriendInfo(human.getHumanGuid(), fromRoleId));
		human.sendMessage(selfAddMsg);
		// 更新自己的好友申请列表
		GCRemoveApply gcRemoveApply = new GCRemoveApply();
		gcRemoveApply.setFromRoleId(fromRoleId);
		human.sendMessage(gcRemoveApply);
		// 提示成功添加**为好友
		human.sendSuccessMessage(LangConstants.ADD_FRIEND_SUCCESS, friendInfo.getRoleName());
		Human applyer = sceneManager.getSceneHumanManager().getHumanByGuid(fromRoleId);
		if(applyer != null){
			// 更新自己的好友申请列表
			GCAddFriendInfo otherAddMsg = new GCAddFriendInfo();
			FriendInfo otherFriendInfo = friendService.getFriendInfo(fromRoleId, human.getHumanGuid());
			if(otherFriendInfo == null){
				return;
			}
			otherAddMsg.setFriendInfo(otherFriendInfo);
			applyer.sendMessage(otherAddMsg);
			// 提示对方玩家某人通过他的好友请求
			applyer.sendSuccessMessage(LangConstants.ADD_FRIEND_SUCCESS, human.getName());
		}
	}

}
