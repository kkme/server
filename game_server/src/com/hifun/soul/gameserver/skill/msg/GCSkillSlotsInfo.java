package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回技能栏的状态
 *
 * @author SevenSoul
 */
@Component
public class GCSkillSlotsInfo extends GCMessage{
	
	/** 技能栏数据 */
	private com.hifun.soul.gameserver.skill.slot.SkillSlot[] slots;

	public GCSkillSlotsInfo (){
	}
	
	public GCSkillSlotsInfo (
			com.hifun.soul.gameserver.skill.slot.SkillSlot[] slots ){
			this.slots = slots;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slots = new com.hifun.soul.gameserver.skill.slot.SkillSlot[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.slot.SkillSlot objslots = new com.hifun.soul.gameserver.skill.slot.SkillSlot();
			slots[i] = objslots;
					objslots.setSlotIndex(readInteger());
							objslots.setState(readInteger());
							objslots.setNeedHumanLevel(readInteger());
							objslots.setCostCoin(readInteger());
							objslots.setCostCrystal(readInteger());
							objslots.setOpenTitle(readInteger());
							objslots.setOpenTitleName(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(slots.length);
	for(int i=0; i<slots.length; i++){
	com.hifun.soul.gameserver.skill.slot.SkillSlot objslots = slots[i];
				writeInteger(objslots.getSlotIndex());
				writeInteger(objslots.getState());
				writeInteger(objslots.getNeedHumanLevel());
				writeInteger(objslots.getCostCoin());
				writeInteger(objslots.getCostCrystal());
				writeInteger(objslots.getOpenTitle());
				writeString(objslots.getOpenTitleName());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SKILL_SLOTS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SKILL_SLOTS_INFO";
	}

	public com.hifun.soul.gameserver.skill.slot.SkillSlot[] getSlots(){
		return slots;
	}

	public void setSlots(com.hifun.soul.gameserver.skill.slot.SkillSlot[] slots){
		this.slots = slots;
	}	
}