package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示仇人列表
 * 
 * @author SevenSoul
 */
@Component
public class CGShowAbattoirEnemy extends CGMessage{
	
	
	public CGShowAbattoirEnemy (){
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
		return MessageType.CG_SHOW_ABATTOIR_ENEMY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_ABATTOIR_ENEMY";
	}

	@Override
	public void execute() {
	}
}