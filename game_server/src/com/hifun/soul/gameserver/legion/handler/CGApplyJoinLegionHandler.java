package com.hifun.soul.gameserver.legion.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionApply;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGApplyJoinLegion;
import com.hifun.soul.gameserver.legion.template.LegionConstantsTemplate;

/**
 * 申请加入军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGApplyJoinLegionHandler implements
		IMessageHandlerWithType<CGApplyJoinLegion> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_APPLY_JOIN_LEGION;
	}

	@Override
	public void execute(CGApplyJoinLegion message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 判断功能是否开启
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.LEGION, true)) {
			return;
		}
		// 1 是否已加入军团
		Legion selfLegion = globalLegionManager.getLegion(human.getHumanGuid());
		if (selfLegion != null) {
			human.sendErrorMessage(LangConstants.APPLY_LEGION_IS_JOINED_LEGION);
			return;
		}
		// 2 是否超出申请次数限制
		int applyNum = 0;
		List<Legion> legionList = globalLegionManager.getLegionList();
		for (Legion legion : legionList) {
			LegionApply legionApply = legion.getApplyListMap().get(
					human.getHumanGuid());
			if (legionApply != null) {
				applyNum++;
			}
		}
		LegionConstantsTemplate legionConstantsTemplate = GameServerAssist
				.getLegionTemplateManager().getConstantsTemplate();
		int applyNumLimit = legionConstantsTemplate.getCanApplyLegionNum();
		if (applyNum > applyNumLimit) {
			human.sendErrorMessage(LangConstants.APPLY_LEGION_OVER_NUM,
					applyNumLimit);
			return;
		}
		// 3 所申请军团人数是否已满
		Legion appleyLegion = globalLegionManager.getLegionById(message
				.getJoinLegionId());
		if (appleyLegion.getMemberNum() == appleyLegion.getMemberLimit()) {
			human.sendErrorMessage(LangConstants.JOIN_LEGION_MEMBER_IS_FULL);
			return;
		}
		// 4 申请信息保存
		globalLegionManager.applyJoinLegion(human, message.getJoinLegionId());
	}

}
