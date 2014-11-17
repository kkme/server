package com.hifun.soul.gameserver.prison.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.callback.PrisonBattleFinishedCallback;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.PrisonBattleType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGSlaveRevolt;

/**
 * 奴隶反抗主人
 * 
 * @author yandajun
 * 
 */
@Component
public class CGSlaveRevoltHandler implements
		IMessageHandlerWithType<CGSlaveRevolt> {
	@Autowired
	private IBattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_SLAVE_REVOLT;
	}

	@Override
	public void execute(CGSlaveRevolt message) {
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
		Prisoner master = GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(slave.getMasterId());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		// 是否达到反抗次数限制
		if (slave.getRevoltedNum() >= slave.getTotalRevoltNum()) {
			human.sendErrorMessage(LangConstants.NO_MORE_REVOLT_TIMES);
			return;
		}
		// 是否在互动中
		if (slave.isInteracting()) {
			human.sendErrorMessage(LangConstants.PRISON_INTERACTING);
			return;
		}
		// 是否在战斗中
		if (master.isFighting()) {
			human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
			return;
		}
		// 进入战斗
		battleManager.pvpBattleEnter(human, master.getHumanId(),
				new PrisonBattleFinishedCallback(human,
						PrisonBattleType.REVOLT, null));
	}

}
