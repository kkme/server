package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应删除军团成员
 *
 * @author SevenSoul
 */
@Component
public class GCRemoveLegionMember extends GCMessage{
	

	public GCRemoveLegionMember (){
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
		return MessageType.GC_REMOVE_LEGION_MEMBER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_LEGION_MEMBER";
	}
}