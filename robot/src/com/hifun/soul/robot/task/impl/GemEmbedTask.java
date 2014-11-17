package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.item.msg.CGGemEmbed;
import com.hifun.soul.gameserver.item.msg.CGGemExtract;
import com.hifun.soul.gameserver.item.msg.CGGemPunch;
import com.hifun.soul.gameserver.item.msg.CGPutEquip;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class GemEmbedTask extends LoopExecuteTask {

	private int i=0;
	
	public GemEmbedTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		switch (i) {
		case 0:
			CGGemEmbed cgGemEmbed = new CGGemEmbed();
			cgGemEmbed.setEquipBagIndex(0);
			cgGemEmbed.setEquipBagType(BagType.MAIN_BAG.getIndex());
			cgGemEmbed.setGemBagIndex(MathUtils.randomInt(10));
			cgGemEmbed.setGemBagType(BagType.MAIN_BAG.getIndex());
			cgGemEmbed.setIndex((short)MathUtils.randomInt(3));
			getRobot().sendMessage(cgGemEmbed);
			break;
		case 1:
			CGGemExtract cgGemExtract = new CGGemExtract();
			cgGemExtract.setEquipBagIndex(0);
			cgGemExtract.setEquipBagType(BagType.MAIN_BAG.getIndex());
			cgGemExtract.setGemIndex((short)MathUtils.randomInt(3));
			getRobot().sendMessage(cgGemExtract);
			break;
		case 2:
			CGGemPunch cgGemPunch = new CGGemPunch();
			cgGemPunch.setEquipBagIndex(0);
			cgGemPunch.setEquipBagType(BagType.MAIN_BAG.getIndex());
			getRobot().sendMessage(cgGemPunch);
			break;
		case 3:
			CGPutEquip cgPutEquip = new CGPutEquip();
			cgPutEquip.setEquipBagIndex(0);
			cgPutEquip.setEquipBagType(BagType.MAIN_BAG.getIndex());
			getRobot().sendMessage(cgPutEquip);
			break;
		}
		
		i = (i++)%4;
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
