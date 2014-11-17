package com.hifun.soul.gameserver.matchbattle.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.matchbattle.EncourageType;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.MatchBattleServiceStatus;
import com.hifun.soul.gameserver.matchbattle.msg.CGEncourageMatchBattle;
import com.hifun.soul.gameserver.matchbattle.msg.GCEncourageMatchBattle;
import com.hifun.soul.gameserver.matchbattle.service.MatchBattleService;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleConfigTemplate;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGEncourageMatchBattleHandler implements IMessageHandlerWithType<CGEncourageMatchBattle> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ENCOURAGE_MATCH_BATTLE;
	}

	@Override
	public void execute(CGEncourageMatchBattle message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.MATCH_BATTLE, true)) {
			return;
		}

		EncourageType encourageType = EncourageType.indexOf(message.getEncourageType());
		if (encourageType == null) {
			return;
		}
		MatchBattleService matchBattleService = GameServerAssist.getMatchBattleService();
		// 判断活动是否开启
		if (matchBattleService.getStatus() != MatchBattleServiceStatus.READY
				&& matchBattleService.getStatus() != MatchBattleServiceStatus.RUNNING) {
			human.sendErrorMessage(LangConstants.MATCH_BATTLE_NOT_OPEN);
			return;
		}
		MatchBattleConfigTemplate matchBattleConfig = GameServerAssist.getMatchBattleTemplateManager()
				.getMatchBattleConfig();
		// 判断是否已经达到鼓舞上限
		MatchBattleRole matchBattleRole = matchBattleService.searchMatchBattleRole(human.getHumanGuid());
		if (matchBattleRole == null) {
			return;
		}
		if (matchBattleRole.getEncourageRate() >= matchBattleConfig.getMaxEncourageDamage()) {
			human.sendErrorMessage(LangConstants.MAX_ENCOURAGE_RATE);
			return;
		}
		int encourageSuccessRate = 0;
		int costMoneyNum = 0;
		int costStoneNum = 0;		
		switch (encourageType) {
			case COIN:
				encourageSuccessRate = matchBattleConfig.getCoinEncourageRate();
				costMoneyNum = matchBattleConfig.getEncourageCoinCost();
				// 判断是否有足够的货币
				if (!human.getWallet().isEnough(CurrencyType.COIN, costMoneyNum)) {
					human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.COIN.getDesc());
					return;
				}
				// 扣除金钱
				if (human.getWallet().costMoney(CurrencyType.COIN, costMoneyNum, MoneyLogReason.MATCH_BATTLE_ENCOURAGE, "")) {
					if (MathUtils.shake(encourageSuccessRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
						encourageSuccess(human, matchBattleRole, matchBattleConfig);
					} else {
						human.sendGenericMessage(LangConstants.ENCOURAGE_FAIL);
					}
				}
				break;
			case CRYSTAL:
				encourageSuccessRate = matchBattleConfig.getCrystalEncourageRate();
				costMoneyNum = matchBattleConfig.getEncourageCrystalCost();				
				// 判断是否有足够的货币
				if (!human.getWallet().isEnough(CurrencyType.CRYSTAL, costMoneyNum)) {
					human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.CRYSTAL.getDesc());
					return;
				}
				// 扣除金钱
				if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costMoneyNum, MoneyLogReason.MATCH_BATTLE_ENCOURAGE, "")) {
					if (MathUtils.shake(encourageSuccessRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
						encourageSuccess(human, matchBattleRole, matchBattleConfig);
					} else {
						human.sendGenericMessage(LangConstants.ENCOURAGE_FAIL);
					}
				}
				break;
			case FORGE_STONE:
				encourageSuccessRate = matchBattleConfig.getForgeStoneEncourageRate();
				costStoneNum = matchBattleConfig.getForgeStoneEncourageCost();
				if (human.getBagManager().getItemCount(ItemConstantId.FORGE_STONE_ID) < costStoneNum) {
					human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
							GameServerAssist.getSysLangService().read(LangConstants.FORGE_STONE));
					return;
				}
				if (human.getBagManager().removeItemByItemId(ItemConstantId.FORGE_STONE_ID, costStoneNum,
						ItemLogReason.MATCH_BATTLE_ENCOURAGE, "")) {
					if (MathUtils.shake(encourageSuccessRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
						encourageSuccess(human, matchBattleRole, matchBattleConfig);
					} else {
						human.sendGenericMessage(LangConstants.ENCOURAGE_FAIL);
					}
				}
				break;
			default:
				break;
		}
	}

	private void encourageSuccess(Human human, MatchBattleRole matchBattleRole,
			MatchBattleConfigTemplate matchBattleConfig) {
		human.sendImportantMessage(LangConstants.ENCOURAGE_SUCCESS);
		// 加鼓舞
		int newEncourageRate = matchBattleRole.getEncourageRate() + matchBattleConfig.getEncourageDamage();
		if (newEncourageRate > matchBattleConfig.getMaxEncourageDamage()) {
			newEncourageRate = matchBattleConfig.getMaxEncourageDamage();
		}
		matchBattleRole.setEncourageRate(newEncourageRate);
		GameServerAssist.getMatchBattleService().saveBattleRole(matchBattleRole);
		GCEncourageMatchBattle gcMsg = new GCEncourageMatchBattle();
		gcMsg.setEncourageRate(newEncourageRate / 100);
		gcMsg.setIfFull(newEncourageRate >= matchBattleConfig.getMaxEncourageDamage());
		human.sendMessage(gcMsg);
	}

}
