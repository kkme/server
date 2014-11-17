package com.hifun.soul.gameserver.legionboss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionboss.LegionBossRoleInfo;
import com.hifun.soul.gameserver.legionboss.msg.CGEncourageLegionBossWar;
import com.hifun.soul.gameserver.legionboss.msg.GCEncourageLegionBossWar;
import com.hifun.soul.gameserver.legionboss.service.LegionBossService;
import com.hifun.soul.gameserver.legionboss.template.LegionBossTemplate;
import com.hifun.soul.gameserver.matchbattle.EncourageType;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGEncourageLegionBossWarHandler implements
		IMessageHandlerWithType<CGEncourageLegionBossWar> {

	@Autowired
	private LegionBossService bossWarService;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ENCOURAGE_LEGION_BOSS_WAR;
	}

	@Override
	public void execute(CGEncourageLegionBossWar message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human,
				GameFuncType.LEGION_BOSS_WAR, true)) {
			return;
		}

		EncourageType encourageType = EncourageType.indexOf(message
				.getEncourageType());
		if (encourageType == null) {
			return;
		}

		// 判断当前boss战是否开启
		if (!bossWarService.bossWarIsOpen()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_NOT_OPEN);
			return;
		}

		LegionBossTemplate bossTemplate = bossWarService.getBossTemplate();
		if (bossTemplate == null) {
			return;
		}

		// 判断是否已经达到鼓舞上限
		LegionBossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human
				.getHumanGuid());
		if (bossRoleInfo == null
				|| bossRoleInfo.getEncourageRate() >= bossTemplate
						.getMaxEncourageDamage()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_MAX_ENCOURAGE_RATE);
			return;
		}

		int encourageSuccessRate = 0;
		int costNum = 0;
		switch (encourageType) {
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
		case CRYSTAL:
			encourageSuccessRate = bossTemplate.getCrystalRate();
			costNum = bossTemplate.getCrystal();
			// 消耗魔晶鼓舞
			if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
					MoneyLogReason.LEGION_BOSS_ENCOURAGE, "")) {
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

	private void encourageSuccess(Human human, LegionBossRoleInfo bossRoleInfo,
			LegionBossTemplate bossTemplate) {
		human.sendImportantMessage(LangConstants.LEGION_BOSS_ENCOURAGE_SUCCESS);
		// 加鼓舞
		int newEncourageRate = bossRoleInfo.getEncourageRate()
				+ bossTemplate.getEncourageDamage();
		if (newEncourageRate > bossTemplate.getMaxEncourageDamage()) {
			newEncourageRate = bossTemplate.getMaxEncourageDamage();
		}
		bossRoleInfo.setEncourageRate(newEncourageRate);
		// 更新数据库
		bossWarService.updateBossRoleInfoToDB(bossRoleInfo);

		GCEncourageLegionBossWar gcMsg = new GCEncourageLegionBossWar();
		gcMsg.setEncourageRate(newEncourageRate * 100
				/ bossTemplate.getMaxEncourageDamage());
		gcMsg.setIsFull(newEncourageRate >= bossTemplate
				.getMaxEncourageDamage());
		human.sendMessage(gcMsg);
	}

}
