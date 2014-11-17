package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 收获所有矿坑
 * 
 * @author SevenSoul
 */
@Component
public class CGAllMineHarvest extends CGMessage{
	
	
	public CGAllMineHarvest (){
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
		return MessageType.CG_ALL_MINE_HARVEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ALL_MINE_HARVEST";
	}

	@Override
	public void execute() {
	}
}