package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求添加多个推荐好友
 * 
 * @author SevenSoul
 */
@Component
public class CGAddFriends extends CGMessage{
	
	/** 角色列表 */
	private long[] roleIds;
	
	public CGAddFriends (){
	}
	
	public CGAddFriends (
			long[] roleIds ){
			this.roleIds = roleIds;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
				roleIds = new long[count];
				for(int i=0; i<count; i++){
			roleIds[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(roleIds.length);
		for(int i=0; i<roleIds.length; i++){
			writeLong(roleIds[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ADD_FRIENDS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ADD_FRIENDS";
	}

	public long[] getRoleIds(){
		return roleIds;
	}

	public void setRoleIds(long[] roleIds){
		this.roleIds = roleIds;
	}	

	@Override
	public void execute() {
	}
}