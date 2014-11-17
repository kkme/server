package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开嗜血神殿面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenBloodTemplePanel extends CGMessage{
	
	
	public CGOpenBloodTemplePanel (){
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
		return MessageType.CG_OPEN_BLOOD_TEMPLE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_BLOOD_TEMPLE_PANEL";
	}

	@Override
	public void execute() {
	}
}