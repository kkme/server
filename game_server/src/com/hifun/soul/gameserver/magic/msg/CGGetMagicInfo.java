package com.hifun.soul.gameserver.magic.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求获取魔法信息
 * 
 * @author SevenSoul
 */
@Component
public class CGGetMagicInfo extends CGMessage{
	
	/** 当前的技能系类型 */
	private int skillDevelopType;
	
	public CGGetMagicInfo (){
	}
	
	public CGGetMagicInfo (
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
		return MessageType.CG_GET_MAGIC_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_MAGIC_INFO";
	}

	public int getSkillDevelopType(){
		return skillDevelopType;
	}
		
	public void setSkillDevelopType(int skillDevelopType){
		this.skillDevelopType = skillDevelopType;
	}

	@Override
	public void execute() {
	}
}