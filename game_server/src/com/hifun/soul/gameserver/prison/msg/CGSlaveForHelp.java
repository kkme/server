package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 奴隶求救
 * 
 * @author SevenSoul
 */
@Component
public class CGSlaveForHelp extends CGMessage{
	
	/** 要求救的角色ID */
	private long helperHumanId;
	
	public CGSlaveForHelp (){
	}
	
	public CGSlaveForHelp (
			long helperHumanId ){
			this.helperHumanId = helperHumanId;
	}
	
	@Override
	protected boolean readImpl() {
		helperHumanId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(helperHumanId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLAVE_FOR_HELP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLAVE_FOR_HELP";
	}

	public long getHelperHumanId(){
		return helperHumanId;
	}
		
	public void setHelperHumanId(long helperHumanId){
		this.helperHumanId = helperHumanId;
	}

	@Override
	public void execute() {
	}
}