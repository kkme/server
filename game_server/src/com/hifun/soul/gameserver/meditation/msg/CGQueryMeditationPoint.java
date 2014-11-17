package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 查询结束当前冥想可获得多少科技点
 * 
 * @author SevenSoul
 */
@Component
public class CGQueryMeditationPoint extends CGMessage{
	
	
	public CGQueryMeditationPoint (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_QUERY_MEDITATION_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUERY_MEDITATION_POINT";
	}

	@Override
	public void execute() {
	}
}