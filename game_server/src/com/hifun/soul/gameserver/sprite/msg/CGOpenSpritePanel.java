package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开精灵面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenSpritePanel extends CGMessage{
	
	
	public CGOpenSpritePanel (){
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
		return MessageType.CG_OPEN_SPRITE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_SPRITE_PANEL";
	}

	@Override
	public void execute() {
	}
}