package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开神秘商店面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowSpecialShopPanel extends CGMessage{
	
	
	public CGShowSpecialShopPanel (){
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
		return MessageType.CG_SHOW_SPECIAL_SHOP_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_SPECIAL_SHOP_PANEL";
	}

	@Override
	public void execute() {
	}
}