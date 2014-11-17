package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求退出房间
 * 
 * @author SevenSoul
 */
@Component
public class CGQuitBloodTempleRoom extends CGMessage{
	
	
	public CGQuitBloodTempleRoom (){
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
		return MessageType.CG_QUIT_BLOOD_TEMPLE_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUIT_BLOOD_TEMPLE_ROOM";
	}

	@Override
	public void execute() {
	}
}