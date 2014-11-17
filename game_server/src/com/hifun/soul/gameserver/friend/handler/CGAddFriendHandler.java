package com.hifun.soul.gameserver.friend.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.FriendInfo;
import com.hifun.soul.gameserver.friend.manager.HumanFriendManager;
import com.hifun.soul.gameserver.friend.msg.CGAddFriend;
import com.hifun.soul.gameserver.friend.msg.GCFriendApply;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGAddFriendHandler implements IMessageHandlerWithType<CGAddFriend> {
	@Autowired
	private IDataService dataService;
	@Autowired
	private GameWorld sceneManager;
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ADD_FRIEND;
	}

	@Override
	public void execute(final CGAddFriend message) {
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
		final HumanFriendManager humanFriendManager = human.getHumanFriendManager();
		// 如果正在上一个好友申请没处理完退出
		if(humanFriendManager.isApplying()){
			return;
		}
		else{
			humanFriendManager.startApplying();
		}
		// 判断名字是否输入为空
		final String name = message.getName();
		if(name == null
				|| "".equals(name.trim())){
			humanFriendManager.stopApplying();
			return;
		}
		// 判断是否是自己
		if(name.equals(human.getName())){
			human.sendErrorMessage(LangConstants.CAN_NOT_ADD_SELF);
			humanFriendManager.stopApplying();
			return;
		}
		// 去数据库查询是否有这个玩家
		dataService.query(DataQueryConstants.QUERY_HUMAN_BY_NAME, 
				new String[] { "name" }, new Object[] { name },
				new IDBCallback<List<?>>() {
					@Override
					public void onSucceed(List<?> result) {
						// 判断用户名是否存在
						if(result.isEmpty()){
							human.sendErrorMessage(LangConstants.NO_ROLE);
							humanFriendManager.stopApplying();
							return;
						}
						final long friendUUID = (Long)(result.get(0));
						// 判断对方是否向你发出过申请
						if(friendService.isFriendApplying(human.getHumanGuid(),friendUUID)){
							human.sendGenericMessage(LangConstants.APPLYED);
							humanFriendManager.stopApplying();
							return;
						}
						// 判断是否已经发出过申请
						if(friendService.isSelfApplyed(human.getHumanGuid(),friendUUID)){
							human.sendSuccessMessage(LangConstants.APPLY_SUCCESS);
							humanFriendManager.stopApplying();
							return;
						}
						// 判断是否已在自己好友列表
						if(friendService.isFriend(human.getHumanGuid(),friendUUID)){
							human.sendGenericMessage(LangConstants.IS_IN_FRIENDS);
							humanFriendManager.stopApplying();
							return;
						}
						// 判断对方的好友申请是否达到上限
						if(friendService.friendApplyIsFull(friendUUID)){
							human.sendGenericMessage(LangConstants.CAN_NOT_SEND_APPLY);
							humanFriendManager.stopApplying();
							return;
						}
						// 判断自己的好友数量是否达到上限
						if(!friendService.canAddFriends(human.getHumanGuid(),1)){
							human.sendGenericMessage(LangConstants.SELF_MAX_FRIEND_NUM);
							humanFriendManager.stopApplying();
							return;
						}
						// 好友在线，获取到好友的好友管理器判断是否该玩家好友已满
						if(!friendService.canAddFriends(friendUUID,1)){
							human.sendGenericMessage(LangConstants.OTHER_MAX_FRIEND_NUM);
							humanFriendManager.stopApplying();
							return;
						}
						// 发送好友申请
						friendService.sendFriendApplying(human.getHumanGuid(),friendUUID);
						// 好友申请已经成功发出
						human.sendSuccessMessage(LangConstants.APPLY_SUCCESS);
						// 通知客户端
						Human friend = sceneManager.getSceneHumanManager().getHumanByGuid(friendUUID);
						if(friend != null){
							GCFriendApply gcFriendApply = new GCFriendApply();
							FriendInfo friendInfo = friendService.getFriendInfo(friendUUID, human.getHumanGuid());
							if(friendInfo != null){
								gcFriendApply.setApplyer(friendInfo);
								friend.sendMessage(gcFriendApply);
							}
						}
						humanFriendManager.stopApplying();
					}
					
					@Override
					public void onFailed(String errorMsg) {
						humanFriendManager.stopApplying();
					}
				});
	}

}
