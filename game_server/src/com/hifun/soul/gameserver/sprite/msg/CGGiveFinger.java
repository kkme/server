package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求出拳
 * 
 * @author SevenSoul
 */
@Component
public class CGGiveFinger extends CGMessage{
	
	/** 出拳类型 */
	private int fingerType;
	
	public CGGiveFinger (){
	}
	
	public CGGiveFinger (
			int fingerType ){
			this.fingerType = fingerType;
	}
	
	@Override
	protected boolean readImpl() {
		fingerType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(fingerType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GIVE_FINGER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GIVE_FINGER";
	}

	public int getFingerType(){
		return fingerType;
	}
		
	public void setFingerType(int fingerType){
		this.fingerType = fingerType;
	}

	@Override
	public void execute() {
	}
}