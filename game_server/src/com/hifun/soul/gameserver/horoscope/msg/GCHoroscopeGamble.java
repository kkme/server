package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 星运占星
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeGamble extends GCMessage{
	
	/** 占星师状态 */
	private com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos;
	/** 包中星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo horoscopeInfo;
	/** 免费占星次数 */
	private int freeTimes;

	public GCHoroscopeGamble (){
	}
	
	public GCHoroscopeGamble (
			com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos,
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo horoscopeInfo,
			int freeTimes ){
			this.stargazerInfos = stargazerInfos;
			this.horoscopeInfo = horoscopeInfo;
			this.freeTimes = freeTimes;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		stargazerInfos = new com.hifun.soul.gameserver.horoscope.StargazerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.StargazerInfo objstargazerInfos = new com.hifun.soul.gameserver.horoscope.StargazerInfo();
			stargazerInfos[i] = objstargazerInfos;
					objstargazerInfos.setStargazerId(readInteger());
							objstargazerInfos.setName(readString());
							objstargazerInfos.setDesc(readString());
							objstargazerInfos.setIcon(readInteger());
							objstargazerInfos.setCostCurrencyType(readShort());
							objstargazerInfos.setCostCurrencyNum(readInteger());
							objstargazerInfos.setOpen(readBoolean());
				}
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
				freeTimes = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(stargazerInfos.length);
	for(int i=0; i<stargazerInfos.length; i++){
	com.hifun.soul.gameserver.horoscope.StargazerInfo objstargazerInfos = stargazerInfos[i];
				writeInteger(objstargazerInfos.getStargazerId());
				writeString(objstargazerInfos.getName());
				writeString(objstargazerInfos.getDesc());
				writeInteger(objstargazerInfos.getIcon());
				writeShort(objstargazerInfos.getCostCurrencyType());
				writeInteger(objstargazerInfos.getCostCurrencyNum());
				writeBoolean(objstargazerInfos.getOpen());
	}
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
		writeInteger(freeTimes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HOROSCOPE_GAMBLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_GAMBLE";
	}

	public com.hifun.soul.gameserver.horoscope.StargazerInfo[] getStargazerInfos(){
		return stargazerInfos;
	}

	public void setStargazerInfos(com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos){
		this.stargazerInfos = stargazerInfos;
	}	

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo getHoroscopeInfo(){
		return horoscopeInfo;
	}
		
	public void setHoroscopeInfo(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo horoscopeInfo){
		this.horoscopeInfo = horoscopeInfo;
	}

	public int getFreeTimes(){
		return freeTimes;
	}
		
	public void setFreeTimes(int freeTimes){
		this.freeTimes = freeTimes;
	}
}