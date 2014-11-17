package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求购买加倍次数
 * 
 * @author SevenSoul
 */
@Component
public class CGBuyMarsMultipleNum extends CGMessage{
	
	
	public CGBuyMarsMultipleNum (){
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
		return MessageType.CG_BUY_MARS_MULTIPLE_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BUY_MARS_MULTIPLE_NUM";
	}

	@Override
	public void execute() {
	}
}