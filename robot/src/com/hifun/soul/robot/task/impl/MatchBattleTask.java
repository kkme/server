package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.battle.msg.CGRequestBattleGiveUp;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.matchbattle.msg.CGCancelReadyForMatchBattle;
import com.hifun.soul.gameserver.matchbattle.msg.CGEncourageMatchBattle;
import com.hifun.soul.gameserver.matchbattle.msg.CGJoinMatchBattleScene;
import com.hifun.soul.gameserver.matchbattle.msg.CGLeaveMatchBattleScene;
import com.hifun.soul.gameserver.matchbattle.msg.CGReadyForMatchBattle;
import com.hifun.soul.gameserver.matchbattle.msg.CGUpdateAutoMatchBattleSetting;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

/**
 * 装备洗炼测试
 */
public class MatchBattleTask extends LoopExecuteTask {
	private boolean isGive;
	
	public MatchBattleTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		if(!isGive){
			isGive = true;
			CGChatMsg cgChatMsg = new CGChatMsg();
			cgChatMsg.setContent("!giveexperience 100000");
			getRobot().sendMessage(cgChatMsg);
		}
		CGRequestBattleGiveUp cgRequestBattleGiveUp = new CGRequestBattleGiveUp();
		getRobot().sendMessage(cgRequestBattleGiveUp);
		
		CGJoinMatchBattleScene cgJoinMatchBattleScene = new CGJoinMatchBattleScene();
		getRobot().sendMessage(cgJoinMatchBattleScene);
		
		int i = MathUtils.random(1, 5);
		switch(i){
		case 1:
			CGEncourageMatchBattle cgEncourageMatchBattle = new CGEncourageMatchBattle();
			cgEncourageMatchBattle.setEncourageType(MathUtils.random(1, 2));
			getRobot().sendMessage(cgEncourageMatchBattle);
			break;
		case 2:
			CGReadyForMatchBattle cgReadyForMatchBattle = new CGReadyForMatchBattle();
			getRobot().sendMessage(cgReadyForMatchBattle);
			break;
		case 3:
			CGUpdateAutoMatchBattleSetting cgUpdateAutoMatchBattleSetting = new CGUpdateAutoMatchBattleSetting();
			cgUpdateAutoMatchBattleSetting.setIsAutoJoinBattle(MathUtils.shake(0.5f));
			getRobot().sendMessage(cgUpdateAutoMatchBattleSetting);
			break;
		case 4:
			CGCancelReadyForMatchBattle cgCancelReadyForMatchBattle = new CGCancelReadyForMatchBattle();
			getRobot().sendMessage(cgCancelReadyForMatchBattle);
			break;
		case 5:
			CGLeaveMatchBattleScene cgLeaveMatchBattleScene = new CGLeaveMatchBattleScene();
			getRobot().sendMessage(cgLeaveMatchBattleScene);
			break;
		}
	}

	@Override
	public void onResponse(IMessage message) {
	}

}
