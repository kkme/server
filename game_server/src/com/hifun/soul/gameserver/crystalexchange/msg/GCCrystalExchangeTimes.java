package com.hifun.soul.gameserver.crystalexchange.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 登录时候发送今天剩余的兑换次数
 *
 * @author SevenSoul
 */
@Component
public class GCCrystalExchangeTimes extends GCMessage{
	
	/** 当天剩余次数 */
	private int leftTimes;

	public GCCrystalExchangeTimes (){
	}
	
	public GCCrystalExchangeTimes (
			int leftTimes ){
			this.leftTimes = leftTimes;
	}

	@Override
	protected boolean readImpl() {
		leftTimes = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(leftTimes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CRYSTAL_EXCHANGE_TIMES;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CRYSTAL_EXCHANGE_TIMES";
	}

	public int getLeftTimes(){
		return leftTimes;
	}
		
	public void setLeftTimes(int leftTimes){
		this.leftTimes = leftTimes;
	}
}