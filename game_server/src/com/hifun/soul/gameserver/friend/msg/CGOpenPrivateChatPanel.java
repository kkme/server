package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开私聊面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenPrivateChatPanel extends CGMessage{
	
	/** 角色id */
	private long roleId;
	
	public CGOpenPrivateChatPanel (){
	}
	
	public CGOpenPrivateChatPanel (
			long roleId ){
			this.roleId = roleId;
	}
	
	@Override
	protected boolean readImpl() {
		roleId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(roleId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_PRIVATE_CHAT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_PRIVATE_CHAT_PANEL";
	}

	public long getRoleId(){
		return roleId;
	}
		
	public void setRoleId(long roleId){
		this.roleId = roleId;
	}

	@Override
	public void execute() {
	}
}