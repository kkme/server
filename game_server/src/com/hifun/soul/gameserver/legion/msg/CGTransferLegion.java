package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求转让军团 
 * 
 * @author SevenSoul
 */
@Component
public class CGTransferLegion extends CGMessage{
	
	/** 转让角色ID */
	private long transferHumanGuid;
	
	public CGTransferLegion (){
	}
	
	public CGTransferLegion (
			long transferHumanGuid ){
			this.transferHumanGuid = transferHumanGuid;
	}
	
	@Override
	protected boolean readImpl() {
		transferHumanGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(transferHumanGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TRANSFER_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TRANSFER_LEGION";
	}

	public long getTransferHumanGuid(){
		return transferHumanGuid;
	}
		
	public void setTransferHumanGuid(long transferHumanGuid){
		this.transferHumanGuid = transferHumanGuid;
	}

	@Override
	public void execute() {
	}
}