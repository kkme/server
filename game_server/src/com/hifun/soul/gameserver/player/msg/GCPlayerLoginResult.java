package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 用户登录结果
 *
 * @author SevenSoul
 */
@Component
public class GCPlayerLoginResult extends GCMessage{
	
	/** 登录结果码  */
	private int resultCode;

	public GCPlayerLoginResult (){
	}
	
	public GCPlayerLoginResult (
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
		return MessageType.GC_PLAYER_LOGIN_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PLAYER_LOGIN_RESULT";
	}

	public int getResultCode(){
		return resultCode;
	}
		
	public void setResultCode(int resultCode){
		this.resultCode = resultCode;
	}
}