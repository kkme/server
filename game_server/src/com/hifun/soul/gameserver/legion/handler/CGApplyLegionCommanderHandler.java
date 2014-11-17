package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGApplyLegionCommander;

/**
 * 申请晋升团长
 * 
 * @author yandajun
 * 
 */
@Component
public class CGApplyLegionCommanderHandler implements
		IMessageHandlerWithType<CGApplyLegionCommander> {

	@Override
	public short getMessageType() {
		return MessageType.CG_APPLY_LEGION_COMMANDER;
	}

	@Override
	public void execute(CGApplyLegionCommander message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 是否已加入军团
		Legion selfLegion = globalLegionManager.getLegion(human.getHumanGuid());
		if (selfLegion == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		// 是否已经是团长
		if (selfLegion.getCommanderId() == human.getHumanGuid()) {
			human.sendErrorMessage(LangConstants.APPLY_LEGION_COMMANDER_IS);
			return;
		}
		// 申请晋升团长
		globalLegionManager.applyLegionCommander(human);
	}

}
