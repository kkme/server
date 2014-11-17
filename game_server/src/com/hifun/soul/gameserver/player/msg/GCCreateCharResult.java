package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 玩家创建角色结果
 *
 * @author SevenSoul
 */
@Component
public class GCCreateCharResult extends GCMessage{
	
	/** 创建角色结果码  */
	private int resultCode;

	public GCCreateCharResult (){
	}
	
	public GCCreateCharResult (
			int resultCode ){
			this.resultCode = resultCode;
	}

	@Override
	protected boolean readImpl() {
		resultCode = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(resultCode);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CREATE_CHAR_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CREATE_CHAR_RESULT";
	}

	public int getResultCode(){
		return resultCode;
	}
		
	public void setResultCode(int resultCode){
		this.resultCode = resultCode;
	}
}