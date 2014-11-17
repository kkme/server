package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 通知客户端直接进征战场景
 *
 * @author SevenSoul
 */
@Component
public class GCDirectToAreaScene extends GCMessage{
	

	public GCDirectToAreaScene (){
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
		return MessageType.GC_DIRECT_TO_AREA_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DIRECT_TO_AREA_SCENE";
	}
}