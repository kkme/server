package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 装备星运
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeOn extends CGMessage{
	
	/** 装备星运所在的背包 */
	private int bagType;
	/** 装备星运所在背包中的位置 */
	private int index;
	
	public CGHoroscopeOn (){
	}
	
	public CGHoroscopeOn (
			int bagType,
			int index ){
			this.bagType = bagType;
			this.index = index;
	}
	
	@Override
	protected boolean readImpl() {
		bagType = readInteger();
		index = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagType);
		writeInteger(index);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HOROSCOPE_ON;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_ON";
	}

	public int getBagType(){
		return bagType;
	}
		
	public void setBagType(int bagType){
		this.bagType = bagType;
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