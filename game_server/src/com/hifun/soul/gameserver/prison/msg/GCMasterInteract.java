package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应主人与奴隶互动
 *
 * @author SevenSoul
 */
@Component
public class GCMasterInteract extends GCMessage{
	
	/** 剩余互动次数 */
	private int remainInteractNum;

	public GCMasterInteract (){
	}
	
	public GCMasterInteract (
			int remainInteractNum ){
			this.remainInteractNum = remainInteractNum;
	}

	@Override
	protected boolean readImpl() {
		remainInteractNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainInteractNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MASTER_INTERACT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MASTER_INTERACT";
	}

	public int getRemainInteractNum(){
		return remainInteractNum;
	}
		
	public void setRemainInteractNum(int remainInteractNum){
		this.remainInteractNum = remainInteractNum;
	}
}