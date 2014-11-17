package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应拒绝加入军团
 *
 * @author SevenSoul
 */
@Component
public class GCRejectJoinLegion extends GCMessage{
	

	public GCRejectJoinLegion (){
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
		return MessageType.GC_REJECT_JOIN_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REJECT_JOIN_LEGION";
	}
}