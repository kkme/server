package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应主人释放奴隶
 *
 * @author SevenSoul
 */
@Component
public class GCMasterRelease extends GCMessage{
	

	public GCMasterRelease (){
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
		return MessageType.GC_MASTER_RELEASE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MASTER_RELEASE";
	}
}