package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回购买技能栏结果
 *
 * @author SevenSoul
 */
@Component
public class GCOpenSkillSlotResult extends GCMessage{
	
	/** 技能栏索引 */
	private int slotIndex;
	/** 开启结果是否成功 */
	private boolean result;

	public GCOpenSkillSlotResult (){
	}
	
	public GCOpenSkillSlotResult (
			int slotIndex,
			boolean result ){
			this.slotIndex = slotIndex;
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		slotIndex = readInteger();
		result = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotIndex);
		writeBoolean(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_SKILL_SLOT_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_SKILL_SLOT_RESULT";
	}

	public int getSlotIndex(){
		return slotIndex;
	}
		
	public void setSlotIndex(int slotIndex){
		this.slotIndex = slotIndex;
	}

	public boolean getResult(){
		return result;
	}
		
	public void setResult(boolean result){
		this.result = result;
	}
}