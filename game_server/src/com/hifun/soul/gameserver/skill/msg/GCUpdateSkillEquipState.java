package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务端通知更新技能的装备状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateSkillEquipState extends GCMessage{
	
	/** 技能ID */
	private int skillId;
	/** 是否装备上 */
	private boolean carried;
	/** 装备上的栏位索引 */
	private int slotIndex;

	public GCUpdateSkillEquipState (){
	}
	
	public GCUpdateSkillEquipState (
			int skillId,
			boolean carried,
			int slotIndex ){
			this.skillId = skillId;
			this.carried = carried;
			this.slotIndex = slotIndex;
	}

	@Override
	protected boolean readImpl() {
		skillId = readInteger();
		carried = readBoolean();
		slotIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillId);
		writeBoolean(carried);
		writeInteger(slotIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_SKILL_EQUIP_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_SKILL_EQUIP_STATE";
	}

	public int getSkillId(){
		return skillId;
	}
		
	public void setSkillId(int skillId){
		this.skillId = skillId;
	}

	public boolean getCarried(){
		return carried;
	}
		
	public void setCarried(boolean carried){
		this.carried = carried;
	}

	public int getSlotIndex(){
		return slotIndex;
	}
		
	public void setSlotIndex(int slotIndex){
		this.slotIndex = slotIndex;
	}
}