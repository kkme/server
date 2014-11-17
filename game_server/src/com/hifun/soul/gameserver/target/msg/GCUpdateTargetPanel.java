package com.hifun.soul.gameserver.target.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新个人目标面板
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateTargetPanel extends GCMessage{
	

	public GCUpdateTargetPanel (){
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
		return MessageType.GC_UPDATE_TARGET_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_TARGET_PANEL";
	}
}