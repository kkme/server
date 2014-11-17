package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求好友留言
 * 
 * @author SevenSoul
 */
@Component
public class CGFriendChat extends CGMessage{
	
	/** 角色id */
	private long destRoleId;
	/** 角色名称 */
	private String destRoleName;
	/** 留言内容 */
	private String content;
	
	public CGFriendChat (){
	}
	
	public CGFriendChat (
			long destRoleId,
			String destRoleName,
			String content ){
			this.destRoleId = destRoleId;
			this.destRoleName = destRoleName;
			this.content = content;
	}
	
	@Override
	protected boolean readImpl() {
		destRoleId = readLong();
		destRoleName = readString();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(destRoleId);
		writeString(destRoleName);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FRIEND_CHAT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FRIEND_CHAT";
	}

	public long getDestRoleId(){
		return destRoleId;
	}
		
	public void setDestRoleId(long destRoleId){
		this.destRoleId = destRoleId;
	}

	public String getDestRoleName(){
		return destRoleName;
	}
		
	public void setDestRoleName(String destRoleName){
		this.destRoleName = destRoleName;
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