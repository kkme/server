package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 查询开启冥想协助位价格
 * 
 * @author SevenSoul
 */
@Component
public class CGQueryOpenAssistPositionPrice extends CGMessage{
	
	
	public CGQueryOpenAssistPositionPrice (){
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
		return MessageType.CG_QUERY_OPEN_ASSIST_POSITION_PRICE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUERY_OPEN_ASSIST_POSITION_PRICE";
	}

	@Override
	public void execute() {
	}
}