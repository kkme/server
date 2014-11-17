package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGEscortLegionPray;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;

/**
 * 军团祈福，押运收益加成
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortLegionPrayHandler implements
		IMessageHandlerWithType<CGEscortLegionPray> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_LEGION_PRAY;
	}

	@Override
	public void execute(CGEscortLegionPray message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		if (legion == null) {
			human.sendErrorMessage(LangConstants.ESCORT_PRAY_NO_LEGION);
			return;
		}
		// vip等级是否开启
		if (human.getVipLevel() < GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getLegionPrayVipLevel()) {
			human.sendErrorMessage(LangConstants.ESCORT_NO_LEGION_PRAY_RIGHT);
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		if (globalEscortManager.hasLegionPray(human)) {
			human.sendErrorMessage(LangConstants.ESCORT_LEGION_PRAYED);
			return;
		}
		globalEscortManager.legionPray(human);
	}

}
