package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开技能面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowSkillPanel extends CGMessage{
	
	
	public CGShowSkillPanel (){
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
		return MessageType.CG_SHOW_SKILL_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_SKILL_PANEL";
	}

	@Override
	public void execute() {
	}
}