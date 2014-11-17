package com.hifun.soul.gameserver.refine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 背包满的提示
 *
 * @author SevenSoul
 */
@Component
public class GCRefineBagFull extends GCMessage{
	

	public GCRefineBagFull (){
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
		return MessageType.GC_REFINE_BAG_FULL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REFINE_BAG_FULL";
	}
}