package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务端通知客户端使用技能
 *
 * @author SevenSoul
 */
@Component
public class GCUseSkill extends GCMessage{
	
	/** 技能ID */
	private int skillId;
	/** 攻击者ID */
	private long attackerId;
	/** 目标ID */
	private long targetId;

	public GCUseSkill (){
	}
	
	public GCUseSkill (
			int skillId,
			long attackerId,
			long targetId ){
			this.skillId = skillId;
			this.attackerId = attackerId;
			this.targetId = targetId;
	}

	@Override
	protected boolean readImpl() {
		skillId = readInteger();
		attackerId = readLong();
		targetId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillId);
		writeLong(attackerId);
		writeLong(targetId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_USE_SKILL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_USE_SKILL";
	}

	public int getSkillId(){
		return skillId;
	}
		
	public void setSkillId(int skillId){
		this.skillId = skillId;
	}

	public long getAttackerId(){
		return attackerId;
	}
		
	public void setAttackerId(long attackerId){
		this.attackerId = attackerId;
	}

	public long getTargetId(){
		return targetId;
	}
		
	public void setTargetId(long targetId){
		this.targetId = targetId;
	}
}