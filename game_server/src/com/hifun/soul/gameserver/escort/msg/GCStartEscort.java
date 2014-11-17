package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应开始押运
 *
 * @author SevenSoul
 */
@Component
public class GCStartEscort extends GCMessage{
	

	public GCStartEscort (){
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
		return MessageType.GC_START_ESCORT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_ESCORT";
	}
}