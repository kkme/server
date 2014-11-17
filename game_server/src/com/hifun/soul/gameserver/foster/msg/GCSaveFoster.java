package com.hifun.soul.gameserver.foster.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 保存当前的数值
 *
 * @author SevenSoul
 */
@Component
public class GCSaveFoster extends GCMessage{
	
	/** 培养属性 */
	private com.hifun.soul.gameserver.foster.FosterAttribute[] attributes;

	public GCSaveFoster (){
	}
	
	public GCSaveFoster (
			com.hifun.soul.gameserver.foster.FosterAttribute[] attributes ){
			this.attributes = attributes;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		attributes = new com.hifun.soul.gameserver.foster.FosterAttribute[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.foster.FosterAttribute objattributes = new com.hifun.soul.gameserver.foster.FosterAttribute();
			attributes[i] = objattributes;
					objattributes.setId(readInteger());
							objattributes.setName(readString());
							objattributes.setMax(readInteger());
							objattributes.setCurrent(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(attributes.length);
	for(int i=0; i<attributes.length; i++){
	com.hifun.soul.gameserver.foster.FosterAttribute objattributes = attributes[i];
				writeInteger(objattributes.getId());
				writeString(objattributes.getName());
				writeInteger(objattributes.getMax());
				writeInteger(objattributes.getCurrent());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SAVE_FOSTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SAVE_FOSTER";
	}

	public com.hifun.soul.gameserver.foster.FosterAttribute[] getAttributes(){
		return attributes;
	}

	public void setAttributes(com.hifun.soul.gameserver.foster.FosterAttribute[] attributes){
		this.attributes = attributes;
	}	
}