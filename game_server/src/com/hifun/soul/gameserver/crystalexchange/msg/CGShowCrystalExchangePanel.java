package com.hifun.soul.gameserver.crystalexchange.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开魔晶兑换面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowCrystalExchangePanel extends CGMessage{
	
	
	public CGShowCrystalExchangePanel (){
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
		return MessageType.CG_SHOW_CRYSTAL_EXCHANGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_CRYSTAL_EXCHANGE_PANEL";
	}

	@Override
	public void execute() {
	}
}