package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 玩家选角色
 * 
 * @author SevenSoul
 */
@Component
public class CGSelectChar extends CGMessage{
	
	/** 角色索引  */
	private int charIndex;
	
	public CGSelectChar (){
	}
	
	public CGSelectChar (
			int charIndex ){
			this.charIndex = charIndex;
	}
	
	@Override
	protected boolean readImpl() {
		charIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(charIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SELECT_CHAR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SELECT_CHAR";
	}

	public int getCharIndex(){
		return charIndex;
	}
		
	public void setCharIndex(int charIndex){
		this.charIndex = charIndex;
	}

	@Override
	public void execute() {
	}
}