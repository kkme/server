package com.hifun.soul.gameserver.vip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示VIP面板信息
 * 
 * @author SevenSoul
 */
@Component
public class CGVipShowPannel extends CGMessage{
	
	
	public CGVipShowPannel (){
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
		return MessageType.CG_VIP_SHOW_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VIP_SHOW_PANNEL";
	}

	@Override
	public void execute() {
	}
}