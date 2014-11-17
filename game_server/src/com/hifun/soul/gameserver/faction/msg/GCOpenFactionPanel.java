package com.hifun.soul.gameserver.faction.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开阵营面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenFactionPanel extends GCMessage{
	

	public GCOpenFactionPanel (){
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
		return MessageType.GC_OPEN_FACTION_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_FACTION_PANEL";
	}
}