package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 重置所有技能
 * 
 * @author SevenSoul
 */
@Component
public class CGResetSkills extends CGMessage{
	
	
	public CGResetSkills (){
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
		return MessageType.CG_RESET_SKILLS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RESET_SKILLS";
	}

	@Override
	public void execute() {
	}
}