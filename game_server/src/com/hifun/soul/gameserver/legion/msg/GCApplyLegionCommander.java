package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应晋升团长
 *
 * @author SevenSoul
 */
@Component
public class GCApplyLegionCommander extends GCMessage{
	

	public GCApplyLegionCommander (){
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
		return MessageType.GC_APPLY_LEGION_COMMANDER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPLY_LEGION_COMMANDER";
	}
}