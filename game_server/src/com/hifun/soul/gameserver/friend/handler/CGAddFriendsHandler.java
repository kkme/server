package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.msg.CGAddFriends;
import com.hifun.soul.gameserver.friend.msg.GCFriendApply;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGAddFriendsHandler implements IMessageHandlerWithType<CGAddFriends> {
	@Autowired
	private GameWorld sceneManager;
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ADD_FRIENDS;
	}

	@Override
	public void execute(CGAddFriends message) {
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
		// 批量发送添加在线好友的申请
		// 先判断要求添加好友的数量
		if(message.getRoleIds().length > GameServerAssist.getGameConstants().getFriendRecommendNum()){
			return;
		}
		// 循环添加好友
		for(long roleId : message.getRoleIds()){
			// 判断添加的人是不是自己
			if(human.getHumanGuid() == roleId){
				continue;
			}
			// 判断玩家是否在线
			Human friend = sceneManager.getSceneHumanManager().getHumanByGuid(roleId);
			if(friend == null){
				continue;
			}
			// 判断自己是否发出过好友申请
			if(friendService.isSelfApplyed(human.getHumanGuid(),roleId)){
				continue;
			}
			// 判断对方是否向自己发出过申请
			if(friendService.isFriendApplying(human.getHumanGuid(),roleId)){
				continue;
			}
			// 判断是否已经建立好友关系
			if(friendService.isFriend(human.getHumanGuid(),roleId)){
				continue;
			}
			// 判断自己的好友数量是否达到上限
			if(!friendService.canAddFriends(human.getHumanGuid(),1)){
				human.sendGenericMessage(LangConstants.SELF_MAX_FRIEND_NUM);
				break;
			}
			// 好友在线，获取到好友的好友管理器判断是否该玩家好友已满
			if(!friendService.canAddFriends(roleId,1)){
				continue;
			}
			// 判断对方的好友申请是否达到上限
			if(friendService.friendApplyIsFull(roleId)){
				continue;
			}
			// 发送好友申请
			friendService.sendFriendApplying(human.getHumanGuid(), roleId);
			// 好友申请已经成功发出
			human.sendSuccessMessage(LangConstants.FRIEND_APPLY_SUCCESS, friend.getName());
			// 通知客户端
			GCFriendApply gcFriendApply = new GCFriendApply();
			FriendInfo friendInfo = friendService.getFriendInfo(roleId, human.getHumanGuid());
			gcFriendApply.setApplyer(friendInfo);
			friend.sendMessage(gcFriendApply);
		}
		// 发送出请求之后换一批好友
		human.getHumanFriendManager().sendLuckFriendInfos();
	}
	
}
