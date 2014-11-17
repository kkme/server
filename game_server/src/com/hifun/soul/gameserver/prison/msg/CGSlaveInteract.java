package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 奴隶与主人互动 
 * 
 * @author SevenSoul
 */
@Component
public class CGSlaveInteract extends CGMessage{
	
	/** 互动类型 */
	private int interactedType;
	
	public CGSlaveInteract (){
	}
	
	public CGSlaveInteract (
			int interactedType ){
			this.interactedType = interactedType;
	}
	
	@Override
	protected boolean readImpl() {
		interactedType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(interactedType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLAVE_INTERACT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLAVE_INTERACT";
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