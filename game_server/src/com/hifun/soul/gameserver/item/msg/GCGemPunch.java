package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打孔成功
 *
 * @author SevenSoul
 */
@Component
public class GCGemPunch extends GCMessage{
	

	public GCGemPunch (){
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
		return MessageType.GC_GEM_PUNCH;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GEM_PUNCH";
	}
}