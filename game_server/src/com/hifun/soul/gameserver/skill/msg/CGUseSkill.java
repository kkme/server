package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端请求释放技能
 * 
 * @author SevenSoul
 */
@Component
public class CGUseSkill extends CGMessage{
	
	/** 技能ID */
	private int skillId;
	/** 选中的宝石的行 */
	private int assignRow;
	/** 选中的宝石的列 */
	private int assignCol;
	
	public CGUseSkill (){
	}
	
	public CGUseSkill (
			int skillId,
			int assignRow,
			int assignCol ){
			this.skillId = skillId;
			this.assignRow = assignRow;
			this.assignCol = assignCol;
	}
	
	@Override
	protected boolean readImpl() {
		skillId = readInteger();
		assignRow = readInteger();
		assignCol = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillId);
		writeInteger(assignRow);
		writeInteger(assignCol);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_USE_SKILL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_USE_SKILL";
	}

	public int getSkillId(){
		return skillId;
	}
		
	public void setSkillId(int skillId){
		this.skillId = skillId;
	}

	public int getAssignRow(){
		return assignRow;
	}
		
	public void setAssignRow(int assignRow){
		this.assignRow = assignRow;
	}

	public int getAssignCol(){
		return assignCol;
	}
		
	public void setAssignCol(int assignCol){
		this.assignCol = assignCol;
	}

	@Override
	public void execute() {
	}
}