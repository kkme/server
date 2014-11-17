package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应申请抓捕
 *
 * @author SevenSoul
 */
@Component
public class GCApplyArrest extends GCMessage{
	

	public GCApplyArrest (){
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
		return MessageType.GC_APPLY_ARREST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPLY_ARREST";
	}
}