package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求战斗聊天信息
 * 
 * @author SevenSoul
 */
@Component
public class CGBattleChat extends CGMessage{
	
	/** 请求者GUID */
	private long senderGuid;
	/** 发送内容 */
	private String content;
	
	public CGBattleChat (){
	}
	
	public CGBattleChat (
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
		return MessageType.CG_BATTLE_CHAT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BATTLE_CHAT";
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

	@Override
	public void execute() {
	}
}