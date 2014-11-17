package com.hifun.soul.robot.task.impl;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.item.msg.CGEquipForge;
import com.hifun.soul.gameserver.item.msg.CGEquipForgeReplace;
import com.hifun.soul.gameserver.item.msg.CGEquipForgeToLock;
import com.hifun.soul.gameserver.item.msg.CGSelectEquipToForge;
import com.hifun.soul.gameserver.item.msg.GCEquipForgeToLock;
import com.hifun.soul.gameserver.item.msg.GCSelectEquipToForge;
import com.hifun.soul.gameserver.item.msg.GCUpdateEquipForgePanel;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

/**
 * 装备洗炼测试
 */
public class ForgeTask extends LoopExecuteTask {
	private boolean isGiveEquip;
	
	public ForgeTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		if(!isGiveEquip){
			// 获得装备
			CGChatMsg cgChatMsg = new CGChatMsg();
			cgChatMsg.setContent("!giveitem 110411 1");
			getRobot().sendMessage(cgChatMsg);
			isGiveEquip = true;
		}
		if(MathUtils.shake(0.2f)){
			// 加钱
			CGChatMsg cgChatMsg = new CGChatMsg();
			cgChatMsg.setContent("!givemoney 2 10 1");
			getRobot().sendMessage(cgChatMsg);
			// 获取灵石
			CGChatMsg cgChatMsg1= new CGChatMsg();
			cgChatMsg1.setContent("!giveitem 312002 10");
			getRobot().sendMessage(cgChatMsg1);
		}
		// 选中某件装备去洗炼
		CGSelectEquipToForge cgMsg = new CGSelectEquipToForge();
		cgMsg.setEquipBagType(BagType.MAIN_BAG.getIndex());
		cgMsg.setEquipBagIndex(5);
		getRobot().sendMessage(cgMsg);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCSelectEquipToForge){
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			// 发送洗练或者洗练锁定
			if(MathUtils.shake(0.8f)){
				CGEquipForgeToLock cgEquipForgeToLock = new CGEquipForgeToLock();
				cgEquipForgeToLock.setEquipBagType(BagType.MAIN_BAG.getIndex());
				cgEquipForgeToLock.setEquipBagIndex(5);
				cgEquipForgeToLock.setLockNum(MathUtils.random(0, 2));
				getRobot().sendMessage(cgEquipForgeToLock);
			}
			else{
				CGEquipForge cgEquipForge = new CGEquipForge();
				cgEquipForge.setEquipBagType(BagType.MAIN_BAG.getIndex());
				cgEquipForge.setEquipBagIndex(5);
				cgEquipForge.setLocks(new int[]{0,1});
				getRobot().sendMessage(cgEquipForge);
			}
		}
		else if(message instanceof GCEquipForgeToLock){
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			CGEquipForge cgEquipForge = new CGEquipForge();
			cgEquipForge.setEquipBagType(BagType.MAIN_BAG.getIndex());
			cgEquipForge.setEquipBagIndex(5);
			cgEquipForge.setLocks(new int[]{1,2});
			getRobot().sendMessage(cgEquipForge);
		}
		else if(message instanceof GCUpdateEquipForgePanel){
			// 延迟一定时间执行
			try{
				Thread.sleep(MathUtils.random(0, 1000));
			}
			catch (Exception e) {
				System.out.println(e);
			}
			CGEquipForgeReplace cgEquipForgeReplace = new CGEquipForgeReplace();
			cgEquipForgeReplace.setEquipBagType(BagType.MAIN_BAG.getIndex());
			cgEquipForgeReplace.setEquipBagIndex(5);
			cgEquipForgeReplace.setLocks(new int[]{1,2});
			getRobot().sendMessage(cgEquipForgeReplace);
		}
	}

}
