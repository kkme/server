package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知技能效果直接作用
 *
 * @author SevenSoul
 */
@Component
public class GCEffectDirect extends GCMessage{
	
	/** 攻击者ID */
	private long attackerId;
	/** 目标ID */
	private long targetId;
	/** 技能ID */
	private int skillId;
	/** 连击次数 */
	private int combo;
	/** 是否暴击 */
	private boolean crit;
	/** 是否被格挡 */
	private boolean parry;

	public GCEffectDirect (){
	}
	
	public GCEffectDirect (
			long attackerId,
			long targetId,
			int skillId,
			int combo,
			boolean crit,
			boolean parry ){
			this.attackerId = attackerId;
			this.targetId = targetId;
			this.skillId = skillId;
			this.combo = combo;
			this.crit = crit;
			this.parry = parry;
	}

	@Override
	protected boolean readImpl() {
		attackerId = readLong();
		targetId = readLong();
		skillId = readInteger();
		combo = readInteger();
		crit = readBoolean();
		parry = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(attackerId);
		writeLong(targetId);
		writeInteger(skillId);
		writeInteger(combo);
		writeBoolean(crit);
		writeBoolean(parry);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EFFECT_DIRECT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EFFECT_DIRECT";
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

	public int getSkillId(){
		return skillId;
	}
		
	public void setSkillId(int skillId){
		this.skillId = skillId;
	}

	public int getCombo(){
		return combo;
	}
		
	public void setCombo(int combo){
		this.combo = combo;
	}

	public boolean getCrit(){
		return crit;
	}
		
	public void setCrit(boolean crit){
		this.crit = crit;
	}

	public boolean getParry(){
		return parry;
	}
		
	public void setParry(boolean parry){
		this.parry = parry;
	}
}