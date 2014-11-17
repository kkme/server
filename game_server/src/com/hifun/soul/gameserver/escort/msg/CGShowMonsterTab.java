package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请展示怪物标签页
 * 
 * @author SevenSoul
 */
@Component
public class CGShowMonsterTab extends CGMessage{
	
	
	public CGShowMonsterTab (){
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
		return MessageType.CG_SHOW_MONSTER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_MONSTER_TAB";
	}

	@Override
	public void execute() {
	}
}