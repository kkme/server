package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开星运面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowHoroscopePanel extends GCMessage{
	
	/** 占星师状态 */
	private com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos;
	/** 包中星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos;
	/** 装备位置上星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipBagHoroscopeInfos;
	/** 仓库中星运列表 */
	private com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] storageBagHoroscopeInfos;
	/** 角色信息 */
	private com.hifun.soul.common.model.human.CharacterInfo characterInfo;
	/** 星运装备位开启状态 */
	private com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo[] horoscopeSoulInfos;
	/** 免费占星次数 */
	private int freeTimes;
	/** 仓库格子开启的个数 */
	private int openSize;

	public GCShowHoroscopePanel (){
	}
	
	public GCShowHoroscopePanel (
			com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos,
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] mainBagHoroscopeInfos,
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipBagHoroscopeInfos,
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] storageBagHoroscopeInfos,
			com.hifun.soul.common.model.human.CharacterInfo characterInfo,
			com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo[] horoscopeSoulInfos,
			int freeTimes,
			int openSize ){
			this.stargazerInfos = stargazerInfos;
			this.mainBagHoroscopeInfos = mainBagHoroscopeInfos;
			this.equipBagHoroscopeInfos = equipBagHoroscopeInfos;
			this.storageBagHoroscopeInfos = storageBagHoroscopeInfos;
			this.characterInfo = characterInfo;
			this.horoscopeSoulInfos = horoscopeSoulInfos;
			this.freeTimes = freeTimes;
			this.openSize = openSize;
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
		count = readShort();
		count = count < 0 ? 0 : count;
		storageBagHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objstorageBagHoroscopeInfos = new com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo();
			storageBagHoroscopeInfos[i] = objstorageBagHoroscopeInfos;
					objstorageBagHoroscopeInfos.setHoroscopeBag(readInteger());
							objstorageBagHoroscopeInfos.setIndex(readInteger());
							objstorageBagHoroscopeInfos.setHoroscopeId(readInteger());
							objstorageBagHoroscopeInfos.setName(readString());
							objstorageBagHoroscopeInfos.setDesc(readString());
							objstorageBagHoroscopeInfos.setColor(readInteger());
							objstorageBagHoroscopeInfos.setLevel(readInteger());
							objstorageBagHoroscopeInfos.setExperience(readInteger());
							objstorageBagHoroscopeInfos.setMaxExperience(readInteger());
							objstorageBagHoroscopeInfos.setKey(readInteger());
							objstorageBagHoroscopeInfos.setValue(readInteger());
							objstorageBagHoroscopeInfos.setIcon(readInteger());
							objstorageBagHoroscopeInfos.setNextHoroscopeId(readInteger());
							objstorageBagHoroscopeInfos.setPropertyAddType(readInteger());
				}
		characterInfo = new com.hifun.soul.common.model.human.CharacterInfo();
						characterInfo.setGuid(readLong());
						characterInfo.setName(readString());
						characterInfo.setLevel(readInteger());
						characterInfo.setOccupation(readInteger());
						characterInfo.setLegionId(readLong());
						characterInfo.setLegionName(readString());
						characterInfo.setTitleName(readString());
				count = readShort();
		count = count < 0 ? 0 : count;
		horoscopeSoulInfos = new com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo objhoroscopeSoulInfos = new com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo();
			horoscopeSoulInfos[i] = objhoroscopeSoulInfos;
					objhoroscopeSoulInfos.setHoroscopeSoulType(readInteger());
							objhoroscopeSoulInfos.setDesc(readString());
							objhoroscopeSoulInfos.setOpen(readBoolean());
							objhoroscopeSoulInfos.setLevel(readInteger());
				}
		freeTimes = readInteger();
		openSize = readInteger();
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
	writeShort(storageBagHoroscopeInfos.length);
	for(int i=0; i<storageBagHoroscopeInfos.length; i++){
	com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo objstorageBagHoroscopeInfos = storageBagHoroscopeInfos[i];
				writeInteger(objstorageBagHoroscopeInfos.getHoroscopeBag());
				writeInteger(objstorageBagHoroscopeInfos.getIndex());
				writeInteger(objstorageBagHoroscopeInfos.getHoroscopeId());
				writeString(objstorageBagHoroscopeInfos.getName());
				writeString(objstorageBagHoroscopeInfos.getDesc());
				writeInteger(objstorageBagHoroscopeInfos.getColor());
				writeInteger(objstorageBagHoroscopeInfos.getLevel());
				writeInteger(objstorageBagHoroscopeInfos.getExperience());
				writeInteger(objstorageBagHoroscopeInfos.getMaxExperience());
				writeInteger(objstorageBagHoroscopeInfos.getKey());
				writeInteger(objstorageBagHoroscopeInfos.getValue());
				writeInteger(objstorageBagHoroscopeInfos.getIcon());
				writeInteger(objstorageBagHoroscopeInfos.getNextHoroscopeId());
				writeInteger(objstorageBagHoroscopeInfos.getPropertyAddType());
	}
		writeLong(characterInfo.getGuid());
		writeString(characterInfo.getName());
		writeInteger(characterInfo.getLevel());
		writeInteger(characterInfo.getOccupation());
		writeLong(characterInfo.getLegionId());
		writeString(characterInfo.getLegionName());
		writeString(characterInfo.getTitleName());
	writeShort(horoscopeSoulInfos.length);
	for(int i=0; i<horoscopeSoulInfos.length; i++){
	com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo objhoroscopeSoulInfos = horoscopeSoulInfos[i];
				writeInteger(objhoroscopeSoulInfos.getHoroscopeSoulType());
				writeString(objhoroscopeSoulInfos.getDesc());
				writeBoolean(objhoroscopeSoulInfos.getOpen());
				writeInteger(objhoroscopeSoulInfos.getLevel());
	}
		writeInteger(freeTimes);
		writeInteger(openSize);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_HOROSCOPE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_HOROSCOPE_PANEL";
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

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] getEquipBagHoroscopeInfos(){
		return equipBagHoroscopeInfos;
	}

	public void setEquipBagHoroscopeInfos(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] equipBagHoroscopeInfos){
		this.equipBagHoroscopeInfos = equipBagHoroscopeInfos;
	}	

	public com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] getStorageBagHoroscopeInfos(){
		return storageBagHoroscopeInfos;
	}

	public void setStorageBagHoroscopeInfos(com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo[] storageBagHoroscopeInfos){
		this.storageBagHoroscopeInfos = storageBagHoroscopeInfos;
	}	

	public com.hifun.soul.common.model.human.CharacterInfo getCharacterInfo(){
		return characterInfo;
	}
		
	public void setCharacterInfo(com.hifun.soul.common.model.human.CharacterInfo characterInfo){
		this.characterInfo = characterInfo;
	}

	public com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo[] getHoroscopeSoulInfos(){
		return horoscopeSoulInfos;
	}

	public void setHoroscopeSoulInfos(com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo[] horoscopeSoulInfos){
		this.horoscopeSoulInfos = horoscopeSoulInfos;
	}	

	public int getFreeTimes(){
		return freeTimes;
	}
		
	public void setFreeTimes(int freeTimes){
		this.freeTimes = freeTimes;
	}

	public int getOpenSize(){
		return openSize;
	}
		
	public void setOpenSize(int openSize){
		this.openSize = openSize;
	}
}