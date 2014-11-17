package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 判断装备上否可以升级
 * 
 * @author SevenSoul
 */
@Component
public class CGCheckEquipMake extends CGMessage{
	
	
	public CGCheckEquipMake (){
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
		return MessageType.CG_CHECK_EQUIP_MAKE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHECK_EQUIP_MAKE";
	}

	@Override
	public void execute() {
	}
}