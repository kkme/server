package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 某玩家不在线，用于私聊的特殊显示
 *
 * @author SevenSoul
 */
@Component
public class GCFriendOffline extends GCMessage{
	
	/** 角色id */
	private long roleId;
	/** 角色名称 */
	private String roleName;

	public GCFriendOffline (){
	}
	
	public GCFriendOffline (
			long roleId,
			String roleName ){
			this.roleId = roleId;
			this.roleName = roleName;
	}

	@Override
	protected boolean readImpl() {
		roleId = readLong();
		roleName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(roleId);
		writeString(roleName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_OFFLINE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_OFFLINE";
	}

	public long getRoleId(){
		return roleId;
	}
		
	public void setRoleId(long roleId){
		this.roleId = roleId;
	}

	public String getRoleName(){
		return roleName;
	}
		
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
}