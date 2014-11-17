package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 主人与奴隶互动
 * 
 * @author SevenSoul
 */
@Component
public class CGMasterInteract extends CGMessage{
	
	/** 互动的奴隶角色ID */
	private long slaveHumanId;
	/** 互动类型 */
	private int interactedType;
	
	public CGMasterInteract (){
	}
	
	public CGMasterInteract (
			long slaveHumanId,
			int interactedType ){
			this.slaveHumanId = slaveHumanId;
			this.interactedType = interactedType;
	}
	
	@Override
	protected boolean readImpl() {
		slaveHumanId = readLong();
		interactedType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(slaveHumanId);
		writeInteger(interactedType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MASTER_INTERACT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MASTER_INTERACT";
	}

	public long getSlaveHumanId(){
		return slaveHumanId;
	}
		
	public void setSlaveHumanId(long slaveHumanId){
		this.slaveHumanId = slaveHumanId;
	}

	public int getInteractedType(){
		return interactedType;
	}
		
	public void setInteractedType(int interactedType){
		this.interactedType = interactedType;
	}

	@Override
	public void execute() {
	}
}