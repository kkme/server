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
import com.hifun.soul.gameserver.prison.msg.CGSlaveInteract;

/**
 * 奴隶与主人互动
 * 
 * @author yandajun
 * 
 */
@Component
public class CGSlaveInteractHandler implements
		IMessageHandlerWithType<CGSlaveInteract> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SLAVE_INTERACT;
	}

	@Override
	public void execute(CGSlaveInteract message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner slave = manager.getPrisoner(human.getHumanGuid());
		if (slave == null) {
			return;
		}
		if (slave.getIdentityType() != IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		Prisoner master = manager.getPrisoner(slave.getMasterId());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		// 是否达到互动次数限制
		if (slave.getInteractedNum() >= slave.getTotalInteractNum()) {
			human.sendErrorMessage(LangConstants.NO_MORE_INTERACT_TIMES);
			return;
		}
		// 是否在互动中
		if (slave.isInteracting()) {
			human.sendErrorMessage(LangConstants.PRISON_INTERACTING);
			return;
		}
		// 主人是否在战斗中
		if (master.isFighting()) {
			human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
			return;
		}
		GameServerAssist.getGlobalPrisonManager().interact(human,
				message.getInteractedType());
	}

}
