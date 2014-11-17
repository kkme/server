package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.foster.msg.CGFoster;
import com.hifun.soul.gameserver.foster.msg.CGSaveFoster;
import com.hifun.soul.gameserver.foster.msg.CGShowFosterPanel;
import com.hifun.soul.gameserver.foster.msg.GCFoster;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

/**
 * 试炼塔测试
 */
public class ForsterTask extends LoopExecuteTask {
	
	public ForsterTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		if(MathUtils.shake(0.5f)){
			CGShowFosterPanel cgMsg = new CGShowFosterPanel();
			getRobot().sendMessage(cgMsg);
		}
		else{
			CGFoster cgMsg = new CGFoster();
			cgMsg.setModeId(MathUtils.random(1, 6));
			getRobot().sendMessage(cgMsg);
		}
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCFoster){
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			// 保存
			CGSaveFoster cgMsg = new CGSaveFoster();
			getRobot().sendMessage(cgMsg);
		}
	}

}
