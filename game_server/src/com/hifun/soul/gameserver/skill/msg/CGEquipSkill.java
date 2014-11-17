package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求装备技能
 * 
 * @author SevenSoul
 */
@Component
public class CGEquipSkill extends CGMessage{
	
	/** 技能ID */
	private int skillId;
	
	public CGEquipSkill (){
	}
	
	public CGEquipSkill (
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
		return MessageType.CG_EQUIP_SKILL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EQUIP_SKILL";
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