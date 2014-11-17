package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 申请解救
 * 
 * @author SevenSoul
 */
@Component
public class CGApplyRescue extends CGMessage{
	
	/** 要解救的角色ID */
	private long rescueHumanId;
	
	public CGApplyRescue (){
	}
	
	public CGApplyRescue (
			long rescueHumanId ){
			this.rescueHumanId = rescueHumanId;
	}
	
	@Override
	protected boolean readImpl() {
		rescueHumanId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(rescueHumanId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_APPLY_RESCUE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_APPLY_RESCUE";
	}

	public long getRescueHumanId(){
		return rescueHumanId;
	}
		
	public void setRescueHumanId(long rescueHumanId){
		this.rescueHumanId = rescueHumanId;
	}

	@Override
	public void execute() {
	}
}