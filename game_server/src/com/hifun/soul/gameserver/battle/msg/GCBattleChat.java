package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器广播战斗聊天信息 
 *
 * @author SevenSoul
 */
@Component
public class GCBattleChat extends GCMessage{
	
	/** 请求者GUID */
	private long senderGuid;
	/** 发送内容 */
	private String content;

	public GCBattleChat (){
	}
	
	public GCBattleChat (
			long senderGuid,
			String content ){
			this.senderGuid = senderGuid;
			this.content = content;
	}

	@Override
	protected boolean readImpl() {
		senderGuid = readLong();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(senderGuid);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_CHAT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_CHAT";
	}

	public long getSenderGuid(){
		return senderGuid;
	}
		
	public void setSenderGuid(long senderGuid){
		this.senderGuid = senderGuid;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}
}