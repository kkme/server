package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGRemoveLegionMember;
import com.hifun.soul.gameserver.legion.template.LegionRightTemplate;

/**
 * 剔除军团成员
 * 
 * @author yandajun
 * 
 */
@Component
public class CGRemoveLegionMemberHandler implements
		IMessageHandlerWithType<CGRemoveLegionMember> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REMOVE_LEGION_MEMBER;
	}

	@Override
	public void execute(CGRemoveLegionMember message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager legionManager = GameServerAssist
				.getGlobalLegionManager();
		// 是否在军团中
		if (legionManager.getLegion(human.getHumanGuid()) == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		// 判断是否具有踢人权限
		LegionMember legionMember = legionManager.getLegionMember(human
				.getHumanGuid());
		int position = legionMember.getPosition();
		LegionRightTemplate legionRightTemplate = GameServerAssist
				.getLegionTemplateManager().getLegionRightTemplate(position);
		if (legionRightTemplate.getRemoveMember() == 0) {
			human.sendErrorMessage(LangConstants.REMOVE_LEGION_MEMBER_WITHOUT_RIGHT);
			return;
		}
		// 不能剔除自己
		if (human.getHumanGuid() == message.getRemoveHumanGuid()) {
			human.sendErrorMessage(LangConstants.REMOVE_LEGION_CAN_NOT_SELF);
			return;
		}
		// 同级别或高级别不能剔除
		LegionMember removeMember = legionManager.getLegionMember(message
				.getRemoveHumanGuid());
		if (removeMember == null) {
			return;
		}
		if (position <= removeMember.getPosition()) {
			human.sendErrorMessage(LangConstants.REMOVE_LEGION_MEMBER_SAME_POSITION);
			return;
		}
		// 踢人
		legionManager.removeLegionMember(human, message.getRemoveHumanGuid());
	}
}
