package com.hifun.soul.gameserver.skill.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.skill.msg.CGGetSkillSlotTip;
import com.hifun.soul.gameserver.skill.msg.GCSkillSlotInfo;
import com.hifun.soul.gameserver.skill.slot.SkillSlot;

@Component
public class CGGetSkillSlotTipHandler implements
		IMessageHandlerWithType<CGGetSkillSlotTip> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_SKILL_SLOT_TIP;
	}

	@Override
	public void execute(CGGetSkillSlotTip message) {
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(message.getPlayer().getHuman(), GameFuncType.SKILL, true)){
			return;
		}
		GCSkillSlotInfo slotMsg = new GCSkillSlotInfo();
		SkillSlot slot = message.getPlayer().getHuman().getSkillManager().getCanOpenSkillSlot();
		slotMsg.setHumanLevel(slot.getTemplate().getHumanLevel());
		slotMsg.setCoin(slot.getTemplate().getCostCoin());
		slotMsg.setCrystal(slot.getTemplate().getCostCrystal());
		message.getPlayer().sendMessage(slotMsg);
	}

}
