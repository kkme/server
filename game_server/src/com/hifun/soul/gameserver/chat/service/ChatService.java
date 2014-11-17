package com.hifun.soul.gameserver.chat.service;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.gameserver.chat.ChatType;
import com.hifun.soul.gameserver.chat.GuildChat;
import com.hifun.soul.gameserver.chat.IChatStrategy;
import com.hifun.soul.gameserver.chat.NearChat;
import com.hifun.soul.gameserver.chat.PrivateChat;
import com.hifun.soul.gameserver.chat.TeamChat;
import com.hifun.soul.gameserver.chat.WorldChat;


/**
 * 
 * 聊天的服务管理
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class ChatService implements IInitializeRequired {
	
	public HashMap<ChatType,IChatStrategy> strategys = new HashMap<ChatType,IChatStrategy>();

	@Override
	public void init() {
		registerChatStrategys();
	}
	
	/**
	 * 注册聊天类型
	 * 
	 */
	private void registerChatStrategys() {
		strategys.put(ChatType.PRIVATE, new PrivateChat());
		strategys.put(ChatType.WORLD, new WorldChat());
		strategys.put(ChatType.GUILD, new GuildChat());
		strategys.put(ChatType.TEAM, new TeamChat());
		strategys.put(ChatType.NEAR, new NearChat());
	}
	
	public IChatStrategy getChatStrategy(ChatType chatType) {
		return strategys.get(chatType);
	}

}
