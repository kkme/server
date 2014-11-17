package com.hifun.soul.gameserver.prison.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGMasterRelease;

/**
 * 释放奴隶
 * 
 * @author yandajun
 * 
 */
@Component
public class CGMasterReleaseHandler implements
		IMessageHandlerWithType<CGMasterRelease> {

	@Override
	public short getMessageType() {
		return MessageType.CG_MASTER_RELEASE;
	}

	@Override
	public void execute(CGMasterRelease message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner master = manager.getPrisoner(human.getHumanGuid());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		Prisoner slave = manager.getPrisoner(message.getSlaveHumanId());
		if (slave == null) {
			return;
		}
		if (slave.getIdentityType() != IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		// 互动中不能释放
		if (slave.isInteracting()) {
			human.sendErrorMessage(LangConstants.PRISON_INTERACTING);
			return;
		}
		// 该奴隶战斗中不能释放
		if (slave.isFighting()) {
			human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
			return;
		}
		// 主人战斗中不能释放
		if (master.isFighting()) {
			human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
			return;
		}
		manager.release(human, message.getSlaveHumanId());
	}

}
