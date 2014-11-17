package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.msg.CGRejectJoinLegion;
import com.hifun.soul.gameserver.legion.template.LegionRightTemplate;

/**
 * 拒绝加入军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGRejectJoinLegionHandler implements
		IMessageHandlerWithType<CGRejectJoinLegion> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REJECT_JOIN_LEGION;
	}

	@Override
	public void execute(CGRejectJoinLegion message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		// 是否在军团中
		if (GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid()) == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		// 判断是否有审核权限
		LegionMember legionMember = GameServerAssist.getGlobalLegionManager()
				.getLegionMember(human.getHumanGuid());
		int position = legionMember.getPosition();
		LegionRightTemplate legionRightTemplate = GameServerAssist
				.getLegionTemplateManager().getLegionRightTemplate(position);
		if (legionRightTemplate.getCheckApply() == 0) {
			human.sendErrorMessage(LangConstants.CHECK_APPLY_JOIN_LEGION_WITHOUT_RIGHT);
			return;
		}
		// 拒绝加入军团
		GameServerAssist.getGlobalLegionManager().rejectJoinLegion(human,
				message.getJoinHumanGuid());
	}

}
