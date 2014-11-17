package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 开启新的精英副本类型
 *
 * @author SevenSoul
 */
@Component
public class GCNewEliteStageTypeOpen extends GCMessage{
	
	/** 新开启的副本类型(key:副本id;value:副本名称) */
	private com.hifun.soul.core.util.KeyValuePair<Integer,String>[] eliteStageType;

	public GCNewEliteStageTypeOpen (){
	}
	
	public GCNewEliteStageTypeOpen (
			com.hifun.soul.core.util.KeyValuePair<Integer,String>[] eliteStageType ){
			this.eliteStageType = eliteStageType;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		eliteStageType = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,String> objeliteStageType = new com.hifun.soul.core.util.KeyValuePair<Integer,String>();
			eliteStageType[i] = objeliteStageType;
					objeliteStageType.setKey(readInteger());
							objeliteStageType.setValue(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(eliteStageType.length);
	for(int i=0; i<eliteStageType.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,String> objeliteStageType = eliteStageType[i];
				writeInteger(objeliteStageType.getKey());
				writeString(objeliteStageType.getValue());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_NEW_ELITE_STAGE_TYPE_OPEN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NEW_ELITE_STAGE_TYPE_OPEN";
	}

	public com.hifun.soul.core.util.KeyValuePair<Integer,String>[] getEliteStageType(){
		return eliteStageType;
	}

	public void setEliteStageType(com.hifun.soul.core.util.KeyValuePair<Integer,String>[] eliteStageType){
		this.eliteStageType = eliteStageType;
	}	
}