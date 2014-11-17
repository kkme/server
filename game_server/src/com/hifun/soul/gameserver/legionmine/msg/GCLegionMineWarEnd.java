package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 活动结束通知客户端
 *
 * @author SevenSoul
 */
@Component
public class GCLegionMineWarEnd extends GCMessage{
	

	public GCLegionMineWarEnd (){
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
		return MessageType.GC_LEGION_MINE_WAR_END;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEGION_MINE_WAR_END";
	}
}