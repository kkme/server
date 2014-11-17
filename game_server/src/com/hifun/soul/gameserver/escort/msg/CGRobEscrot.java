package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 拦截押运
 * 
 * @author SevenSoul
 */
@Component
public class CGRobEscrot extends CGMessage{
	
	/** 押运ID */
	private long escortId;
	
	public CGRobEscrot (){
	}
	
	public CGRobEscrot (
			long escortId ){
			this.escortId = escortId;
	}
	
	@Override
	protected boolean readImpl() {
		escortId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(escortId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ROB_ESCROT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROB_ESCROT";
	}

	public long getEscortId(){
		return escortId;
	}
		
	public void setEscortId(long escortId){
		this.escortId = escortId;
	}

	@Override
	public void execute() {
	}
}