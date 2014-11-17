package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 装备强化完成
 *
 * @author SevenSoul
 */
@Component
public class GCEquipUpgrade extends GCMessage{
	

	public GCEquipUpgrade (){
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
		return MessageType.GC_EQUIP_UPGRADE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EQUIP_UPGRADE";
	}
}