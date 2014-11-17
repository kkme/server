package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端获取技能栏tips
 * 
 * @author SevenSoul
 */
@Component
public class CGGetSkillSlotTip extends CGMessage{
	
	
	public CGGetSkillSlotTip (){
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
		return MessageType.CG_GET_SKILL_SLOT_TIP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_SKILL_SLOT_TIP";
	}

	@Override
	public void execute() {
	}
}