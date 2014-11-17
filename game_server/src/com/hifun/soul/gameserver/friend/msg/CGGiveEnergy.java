package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 赠送好友体力
 * 
 * @author SevenSoul
 */
@Component
public class CGGiveEnergy extends CGMessage{
	
	/** 角色id */
	private long toRoleId;
	
	public CGGiveEnergy (){
	}
	
	public CGGiveEnergy (
			long toRoleId ){
			this.toRoleId = toRoleId;
	}
	
	@Override
	protected boolean readImpl() {
		toRoleId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(toRoleId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GIVE_ENERGY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GIVE_ENERGY";
	}

	public long getToRoleId(){
		return toRoleId;
	}
		
	public void setToRoleId(long toRoleId){
		this.toRoleId = toRoleId;
	}

	@Override
	public void execute() {
	}
}