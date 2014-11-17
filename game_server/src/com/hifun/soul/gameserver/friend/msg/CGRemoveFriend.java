package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求删除好友
 * 
 * @author SevenSoul
 */
@Component
public class CGRemoveFriend extends CGMessage{
	
	/** 好友id */
	private long roleId;
	
	public CGRemoveFriend (){
	}
	
	public CGRemoveFriend (
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
		return MessageType.CG_REMOVE_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REMOVE_FRIEND";
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