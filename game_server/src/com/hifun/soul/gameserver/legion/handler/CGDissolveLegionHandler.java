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
import com.hifun.soul.gameserver.legion.msg.CGDissolveLegion;

/**
 * 解散军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGDissolveLegionHandler implements
		IMessageHandlerWithType<CGDissolveLegion> {

	@Override
	public short getMessageType() {
		return MessageType.CG_DISSOLVE_LEGION;
	}

	@Override
	public void execute(CGDissolveLegion message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 是否是团长
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		if (legion.getCommanderId() != human.getHumanGuid()) {
			human.sendErrorMessage(LangConstants.DISSOLVE_LEGION_IS_NOT_COMMANDER);
			return;
		}
		GameServerAssist.getGlobalLegionManager().dissolveLegion(human);
	}

}
