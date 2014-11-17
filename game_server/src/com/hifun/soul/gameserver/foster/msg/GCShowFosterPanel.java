package com.hifun.soul.gameserver.foster.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回培养面板所有数据
 *
 * @author SevenSoul
 */
@Component
public class GCShowFosterPanel extends GCMessage{
	
	/** 培养属性 */
	private com.hifun.soul.gameserver.foster.FosterAttribute[] attributes;
	/** 培养模式 */
	private com.hifun.soul.gameserver.foster.FosterMode[] fosterMode;

	public GCShowFosterPanel (){
	}
	
	public GCShowFosterPanel (
			com.hifun.soul.gameserver.foster.FosterAttribute[] attributes,
			com.hifun.soul.gameserver.foster.FosterMode[] fosterMode ){
			this.attributes = attributes;
			this.fosterMode = fosterMode;
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
		count = readShort();
		count = count < 0 ? 0 : count;
		fosterMode = new com.hifun.soul.gameserver.foster.FosterMode[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.foster.FosterMode objfosterMode = new com.hifun.soul.gameserver.foster.FosterMode();
			fosterMode[i] = objfosterMode;
					objfosterMode.setId(readInteger());
							objfosterMode.setName(readString());
							objfosterMode.setDesc(readString());
							objfosterMode.setType(readInteger());
							objfosterMode.setCostCurrencyType(readInteger());
							objfosterMode.setCostCurrencyNum(readInteger());
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
	writeShort(fosterMode.length);
	for(int i=0; i<fosterMode.length; i++){
	com.hifun.soul.gameserver.foster.FosterMode objfosterMode = fosterMode[i];
				writeInteger(objfosterMode.getId());
				writeString(objfosterMode.getName());
				writeString(objfosterMode.getDesc());
				writeInteger(objfosterMode.getType());
				writeInteger(objfosterMode.getCostCurrencyType());
				writeInteger(objfosterMode.getCostCurrencyNum());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_FOSTER_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_FOSTER_PANEL";
	}

	public com.hifun.soul.gameserver.foster.FosterAttribute[] getAttributes(){
		return attributes;
	}

	public void setAttributes(com.hifun.soul.gameserver.foster.FosterAttribute[] attributes){
		this.attributes = attributes;
	}	

	public com.hifun.soul.gameserver.foster.FosterMode[] getFosterMode(){
		return fosterMode;
	}

	public void setFosterMode(com.hifun.soul.gameserver.foster.FosterMode[] fosterMode){
		this.fosterMode = fosterMode;
	}	
}