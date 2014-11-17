package com.hifun.soul.gameserver.cd.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * cd加速，返回需要的花费
 * 
 * @author SevenSoul
 */
@Component
public class CGCdSpeedUp extends CGMessage{
	
	/** cd类型 */
	private int cdType;
	
	public CGCdSpeedUp (){
	}
	
	public CGCdSpeedUp (
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
		return MessageType.CG_CD_SPEED_UP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CD_SPEED_UP";
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