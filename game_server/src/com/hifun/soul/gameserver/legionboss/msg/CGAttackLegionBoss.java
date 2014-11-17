package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 攻击boss
 * 
 * @author SevenSoul
 */
@Component
public class CGAttackLegionBoss extends CGMessage{
	
	
	public CGAttackLegionBoss (){
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
		return MessageType.CG_ATTACK_LEGION_BOSS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ATTACK_LEGION_BOSS";
	}

	@Override
	public void execute() {
	}
}