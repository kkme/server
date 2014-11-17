package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 更新采矿面板消息
 * 
 * @author SevenSoul
 */
@Component
public class CGUpdateMinePannel extends CGMessage{
	
	
	public CGUpdateMinePannel (){
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
		return MessageType.CG_UPDATE_MINE_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPDATE_MINE_PANNEL";
	}

	@Override
	public void execute() {
	}
}