package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开军团矿战面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenLegionMineWarPanel extends CGMessage{
	
	
	public CGOpenLegionMineWarPanel (){
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
		return MessageType.CG_OPEN_LEGION_MINE_WAR_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_LEGION_MINE_WAR_PANEL";
	}

	@Override
	public void execute() {
	}
}