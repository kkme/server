package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应奴隶反抗主人
 *
 * @author SevenSoul
 */
@Component
public class GCSlaveRevolt extends GCMessage{
	

	public GCSlaveRevolt (){
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
		return MessageType.GC_SLAVE_REVOLT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLAVE_REVOLT";
	}
}