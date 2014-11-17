package com.hifun.soul.gameserver.skill.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端战斗携带的技能
 *
 * @author SevenSoul
 */
@Component
public class GCBattleCarriedSkills extends GCMessage{
	
	/** 玩家战斗携带的技能 */
	private com.hifun.soul.gameserver.skill.template.SkillInfo[] skills;

	public GCBattleCarriedSkills (){
	}
	
	public GCBattleCarriedSkills (
			com.hifun.soul.gameserver.skill.template.SkillInfo[] skills ){
			this.skills = skills;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		skills = new com.hifun.soul.gameserver.skill.template.SkillInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.skill.template.SkillInfo objskills = new com.hifun.soul.gameserver.skill.template.SkillInfo();
			skills[i] = objskills;
					objskills.setSkillId(readInteger());
							objskills.setSkillName(readString());
							objskills.setSkillDesc(readString());
							objskills.setSkillActionId(readInteger());
							objskills.setFlyEffectId(readInteger());
							objskills.setBeHitedEffectId(readInteger());
							objskills.setEnemyBeHitedEffectId(readInteger());
							objskills.setCooldownRound(readInteger());
							objskills.setAttackType(readInteger());
							objskills.setSkillType(readInteger());
							objskills.setNeedSelectGem(readBoolean());
							objskills.setRangeType(readInteger());
							objskills.setUseRoundOver(readBoolean());
							objskills.setRedCost(readInteger());
							objskills.setYellowCost(readInteger());
							objskills.setGreenCost(readInteger());
							objskills.setBlueCost(readInteger());
							objskills.setPurpleCost(readInteger());
							objskills.setIsCarried(readBoolean());
							objskills.setSkillSound(readInteger());
							objskills.setSkillIcon(readInteger());
							objskills.setSlotIndex(readInteger());
							objskills.setSkillState(readInteger());
							objskills.setSkillDevelopType(readInteger());
							objskills.setNeedLevel(readInteger());
							objskills.setNeedSkillPoints(readInteger());
							objskills.setSkillScrollName(readString());
							objskills.setIsNeedSkillScroll(readBoolean());
							objskills.setPreSkillName(readString());
							objskills.setPreSkillIsOpen(readBoolean());
							objskills.setHasSkillScroll(readBoolean());
							objskills.setSkillPointsIsEnough(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(skills.length);
	for(int i=0; i<skills.length; i++){
	com.hifun.soul.gameserver.skill.template.SkillInfo objskills = skills[i];
				writeInteger(objskills.getSkillId());
				writeString(objskills.getSkillName());
				writeString(objskills.getSkillDesc());
				writeInteger(objskills.getSkillActionId());
				writeInteger(objskills.getFlyEffectId());
				writeInteger(objskills.getBeHitedEffectId());
				writeInteger(objskills.getEnemyBeHitedEffectId());
				writeInteger(objskills.getCooldownRound());
				writeInteger(objskills.getAttackType());
				writeInteger(objskills.getSkillType());
				writeBoolean(objskills.getNeedSelectGem());
				writeInteger(objskills.getRangeType());
				writeBoolean(objskills.getUseRoundOver());
				writeInteger(objskills.getRedCost());
				writeInteger(objskills.getYellowCost());
				writeInteger(objskills.getGreenCost());
				writeInteger(objskills.getBlueCost());
				writeInteger(objskills.getPurpleCost());
				writeBoolean(objskills.getIsCarried());
				writeInteger(objskills.getSkillSound());
				writeInteger(objskills.getSkillIcon());
				writeInteger(objskills.getSlotIndex());
				writeInteger(objskills.getSkillState());
				writeInteger(objskills.getSkillDevelopType());
				writeInteger(objskills.getNeedLevel());
				writeInteger(objskills.getNeedSkillPoints());
				writeString(objskills.getSkillScrollName());
				writeBoolean(objskills.getIsNeedSkillScroll());
				writeString(objskills.getPreSkillName());
				writeBoolean(objskills.getPreSkillIsOpen());
				writeBoolean(objskills.getHasSkillScroll());
				writeBoolean(objskills.getSkillPointsIsEnough());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_CARRIED_SKILLS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_CARRIED_SKILLS";
	}

	public com.hifun.soul.gameserver.skill.template.SkillInfo[] getSkills(){
		return skills;
	}

	public void setSkills(com.hifun.soul.gameserver.skill.template.SkillInfo[] skills){
		this.skills = skills;
	}	
}