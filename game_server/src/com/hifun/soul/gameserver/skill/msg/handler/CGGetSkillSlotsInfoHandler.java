package com.hifun.soul.gameserver.skill.msg.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.skill.msg.CGGetSkillSlotsInfo;
import com.hifun.soul.gameserver.skill.msg.GCSkillSlotsInfo;
import com.hifun.soul.gameserver.skill.slot.SkillSlot;
@Component
public class CGGetSkillSlotsInfoHandler implements
		IMessageHandlerWithType<CGGetSkillSlotsInfo> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_SKILL_SLOTS_INFO;
	}

	@Override
	public void execute(CGGetSkillSlotsInfo message) {
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(message.getPlayer().getHuman(), GameFuncType.SKILL, true)){
			return;
		}
		GCSkillSlotsInfo slotsMsg = new GCSkillSlotsInfo();
		List<SkillSlot> slots = message.getPlayer().getHuman().getSkillManager().getSkillSlots();
		slotsMsg.setSlots(slots.toArray(new SkillSlot[0]));
		message.getPlayer().sendMessage(slotsMsg);
	}

}
