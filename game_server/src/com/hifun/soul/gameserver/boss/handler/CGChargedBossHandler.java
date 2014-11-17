package com.hifun.soul.gameserver.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.boss.BossInfo;
import com.hifun.soul.gameserver.boss.BossRoleInfo;
import com.hifun.soul.gameserver.boss.BossState;
import com.hifun.soul.gameserver.boss.msg.CGChargedBoss;
import com.hifun.soul.gameserver.boss.msg.GCChargedBoss;
import com.hifun.soul.gameserver.boss.service.BossWarService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGChargedBossHandler implements
		IMessageHandlerWithType<CGChargedBoss> {
	@Autowired
	private BossWarService bossWarService;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_CHARGED_BOSS;
	}

	@Override
	public void execute(CGChargedBoss message) {
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
			human.sendErrorMessage(LangConstants.LEGION_BOSS_JUST_OPEN);
			return;
		}
		// 判断boss是否已经被击败
		BossInfo bossInfo = bossWarService.getBossInfo();
		if(bossInfo.getRemainBlood() <=0
				|| bossInfo.getBossState() != BossState.LIVE.getIndex()){
			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_OPEN);
			return;
		}
		// 判断玩家是否有充能点
		BossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human.getHumanGuid());
		if(bossRoleInfo == null){
			return;
		}
		if(bossRoleInfo.getChargedstrikeRate() <= 0){
			human.sendErrorMessage(LangConstants.NO_ENOUGH_CHARGED);
			return;
		}
		// 计算充能点应该对boss造成的伤害
		int attack = BattleCalculator.calculateSkillFinalDamageForMockBattle(true,human,bossWarService.getMonster(),0,0).getFinalDamage();
		int damage = bossWarService.getChargestrikeDamage(attack, bossRoleInfo.getChargedstrikeRate());
		// 计算上鼓舞的加成
		if (bossRoleInfo.getEncourageRate() > 0) {
			damage = (int) (damage * (1 + bossRoleInfo.getEncourageRate()/SharedConstants.DEFAULT_FLOAT_BASE)) ;
		}
		bossRoleInfo.setChargedstrikeRate(0);
		bossWarService.updateBossRoleInfoToDB(bossRoleInfo);
		// 如果计算出来的伤害值为0则做小值处理
		if(damage <= 0){
			damage = 1;
		}
		// 通知客户端造成的伤害值
		GCChargedBoss gcMsg = new GCChargedBoss();
		gcMsg.setDamage(damage);
		human.sendMessage(gcMsg);
		// 对boss造成伤害的逻辑统一处理
		bossWarService.damageBoss(human, damage, true);

	}

}
