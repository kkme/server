package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求收获
 * 
 * @author SevenSoul
 */
@Component
public class CGLegionMineWarHarvest extends CGMessage{
	
	
	public CGLegionMineWarHarvest (){
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
		return MessageType.CG_LEGION_MINE_WAR_HARVEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEGION_MINE_WAR_HARVEST";
	}

	@Override
	public void execute() {
	}
}