package com.hifun.soul.gameserver.elitestage.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.elitestage.EliteStageTemplateManager;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.elitestage.callback.BattleWithEliteMonsterCallback;
import com.hifun.soul.gameserver.elitestage.msg.CGAttackEliteStageBattle;
import com.hifun.soul.gameserver.elitestage.msg.GCAttackEliteStageWarning;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.MonsterFactory;

/**
 * 请求进入战斗
 * 
 * @author magicstone
 *
 */
@Component
public class CGAttackEliteStageBattleHandler implements IMessageHandlerWithType<CGAttackEliteStageBattle> {
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ATTACK_ELITE_STAGE_BATTLE;
	}

	@Override
	public void execute(CGAttackEliteStageBattle message) {
		EliteStageTemplateManager templateManager = GameServerAssist.getEliteStageTemplateManager();
		GameFuncService gameFuncService = GameServerAssist.getGameFuncService();
		IBattleManager battleManager = GameServerAssist.getBattleManager();
		MonsterFactory monsterFactory = GameServerAssist.getMonsterFactory();
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ELITE, true)){
			return;
		}
		if(human.getEnergy()<SharedConstants.ELITE_ENERGY_NUM){
			human.sendErrorMessage(LangConstants.ENERGY_NOT_ENOUGH);
			return;
		}
		// 判断该副本类型是否开放
		int stageId = message.getStageId();
		int typeId = templateManager.getEliteStageTemplate(stageId).getType();
		if (human.getLevel() < templateManager.getEliteStageTypeTempalte(typeId).getOpenLevel()) {
			return;
		}
		if(message.getIgnoreWarning()==false && human.getBagManager().isFull(BagType.MAIN_BAG)){
			String warningInfo = GameServerAssist.getSysLangService().read(LangConstants.ELITE_BAG_IS_FULL_WARNING);
			GCAttackEliteStageWarning gcWarningMsg = new GCAttackEliteStageWarning();
			gcWarningMsg.setStageId(stageId);
			gcWarningMsg.setWarningInfo(warningInfo);
			human.sendMessage(gcWarningMsg);
			return;
		}
		HumanEliteStageManager humanStageManager = human.getHumanEliteStageManager();
		if (!humanStageManager.canAttackStage(stageId)) {
			return;
		}
		// 关卡怪物
		int monsterId = templateManager.getEliteStageTemplate(stageId).getMonsterId();
		Monster monster = monsterFactory.createMonster(monsterId);
		if (monster == null) {
			return;
		}
		human.setEnergy(human.getEnergy()-SharedConstants.ELITE_ENERGY_NUM,EnergyLogReason.BATTLE_WITH_ELITE_USE_ENERGY,"stageId:"+stageId);
		battleManager.startBattleWithMapMonster(human, monster,
				new BattleWithEliteMonsterCallback(human, monster,stageId));
	}

}
