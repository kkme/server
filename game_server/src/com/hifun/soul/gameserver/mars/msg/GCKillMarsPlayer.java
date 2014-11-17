package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应击杀战神之巅玩家
 *
 * @author SevenSoul
 */
@Component
public class GCKillMarsPlayer extends GCMessage{
	

	public GCKillMarsPlayer (){
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
		return MessageType.GC_KILL_MARS_PLAYER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_KILL_MARS_PLAYER";
	}
}