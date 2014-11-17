package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 攻击竞技场成员
 *
 * @author SevenSoul
 */
@Component
public class GCAttackArenaMember extends GCMessage{
	

	public GCAttackArenaMember (){
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
		return MessageType.GC_ATTACK_ARENA_MEMBER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ATTACK_ARENA_MEMBER";
	}
}