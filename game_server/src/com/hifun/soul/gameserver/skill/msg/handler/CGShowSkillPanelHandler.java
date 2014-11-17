package com.hifun.soul.gameserver.skill.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.skill.msg.CGShowSkillPanel;

@Component
public class CGShowSkillPanelHandler implements
		IMessageHandlerWithType<CGShowSkillPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_SKILL_PANEL;
	}

	@Override
	public void execute(CGShowSkillPanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		human.getSkillManager().sendAllSkillInfos();
		human.getHumanGuideManager().showGuide(GuideType.SKILL.getIndex());
	}

}
