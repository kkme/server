package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.arena.msg.CGAttackArenaMember;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class ArenaTask extends LoopExecuteTask {
	
	public ArenaTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		// 打开竞技场面板
//		CGOpenArenaPanel cgOpenArenaPanel = new CGOpenArenaPanel();
//		getRobot().sendMessage(cgOpenArenaPanel);
		
		// 添加战斗次数
//		CGAddArenaBattleTime cgAddArenaBattleTime = new CGAddArenaBattleTime();
//		getRobot().sendMessage(cgAddArenaBattleTime);
		
		// 领取排行榜奖励
//		CGGetArenaRankReward cgGetArenaRankReward = new CGGetArenaRankReward();
//		getRobot().sendMessage(cgGetArenaRankReward);
		
//		CGShowArenaRankList cgShowArenaRankList = new CGShowArenaRankList();
//		getRobot().sendMessage(cgShowArenaRankList);
		
//		CGViewArenaRankRewardInfo cgViewArenaRankRewardInfo = new CGViewArenaRankRewardInfo();
//		getRobot().sendMessage(cgViewArenaRankRewardInfo);
		
		CGAttackArenaMember cgAttackArenaMember = new CGAttackArenaMember();
		cgAttackArenaMember.setHumanGuid(288797724151645161L);
		getRobot().sendMessage(cgAttackArenaMember);
		
	}

	@Override
	public void onResponse(IMessage message) {
	}

}
