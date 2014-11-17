package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGAgreeJoinLegion;
import com.hifun.soul.gameserver.legion.template.LegionRightTemplate;

/**
 * 同意加入军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGAgreeJoinLegionHandler implements
		IMessageHandlerWithType<CGAgreeJoinLegion> {

	@Override
	public short getMessageType() {
		return MessageType.CG_AGREE_JOIN_LEGION;
	}

	@Override
	public void execute(CGAgreeJoinLegion message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		// 判断是否有审核权限
		GlobalLegionManager legionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验身份
		Legion legion = legionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		LegionMember legionMember = legionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		int position = legionMember.getPosition();
		LegionRightTemplate legionRightTemplate = GameServerAssist
				.getLegionTemplateManager().getLegionRightTemplate(position);
		if (legionRightTemplate.getCheckApply() == 0) {
			human.sendErrorMessage(LangConstants.CHECK_APPLY_JOIN_LEGION_WITHOUT_RIGHT);
			return;
		}
		// 是否已在军团中
		Legion applyerLegion = legionManager.getLegion(message
				.getJoinHumanGuid());
		if (applyerLegion != null) {
			human.sendErrorMessage(LangConstants.CHECK_APPLY_JOINED_LEGION,
					applyerLegion.getLegionName());
			return;
		}
		// 判断人数是否已满
		if (legion.getMemberNum() >= legion.getMemberLimit()) {
			human.sendErrorMessage(LangConstants.JOIN_LEGION_MEMBER_IS_FULL);
			return;
		}
		// 同意加入军团
		GameServerAssist.getGlobalLegionManager().agreeJoinLegion(human,
				message.getJoinHumanGuid());
	}

}
