package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 身份变更通知客户端更新面板
 *
 * @author SevenSoul
 */
@Component
public class GCPrisonIdentityChanged extends GCMessage{
	
	/** 身份类型 */
	private int identityType;

	public GCPrisonIdentityChanged (){
	}
	
	public GCPrisonIdentityChanged (
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
		return MessageType.GC_PRISON_IDENTITY_CHANGED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PRISON_IDENTITY_CHANGED";
	}

	public int getIdentityType(){
		return identityType;
	}
		
	public void setIdentityType(int identityType){
		this.identityType = identityType;
	}
}