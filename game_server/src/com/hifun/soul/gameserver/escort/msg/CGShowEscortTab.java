package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示押运标签页 
 * 
 * @author SevenSoul
 */
@Component
public class CGShowEscortTab extends CGMessage{
	
	/** 押运ID */
	private long escortId;
	
	public CGShowEscortTab (){
	}
	
	public CGShowEscortTab (
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
		return MessageType.CG_SHOW_ESCORT_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_ESCORT_TAB";
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