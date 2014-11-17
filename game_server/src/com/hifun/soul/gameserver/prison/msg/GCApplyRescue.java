package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应申请解救
 *
 * @author SevenSoul
 */
@Component
public class GCApplyRescue extends GCMessage{
	

	public GCApplyRescue (){
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
		return MessageType.GC_APPLY_RESCUE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPLY_RESCUE";
	}
}