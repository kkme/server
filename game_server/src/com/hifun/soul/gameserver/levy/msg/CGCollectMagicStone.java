package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 收集魔法石
 * 
 * @author SevenSoul
 */
@Component
public class CGCollectMagicStone extends CGMessage{
	
	
	public CGCollectMagicStone (){
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
		return MessageType.CG_COLLECT_MAGIC_STONE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_COLLECT_MAGIC_STONE";
	}

	@Override
	public void execute() {
	}
}