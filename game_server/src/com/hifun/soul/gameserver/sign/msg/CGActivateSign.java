package com.hifun.soul.gameserver.sign.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 激活指定星座 
 * 
 * @author SevenSoul
 */
@Component
public class CGActivateSign extends CGMessage{
	
	/** 星座id */
	private int signId;
	
	public CGActivateSign (){
	}
	
	public CGActivateSign (
			int signId ){
			this.signId = signId;
	}
	
	@Override
	protected boolean readImpl() {
		signId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(signId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ACTIVATE_SIGN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ACTIVATE_SIGN";
	}

	public int getSignId(){
		return signId;
	}
		
	public void setSignId(int signId){
		this.signId = signId;
	}

	@Override
	public void execute() {
	}
}