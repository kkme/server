package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 查询结束当前冥想可获得多少科技点
 *
 * @author SevenSoul
 */
@Component
public class GCQueryMeditationPoint extends GCMessage{
	
	/** 获得科技点 */
	private int techPoint;

	public GCQueryMeditationPoint (){
	}
	
	public GCQueryMeditationPoint (
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
		return MessageType.GC_QUERY_MEDITATION_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUERY_MEDITATION_POINT";
	}

	public int getTechPoint(){
		return techPoint;
	}
		
	public void setTechPoint(int techPoint){
		this.techPoint = techPoint;
	}
}