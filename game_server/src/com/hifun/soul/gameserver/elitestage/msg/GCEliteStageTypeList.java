package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 精英副本类型列表
 *
 * @author SevenSoul
 */
@Component
public class GCEliteStageTypeList extends GCMessage{
	
	/** 精英副本类型信息 */
	private com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo[] eliteStageTypeList;

	public GCEliteStageTypeList (){
	}
	
	public GCEliteStageTypeList (
			com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo[] eliteStageTypeList ){
			this.eliteStageTypeList = eliteStageTypeList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		eliteStageTypeList = new com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo objeliteStageTypeList = new com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo();
			eliteStageTypeList[i] = objeliteStageTypeList;
					objeliteStageTypeList.setStageType(readInteger());
							objeliteStageTypeList.setTypeName(readString());
							objeliteStageTypeList.setOpenLevel(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(eliteStageTypeList.length);
	for(int i=0; i<eliteStageTypeList.length; i++){
	com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo objeliteStageTypeList = eliteStageTypeList[i];
				writeInteger(objeliteStageTypeList.getStageType());
				writeString(objeliteStageTypeList.getTypeName());
				writeInteger(objeliteStageTypeList.getOpenLevel());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ELITE_STAGE_TYPE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ELITE_STAGE_TYPE_LIST";
	}

	public com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo[] getEliteStageTypeList(){
		return eliteStageTypeList;
	}

	public void setEliteStageTypeList(com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo[] eliteStageTypeList){
		this.eliteStageTypeList = eliteStageTypeList;
	}	
}