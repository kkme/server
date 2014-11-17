package com.hifun.soul.gameserver.gift.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开天赋面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowGiftPanel extends CGMessage{
	
	
	public CGShowGiftPanel (){
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
		return MessageType.CG_SHOW_GIFT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_GIFT_PANEL";
	}

	@Override
	public void execute() {
	}
}