package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 一键星运合成
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeCompoundAuto extends GCMessage{
	
	/** 包中星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos;

	public GCHoroscopeCompoundAuto (){
	}
	
	public GCHoroscopeCompoundAuto (
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos ){
			this.mainBagHoroscopeInfos = mainBagHoroscopeInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		mainBagHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objmainBagHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo();
			mainBagHoroscopeInfos[i] = objmainBagHoroscopeInfos;
					objmainBagHoroscopeInfos.setHoroscopeBag(readInteger());
							objmainBagHoroscopeInfos.setIndex(readInteger());
							objmainBagHoroscopeInfos.setHoroscopeId(readInteger());
							objmainBagHoroscopeInfos.setName(readString());
							objmainBagHoroscopeInfos.setDesc(readString());
							objmainBagHoroscopeInfos.setColor(readInteger());
							objmainBagHoroscopeInfos.setLevel(readInteger());
							objmainBagHoroscopeInfos.setExperience(readInteger());
							objmainBagHoroscopeInfos.setMaxExperience(readInteger());
							objmainBagHoroscopeInfos.setKey(readInteger());
							objmainBagHoroscopeInfos.setValue(readInteger());
							objmainBagHoroscopeInfos.setIcon(readInteger());
							objmainBagHoroscopeInfos.setNextHoroscopeId(readInteger());
							objmainBagHoroscopeInfos.setPropertyAddType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(mainBagHoroscopeInfos.length);
	for(int i=0; i<mainBagHoroscopeInfos.length; i++){
	com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objmainBagHoroscopeInfos = mainBagHoroscopeInfos[i];
				writeInteger(objmainBagHoroscopeInfos.getHoroscopeBag());
				writeInteger(objmainBagHoroscopeInfos.getIndex());
				writeInteger(objmainBagHoroscopeInfos.getHoroscopeId());
				writeString(objmainBagHoroscopeInfos.getName());
				writeString(objmainBagHoroscopeInfos.getDesc());
				writeInteger(objmainBagHoroscopeInfos.getColor());
				writeInteger(objmainBagHoroscopeInfos.getLevel());
				writeInteger(objmainBagHoroscopeInfos.getExperience());
				writeInteger(objmainBagHoroscopeInfos.getMaxExperience());
				writeInteger(objmainBagHoroscopeInfos.getKey());
				writeInteger(objmainBagHoroscopeInfos.getValue());
				writeInteger(objmainBagHoroscopeInfos.getIcon());
				writeInteger(objmainBagHoroscopeInfos.getNextHoroscopeId());
				writeInteger(objmainBagHoroscopeInfos.getPropertyAddType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HOROSCOPE_COMPOUND_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_COMPOUND_AUTO";
	}

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] getMainBagHoroscopeInfos(){
		return mainBagHoroscopeInfos;
	}

	public void setMainBagHoroscopeInfos(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos){
		this.mainBagHoroscopeInfos = mainBagHoroscopeInfos;
	}	
}