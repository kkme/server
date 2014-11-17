package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开战俘营面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenPrisonPanel extends GCMessage{
	
	/** 身份类型 */
	private int identityType;

	public GCOpenPrisonPanel (){
	}
	
	public GCOpenPrisonPanel (
			int identityType ){
			this.identityType = identityType;
	}

	@Override
	protected boolean readImpl() {
		identityType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(identityType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_PRISON_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_PRISON_PANEL";
	}

	public int getIdentityType(){
		return identityType;
	}
		
	public void setIdentityType(int identityType){
		this.identityType = identityType;
	}
}