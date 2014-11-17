package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开装备强化面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowEquipUpgradePanel extends GCMessage{
	

	public GCShowEquipUpgradePanel (){
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
		return MessageType.GC_SHOW_EQUIP_UPGRADE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_EQUIP_UPGRADE_PANEL";
	}
}