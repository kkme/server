package com.hifun.soul.gameserver.mine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.callback.BattleWhenOpenMineCallback;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.mine.msg.CGOpenMineFieldBattleResponse;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.MonsterFactory;

/**
 * 客户端开启矿坑时对战斗请求的响应处理
 * @author magicstone
 *
 */
@Component
public class CGOpenMineFieldBattleResponseHandler implements IMessageHandlerWithType<CGOpenMineFieldBattleResponse> {	
	@Autowired
	private MonsterFactory monsterFactory;
	@Autowired
	private IBattleManager battleManager;
	@Override
	public short getMessageType() {		
		return MessageType.CG_OPEN_MINE_FIELD_BATTLE_RESPONSE;
	}

	@Override
	public void execute(CGOpenMineFieldBattleResponse message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human==null){
			return;
		}
		HumanMineManager mineManager = human.getHumanMineManager();
		if(!message.getChallenge()){
			mineManager.onEncounterBattleEnd(false);			
			return;
		}
		//开始战斗		
		Monster monster = monsterFactory.createMonster(mineManager.getMonsterId());
		if (monster == null) {
			return;
		}
		
		// 矿场战斗遇怪不消除体力
//		human.setEnergy(human.getEnergy()-SharedConstants.STAGE_ENERGY_NUM);
		
		battleManager.startBattleWithMapMonster(human, monster,
				new BattleWhenOpenMineCallback(human, monster));
	}

}
