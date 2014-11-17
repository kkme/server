package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.gift.msg.CGActiveGift;
import com.hifun.soul.gameserver.gift.msg.CGResetGift;
import com.hifun.soul.gameserver.gift.msg.CGShowGiftPanel;
import com.hifun.soul.gameserver.gift.msg.GCShowGiftPanel;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

/**
 * 装备洗炼测试
 */
public class GiftTask extends LoopExecuteTask {
	private boolean isGive;
	
	public GiftTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		if(!isGive){
			// 获得装备
			CGChatMsg cgChatMsg = new CGChatMsg();
			cgChatMsg.setContent("!givegiftpoint 10000");
			getRobot().sendMessage(cgChatMsg);
			isGive = true;
		}
		if(MathUtils.shake(0.5f)){
			CGShowGiftPanel cgMsg = new CGShowGiftPanel();
			getRobot().sendMessage(cgMsg);
		}
		else{
			CGActiveGift cgMsg = new CGActiveGift();
			cgMsg.setGiftId(MathUtils.random(101, 104));
			getRobot().sendMessage(cgMsg);
		}
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCShowGiftPanel){
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			// 发送洗练或者洗练锁定
			if(MathUtils.shake(0.01f)){
				CGResetGift cgMsg = new CGResetGift();
				getRobot().sendMessage(cgMsg);
			}
		}
	}

}
