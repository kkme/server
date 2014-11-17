package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 玩家请求角色列表
 * 
 * @author SevenSoul
 */
@Component
public class CGGetCharList extends CGMessage{
	
	
	public CGGetCharList (){
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
		return MessageType.CG_GET_CHAR_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_CHAR_LIST";
	}

	@Override
	public void execute() {
	}
}