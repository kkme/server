package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求购买技能栏
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenSkillSlot extends CGMessage{
	
	/** 技能栏索引 */
	private int slotIndex;
	
	public CGOpenSkillSlot (){
	}
	
	public CGOpenSkillSlot (
			int slotIndex ){
			this.slotIndex = slotIndex;
	}
	
	@Override
	protected boolean readImpl() {
		slotIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_SKILL_SLOT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_SKILL_SLOT";
	}

	public int getSlotIndex(){
		return slotIndex;
	}
		
	public void setSlotIndex(int slotIndex){
		this.slotIndex = slotIndex;
	}

	@Override
	public void execute() {
	}
}