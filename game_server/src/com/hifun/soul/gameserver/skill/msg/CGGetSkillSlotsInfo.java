package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求技能栏的状态
 * 
 * @author SevenSoul
 */
@Component
public class CGGetSkillSlotsInfo extends CGMessage{
	
	
	public CGGetSkillSlotsInfo (){
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
		return MessageType.CG_GET_SKILL_SLOTS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_SKILL_SLOTS_INFO";
	}

	@Override
	public void execute() {
	}
}