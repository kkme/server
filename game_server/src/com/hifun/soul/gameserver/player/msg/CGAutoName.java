package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 自动生成角色名字请求
 * 
 * @author SevenSoul
 */
@Component
public class CGAutoName extends CGMessage{
	
	/** 角色职业  */
	private int occupation;
	
	public CGAutoName (){
	}
	
	public CGAutoName (
			int occupation ){
			this.occupation = occupation;
	}
	
	@Override
	protected boolean readImpl() {
		occupation = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(occupation);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_AUTO_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_AUTO_NAME";
	}

	public int getOccupation(){
		return occupation;
	}
		
	public void setOccupation(int occupation){
		this.occupation = occupation;
	}

	@Override
	public void execute() {
	}
}