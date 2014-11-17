package com.hifun.soul.gameserver.mars.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.manager.BattleManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.battle.MarsPVEBattleCallback;
import com.hifun.soul.gameserver.mars.battle.MarsPVPBattleCallback;
import com.hifun.soul.gameserver.mars.msg.CGKillMarsPlayer;
import com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo;
import com.hifun.soul.gameserver.monster.Monster;

@Component
public class CGKillMarsPlayerHandler implements
		IMessageHandlerWithType<CGKillMarsPlayer> {
	@Autowired
	private BattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_KILL_MARS_PLAYER;
	}

	@Override
	public void execute(CGKillMarsPlayer message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MARS, true)) {
			return;
		}
		// 判断是否还有挑战次数
		if (human.getMarsRemainKillNum() <= 0) {
			human.sendErrorMessage(LangConstants.HAVE_NO_KILL_TIMES);
			return;
		}
		MarsRoomInfo roomInfo = human.getHumanMarsManager().getRoomInfo(
				message.getRoomType());
		if (roomInfo == null) {
			return;
		}
		// 如果加倍，消耗魔晶
		int multiple = message.getMultiple();
		if (multiple == 0) {
			return;
		}

		if (multiple > 1) {
			// 校验vip等级是否可以加倍
			int needVipLevel = GameServerAssist.getMarsTemplateManager()
					.getMarsBetTemplate(multiple).getVipLevel();
			if (human.getVipLevel() < needVipLevel) {
				human.sendErrorMessage(LangConstants.MARS_CAN_NOT_MULTIPLE,
						multiple);
				return;
			}
			// 是否还有加倍次数
			if (human.getMarsRemainMultipleNum() <= 0) {
				human.sendErrorMessage(LangConstants.HAVE_NO_MULTIPLE_TIMES);
				return;
			}
		}
		int costNum = GameServerAssist.getMarsTemplateManager()
				.getMarsBetTemplate(multiple).getCostNum();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
				MoneyLogReason.MARS_MULTIPLE_KILL, "")) {
			MarsPlayerInfo ownerInfo = roomInfo.getOwnerInfo();
			// 进入战斗
			switch (ownerInfo.getPlayerType()) {
			case NPC:
				Monster monster = GameServerAssist.getMonsterFactory()
						.createMonster((int) ownerInfo.getPlayerId());
				battleManager.startBattleWithMapMonster(human, monster,
						new MarsPVEBattleCallback(human, message.getRoomType(),
								multiple));
				break;
			case PLAYER:
				battleManager.pvpBattleEnter(human, ownerInfo.getPlayerId(),
						new MarsPVPBattleCallback(human, message.getRoomType(),
								multiple));
				break;
			}
		}

	}
}
