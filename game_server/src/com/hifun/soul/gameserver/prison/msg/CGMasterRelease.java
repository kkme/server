package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 主人释放奴隶
 * 
 * @author SevenSoul
 */
@Component
public class CGMasterRelease extends CGMessage{
	
	/** 释放的奴隶角色ID */
	private long slaveHumanId;
	
	public CGMasterRelease (){
	}
	
	public CGMasterRelease (
			long slaveHumanId ){
			this.slaveHumanId = slaveHumanId;
	}
	
	@Override
	protected boolean readImpl() {
		slaveHumanId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(slaveHumanId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MASTER_RELEASE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MASTER_RELEASE";
	}

	public long getSlaveHumanId(){
		return slaveHumanId;
	}
		
	public void setSlaveHumanId(long slaveHumanId){
		this.slaveHumanId = slaveHumanId;
	}

	@Override
	public void execute() {
	}
}