package com.hifun.soul.gameserver.warrior.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.manager.BattleManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.warrior.WarriorOpponent;
import com.hifun.soul.gameserver.warrior.battle.WarriorPVEBattleCallback;
import com.hifun.soul.gameserver.warrior.battle.WarriorPVPBattleCallback;
import com.hifun.soul.gameserver.warrior.manager.HumanWarriorManager;
import com.hifun.soul.gameserver.warrior.msg.CGStartWarriorBattleRequest;
@Deprecated
public class CGStartWarriorBattleRequestHandler implements IMessageHandlerWithType<CGStartWarriorBattleRequest> {
	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private BattleManager battleManager;
	@Override
	public short getMessageType() {		
		return MessageType.CG_START_WARRIOR_BATTLE_REQUEST;
	}

	@Override
	public void execute(CGStartWarriorBattleRequest message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}		
		Human human = player.getHuman();
		if(human == null){
			return;
		}		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.WARRIOR_WAY, true)){
			return;
		}
		HumanWarriorManager warriorManager = human.getHumanWarriorManager();
		if(!warriorManager.canChallenge()){
			return;
		}
		WarriorOpponent opponent = warriorManager.getOpponent();
		if(opponent.getBattleUnit() instanceof Human){
			battleManager.pvpBattleEnter(human, opponent.getBattleUnit().getUnitGuid(), new WarriorPVPBattleCallback(human,opponent.getBattleWinRewardNum()));
		}else if(opponent.getBattleUnit() instanceof Monster){
			Monster npc = (Monster)opponent.getBattleUnit();
			battleManager.startBattleWithMapMonster(human, npc, new WarriorPVEBattleCallback(human,opponent.getBattleWinRewardNum()));
		}
	}

}
