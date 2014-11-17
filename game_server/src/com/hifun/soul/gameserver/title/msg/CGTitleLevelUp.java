package com.hifun.soul.gameserver.title.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求升级军衔
 * 
 * @author SevenSoul
 */
@Component
public class CGTitleLevelUp extends CGMessage{
	
	
	public CGTitleLevelUp (){
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
		return MessageType.CG_TITLE_LEVEL_UP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TITLE_LEVEL_UP";
	}

	@Override
	public void execute() {
	}
}