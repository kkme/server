package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.boss.msg.CGAttackBoss;
import com.hifun.soul.gameserver.boss.msg.CGChargedBoss;
import com.hifun.soul.gameserver.boss.msg.CGChargedBossWar;
import com.hifun.soul.gameserver.boss.msg.CGEncourageBossWar;
import com.hifun.soul.gameserver.boss.msg.CGGetBossReward;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class BossWarTask extends LoopExecuteTask {

	public BossWarTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		CGChatMsg msg = new CGChatMsg();
		msg.setContent("!openBossWarPanel");
		getRobot().sendMessage(msg);
		
		CGChatMsg msg1 = new CGChatMsg();
		msg.setContent("!startBossWar");
		getRobot().sendMessage(msg1);
		
		CGChatMsg msg2 = new CGChatMsg();
		msg.setContent("!stopBossWar");
		getRobot().sendMessage(msg2);
		
		// 给boss战充能
		CGChargedBossWar cgChargeBossWar = new CGChargedBossWar();
		getRobot().sendMessage(cgChargeBossWar);
		
		// 鼓舞
		CGEncourageBossWar cgEncourageBossWar = new CGEncourageBossWar();
		cgEncourageBossWar.setEncourageType(CurrencyType.CRYSTAL.getIndex());
		getRobot().sendMessage(cgEncourageBossWar);
		
		// 攻击boss
		CGAttackBoss cgAttackBoss = new CGAttackBoss();
		getRobot().sendMessage(cgAttackBoss);
		
		// 用充能打击boss
		CGChargedBoss cgChargeBoss = new CGChargedBoss();
		getRobot().sendMessage(cgChargeBoss);
		
		// 领取奖励
		CGGetBossReward cgGetBossReward = new CGGetBossReward();
		getRobot().sendMessage(cgGetBossReward);
		
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
