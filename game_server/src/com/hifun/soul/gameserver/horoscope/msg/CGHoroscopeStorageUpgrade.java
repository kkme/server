package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 星运背包开格子
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeStorageUpgrade extends CGMessage{
	
	/** 新开启数量 */
	private int openSize;
	
	public CGHoroscopeStorageUpgrade (){
	}
	
	public CGHoroscopeStorageUpgrade (
			int openSize ){
			this.openSize = openSize;
	}
	
	@Override
	protected boolean readImpl() {
		openSize = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(openSize);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HOROSCOPE_STORAGE_UPGRADE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_STORAGE_UPGRADE";
	}

	public int getOpenSize(){
		return openSize;
	}
		
	public void setOpenSize(int openSize){
		this.openSize = openSize;
	}

	@Override
	public void execute() {
	}
}