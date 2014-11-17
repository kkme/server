package com.hifun.soul.gameserver.magic.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求转系
 * 
 * @author SevenSoul
 */
@Component
public class CGChangeSkillDevelopType extends CGMessage{
	
	/** 要转的系 */
	private int skillDevelopType;
	
	public CGChangeSkillDevelopType (){
	}
	
	public CGChangeSkillDevelopType (
			int skillDevelopType ){
			this.skillDevelopType = skillDevelopType;
	}
	
	@Override
	protected boolean readImpl() {
		skillDevelopType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillDevelopType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CHANGE_SKILL_DEVELOP_TYPE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHANGE_SKILL_DEVELOP_TYPE";
	}

	public int getSkillDevelopType(){
		return skillDevelopType;
	}
		
	public void setSkillDevelopType(int skillDevelopType){
		this.skillDevelopType = skillDevelopType;
	}

	@Override
	public void execute() {
	}
}