package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 展示夺俘之敌标签页
 * 
 * @author SevenSoul
 */
@Component
public class CGShowEnemyTab extends CGMessage{
	
	
	public CGShowEnemyTab (){
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
		return MessageType.CG_SHOW_ENEMY_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_ENEMY_TAB";
	}

	@Override
	public void execute() {
	}
}