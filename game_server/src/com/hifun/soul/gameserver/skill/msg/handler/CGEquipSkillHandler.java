package com.hifun.soul.gameserver.skill.msg.handler;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillStateType;
import com.hifun.soul.gameserver.skill.msg.CGEquipSkill;
import com.hifun.soul.gameserver.skill.msg.GCUpdateSkillEquipState;

@Component
public class CGEquipSkillHandler implements
		IMessageHandlerWithType<CGEquipSkill> {
	
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_EQUIP_SKILL;
	}

	@Override
	public void execute(CGEquipSkill message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.SKILL, true)){
			return;
		}
		Collection<ISkill> skills = human.getSkillManager().getAllSkills();
		ISkill skill = null;
		for (ISkill each : human.getCarriedSkills()) {
			if (message.getSkillId() == each.getSkillId()) {
				skill = each;
				break;
			}
		}
		// 已经装备了的技能;
		if (skill != null) {
			return;
		}
		for (ISkill each : skills) {
			if (message.getSkillId() == each.getSkillId()) {
				skill = each;
				break;
			}
		}
		// 没有此技能
		if (skill == null) {
			return;
		}
		// 判断技能状态
		if(skill.getSkillState() != SkillStateType.STUDYED.getIndex()){
			return;
		}
		
		boolean result = human.getSkillManager().equipSkill(skill);
		if (!result) {
			return;
		}
		GCUpdateSkillEquipState skillMsg = new GCUpdateSkillEquipState();
		skillMsg.setSkillId(message.getSkillId());
		skillMsg.setCarried(true);
		skillMsg.setSlotIndex(skill.getSlotIndex());
		human.sendMessage(skillMsg);
	}

}
