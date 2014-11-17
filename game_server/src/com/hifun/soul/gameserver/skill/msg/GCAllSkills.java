package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器发送所有玩家的技能
 *
 * @author SevenSoul
 */
@Component
public class GCAllSkills extends GCMessage{
	
	/** 默认技能 */
	private com.hifun.soul.gameserver.skill.template.SkillInfo[] defaultSkills;
	/** 宝石技能 */
	private com.hifun.soul.gameserver.skill.template.SkillInfo[] gemSkills;
	/** 能量技能 */
	private com.hifun.soul.gameserver.skill.template.SkillInfo[] energySkills;
	/** 辅助技能 */
	private com.hifun.soul.gameserver.skill.template.SkillInfo[] assistSkills;
	/** 技能点 */
	private int skillPoints;
	/** 魔晶数量 */
	private int crystal;
	/** 技能发展类型信息 */
	private com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] skillDevelopInfos;

	public GCAllSkills (){
	}
	
	public GCAllSkills (
			com.hifun.soul.gameserver.skill.template.SkillInfo[] defaultSkills,
			com.hifun.soul.gameserver.skill.template.SkillInfo[] gemSkills,
			com.hifun.soul.gameserver.skill.template.SkillInfo[] energySkills,
			com.hifun.soul.gameserver.skill.template.SkillInfo[] assistSkills,
			int skillPoints,
			int crystal,
			com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] skillDevelopInfos ){
			this.defaultSkills = defaultSkills;
			this.gemSkills = gemSkills;
			this.energySkills = energySkills;
			this.assistSkills = assistSkills;
			this.skillPoints = skillPoints;
			this.crystal = crystal;
			this.skillDevelopInfos = skillDevelopInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		defaultSkills = new com.hifun.soul.gameserver.skill.template.SkillInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillInfo objdefaultSkills = new com.hifun.soul.gameserver.skill.template.SkillInfo();
			defaultSkills[i] = objdefaultSkills;
					objdefaultSkills.setSkillId(readInteger());
							objdefaultSkills.setSkillName(readString());
							objdefaultSkills.setSkillDesc(readString());
							objdefaultSkills.setSkillActionId(readInteger());
							objdefaultSkills.setFlyEffectId(readInteger());
							objdefaultSkills.setBeHitedEffectId(readInteger());
							objdefaultSkills.setEnemyBeHitedEffectId(readInteger());
							objdefaultSkills.setCooldownRound(readInteger());
							objdefaultSkills.setAttackType(readInteger());
							objdefaultSkills.setSkillType(readInteger());
							objdefaultSkills.setNeedSelectGem(readBoolean());
							objdefaultSkills.setRangeType(readInteger());
							objdefaultSkills.setUseRoundOver(readBoolean());
							objdefaultSkills.setRedCost(readInteger());
							objdefaultSkills.setYellowCost(readInteger());
							objdefaultSkills.setGreenCost(readInteger());
							objdefaultSkills.setBlueCost(readInteger());
							objdefaultSkills.setPurpleCost(readInteger());
							objdefaultSkills.setIsCarried(readBoolean());
							objdefaultSkills.setSkillSound(readInteger());
							objdefaultSkills.setSkillIcon(readInteger());
							objdefaultSkills.setSlotIndex(readInteger());
							objdefaultSkills.setSkillState(readInteger());
							objdefaultSkills.setSkillDevelopType(readInteger());
							objdefaultSkills.setNeedLevel(readInteger());
							objdefaultSkills.setNeedSkillPoints(readInteger());
							objdefaultSkills.setSkillScrollName(readString());
							objdefaultSkills.setIsNeedSkillScroll(readBoolean());
							objdefaultSkills.setPreSkillName(readString());
							objdefaultSkills.setPreSkillIsOpen(readBoolean());
							objdefaultSkills.setHasSkillScroll(readBoolean());
							objdefaultSkills.setSkillPointsIsEnough(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		gemSkills = new com.hifun.soul.gameserver.skill.template.SkillInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillInfo objgemSkills = new com.hifun.soul.gameserver.skill.template.SkillInfo();
			gemSkills[i] = objgemSkills;
					objgemSkills.setSkillId(readInteger());
							objgemSkills.setSkillName(readString());
							objgemSkills.setSkillDesc(readString());
							objgemSkills.setSkillActionId(readInteger());
							objgemSkills.setFlyEffectId(readInteger());
							objgemSkills.setBeHitedEffectId(readInteger());
							objgemSkills.setEnemyBeHitedEffectId(readInteger());
							objgemSkills.setCooldownRound(readInteger());
							objgemSkills.setAttackType(readInteger());
							objgemSkills.setSkillType(readInteger());
							objgemSkills.setNeedSelectGem(readBoolean());
							objgemSkills.setRangeType(readInteger());
							objgemSkills.setUseRoundOver(readBoolean());
							objgemSkills.setRedCost(readInteger());
							objgemSkills.setYellowCost(readInteger());
							objgemSkills.setGreenCost(readInteger());
							objgemSkills.setBlueCost(readInteger());
							objgemSkills.setPurpleCost(readInteger());
							objgemSkills.setIsCarried(readBoolean());
							objgemSkills.setSkillSound(readInteger());
							objgemSkills.setSkillIcon(readInteger());
							objgemSkills.setSlotIndex(readInteger());
							objgemSkills.setSkillState(readInteger());
							objgemSkills.setSkillDevelopType(readInteger());
							objgemSkills.setNeedLevel(readInteger());
							objgemSkills.setNeedSkillPoints(readInteger());
							objgemSkills.setSkillScrollName(readString());
							objgemSkills.setIsNeedSkillScroll(readBoolean());
							objgemSkills.setPreSkillName(readString());
							objgemSkills.setPreSkillIsOpen(readBoolean());
							objgemSkills.setHasSkillScroll(readBoolean());
							objgemSkills.setSkillPointsIsEnough(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		energySkills = new com.hifun.soul.gameserver.skill.template.SkillInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillInfo objenergySkills = new com.hifun.soul.gameserver.skill.template.SkillInfo();
			energySkills[i] = objenergySkills;
					objenergySkills.setSkillId(readInteger());
							objenergySkills.setSkillName(readString());
							objenergySkills.setSkillDesc(readString());
							objenergySkills.setSkillActionId(readInteger());
							objenergySkills.setFlyEffectId(readInteger());
							objenergySkills.setBeHitedEffectId(readInteger());
							objenergySkills.setEnemyBeHitedEffectId(readInteger());
							objenergySkills.setCooldownRound(readInteger());
							objenergySkills.setAttackType(readInteger());
							objenergySkills.setSkillType(readInteger());
							objenergySkills.setNeedSelectGem(readBoolean());
							objenergySkills.setRangeType(readInteger());
							objenergySkills.setUseRoundOver(readBoolean());
							objenergySkills.setRedCost(readInteger());
							objenergySkills.setYellowCost(readInteger());
							objenergySkills.setGreenCost(readInteger());
							objenergySkills.setBlueCost(readInteger());
							objenergySkills.setPurpleCost(readInteger());
							objenergySkills.setIsCarried(readBoolean());
							objenergySkills.setSkillSound(readInteger());
							objenergySkills.setSkillIcon(readInteger());
							objenergySkills.setSlotIndex(readInteger());
							objenergySkills.setSkillState(readInteger());
							objenergySkills.setSkillDevelopType(readInteger());
							objenergySkills.setNeedLevel(readInteger());
							objenergySkills.setNeedSkillPoints(readInteger());
							objenergySkills.setSkillScrollName(readString());
							objenergySkills.setIsNeedSkillScroll(readBoolean());
							objenergySkills.setPreSkillName(readString());
							objenergySkills.setPreSkillIsOpen(readBoolean());
							objenergySkills.setHasSkillScroll(readBoolean());
							objenergySkills.setSkillPointsIsEnough(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		assistSkills = new com.hifun.soul.gameserver.skill.template.SkillInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillInfo objassistSkills = new com.hifun.soul.gameserver.skill.template.SkillInfo();
			assistSkills[i] = objassistSkills;
					objassistSkills.setSkillId(readInteger());
							objassistSkills.setSkillName(readString());
							objassistSkills.setSkillDesc(readString());
							objassistSkills.setSkillActionId(readInteger());
							objassistSkills.setFlyEffectId(readInteger());
							objassistSkills.setBeHitedEffectId(readInteger());
							objassistSkills.setEnemyBeHitedEffectId(readInteger());
							objassistSkills.setCooldownRound(readInteger());
							objassistSkills.setAttackType(readInteger());
							objassistSkills.setSkillType(readInteger());
							objassistSkills.setNeedSelectGem(readBoolean());
							objassistSkills.setRangeType(readInteger());
							objassistSkills.setUseRoundOver(readBoolean());
							objassistSkills.setRedCost(readInteger());
							objassistSkills.setYellowCost(readInteger());
							objassistSkills.setGreenCost(readInteger());
							objassistSkills.setBlueCost(readInteger());
							objassistSkills.setPurpleCost(readInteger());
							objassistSkills.setIsCarried(readBoolean());
							objassistSkills.setSkillSound(readInteger());
							objassistSkills.setSkillIcon(readInteger());
							objassistSkills.setSlotIndex(readInteger());
							objassistSkills.setSkillState(readInteger());
							objassistSkills.setSkillDevelopType(readInteger());
							objassistSkills.setNeedLevel(readInteger());
							objassistSkills.setNeedSkillPoints(readInteger());
							objassistSkills.setSkillScrollName(readString());
							objassistSkills.setIsNeedSkillScroll(readBoolean());
							objassistSkills.setPreSkillName(readString());
							objassistSkills.setPreSkillIsOpen(readBoolean());
							objassistSkills.setHasSkillScroll(readBoolean());
							objassistSkills.setSkillPointsIsEnough(readBoolean());
				}
		skillPoints = readInteger();
		crystal = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		skillDevelopInfos = new com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillDevelopInfo objskillDevelopInfos = new com.hifun.soul.gameserver.skill.template.SkillDevelopInfo();
			skillDevelopInfos[i] = objskillDevelopInfos;
					objskillDevelopInfos.setSkillDevelopType(readInteger());
							objskillDevelopInfos.setIcon(readInteger());
							objskillDevelopInfos.setRecommendPointType(readInteger());
							objskillDevelopInfos.setMainCostMagic(readInteger());
							objskillDevelopInfos.setAssistCostMagic(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(defaultSkills.length);
	for(int i=0; i<defaultSkills.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillInfo objdefaultSkills = defaultSkills[i];
				writeInteger(objdefaultSkills.getSkillId());
				writeString(objdefaultSkills.getSkillName());
				writeString(objdefaultSkills.getSkillDesc());
				writeInteger(objdefaultSkills.getSkillActionId());
				writeInteger(objdefaultSkills.getFlyEffectId());
				writeInteger(objdefaultSkills.getBeHitedEffectId());
				writeInteger(objdefaultSkills.getEnemyBeHitedEffectId());
				writeInteger(objdefaultSkills.getCooldownRound());
				writeInteger(objdefaultSkills.getAttackType());
				writeInteger(objdefaultSkills.getSkillType());
				writeBoolean(objdefaultSkills.getNeedSelectGem());
				writeInteger(objdefaultSkills.getRangeType());
				writeBoolean(objdefaultSkills.getUseRoundOver());
				writeInteger(objdefaultSkills.getRedCost());
				writeInteger(objdefaultSkills.getYellowCost());
				writeInteger(objdefaultSkills.getGreenCost());
				writeInteger(objdefaultSkills.getBlueCost());
				writeInteger(objdefaultSkills.getPurpleCost());
				writeBoolean(objdefaultSkills.getIsCarried());
				writeInteger(objdefaultSkills.getSkillSound());
				writeInteger(objdefaultSkills.getSkillIcon());
				writeInteger(objdefaultSkills.getSlotIndex());
				writeInteger(objdefaultSkills.getSkillState());
				writeInteger(objdefaultSkills.getSkillDevelopType());
				writeInteger(objdefaultSkills.getNeedLevel());
				writeInteger(objdefaultSkills.getNeedSkillPoints());
				writeString(objdefaultSkills.getSkillScrollName());
				writeBoolean(objdefaultSkills.getIsNeedSkillScroll());
				writeString(objdefaultSkills.getPreSkillName());
				writeBoolean(objdefaultSkills.getPreSkillIsOpen());
				writeBoolean(objdefaultSkills.getHasSkillScroll());
				writeBoolean(objdefaultSkills.getSkillPointsIsEnough());
	}
	writeShort(gemSkills.length);
	for(int i=0; i<gemSkills.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillInfo objgemSkills = gemSkills[i];
				writeInteger(objgemSkills.getSkillId());
				writeString(objgemSkills.getSkillName());
				writeString(objgemSkills.getSkillDesc());
				writeInteger(objgemSkills.getSkillActionId());
				writeInteger(objgemSkills.getFlyEffectId());
				writeInteger(objgemSkills.getBeHitedEffectId());
				writeInteger(objgemSkills.getEnemyBeHitedEffectId());
				writeInteger(objgemSkills.getCooldownRound());
				writeInteger(objgemSkills.getAttackType());
				writeInteger(objgemSkills.getSkillType());
				writeBoolean(objgemSkills.getNeedSelectGem());
				writeInteger(objgemSkills.getRangeType());
				writeBoolean(objgemSkills.getUseRoundOver());
				writeInteger(objgemSkills.getRedCost());
				writeInteger(objgemSkills.getYellowCost());
				writeInteger(objgemSkills.getGreenCost());
				writeInteger(objgemSkills.getBlueCost());
				writeInteger(objgemSkills.getPurpleCost());
				writeBoolean(objgemSkills.getIsCarried());
				writeInteger(objgemSkills.getSkillSound());
				writeInteger(objgemSkills.getSkillIcon());
				writeInteger(objgemSkills.getSlotIndex());
				writeInteger(objgemSkills.getSkillState());
				writeInteger(objgemSkills.getSkillDevelopType());
				writeInteger(objgemSkills.getNeedLevel());
				writeInteger(objgemSkills.getNeedSkillPoints());
				writeString(objgemSkills.getSkillScrollName());
				writeBoolean(objgemSkills.getIsNeedSkillScroll());
				writeString(objgemSkills.getPreSkillName());
				writeBoolean(objgemSkills.getPreSkillIsOpen());
				writeBoolean(objgemSkills.getHasSkillScroll());
				writeBoolean(objgemSkills.getSkillPointsIsEnough());
	}
	writeShort(energySkills.length);
	for(int i=0; i<energySkills.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillInfo objenergySkills = energySkills[i];
				writeInteger(objenergySkills.getSkillId());
				writeString(objenergySkills.getSkillName());
				writeString(objenergySkills.getSkillDesc());
				writeInteger(objenergySkills.getSkillActionId());
				writeInteger(objenergySkills.getFlyEffectId());
				writeInteger(objenergySkills.getBeHitedEffectId());
				writeInteger(objenergySkills.getEnemyBeHitedEffectId());
				writeInteger(objenergySkills.getCooldownRound());
				writeInteger(objenergySkills.getAttackType());
				writeInteger(objenergySkills.getSkillType());
				writeBoolean(objenergySkills.getNeedSelectGem());
				writeInteger(objenergySkills.getRangeType());
				writeBoolean(objenergySkills.getUseRoundOver());
				writeInteger(objenergySkills.getRedCost());
				writeInteger(objenergySkills.getYellowCost());
				writeInteger(objenergySkills.getGreenCost());
				writeInteger(objenergySkills.getBlueCost());
				writeInteger(objenergySkills.getPurpleCost());
				writeBoolean(objenergySkills.getIsCarried());
				writeInteger(objenergySkills.getSkillSound());
				writeInteger(objenergySkills.getSkillIcon());
				writeInteger(objenergySkills.getSlotIndex());
				writeInteger(objenergySkills.getSkillState());
				writeInteger(objenergySkills.getSkillDevelopType());
				writeInteger(objenergySkills.getNeedLevel());
				writeInteger(objenergySkills.getNeedSkillPoints());
				writeString(objenergySkills.getSkillScrollName());
				writeBoolean(objenergySkills.getIsNeedSkillScroll());
				writeString(objenergySkills.getPreSkillName());
				writeBoolean(objenergySkills.getPreSkillIsOpen());
				writeBoolean(objenergySkills.getHasSkillScroll());
				writeBoolean(objenergySkills.getSkillPointsIsEnough());
	}
	writeShort(assistSkills.length);
	for(int i=0; i<assistSkills.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillInfo objassistSkills = assistSkills[i];
				writeInteger(objassistSkills.getSkillId());
				writeString(objassistSkills.getSkillName());
				writeString(objassistSkills.getSkillDesc());
				writeInteger(objassistSkills.getSkillActionId());
				writeInteger(objassistSkills.getFlyEffectId());
				writeInteger(objassistSkills.getBeHitedEffectId());
				writeInteger(objassistSkills.getEnemyBeHitedEffectId());
				writeInteger(objassistSkills.getCooldownRound());
				writeInteger(objassistSkills.getAttackType());
				writeInteger(objassistSkills.getSkillType());
				writeBoolean(objassistSkills.getNeedSelectGem());
				writeInteger(objassistSkills.getRangeType());
				writeBoolean(objassistSkills.getUseRoundOver());
				writeInteger(objassistSkills.getRedCost());
				writeInteger(objassistSkills.getYellowCost());
				writeInteger(objassistSkills.getGreenCost());
				writeInteger(objassistSkills.getBlueCost());
				writeInteger(objassistSkills.getPurpleCost());
				writeBoolean(objassistSkills.getIsCarried());
				writeInteger(objassistSkills.getSkillSound());
				writeInteger(objassistSkills.getSkillIcon());
				writeInteger(objassistSkills.getSlotIndex());
				writeInteger(objassistSkills.getSkillState());
				writeInteger(objassistSkills.getSkillDevelopType());
				writeInteger(objassistSkills.getNeedLevel());
				writeInteger(objassistSkills.getNeedSkillPoints());
				writeString(objassistSkills.getSkillScrollName());
				writeBoolean(objassistSkills.getIsNeedSkillScroll());
				writeString(objassistSkills.getPreSkillName());
				writeBoolean(objassistSkills.getPreSkillIsOpen());
				writeBoolean(objassistSkills.getHasSkillScroll());
				writeBoolean(objassistSkills.getSkillPointsIsEnough());
	}
		writeInteger(skillPoints);
		writeInteger(crystal);
	writeShort(skillDevelopInfos.length);
	for(int i=0; i<skillDevelopInfos.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillDevelopInfo objskillDevelopInfos = skillDevelopInfos[i];
				writeInteger(objskillDevelopInfos.getSkillDevelopType());
				writeInteger(objskillDevelopInfos.getIcon());
				writeInteger(objskillDevelopInfos.getRecommendPointType());
				writeInteger(objskillDevelopInfos.getMainCostMagic());
				writeString(objskillDevelopInfos.getAssistCostMagic());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ALL_SKILLS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ALL_SKILLS";
	}

	public com.hifun.soul.gameserver.skill.template.SkillInfo[] getDefaultSkills(){
		return defaultSkills;
	}

	public void setDefaultSkills(com.hifun.soul.gameserver.skill.template.SkillInfo[] defaultSkills){
		this.defaultSkills = defaultSkills;
	}	

	public com.hifun.soul.gameserver.skill.template.SkillInfo[] getGemSkills(){
		return gemSkills;
	}

	public void setGemSkills(com.hifun.soul.gameserver.skill.template.SkillInfo[] gemSkills){
		this.gemSkills = gemSkills;
	}	

	public com.hifun.soul.gameserver.skill.template.SkillInfo[] getEnergySkills(){
		return energySkills;
	}

	public void setEnergySkills(com.hifun.soul.gameserver.skill.template.SkillInfo[] energySkills){
		this.energySkills = energySkills;
	}	

	public com.hifun.soul.gameserver.skill.template.SkillInfo[] getAssistSkills(){
		return assistSkills;
	}

	public void setAssistSkills(com.hifun.soul.gameserver.skill.template.SkillInfo[] assistSkills){
		this.assistSkills = assistSkills;
	}	

	public int getSkillPoints(){
		return skillPoints;
	}
		
	public void setSkillPoints(int skillPoints){
		this.skillPoints = skillPoints;
	}

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}

	public com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] getSkillDevelopInfos(){
		return skillDevelopInfos;
	}

	public void setSkillDevelopInfos(com.hifun.soul.gameserver.skill.template.SkillDevelopInfo[] skillDevelopInfos){
		this.skillDevelopInfos = skillDevelopInfos;
	}	
}