package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应解散军团
 *
 * @author SevenSoul
 */
@Component
public class GCDissolveLegion extends GCMessage{
	

	public GCDissolveLegion (){
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
		return MessageType.GC_DISSOLVE_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DISSOLVE_LEGION";
	}
}