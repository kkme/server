package com.hifun.soul.gameserver.magic.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应转系
 *
 * @author SevenSoul
 */
@Component
public class GCChangeSkillDevelopType extends GCMessage{
	
	/** 技能系别 */
	private int skillDevelopType;

	public GCChangeSkillDevelopType (){
	}
	
	public GCChangeSkillDevelopType (
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
		return MessageType.GC_CHANGE_SKILL_DEVELOP_TYPE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHANGE_SKILL_DEVELOP_TYPE";
	}

	public int getSkillDevelopType(){
		return skillDevelopType;
	}
		
	public void setSkillDevelopType(int skillDevelopType){
		this.skillDevelopType = skillDevelopType;
	}
}