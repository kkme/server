package com.hifun.soul.gameserver.magic.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回取魔法信息
 *
 * @author SevenSoul
 */
@Component
public class GCGetMagicInfo extends GCMessage{
	
	/** 当前的技能系类型 */
	private int skillDevelopType;
	/** 当前魔法属性集合 */
	private com.hifun.soul.gameserver.magic.model.MagicInfo[] currentMagicList;

	public GCGetMagicInfo (){
	}
	
	public GCGetMagicInfo (
			int skillDevelopType,
			com.hifun.soul.gameserver.magic.model.MagicInfo[] currentMagicList ){
			this.skillDevelopType = skillDevelopType;
			this.currentMagicList = currentMagicList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		skillDevelopType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		currentMagicList = new com.hifun.soul.gameserver.magic.model.MagicInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.magic.model.MagicInfo objcurrentMagicList = new com.hifun.soul.gameserver.magic.model.MagicInfo();
			currentMagicList[i] = objcurrentMagicList;
					objcurrentMagicList.setInitValue(readInteger());
							objcurrentMagicList.setMaxValue(readInteger());
							objcurrentMagicList.setAddValue(readInteger());
							objcurrentMagicList.setEnergyType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillDevelopType);
	writeShort(currentMagicList.length);
	for(int i=0; i<currentMagicList.length; i++){
	com.hifun.soul.gameserver.magic.model.MagicInfo objcurrentMagicList = currentMagicList[i];
				writeInteger(objcurrentMagicList.getInitValue());
				writeInteger(objcurrentMagicList.getMaxValue());
				writeInteger(objcurrentMagicList.getAddValue());
				writeInteger(objcurrentMagicList.getEnergyType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_MAGIC_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_MAGIC_INFO";
	}

	public int getSkillDevelopType(){
		return skillDevelopType;
	}
		
	public void setSkillDevelopType(int skillDevelopType){
		this.skillDevelopType = skillDevelopType;
	}

	public com.hifun.soul.gameserver.magic.model.MagicInfo[] getCurrentMagicList(){
		return currentMagicList;
	}

	public void setCurrentMagicList(com.hifun.soul.gameserver.magic.model.MagicInfo[] currentMagicList){
		this.currentMagicList = currentMagicList;
	}	
}