package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 卸下星运
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeOff extends CGMessage{
	
	/** 卸下星运所在装备位上的位置 */
	private int index;
	
	public CGHoroscopeOff (){
	}
	
	public CGHoroscopeOff (
			int index ){
			this.index = index;
	}
	
	@Override
	protected boolean readImpl() {
		index = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(index);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HOROSCOPE_OFF;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_OFF";
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	@Override
	public void execute() {
	}
}