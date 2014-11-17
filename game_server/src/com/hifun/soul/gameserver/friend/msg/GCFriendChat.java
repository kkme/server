package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 接收好友留言
 *
 * @author SevenSoul
 */
@Component
public class GCFriendChat extends GCMessage{
	
	/** 角色id */
	private long fromRoleId;
	/** 角色名称 */
	private String fromRoleName;
	/** 角色等级 */
	private int fromRoleLevel;
	/** 角色职业 */
	private int fromRoleOccupation;
	/** 聊天时间间隔 */
	private String chatTime;
	/** 留言内容 */
	private String content;

	public GCFriendChat (){
	}
	
	public GCFriendChat (
			long fromRoleId,
			String fromRoleName,
			int fromRoleLevel,
			int fromRoleOccupation,
			String chatTime,
			String content ){
			this.fromRoleId = fromRoleId;
			this.fromRoleName = fromRoleName;
			this.fromRoleLevel = fromRoleLevel;
			this.fromRoleOccupation = fromRoleOccupation;
			this.chatTime = chatTime;
			this.content = content;
	}

	@Override
	protected boolean readImpl() {
		fromRoleId = readLong();
		fromRoleName = readString();
		fromRoleLevel = readInteger();
		fromRoleOccupation = readInteger();
		chatTime = readString();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(fromRoleId);
		writeString(fromRoleName);
		writeInteger(fromRoleLevel);
		writeInteger(fromRoleOccupation);
		writeString(chatTime);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_CHAT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_CHAT";
	}

	public long getFromRoleId(){
		return fromRoleId;
	}
		
	public void setFromRoleId(long fromRoleId){
		this.fromRoleId = fromRoleId;
	}

	public String getFromRoleName(){
		return fromRoleName;
	}
		
	public void setFromRoleName(String fromRoleName){
		this.fromRoleName = fromRoleName;
	}

	public int getFromRoleLevel(){
		return fromRoleLevel;
	}
		
	public void setFromRoleLevel(int fromRoleLevel){
		this.fromRoleLevel = fromRoleLevel;
	}

	public int getFromRoleOccupation(){
		return fromRoleOccupation;
	}
		
	public void setFromRoleOccupation(int fromRoleOccupation){
		this.fromRoleOccupation = fromRoleOccupation;
	}

	public String getChatTime(){
		return chatTime;
	}
		
	public void setChatTime(String chatTime){
		this.chatTime = chatTime;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}
}