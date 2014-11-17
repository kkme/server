package com.hifun.soul.gameserver.legion.handler;

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
import com.hifun.soul.gameserver.legion.LegionResultType;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGQuitLegion;
import com.hifun.soul.gameserver.legion.msg.GCQuitLegion;

/**
 * 退出军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGQuitLegionHandler implements
		IMessageHandlerWithType<CGQuitLegion> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_QUIT_LEGION;
	}

	@Override
	public void execute(CGQuitLegion message) {
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.LEGION, true)) {
			return;
		}
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		// 如果是团长
		if (legion.getCommanderId() == human.getHumanGuid()) {
			if (legion.getMemberNum() == 0) {
				return;
			}
			// 团内不只一个人
			if (legion.getMemberNum() > 1) {
				human.sendErrorMessage(LangConstants.QUIT_LEGION_HAS_OTHER_MEMBER);
				return;
			}
			// 团内只一个人
			if (legion.getMemberNum() == 1) {
				GCQuitLegion gcMsg = new GCQuitLegion();
				gcMsg.setResult(LegionResultType.LEGION_QUIT_ONLY_ONE
						.getIndex());
				human.sendMessage(gcMsg);
				return;
			}
		}
		// 退出军团
		globalLegionManager.quitLegion(human);
	}

}
