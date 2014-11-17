package com.hifun.soul.gameserver.chat.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 聊天
 *
 * @author SevenSoul
 */
@Component
public class GCChatMsg extends GCMessage{
	
	/** 聊天类型 */
	private int chatType;
	/** 聊天内容 */
	private String content;
	/** 目标账号id */
	private long destPassportId;
	/** 目标角色名称 */
	private String destRoleName;
	/** 账号id */
	private long fromPassportId;
	/** 角色名称  */
	private String fromRoleName;

	public GCChatMsg (){
	}
	
	public GCChatMsg (
			int chatType,
			String content,
			long destPassportId,
			String destRoleName,
			long fromPassportId,
			String fromRoleName ){
			this.chatType = chatType;
			this.content = content;
			this.destPassportId = destPassportId;
			this.destRoleName = destRoleName;
			this.fromPassportId = fromPassportId;
			this.fromRoleName = fromRoleName;
	}

	@Override
	protected boolean readImpl() {
		chatType = readInteger();
		content = readString();
		destPassportId = readLong();
		destRoleName = readString();
		fromPassportId = readLong();
		fromRoleName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(chatType);
		writeString(content);
		writeLong(destPassportId);
		writeString(destRoleName);
		writeLong(fromPassportId);
		writeString(fromRoleName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHAT_MSG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHAT_MSG";
	}

	public int getChatType(){
		return chatType;
	}
		
	public void setChatType(int chatType){
		this.chatType = chatType;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public long getDestPassportId(){
		return destPassportId;
	}
		
	public void setDestPassportId(long destPassportId){
		this.destPassportId = destPassportId;
	}

	public String getDestRoleName(){
		return destRoleName;
	}
		
	public void setDestRoleName(String destRoleName){
		this.destRoleName = destRoleName;
	}

	public long getFromPassportId(){
		return fromPassportId;
	}
		
	public void setFromPassportId(long fromPassportId){
		this.fromPassportId = fromPassportId;
	}

	public String getFromRoleName(){
		return fromRoleName;
	}
		
	public void setFromRoleName(String fromRoleName){
		this.fromRoleName = fromRoleName;
	}
}