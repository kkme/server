package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应退出房间
 *
 * @author SevenSoul
 */
@Component
public class GCQuitBloodTempleRoom extends GCMessage{
	

	public GCQuitBloodTempleRoom (){
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
		return MessageType.GC_QUIT_BLOOD_TEMPLE_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUIT_BLOOD_TEMPLE_ROOM";
	}
}