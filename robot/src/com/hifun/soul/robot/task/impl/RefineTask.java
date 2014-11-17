package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.refine.msg.CGAttackAllRefineStage;
import com.hifun.soul.gameserver.refine.msg.CGAttackRefineStage;
import com.hifun.soul.gameserver.refine.msg.CGAutoAttackRefineStage;
import com.hifun.soul.gameserver.refine.msg.CGOpenRefinePanel;
import com.hifun.soul.gameserver.refine.msg.CGRefreshRefineMap;
import com.hifun.soul.gameserver.refine.msg.GCAttackRefineStage;
import com.hifun.soul.gameserver.refine.msg.GCOpenRefinePanel;
import com.hifun.soul.gameserver.refine.msg.GCUpdateRefinePanel;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

/**
 * 试炼塔测试
 */
public class RefineTask extends LoopExecuteTask {
	private boolean isGiveExperience;
	private boolean isGiveMoney;
	
	public RefineTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		if(!isGiveExperience){
			// 获得装备
			CGChatMsg cgChatMsg = new CGChatMsg();
			cgChatMsg.setContent("!giveexperience 1000000");
			getRobot().sendMessage(cgChatMsg);
			isGiveExperience = true;
		}
		if(!isGiveMoney){
			// 加钱
			CGChatMsg cgChatMsg = new CGChatMsg();
			cgChatMsg.setContent("!givemoney 2 100 100");
			getRobot().sendMessage(cgChatMsg);
			isGiveMoney = true;
		}
		// 打开试炼面板
		CGOpenRefinePanel cgOpenRefinePanel = new CGOpenRefinePanel();
		cgOpenRefinePanel.setRefineType(-1);
		getRobot().sendMessage(cgOpenRefinePanel);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCOpenRefinePanel){
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			// 发送攻打、自动攻打或者全部攻打
			if(MathUtils.shake(0.01f)){
				CGAttackRefineStage cgAttackRefineStage = new CGAttackRefineStage();
				cgAttackRefineStage.setRefineType(MathUtils.random(1, 3));
				getRobot().sendMessage(cgAttackRefineStage);
			}
			else if(MathUtils.shake(0.9f)){
				CGAutoAttackRefineStage cgAutoAttackRefineStage = new CGAutoAttackRefineStage();
				cgAutoAttackRefineStage.setRefineType(MathUtils.random(1, 3));
				getRobot().sendMessage(cgAutoAttackRefineStage);
			}
			else if(MathUtils.shake(0.01f)){
				CGAttackAllRefineStage cgAttackAllRefineStage = new CGAttackAllRefineStage();
				cgAttackAllRefineStage.setRefineType(MathUtils.random(1, 3));
				getRobot().sendMessage(cgAttackAllRefineStage);
			}
			else if(MathUtils.shake(0.01f)){
				// 刷新
				CGRefreshRefineMap cgRefreshRefineMap = new CGRefreshRefineMap();
				cgRefreshRefineMap.setRefineType(MathUtils.random(1, 3));
				getRobot().sendMessage(cgRefreshRefineMap);
			}
		}
		else if(message instanceof GCAttackRefineStage){
		}
		else if(message instanceof GCUpdateRefinePanel){
		}
	}

}
