package com.hifun.soul.gameserver.turntable.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 转盘抽奖
 * 
 * @author SevenSoul
 */
@Component
public class CGLottery extends CGMessage{
	
	
	public CGLottery (){
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
		return MessageType.CG_LOTTERY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LOTTERY";
	}

	@Override
	public void execute() {
	}
}