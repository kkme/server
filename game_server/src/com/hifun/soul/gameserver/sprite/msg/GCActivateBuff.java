package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 激活指定的buff 
 *
 * @author SevenSoul
 */
@Component
public class GCActivateBuff extends GCMessage{
	
	/** 激活的buffId */
	private int buffId;

	public GCActivateBuff (){
	}
	
	public GCActivateBuff (
			int buffId ){
			this.buffId = buffId;
	}

	@Override
	protected boolean readImpl() {
		buffId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buffId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ACTIVATE_BUFF;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ACTIVATE_BUFF";
	}

	public int getBuffId(){
		return buffId;
	}
		
	public void setBuffId(int buffId){
		this.buffId = buffId;
	}
}