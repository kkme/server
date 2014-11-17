package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求最大收获科技点
 * 
 * @author SevenSoul
 */
@Component
public class CGTotalMeditationPoint extends CGMessage{
	
	/** 冥想模式索引 */
	private int modeIndex;
	/** 时长模式索引 */
	private int timeIndex;
	
	public CGTotalMeditationPoint (){
	}
	
	public CGTotalMeditationPoint (
			int modeIndex,
			int timeIndex ){
			this.modeIndex = modeIndex;
			this.timeIndex = timeIndex;
	}
	
	@Override
	protected boolean readImpl() {
		modeIndex = readInteger();
		timeIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(modeIndex);
		writeInteger(timeIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TOTAL_MEDITATION_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TOTAL_MEDITATION_POINT";
	}

	public int getModeIndex(){
		return modeIndex;
	}
		
	public void setModeIndex(int modeIndex){
		this.modeIndex = modeIndex;
	}

	public int getTimeIndex(){
		return timeIndex;
	}
		
	public void setTimeIndex(int timeIndex){
		this.timeIndex = timeIndex;
	}

	@Override
	public void execute() {
	}
}