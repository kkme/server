package com.hifun.soul.gameserver.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.friend.msg.CGFriendChat;
import com.hifun.soul.gameserver.friend.msg.GCFriendChat;
import com.hifun.soul.gameserver.friend.msg.GCFriendOffline;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGFriendChatHandler implements IMessageHandlerWithType<CGFriendChat> {
	@Autowired
	private GameWorld gameWorld;
	@Autowired
	private FriendService friendService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_FRIEND_CHAT;
	}

	@Override
	public void execute(CGFriendChat message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FRIEND, true)) {
			return;
		}
		// 判断聊天的对象是否在线
		Human friend = gameWorld.getSceneHumanManager().getHumanByGuid(message.getDestRoleId());
		if(friend == null){
			GCFriendOffline gcMsg = new GCFriendOffline();
			gcMsg.setRoleId(message.getDestRoleId());
			gcMsg.setRoleName(message.getDestRoleName());
			human.sendMessage(gcMsg);
			return;
		}
		// 截取内容长度
		String content = checkContentLength(message.getContent());
		// 过滤聊天内容
		content = filterContent(content);
		String chatTime = TimeUtils.formatYMDHMTime(GameServerAssist.getSystemTimeService().now());
		// 发送消息
		GCFriendChat gcFriendChatOne = new GCFriendChat();
		gcFriendChatOne.setFromRoleId(human.getHumanGuid());
		gcFriendChatOne.setFromRoleName(human.getName());
		gcFriendChatOne.setFromRoleLevel(human.getLevel());
		gcFriendChatOne.setFromRoleOccupation(human.getOccupation().getIndex());
		gcFriendChatOne.setContent(content);
		gcFriendChatOne.setChatTime(chatTime);
		human.sendMessage(gcFriendChatOne);
		GCFriendChat gcFriendChatTwo = new GCFriendChat();
		gcFriendChatTwo.setFromRoleId(human.getHumanGuid());
		gcFriendChatTwo.setFromRoleName(human.getName());
		gcFriendChatTwo.setFromRoleLevel(human.getLevel());
		gcFriendChatTwo.setFromRoleOccupation(human.getOccupation().getIndex());
		gcFriendChatTwo.setContent(content);
		gcFriendChatTwo.setChatTime(chatTime);
		friend.sendMessage(gcFriendChatTwo);
		// 校验一下最近联系人是不是需要修改
		friendService.updateLatestFriends(human.getHumanGuid(), friend.getHumanGuid());
	}
	
	/**
	 * 根据具体消息的长度限制截取内容
	 * @param content
	 * @return
	 */
	private String checkContentLength(String content){
		if (GameServerAssist.getGameConstants().getPrivateChatLength() < content.length()) {
			content = content.substring(0, GameServerAssist.getGameConstants().getPrivateChatLength());
		}
		return content;
	}
	
	/**
	 * 过滤聊天内容
	 * @param content
	 * @return
	 */
	private String filterContent(String content){
		String msgContent = GameServerAssist.getWordFilterService().filterHtmlTag(content);
		boolean hasDirtyWords = GameServerAssist.getWordFilterService().containKeywords(msgContent);
		if (hasDirtyWords) {
			msgContent = GameServerAssist.getWordFilterService().filter(msgContent);
		}
		return msgContent;
	}

}
