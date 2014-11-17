package com.hifun.soul.gameserver.magic.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开附魔面板响应
 *
 * @author SevenSoul
 */
@Component
public class GCOpenAttachMagicPanel extends GCMessage{
	
	/** 当前的技能系类型 */
	private int skillDevelopType;
	/** 转系的魔晶消耗 */
	private int changeTypeCrystalCost;
	/** 当前魔法属性集合 */
	private com.hifun.soul.gameserver.magic.model.MagicInfo[] currentMagicList;
	/** 技能系集合 */
	private com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] skillDevelopList;

	public GCOpenAttachMagicPanel (){
	}
	
	public GCOpenAttachMagicPanel (
			int skillDevelopType,
			int changeTypeCrystalCost,
			com.hifun.soul.gameserver.magic.model.MagicInfo[] currentMagicList,
			com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] skillDevelopList ){
			this.skillDevelopType = skillDevelopType;
			this.changeTypeCrystalCost = changeTypeCrystalCost;
			this.currentMagicList = currentMagicList;
			this.skillDevelopList = skillDevelopList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		skillDevelopType = readInteger();
		changeTypeCrystalCost = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		currentMagicList = new com.hifun.soul.gameserver.magic.model.MagicInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.magic.model.MagicInfo objcurrentMagicList = new com.hifun.soul.gameserver.magic.model.MagicInfo();
			currentMagicList[i] = objcurrentMagicList;
					objcurrentMagicList.setInitValue(readInteger());
							objcurrentMagicList.setMaxValue(readInteger());
							objcurrentMagicList.setAddValue(readInteger());
							objcurrentMagicList.setEnergyType(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		skillDevelopList = new com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillDevelopInfo objskillDevelopList = new com.hifun.soul.gameserver.skill.template.SkillDevelopInfo();
			skillDevelopList[i] = objskillDevelopList;
					objskillDevelopList.setSkillDevelopType(readInteger());
							objskillDevelopList.setIcon(readInteger());
							objskillDevelopList.setRecommendPointType(readInteger());
							objskillDevelopList.setMainCostMagic(readInteger());
							objskillDevelopList.setAssistCostMagic(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillDevelopType);
		writeInteger(changeTypeCrystalCost);
	writeShort(currentMagicList.length);
	for(int i=0; i<currentMagicList.length; i++){
	com.hifun.soul.gameserver.magic.model.MagicInfo objcurrentMagicList = currentMagicList[i];
				writeInteger(objcurrentMagicList.getInitValue());
				writeInteger(objcurrentMagicList.getMaxValue());
				writeInteger(objcurrentMagicList.getAddValue());
				writeInteger(objcurrentMagicList.getEnergyType());
	}
	writeShort(skillDevelopList.length);
	for(int i=0; i<skillDevelopList.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillDevelopInfo objskillDevelopList = skillDevelopList[i];
				writeInteger(objskillDevelopList.getSkillDevelopType());
				writeInteger(objskillDevelopList.getIcon());
				writeInteger(objskillDevelopList.getRecommendPointType());
				writeInteger(objskillDevelopList.getMainCostMagic());
				writeString(objskillDevelopList.getAssistCostMagic());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_ATTACH_MAGIC_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_ATTACH_MAGIC_PANEL";
	}

	public int getSkillDevelopType(){
		return skillDevelopType;
	}
		
	public void setSkillDevelopType(int skillDevelopType){
		this.skillDevelopType = skillDevelopType;
	}

	public int getChangeTypeCrystalCost(){
		return changeTypeCrystalCost;
	}
		
	public void setChangeTypeCrystalCost(int changeTypeCrystalCost){
		this.changeTypeCrystalCost = changeTypeCrystalCost;
	}

	public com.hifun.soul.gameserver.magic.model.MagicInfo[] getCurrentMagicList(){
		return currentMagicList;
	}

	public void setCurrentMagicList(com.hifun.soul.gameserver.magic.model.MagicInfo[] currentMagicList){
		this.currentMagicList = currentMagicList;
	}	

	public com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] getSkillDevelopList(){
		return skillDevelopList;
	}

	public void setSkillDevelopList(com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] skillDevelopList){
		this.skillDevelopList = skillDevelopList;
	}	
}