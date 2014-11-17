package com.hifun.soul.gameserver.skill.msg.handler;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.CGUseSkill;

/**
 * 处理使用技能;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGUseSkillHandler implements IMessageHandlerWithType<CGUseSkill> {
	
	@Override
	public short getMessageType() {
		return MessageType.CG_USE_SKILL;
	}

	@Override
	public void execute(CGUseSkill message) {
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
//		// 判断功能是否开放
//		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.SKILL, true)){
//			return;
//		}
		IBattleContext context = human.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 是否有指定的技能;
		ISkill skill = null;
		for (ISkill each : context.getBattleSkills()) {
			if (each.getSkillId() == message.getSkillId()) {
				skill = each;
				break;
			}
		}
		// 没有此技能
		if (skill == null) {
			return;
		}

		battle.useOtherSkill(human, skill, 0, new ArrayList<ChessBoardSnap>(),
				message.getAssignRow(), message.getAssignCol());

	}

}
