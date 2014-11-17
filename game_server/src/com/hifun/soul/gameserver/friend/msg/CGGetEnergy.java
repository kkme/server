package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取好友赠送的体力
 * 
 * @author SevenSoul
 */
@Component
public class CGGetEnergy extends CGMessage{
	
	/** 角色id */
	private long fromRoleId;
	
	public CGGetEnergy (){
	}
	
	public CGGetEnergy (
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
		return MessageType.CG_GET_ENERGY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_ENERGY";
	}

	public long getFromRoleId(){
		return fromRoleId;
	}
		
	public void setFromRoleId(long fromRoleId){
		this.fromRoleId = fromRoleId;
	}

	@Override
	public void execute() {
	}
}