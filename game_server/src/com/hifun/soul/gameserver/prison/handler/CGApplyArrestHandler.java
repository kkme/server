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
import com.hifun.soul.gameserver.prison.msg.CGApplyArrest;

/**
 * 抓捕
 * 
 * @author yandajun
 * 
 */
@Component
public class CGApplyArrestHandler implements
		IMessageHandlerWithType<CGApplyArrest> {
	@Autowired
	private IBattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_APPLY_ARREST;
	}

	@Override
	public void execute(CGApplyArrest message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner arrestor = manager.getPrisoner(human.getHumanGuid());
		Prisoner beArrested = manager.getPrisoner(message.getArrestHumanId());
		if (arrestor == null || beArrested == null) {
			return;
		}
		if (arrestor.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}

		// 判断是否有抓捕次数
		if (arrestor.getArrestedNum() >= arrestor.getTotalArrestNum()) {
			human.sendErrorMessage(LangConstants.NO_MORE_ARREST_TIMES);
			return;
		}
		// 判断俘虏是否已满
		if (manager.getSlaveList(arrestor).size() >= GameServerAssist
				.getGameConstants().getMaxSlaveNum()) {
			human.sendErrorMessage(LangConstants.SLAVE_IS_FULL);
			return;
		}
		// 判断能不能立即逮捕
		if (beArrested.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			Prisoner master = manager.getPrisoner(beArrested.getMasterId());
			// 主人身份校验
			if (master == null) {
				return;
			}
			if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
				human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
				return;
			}
			if (manager.getSlaveList(master).size() <= 0) {
				return;
			}
			// 不能抓自己的奴隶
			if (master.getHumanId() == human.getHumanGuid()) {
				human.sendErrorMessage(LangConstants.IS_YOUR_SLAVE);
				return;
			}
			// 等级差大于10级，不能抓捕
			if (human.getLevel() - master.getHumanLevel() > GameServerAssist
					.getGameConstants().getArrestLevelDiff()) {
				human.sendErrorMessage(LangConstants.OVER_LEVEL_DIFF_LIMIT);
				return;
			}
			// 判断奴隶与主人之间是否在互动中
			if (beArrested.isInteracting()) {
				human.sendErrorMessage(LangConstants.PRISON_INTERACTING);
				return;
			}
			// 主人正在战俘营发起的战斗
			if (master.isFighting()) {
				human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
				return;
			}
			// 奴隶正在反抗
			if (beArrested.isFighting()) {
				human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
				return;
			}
		} else {
			// 等级差大于10级，不能抓捕
			if (human.getLevel() - beArrested.getHumanLevel() > GameServerAssist
					.getGameConstants().getArrestLevelDiff()) {
				human.sendErrorMessage(LangConstants.OVER_LEVEL_DIFF_LIMIT);
				return;
			}
			// 如果抓捕的是主人，判断主人是否跟所有奴隶在互动
			if (beArrested.getIdentityType() == IdentityType.MASTER.getIndex()
					&& manager.getNotInteractingSlaveList(beArrested).size() == 0) {
				human.sendErrorMessage(LangConstants.PRISON_INTERACTING);
				return;
			}
			// 要抓捕的人正在战斗
			if (beArrested.isFighting()) {
				human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
				return;
			}
			// 自己正在战斗
			if (arrestor.isFighting()) {
				human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
				return;
			}

		}
		// 进入战斗
		if (beArrested.getIdentityType() == IdentityType.FREEMAN.getIndex()) {// 抓捕自由人
			battleManager.pvpBattleEnter(human, message.getArrestHumanId(),
					new PrisonBattleFinishedCallback(human,
							PrisonBattleType.ARREST, null));
		} else if (beArrested.getIdentityType() == IdentityType.MASTER
				.getIndex()) {// 抢夺随机奴隶
			battleManager.pvpBattleEnter(human, message.getArrestHumanId(),
					new PrisonBattleFinishedCallback(human,
							PrisonBattleType.LOOT, null));
		} else if (beArrested.getIdentityType() == IdentityType.SLAVE
				.getIndex()) {// 抢夺某个奴隶
			battleManager.pvpBattleEnter(human, beArrested.getMasterId(),
					new PrisonBattleFinishedCallback(human,
							PrisonBattleType.LOOT, beArrested.getHumanId()));
		}

	}
}
