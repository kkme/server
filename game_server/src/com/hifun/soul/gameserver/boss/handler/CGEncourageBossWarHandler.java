package com.hifun.soul.gameserver.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.boss.BossRoleInfo;
import com.hifun.soul.gameserver.boss.msg.CGEncourageBossWar;
import com.hifun.soul.gameserver.boss.msg.GCEncourageBossWar;
import com.hifun.soul.gameserver.boss.service.BossWarService;
import com.hifun.soul.gameserver.boss.template.BossTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.matchbattle.EncourageType;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGEncourageBossWarHandler implements IMessageHandlerWithType<CGEncourageBossWar> {

	@Autowired
	private BossWarService bossWarService;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ENCOURAGE_BOSS_WAR;
	}

	@Override
	public void execute(CGEncourageBossWar message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.BOSS_WAR, true)) {
			return;
		}

		EncourageType encourageType = EncourageType.indexOf(message.getEncourageType());
		if (encourageType == null) {
			return;
		}

		// 判断当前boss战是否开启
		if (!bossWarService.bossWarIsOpen()) {
			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_OPEN);
			return;
		}

		BossTemplate bossTemplate = bossWarService.getBossTemplate();
		if (bossTemplate == null) {
			return;
		}

		// 判断是否已经达到鼓舞上限
		BossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human.getHumanGuid());
		if (bossRoleInfo == null || bossRoleInfo.getEncourageRate() >= bossTemplate.getMaxEncourageDamage()) {
			human.sendErrorMessage(LangConstants.MAX_ENCOURAGE_RATE);
			return;
		}

		int encourageSuccessRate = 0;
		int costNum = 0;
		int costStoneNum = 0;
		switch (encourageType) {
		case COIN:
			encourageSuccessRate = bossTemplate.getCoinRate();
			costNum = bossTemplate.getCoin();
			// 判断是否有足够的货币
			if (!human.getWallet().isEnough(CurrencyType.COIN, costNum)) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.COIN.getDesc());
				return;
			}
			// 扣除金钱
			if (human.getWallet().costMoney(CurrencyType.COIN, costNum, MoneyLogReason.BOSS_ENCOURAGE, "")) {
				if (MathUtils.shake(encourageSuccessRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
					encourageSuccess(human, bossRoleInfo, bossTemplate);
				} else {
					human.sendGenericMessage(LangConstants.ENCOURAGE_FAIL);
				}
			}
			break;
		case CRYSTAL:
			encourageSuccessRate = bossTemplate.getCrystalRate();
			costNum = bossTemplate.getCrystal();
			// 判断是否有足够的货币
			if (!human.getWallet().isEnough(CurrencyType.CRYSTAL, costNum)) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.CRYSTAL.getDesc());
				return;
			}
			// 扣除金钱
			if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum, MoneyLogReason.BOSS_ENCOURAGE, "")) {
				if (MathUtils.shake(encourageSuccessRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
					encourageSuccess(human, bossRoleInfo, bossTemplate);
				} else {
					human.sendGenericMessage(LangConstants.ENCOURAGE_FAIL);
				}
			}
			break;
		case FORGE_STONE:
			encourageSuccessRate = bossTemplate.getForgeStoneRate();
			costStoneNum = bossTemplate.getForgeStoneCost();
			if (human.getBagManager().getItemCount(ItemConstantId.FORGE_STONE_ID) < costStoneNum) {
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
						GameServerAssist.getSysLangService().read(LangConstants.FORGE_STONE));
				return;
			}
			if (human.getBagManager().removeItemByItemId(ItemConstantId.FORGE_STONE_ID, costStoneNum,
					ItemLogReason.BOSS_WAR_ENCOURAGE, "")) {
				if (MathUtils.shake(encourageSuccessRate / SharedConstants.DEFAULT_FLOAT_BASE)) {
					encourageSuccess(human, bossRoleInfo, bossTemplate);
				} else {
					human.sendGenericMessage(LangConstants.ENCOURAGE_FAIL);
				}
			}
			break;
		case MEDITATION:
			encourageSuccessRate = bossTemplate.getMeditationRate();
			costNum = bossTemplate.getMeditation();
			// 消耗冥想力鼓舞
			if (human.getHumanTechnologyManager().costTechnologyPoints(costNum)) {
				if (MathUtils.shake(encourageSuccessRate
						/ SharedConstants.DEFAULT_FLOAT_BASE)) {
					encourageSuccess(human, bossRoleInfo, bossTemplate);
				} else {
					human.sendGenericMessage(LangConstants.LEGION_BOSS_ENCOURAGE_FAIL);
				}
			}
			break;
		}

	}

	private void encourageSuccess(Human human, BossRoleInfo bossRoleInfo, BossTemplate bossTemplate) {
		human.sendImportantMessage(LangConstants.ENCOURAGE_SUCCESS);
		// 加鼓舞
		int newEncourageRate = bossRoleInfo.getEncourageRate() + bossTemplate.getEncourageDamage();
		if (newEncourageRate > bossTemplate.getMaxEncourageDamage()) {
			newEncourageRate = bossTemplate.getMaxEncourageDamage();
		}
		bossRoleInfo.setEncourageRate(newEncourageRate);
		// 更新数据库
		bossWarService.updateBossRoleInfoToDB(bossRoleInfo);

		GCEncourageBossWar gcMsg = new GCEncourageBossWar();
		gcMsg.setEncourageRate(newEncourageRate * 100 / bossTemplate.getMaxEncourageDamage());
		gcMsg.setIfFull(newEncourageRate >= bossTemplate.getMaxEncourageDamage());
		human.sendMessage(gcMsg);
	}

}
