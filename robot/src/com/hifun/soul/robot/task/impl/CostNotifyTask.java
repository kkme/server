package com.hifun.soul.robot.task.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.costnotify.CostNotifyInfo;
import com.hifun.soul.gameserver.costnotify.CostNotifyType;
import com.hifun.soul.gameserver.costnotify.msg.CGUpdateCostNotify;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class CostNotifyTask extends LoopExecuteTask {

	private CostNotifyType[] costNotifyTypes = CostNotifyType.values();
	
	public CostNotifyTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		CGUpdateCostNotify cgUpdateCostNotify = new CGUpdateCostNotify();
		List<CostNotifyInfo> costNotifyInfoList = new ArrayList<CostNotifyInfo>();
		for(CostNotifyType costNotifyType : costNotifyTypes){
			CostNotifyInfo costNotifyInfo = new CostNotifyInfo();
			costNotifyInfo.setCostNotifyType(costNotifyType.getIndex());
			costNotifyInfo.setOpen(RandomUtils.nextFloat()>0.5?true:false);
			costNotifyInfoList.add(costNotifyInfo);
		}
		cgUpdateCostNotify.setCostNotifyInfos(costNotifyInfoList.toArray(new CostNotifyInfo[0]));
		
		this.sendMessage(cgUpdateCostNotify);
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
