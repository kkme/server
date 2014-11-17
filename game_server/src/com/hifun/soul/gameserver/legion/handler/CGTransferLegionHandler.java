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
import com.hifun.soul.gameserver.legion.msg.CGTransferLegion;

/**
 * 转让军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGTransferLegionHandler implements
		IMessageHandlerWithType<CGTransferLegion> {

	@Override
	public short getMessageType() {
		return MessageType.CG_TRANSFER_LEGION;
	}

	@Override
	public void execute(CGTransferLegion message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();

		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		// 是否在军团中
		if (legion == null) {
			human.sendErrorMessage(LangConstants.NOT_IN_LEGION);
			return;
		}
		// 是否是团长
		if (legion.getCommanderId() != human.getHumanGuid()) {
			human.sendErrorMessage(LangConstants.TRANSFER_LEGION_IS_NOT_COMMANDER);
			return;
		}
		// 转让的角色是否在军团内
		Legion toLegion = globalLegionManager.getLegion(message
				.getTransferHumanGuid());
		if (toLegion == null || toLegion.getId() != legion.getId()) {
			human.sendErrorMessage(LangConstants.TRANSFER_LEGION_IS_NOT_MEMBER);
			return;
		}
		// 转让的角色是自己
		if (legion.getCommanderId() == message.getTransferHumanGuid()) {
			human.sendErrorMessage(LangConstants.TRANSFER_LEGION_CAN_NOT_SELF);
			return;
		}
		// 转让军团
		GameServerAssist.getGlobalLegionManager().transferLegion(human,
				message.getTransferHumanGuid());
	}

}
