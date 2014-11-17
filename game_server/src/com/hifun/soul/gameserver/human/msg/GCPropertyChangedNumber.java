package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 用于发送数值类型的属性改变消息
 *
 * @author SevenSoul
 */
@Component
public class GCPropertyChangedNumber extends GCMessage{
	
	/** 角色UUID */
	private long roleUUID;
	/** 所有变化的属性 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Long>[] properties;

	public GCPropertyChangedNumber (){
	}
	
	public GCPropertyChangedNumber (
			long roleUUID,
			com.hifun.soul.core.util.KeyValuePair<Integer,Long>[] properties ){
			this.roleUUID = roleUUID;
			this.properties = properties;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		roleUUID = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		properties = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Long> obj = new com.hifun.soul.core.util.KeyValuePair<Integer,Long>();
			obj.setKey(readInteger());
			obj.setValue(readLong());
			properties[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(roleUUID);
		writeShort(properties.length);
		for(int i=0; i<properties.length; i++){
			writeInteger(properties[i].getKey());
			writeLong(properties[i].getValue());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PROPERTY_CHANGED_NUMBER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PROPERTY_CHANGED_NUMBER";
	}

	public long getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(long roleUUID){
		this.roleUUID = roleUUID;
	}

	public com.hifun.soul.core.util.KeyValuePair<Integer,Long>[] getProperties(){
		return properties;
	}

	public void setProperties(com.hifun.soul.core.util.KeyValuePair<Integer,Long>[] properties){
		this.properties = properties;
	}	
}