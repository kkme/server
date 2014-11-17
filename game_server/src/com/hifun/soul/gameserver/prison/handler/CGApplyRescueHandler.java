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
import com.hifun.soul.gameserver.prison.msg.CGApplyRescue;

/**
 * 解救
 * 
 * @author yandajun
 * 
 */
@Component
public class CGApplyRescueHandler implements
		IMessageHandlerWithType<CGApplyRescue> {
	@Autowired
	private IBattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_APPLY_RESCUE;
	}

	@Override
	public void execute(CGApplyRescue message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner arrestor = manager.getPrisoner(human.getHumanGuid());
		Prisoner beRescued = manager.getPrisoner(message.getRescueHumanId());
		Prisoner master = GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(beRescued.getMasterId());
		if (arrestor == null || beRescued == null || master == null) {
			return;
		}
		if (arrestor.getIdentityType() == IdentityType.SLAVE.getIndex()
				|| beRescued.getIdentityType() != IdentityType.SLAVE.getIndex()
				|| master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			return;
		}
		// 判断是否是自己的奴隶
		if (beRescued.getMasterId() == arrestor.getHumanId()) {
			human.sendErrorMessage(LangConstants.RESCUE_SELF_SLAVE);
			return;
		}
		// 判断是否有解救次数
		if (arrestor.getRescuedNum() >= arrestor.getTotalRescueNum()) {
			human.sendErrorMessage(LangConstants.NO_MORE_RESCUE_TIMES);
			return;
		}
		// 等级差大于10级，不能营救
		if (human.getLevel() - master.getHumanLevel() > GameServerAssist
				.getGameConstants().getArrestLevelDiff()) {
			human.sendErrorMessage(LangConstants.OVER_LEVEL_DIFF_LIMIT);
			return;
		}
		// 判断是否在互动中
		if (beRescued.isInteracting()) {
			human.sendErrorMessage(LangConstants.PRISON_INTERACTING);
			return;
		}
		// 判断能不能立即解救(主人正在战俘营发起的战斗)
		if (master.isFighting()) {
			human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
			return;
		}
		// 奴隶正在反抗
		if (beRescued.isFighting()) {
			human.sendErrorMessage(LangConstants.PRISON_FIGHTING);
			return;
		}
		// 进入战斗
		battleManager.pvpBattleEnter(human, master.getHumanId(),
				new PrisonBattleFinishedCallback(human,
						PrisonBattleType.RESCUE, message.getRescueHumanId()));
	}

}
