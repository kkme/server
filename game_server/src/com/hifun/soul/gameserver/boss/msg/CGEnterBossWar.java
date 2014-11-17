package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求进入boss战
 * 
 * @author SevenSoul
 */
@Component
public class CGEnterBossWar extends CGMessage{
	
	
	public CGEnterBossWar (){
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
		return MessageType.CG_ENTER_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_BOSS_WAR";
	}

	@Override
	public void execute() {
	}
}