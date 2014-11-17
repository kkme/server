package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 好友申请列表里面某个记录删除(同意或者取消时发)
 *
 * @author SevenSoul
 */
@Component
public class GCRemoveApply extends GCMessage{
	
	/** 角色id */
	private long fromRoleId;

	public GCRemoveApply (){
	}
	
	public GCRemoveApply (
			long fromRoleId ){
			this.fromRoleId = fromRoleId;
	}

	@Override
	protected boolean readImpl() {
		fromRoleId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(fromRoleId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REMOVE_APPLY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_APPLY";
	}

	public long getFromRoleId(){
		return fromRoleId;
	}
		
	public void setFromRoleId(long fromRoleId){
		this.fromRoleId = fromRoleId;
	}
}