package com.hifun.soul.gameserver.legionboss.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionboss.LegionBossRoleInfo;
import com.hifun.soul.gameserver.legionboss.msg.CGChargedLegionBossWar;
import com.hifun.soul.gameserver.legionboss.msg.GCChargedLegionBossWar;
import com.hifun.soul.gameserver.legionboss.service.LegionBossService;
import com.hifun.soul.gameserver.legionboss.template.LegionBossTemplate;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGChargedLegionBossWarHandler implements
		IMessageHandlerWithType<CGChargedLegionBossWar> {
	private Logger logger = Loggers.BOSS_LOGGER;

	@Autowired
	private LegionBossService bossWarService;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_CHARGED_LEGION_BOSS_WAR;
	}

	@Override
	public void execute(CGChargedLegionBossWar message) {
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
		// 判断当前是否正处在boss战的时间段
		if (!bossWarService.bossWarIsOpen()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_NOT_OPEN);
			return;
		}
		// 判断充能的cd时间是否已经到了
		HumanCdManager cdManager = human.getHumanCdManager();
		LegionBossTemplate bossTemplate = bossWarService.getBossTemplate();
		if (bossTemplate == null) {
			logger.error("can not find bossTempate!");
			return;
		}
		long spendTime = cdManager.getSpendTime(CdType.LEGION_BOSS_CHARGED,
				bossTemplate.getCd() * TimeUtils.MIN);
		if (!cdManager.canAddCd(CdType.LEGION_BOSS_CHARGED, spendTime)) {
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 判断充能的上限是否已经到了上限
		LegionBossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human
				.getHumanGuid());
		if (bossRoleInfo == null) {
			return;
		}
		if (bossRoleInfo.getChargedstrikeRate() >= bossTemplate
				.getMaxChargedRate()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_CHARGED_FULL);
			return;
		}
		// 添加充能cd
		cdManager.addCd(CdType.LEGION_BOSS_CHARGED, spendTime);
		// 改变充能值
		int newChargedstrikeRate = bossRoleInfo.getChargedstrikeRate()
				+ bossTemplate.getChargedRate();
		if (newChargedstrikeRate > bossTemplate.getMaxChargedRate()) {
			newChargedstrikeRate = bossTemplate.getMaxChargedRate();
		}
		bossRoleInfo.setChargedstrikeRate(newChargedstrikeRate);
		// 更新boss战角色信息
		bossWarService.updateBossRoleInfoToDB(bossRoleInfo);
		GCChargedLegionBossWar gcMsg = new GCChargedLegionBossWar();
		gcMsg.setChargedstrikeRate(newChargedstrikeRate * 100
				/ bossTemplate.getMaxChargedRate());
		gcMsg.setIsFull(bossRoleInfo.getChargedstrikeRate() >= bossTemplate
				.getMaxChargedRate());
		human.sendMessage(gcMsg);
		human.getHumanCdManager().snapCdQueueInfo(CdType.LEGION_BOSS_CHARGED);
	}

}
