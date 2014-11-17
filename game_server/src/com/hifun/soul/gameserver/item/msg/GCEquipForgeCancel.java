package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 装备洗炼取消
 *
 * @author SevenSoul
 */
@Component
public class GCEquipForgeCancel extends GCMessage{
	

	public GCEquipForgeCancel (){
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
		return MessageType.GC_EQUIP_FORGE_CANCEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EQUIP_FORGE_CANCEL";
	}
}