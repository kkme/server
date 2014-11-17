package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求卸下技能
 * 
 * @author SevenSoul
 */
@Component
public class CGUnequipSkill extends CGMessage{
	
	/** 技能ID */
	private int skillId;
	
	public CGUnequipSkill (){
	}
	
	public CGUnequipSkill (
			int skillId ){
			this.skillId = skillId;
	}
	
	@Override
	protected boolean readImpl() {
		skillId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UNEQUIP_SKILL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UNEQUIP_SKILL";
	}

	public int getSkillId(){
		return skillId;
	}
		
	public void setSkillId(int skillId){
		this.skillId = skillId;
	}

	@Override
	public void execute() {
	}
}