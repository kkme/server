package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端开始行动计时
 *
 * @author SevenSoul
 */
@Component
public class GCStartActionTimer extends GCMessage{
	
	/** 倒计时时间 */
	private int timeout;

	public GCStartActionTimer (){
	}
	
	public GCStartActionTimer (
			int timeout ){
			this.timeout = timeout;
	}

	@Override
	protected boolean readImpl() {
		timeout = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(timeout);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_START_ACTION_TIMER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_ACTION_TIMER";
	}

	public int getTimeout(){
		return timeout;
	}
		
	public void setTimeout(int timeout){
		this.timeout = timeout;
	}
}