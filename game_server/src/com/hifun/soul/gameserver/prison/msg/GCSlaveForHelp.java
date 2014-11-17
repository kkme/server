package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应奴隶求救
 *
 * @author SevenSoul
 */
@Component
public class GCSlaveForHelp extends GCMessage{
	
	/** 剩余求救次数 */
	private int remainSosNum;

	public GCSlaveForHelp (){
	}
	
	public GCSlaveForHelp (
			int remainSosNum ){
			this.remainSosNum = remainSosNum;
	}

	@Override
	protected boolean readImpl() {
		remainSosNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainSosNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLAVE_FOR_HELP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLAVE_FOR_HELP";
	}

	public int getRemainSosNum(){
		return remainSosNum;
	}
		
	public void setRemainSosNum(int remainSosNum){
		this.remainSosNum = remainSosNum;
	}
}