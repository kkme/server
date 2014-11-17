package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 星运介绍
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeIntroduce extends GCMessage{
	
	/** 卸下星运 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] horoscopeInfo;

	public GCHoroscopeIntroduce (){
	}
	
	public GCHoroscopeIntroduce (
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] horoscopeInfo ){
			this.horoscopeInfo = horoscopeInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		horoscopeInfo = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objhoroscopeInfo = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo();
			horoscopeInfo[i] = objhoroscopeInfo;
					objhoroscopeInfo.setHoroscopeId(readInteger());
							objhoroscopeInfo.setName(readString());
							objhoroscopeInfo.setDesc(readString());
							objhoroscopeInfo.setColor(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(horoscopeInfo.length);
	for(int i=0; i<horoscopeInfo.length; i++){
	com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objhoroscopeInfo = horoscopeInfo[i];
				writeInteger(objhoroscopeInfo.getHoroscopeId());
				writeString(objhoroscopeInfo.getName());
				writeString(objhoroscopeInfo.getDesc());
				writeInteger(objhoroscopeInfo.getColor());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HOROSCOPE_INTRODUCE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_INTRODUCE";
	}

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] getHoroscopeInfo(){
		return horoscopeInfo;
	}

	public void setHoroscopeInfo(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] horoscopeInfo){
		this.horoscopeInfo = horoscopeInfo;
	}	
}