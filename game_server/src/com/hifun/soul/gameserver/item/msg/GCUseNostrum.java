package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应使用秘药
 *
 * @author SevenSoul
 */
@Component
public class GCUseNostrum extends GCMessage{
	

	public GCUseNostrum (){
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
		return MessageType.GC_USE_NOSTRUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_USE_NOSTRUM";
	}
}