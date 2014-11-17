package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 星运背包开格子
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeStorageUpgrade extends GCMessage{
	
	/** 仓库格子开启的个数 */
	private int openSize;

	public GCHoroscopeStorageUpgrade (){
	}
	
	public GCHoroscopeStorageUpgrade (
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
		return MessageType.GC_HOROSCOPE_STORAGE_UPGRADE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_STORAGE_UPGRADE";
	}

	public int getOpenSize(){
		return openSize;
	}
		
	public void setOpenSize(int openSize){
		this.openSize = openSize;
	}
}