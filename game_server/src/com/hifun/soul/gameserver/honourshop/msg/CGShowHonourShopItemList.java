package com.hifun.soul.gameserver.honourshop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示荣誉商店道具列表
 * 
 * @author SevenSoul
 */
@Component
public class CGShowHonourShopItemList extends CGMessage{
	
	
	public CGShowHonourShopItemList (){
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
		return MessageType.CG_SHOW_HONOUR_SHOP_ITEM_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_HONOUR_SHOP_ITEM_LIST";
	}

	@Override
	public void execute() {
	}
}