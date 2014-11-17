package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 开始冥想
 *
 * @author SevenSoul
 */
@Component
public class GCStartMeditation extends GCMessage{
	
	/** 冥想最大收益 */
	private int totalPoint;

	public GCStartMeditation (){
	}
	
	public GCStartMeditation (
			int totalPoint ){
			this.totalPoint = totalPoint;
	}

	@Override
	protected boolean readImpl() {
		totalPoint = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalPoint);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_START_MEDITATION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_MEDITATION";
	}

	public int getTotalPoint(){
		return totalPoint;
	}
		
	public void setTotalPoint(int totalPoint){
		this.totalPoint = totalPoint;
	}
}