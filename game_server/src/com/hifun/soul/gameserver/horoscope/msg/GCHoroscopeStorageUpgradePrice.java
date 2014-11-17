package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 星运背包开格子价格询问
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeStorageUpgradePrice extends GCMessage{
	
	/** 货币类型 */
	private short currencyType;
	/** 货币数量 */
	private int currencyNum;
	/** 新开启数量 */
	private int openSize;

	public GCHoroscopeStorageUpgradePrice (){
	}
	
	public GCHoroscopeStorageUpgradePrice (
			short currencyType,
			int currencyNum,
			int openSize ){
			this.currencyType = currencyType;
			this.currencyNum = currencyNum;
			this.openSize = openSize;
	}

	@Override
	protected boolean readImpl() {
		currencyType = readShort();
		currencyNum = readInteger();
		openSize = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(currencyType);
		writeInteger(currencyNum);
		writeInteger(openSize);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HOROSCOPE_STORAGE_UPGRADE_PRICE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_STORAGE_UPGRADE_PRICE";
	}

	public short getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(short currencyType){
		this.currencyType = currencyType;
	}

	public int getCurrencyNum(){
		return currencyNum;
	}
		
	public void setCurrencyNum(int currencyNum){
		this.currencyNum = currencyNum;
	}

	public int getOpenSize(){
		return openSize;
	}
		
	public void setOpenSize(int openSize){
		this.openSize = openSize;
	}
}