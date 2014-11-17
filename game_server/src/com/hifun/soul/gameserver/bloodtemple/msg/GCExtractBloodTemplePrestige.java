package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应提取威望
 *
 * @author SevenSoul
 */
@Component
public class GCExtractBloodTemplePrestige extends GCMessage{
	
	/** 当前威望 */
	private int currentPrestige;

	public GCExtractBloodTemplePrestige (){
	}
	
	public GCExtractBloodTemplePrestige (
			int currentPrestige ){
			this.currentPrestige = currentPrestige;
	}

	@Override
	protected boolean readImpl() {
		currentPrestige = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentPrestige);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EXTRACT_BLOOD_TEMPLE_PRESTIGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EXTRACT_BLOOD_TEMPLE_PRESTIGE";
	}

	public int getCurrentPrestige(){
		return currentPrestige;
	}
		
	public void setCurrentPrestige(int currentPrestige){
		this.currentPrestige = currentPrestige;
	}
}