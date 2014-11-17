package com.hifun.soul.gameserver.legion.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.service.DirtFilterService;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGAddLegionNotice;
import com.hifun.soul.gameserver.legion.template.LegionRightTemplate;

/**
 * 添加军团公告
 * 
 * @author yandajun
 * 
 */
@Component
public class CGAddLegionNoticeHandler implements
		IMessageHandlerWithType<CGAddLegionNotice> {
	@Autowired
	private DirtFilterService dirtFilterService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ADD_LEGION_NOTICE;
	}

	@Override
	public void execute(CGAddLegionNotice message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验身份
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		LegionMember legionMember = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		// 是否有编辑公告的权限
		LegionRightTemplate legionRightTemplate = GameServerAssist
				.getLegionTemplateManager().getLegionRightTemplate(
						legionMember.getPosition());
		if (legionRightTemplate.getEditNotice() != 1) {
			human.sendErrorMessage(LangConstants.HAVE_NO_RIGHT);
			return;
		}
		// 校验公告
		String legionNotice = message.getLegionNotice();
		int noticeMaxLength = GameServerAssist.getLegionTemplateManager()
				.getConstantsTemplate().getLegionNoticeMaxLength();
		if (legionNotice.length() > noticeMaxLength) {
			human.sendErrorMessage(LangConstants.LEGION_NOTICE_TOO_LANG,
					noticeMaxLength);
			return;
		}
		if (dirtFilterService.containsName(legionNotice)) {
			human.sendErrorMessage(LangConstants.LEGION_NOTICE_INPUT_ERROR);
			return;
		}
		globalLegionManager.addLegionNotice(human, legionNotice);
	}
}
