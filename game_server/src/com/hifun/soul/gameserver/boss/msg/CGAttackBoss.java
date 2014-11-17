package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 攻击boss
 * 
 * @author SevenSoul
 */
@Component
public class CGAttackBoss extends CGMessage{
	
	
	public CGAttackBoss (){
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
		return MessageType.CG_ATTACK_BOSS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ATTACK_BOSS";
	}

	@Override
	public void execute() {
	}
}