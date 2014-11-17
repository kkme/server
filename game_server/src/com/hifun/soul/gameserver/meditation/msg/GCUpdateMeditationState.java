package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新冥想状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMeditationState extends GCMessage{
	
	/** 冥想状态 */
	private int meditationState;

	public GCUpdateMeditationState (){
	}
	
	public GCUpdateMeditationState (
			int meditationState ){
			this.meditationState = meditationState;
	}

	@Override
	protected boolean readImpl() {
		meditationState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(meditationState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MEDITATION_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MEDITATION_STATE";
	}

	public int getMeditationState(){
		return meditationState;
	}
		
	public void setMeditationState(int meditationState){
		this.meditationState = meditationState;
	}
}