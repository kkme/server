package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新星运
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeUpdateAndRemove extends GCMessage{
	
	/** 更新星运 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo horoscopeInfo;
	/** 移除星运的背包类型 */
	private int removeBagType;
	/** 移除星运的背包位置 */
	private int removeBagIndex;

	public GCHoroscopeUpdateAndRemove (){
	}
	
	public GCHoroscopeUpdateAndRemove (
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo horoscopeInfo,
			int removeBagType,
			int removeBagIndex ){
			this.horoscopeInfo = horoscopeInfo;
			this.removeBagType = removeBagType;
			this.removeBagIndex = removeBagIndex;
	}

	@Override
	protected boolean readImpl() {
		horoscopeInfo = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo();
						horoscopeInfo.setHoroscopeBag(readInteger());
						horoscopeInfo.setIndex(readInteger());
						horoscopeInfo.setHoroscopeId(readInteger());
						horoscopeInfo.setName(readString());
						horoscopeInfo.setDesc(readString());
						horoscopeInfo.setColor(readInteger());
						horoscopeInfo.setLevel(readInteger());
						horoscopeInfo.setExperience(readInteger());
						horoscopeInfo.setMaxExperience(readInteger());
						horoscopeInfo.setKey(readInteger());
						horoscopeInfo.setValue(readInteger());
						horoscopeInfo.setIcon(readInteger());
						horoscopeInfo.setNextHoroscopeId(readInteger());
						horoscopeInfo.setPropertyAddType(readInteger());
				removeBagType = readInteger();
		removeBagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(horoscopeInfo.getHoroscopeBag());
		writeInteger(horoscopeInfo.getIndex());
		writeInteger(horoscopeInfo.getHoroscopeId());
		writeString(horoscopeInfo.getName());
		writeString(horoscopeInfo.getDesc());
		writeInteger(horoscopeInfo.getColor());
		writeInteger(horoscopeInfo.getLevel());
		writeInteger(horoscopeInfo.getExperience());
		writeInteger(horoscopeInfo.getMaxExperience());
		writeInteger(horoscopeInfo.getKey());
		writeInteger(horoscopeInfo.getValue());
		writeInteger(horoscopeInfo.getIcon());
		writeInteger(horoscopeInfo.getNextHoroscopeId());
		writeInteger(horoscopeInfo.getPropertyAddType());
		writeInteger(removeBagType);
		writeInteger(removeBagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HOROSCOPE_UPDATE_AND_REMOVE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_UPDATE_AND_REMOVE";
	}

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo getHoroscopeInfo(){
		return horoscopeInfo;
	}
		
	public void setHoroscopeInfo(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo horoscopeInfo){
		this.horoscopeInfo = horoscopeInfo;
	}

	public int getRemoveBagType(){
		return removeBagType;
	}
		
	public void setRemoveBagType(int removeBagType){
		this.removeBagType = removeBagType;
	}

	public int getRemoveBagIndex(){
		return removeBagIndex;
	}
		
	public void setRemoveBagIndex(int removeBagIndex){
		this.removeBagIndex = removeBagIndex;
	}
}