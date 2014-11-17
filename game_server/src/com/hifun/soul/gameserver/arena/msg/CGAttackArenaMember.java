package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 攻击竞技场成员
 * 
 * @author SevenSoul
 */
@Component
public class CGAttackArenaMember extends CGMessage{
	
	/** 被攻打玩家的角色id */
	private long humanGuid;
	
	public CGAttackArenaMember (){
	}
	
	public CGAttackArenaMember (
			long humanGuid ){
			this.humanGuid = humanGuid;
	}
	
	@Override
	protected boolean readImpl() {
		humanGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ATTACK_ARENA_MEMBER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ATTACK_ARENA_MEMBER";
	}

	public long getHumanGuid(){
		return humanGuid;
	}
		
	public void setHumanGuid(long humanGuid){
		this.humanGuid = humanGuid;
	}

	@Override
	public void execute() {
	}
}