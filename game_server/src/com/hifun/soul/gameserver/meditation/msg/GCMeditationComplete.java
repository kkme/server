package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 冥想完成
 *
 * @author SevenSoul
 */
@Component
public class GCMeditationComplete extends GCMessage{
	
	/** 获得科技点 */
	private int techPoint;

	public GCMeditationComplete (){
	}
	
	public GCMeditationComplete (
			int techPoint ){
			this.techPoint = techPoint;
	}

	@Override
	protected boolean readImpl() {
		techPoint = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(techPoint);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MEDITATION_COMPLETE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MEDITATION_COMPLETE";
	}

	public int getTechPoint(){
		return techPoint;
	}
		
	public void setTechPoint(int techPoint){
		this.techPoint = techPoint;
	}
}