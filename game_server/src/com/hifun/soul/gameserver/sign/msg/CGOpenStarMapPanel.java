package com.hifun.soul.gameserver.sign.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开星图面板 
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenStarMapPanel extends CGMessage{
	
	
	public CGOpenStarMapPanel (){
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
		return MessageType.CG_OPEN_STAR_MAP_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_STAR_MAP_PANEL";
	}

	@Override
	public void execute() {
	}
}