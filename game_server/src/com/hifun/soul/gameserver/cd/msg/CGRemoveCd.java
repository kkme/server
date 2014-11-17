package com.hifun.soul.gameserver.cd.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 直接消除CD
 * 
 * @author SevenSoul
 */
@Component
public class CGRemoveCd extends CGMessage{
	
	/** cd类型 */
	private int cdType;
	
	public CGRemoveCd (){
	}
	
	public CGRemoveCd (
			int cdType ){
			this.cdType = cdType;
	}
	
	@Override
	protected boolean readImpl() {
		cdType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cdType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REMOVE_CD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REMOVE_CD";
	}

	public int getCdType(){
		return cdType;
	}
		
	public void setCdType(int cdType){
		this.cdType = cdType;
	}

	@Override
	public void execute() {
	}
}