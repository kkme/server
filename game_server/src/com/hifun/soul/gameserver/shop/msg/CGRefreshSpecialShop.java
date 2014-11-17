package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 刷新神秘商店物品
 * 
 * @author SevenSoul
 */
@Component
public class CGRefreshSpecialShop extends CGMessage{
	
	
	public CGRefreshSpecialShop (){
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
		return MessageType.CG_REFRESH_SPECIAL_SHOP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REFRESH_SPECIAL_SHOP";
	}

	@Override
	public void execute() {
	}
}