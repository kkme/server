package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 已装备星运的信息
 *
 * @author SevenSoul
 */
@Component
public class GCEquipHoroscopeInfos extends GCMessage{
	
	/** 装备位置上星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipBagHoroscopeInfos;

	public GCEquipHoroscopeInfos (){
	}
	
	public GCEquipHoroscopeInfos (
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipBagHoroscopeInfos ){
			this.equipBagHoroscopeInfos = equipBagHoroscopeInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		equipBagHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objequipBagHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo();
			equipBagHoroscopeInfos[i] = objequipBagHoroscopeInfos;
					objequipBagHoroscopeInfos.setHoroscopeBag(readInteger());
							objequipBagHoroscopeInfos.setIndex(readInteger());
							objequipBagHoroscopeInfos.setHoroscopeId(readInteger());
							objequipBagHoroscopeInfos.setName(readString());
							objequipBagHoroscopeInfos.setDesc(readString());
							objequipBagHoroscopeInfos.setColor(readInteger());
							objequipBagHoroscopeInfos.setLevel(readInteger());
							objequipBagHoroscopeInfos.setExperience(readInteger());
							objequipBagHoroscopeInfos.setMaxExperience(readInteger());
							objequipBagHoroscopeInfos.setKey(readInteger());
							objequipBagHoroscopeInfos.setValue(readInteger());
							objequipBagHoroscopeInfos.setIcon(readInteger());
							objequipBagHoroscopeInfos.setNextHoroscopeId(readInteger());
							objequipBagHoroscopeInfos.setPropertyAddType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(equipBagHoroscopeInfos.length);
	for(int i=0; i<equipBagHoroscopeInfos.length; i++){
	com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objequipBagHoroscopeInfos = equipBagHoroscopeInfos[i];
				writeInteger(objequipBagHoroscopeInfos.getHoroscopeBag());
				writeInteger(objequipBagHoroscopeInfos.getIndex());
				writeInteger(objequipBagHoroscopeInfos.getHoroscopeId());
				writeString(objequipBagHoroscopeInfos.getName());
				writeString(objequipBagHoroscopeInfos.getDesc());
				writeInteger(objequipBagHoroscopeInfos.getColor());
				writeInteger(objequipBagHoroscopeInfos.getLevel());
				writeInteger(objequipBagHoroscopeInfos.getExperience());
				writeInteger(objequipBagHoroscopeInfos.getMaxExperience());
				writeInteger(objequipBagHoroscopeInfos.getKey());
				writeInteger(objequipBagHoroscopeInfos.getValue());
				writeInteger(objequipBagHoroscopeInfos.getIcon());
				writeInteger(objequipBagHoroscopeInfos.getNextHoroscopeId());
				writeInteger(objequipBagHoroscopeInfos.getPropertyAddType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EQUIP_HOROSCOPE_INFOS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EQUIP_HOROSCOPE_INFOS";
	}

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] getEquipBagHoroscopeInfos(){
		return equipBagHoroscopeInfos;
	}

	public void setEquipBagHoroscopeInfos(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipBagHoroscopeInfos){
		this.equipBagHoroscopeInfos = equipBagHoroscopeInfos;
	}	
}