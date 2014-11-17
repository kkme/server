package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 删除好友
 *
 * @author SevenSoul
 */
@Component
public class GCRemoveFriend extends GCMessage{
	
	/** 好友id */
	private long roleId;

	public GCRemoveFriend (){
	}
	
	public GCRemoveFriend (
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
		return MessageType.GC_REMOVE_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_FRIEND";
	}

	public long getRoleId(){
		return roleId;
	}
		
	public void setRoleId(long roleId){
		this.roleId = roleId;
	}
}