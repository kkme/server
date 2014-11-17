package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 一键星运占星
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeGambleAuto extends GCMessage{
	
	/** 占星师状态 */
	private com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos;
	/** 包中星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos;

	public GCHoroscopeGambleAuto (){
	}
	
	public GCHoroscopeGambleAuto (
			com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos,
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos ){
			this.stargazerInfos = stargazerInfos;
			this.mainBagHoroscopeInfos = mainBagHoroscopeInfos;
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
		return MessageType.GC_HOROSCOPE_GAMBLE_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_GAMBLE_AUTO";
	}

	public com.hifun.soul.gameserver.horoscope.StargazerInfo[] getStargazerInfos(){
		return stargazerInfos;
	}

	public void setStargazerInfos(com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos){
		this.stargazerInfos = stargazerInfos;
	}	

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] getMainBagHoroscopeInfos(){
		return mainBagHoroscopeInfos;
	}

	public void setMainBagHoroscopeInfos(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos){
		this.mainBagHoroscopeInfos = mainBagHoroscopeInfos;
	}	
}