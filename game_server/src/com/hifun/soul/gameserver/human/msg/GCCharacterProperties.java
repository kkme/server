package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 登录时候下发角色全部属性信息
 *
 * @author SevenSoul
 */
@Component
public class GCCharacterProperties extends GCMessage{
	
	/** 角色全局标识 */
	private long guid;
	/** 所有变化的属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] properties;

	public GCCharacterProperties (){
	}
	
	public GCCharacterProperties (
			long guid,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] properties ){
			this.guid = guid;
			this.properties = properties;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		guid = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		properties = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objproperties = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			properties[i] = objproperties;
					objproperties.setKey(readInteger());
							objproperties.setValue(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(guid);
	writeShort(properties.length);
	for(int i=0; i<properties.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objproperties = properties[i];
				writeInteger(objproperties.getKey());
				writeInteger(objproperties.getValue());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARACTER_PROPERTIES;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARACTER_PROPERTIES";
	}

	public long getGuid(){
		return guid;
	}
		
	public void setGuid(long guid){
		this.guid = guid;
	}

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getProperties(){
		return properties;
	}

	public void setProperties(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] properties){
		this.properties = properties;
	}	
}