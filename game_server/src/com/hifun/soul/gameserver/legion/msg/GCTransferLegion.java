package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应转让军团
 *
 * @author SevenSoul
 */
@Component
public class GCTransferLegion extends GCMessage{
	

	public GCTransferLegion (){
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
		return MessageType.GC_TRANSFER_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TRANSFER_LEGION";
	}
}