package com.hifun.soul.gameserver.legionboss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.callback.LegionBossWarCallback;
import com.hifun.soul.gameserver.battle.manager.BattleManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionboss.LegionBossInfo;
import com.hifun.soul.gameserver.legionboss.LegionBossRoleInfo;
import com.hifun.soul.gameserver.legionboss.LegionBossState;
import com.hifun.soul.gameserver.legionboss.msg.CGAttackLegionBoss;
import com.hifun.soul.gameserver.legionboss.service.LegionBossService;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGAttackLegionBossHandler implements
		IMessageHandlerWithType<CGAttackLegionBoss> {

	@Autowired
	private LegionBossService bossWarService;
	@Autowired
	private BattleManager battleManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ATTACK_LEGION_BOSS;
	}

	@Override
	public void execute(CGAttackLegionBoss message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.LEGION_BOSS_WAR, true)) {
			return;
		}
		// 判断当前是否正处在boss战的时间段
		if (!bossWarService.bossWarIsOpen()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_NOT_OPEN);
			return;
		}
		// 已开启，等待CD未冷却
		if (!bossWarService.bossWarReadyForFight()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_JUST_OPEN);
			return;
		}
		// 判断boss是否已经被击败
		LegionBossInfo bossInfo = bossWarService.getBossInfo();
		if (bossInfo.getRemainBlood() <= 0
				|| bossInfo.getBossState() != LegionBossState.LIVE.getIndex()) {
			human.sendErrorMessage(LangConstants.LEGION_BOSS_NOT_OPEN);
			return;
		}
		// boss战玩家数据
		LegionBossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human
				.getHumanGuid());
		if (bossRoleInfo == null) {
			return;
		}
		// 判断boss战cd是否冷却
		HumanCdManager cdManager = human.getHumanCdManager();
		long cd = cdManager.getSpendTime(CdType.LEGION_BOSS_ATTACK, 0);
		if (!cdManager.canAddCd(CdType.LEGION_BOSS_ATTACK, cd)) {
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 攻打boss
		Monster monster = bossWarService.getMonster();
		int hp = bossInfo.getRemainBlood();
		battleManager
				.startBattleWithBossWarMonster(
						human,
						monster,
						new LegionBossWarCallback(human, hp, bossRoleInfo
								.getEncourageRate()));
		// 添加boss战cd
		cdManager.addCd(CdType.LEGION_BOSS_ATTACK, cd);
		cdManager.snapCdQueueInfo(CdType.LEGION_BOSS_ATTACK);
	}
}
