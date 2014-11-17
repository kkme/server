package com.hifun.soul.gameserver.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.callback.BossWarCallback;
import com.hifun.soul.gameserver.battle.manager.BattleManager;
import com.hifun.soul.gameserver.boss.BossInfo;
import com.hifun.soul.gameserver.boss.BossRoleInfo;
import com.hifun.soul.gameserver.boss.BossState;
import com.hifun.soul.gameserver.boss.msg.CGAttackBoss;
import com.hifun.soul.gameserver.boss.service.BossWarService;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGAttackBossHandler implements 
		IMessageHandlerWithType<CGAttackBoss> {

	@Autowired
	private BossWarService bossWarService;
	@Autowired
	private BattleManager battleManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ATTACK_BOSS;
	}

	@Override
	public void execute(CGAttackBoss message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.BOSS_WAR, true)){
			return;
		}
		// 判断当前是否正处在boss战的时间段
		if(!bossWarService.bossWarIsOpen()){
			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_OPEN);
			return;
		}
		//已开启，等待CD未冷却
		if(!bossWarService.bossWarReadyForFight()){			
			return;
		}
		// 判断boss是否已经被击败
		BossInfo bossInfo = bossWarService.getBossInfo();
		if(bossInfo.getRemainBlood() <=0
				|| bossInfo.getBossState() != BossState.LIVE.getIndex()){
			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_OPEN);
			return;
		}
		// boss战玩家数据
		BossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human.getHumanGuid());
		if(bossRoleInfo == null){
			return;
		}
		// 判断boss战cd是否冷却
		HumanCdManager cdManager = human.getHumanCdManager();
		long cd = cdManager.getSpendTime(CdType.BOSS_ATTACK, 0);
		if(!cdManager.canAddCd(CdType.BOSS_ATTACK, cd)){
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 攻打boss
		Monster monster = bossWarService.getMonster();
		int hp = bossInfo.getRemainBlood();
		battleManager.startBattleWithBossWarMonster(human, monster, new BossWarCallback(human, hp,bossRoleInfo.getEncourageRate()));
		// 添加boss战cd
		cdManager.addCd(CdType.BOSS_ATTACK, cd);
		cdManager.snapCdQueueInfo(CdType.BOSS_ATTACK);
	}

}
