package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应退出房间
 *
 * @author SevenSoul
 */
@Component
public class GCQuitAbattoirRoom extends GCMessage{
	

	public GCQuitAbattoirRoom (){
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
		return MessageType.GC_QUIT_ABATTOIR_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUIT_ABATTOIR_ROOM";
	}
}