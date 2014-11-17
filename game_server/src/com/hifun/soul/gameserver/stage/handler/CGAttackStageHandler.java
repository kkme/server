package com.hifun.soul.gameserver.stage.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.callback.BattleWithMapMonsterCallback;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.MonsterFactory;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.msg.CGAttackStage;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.stage.template.StageTemplate;

@Component
public class CGAttackStageHandler implements
		IMessageHandlerWithType<CGAttackStage> {
	private Logger logger = Loggers.STAGE_LOGGER;
	@Autowired
	private IBattleManager battleManager;
	@Autowired
	private MonsterFactory monsterFactory;
	@Autowired
	private StageTemplateManager stageTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_ATTACK_STAGE;
	}

	@Override
	public void execute(CGAttackStage message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 判断是否可以攻打关卡
		HumanStageManager humanStageManager = human.getHumanStageManager();
		if (!humanStageManager.canAttackStage(message.getStageId())) {
			return;
		}
		// 关卡模版校验
		StageTemplate stageTemplate = stageTemplateManager.getStageTemplate(message.getStageId());
		if (stageTemplate == null) {
			logger.error(String.format(
					"StageTemplate can not find! humanGuid=%s; stageId=%s",
					human.getHumanGuid(), message.getStageId()));
		}
		// 判断等级是否足够
		if (stageTemplate.getMinLevel() > human.getLevel()) {
			human.sendGenericMessage(LangConstants.LEVEl_NOT_ENOUGH, stageTemplate.getMinLevel());
			return;
		}
		// 清空上次战斗信息
		humanStageManager.clearBattleInfo();
		// 设置当前战斗关卡
		humanStageManager.setStageId(message.getStageId());
		// 生成关卡怪物
		int monsterId = stageTemplate.getMonsterId();
		Monster monster = monsterFactory.createMonster(monsterId);
		if (monster == null) {
			return;
		}
		// 设置战斗背景
		human.setBattleBgId(stageTemplate.getMapId());
		battleManager.startBattleWithMapMonster(human, monster,
				new BattleWithMapMonsterCallback(human, monster));
	}

}
