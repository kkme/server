package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 推荐添加好友
 *
 * @author SevenSoul
 */
@Component
public class GCRecommendFriendAddInfo extends GCMessage{
	
	/** 角色id */
	private long roleId;
	/** 角色名称 */
	private String roleName;

	public GCRecommendFriendAddInfo (){
	}
	
	public GCRecommendFriendAddInfo (
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
		return MessageType.GC_RECOMMEND_FRIEND_ADD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECOMMEND_FRIEND_ADD_INFO";
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