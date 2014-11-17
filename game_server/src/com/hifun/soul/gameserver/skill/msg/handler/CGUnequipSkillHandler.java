package com.hifun.soul.gameserver.skill.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.CGUnequipSkill;
import com.hifun.soul.gameserver.skill.msg.GCUpdateSkillEquipState;

@Component
public class CGUnequipSkillHandler implements
		IMessageHandlerWithType<CGUnequipSkill> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_UNEQUIP_SKILL;
	}

	@Override
	public void execute(CGUnequipSkill message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.SKILL, true)){
			return;
		}
		ISkill skill = null;
		for (ISkill each : human.getCarriedSkills()) {
			if (message.getSkillId() == each.getSkillId()) {
				skill = each;
				break;
			}
		}
		// 没有此技能;
		if (skill == null) {
			return;
		}
		boolean result = human.getSkillManager().unEquipSkill(skill);
		if (!result) {
			return;
		}
		GCUpdateSkillEquipState skillMsg = new GCUpdateSkillEquipState();
		skillMsg.setSkillId(message.getSkillId());
		skillMsg.setCarried(false);
		skillMsg.setSlotIndex(skill.getSlotIndex());
		human.sendMessage(skillMsg);
	}

}
