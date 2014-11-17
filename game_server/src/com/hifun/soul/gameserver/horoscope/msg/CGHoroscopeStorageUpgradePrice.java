package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 星运背包开格子价格询问
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeStorageUpgradePrice extends CGMessage{
	
	/** 新开启数量 */
	private int openSize;
	
	public CGHoroscopeStorageUpgradePrice (){
	}
	
	public CGHoroscopeStorageUpgradePrice (
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
		return MessageType.CG_HOROSCOPE_STORAGE_UPGRADE_PRICE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_STORAGE_UPGRADE_PRICE";
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