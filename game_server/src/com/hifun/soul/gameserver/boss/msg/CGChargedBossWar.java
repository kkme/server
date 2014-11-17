package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 充能
 * 
 * @author SevenSoul
 */
@Component
public class CGChargedBossWar extends CGMessage{
	
	
	public CGChargedBossWar (){
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
		return MessageType.CG_CHARGED_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHARGED_BOSS_WAR";
	}

	@Override
	public void execute() {
	}
}