package com.hifun.soul.robot.task.impl;

import org.h2.util.MathUtils;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.building.BuildingType;
import com.hifun.soul.gameserver.building.function.BuildingFuncType;
import com.hifun.soul.gameserver.building.msg.CGClickBuilding;
import com.hifun.soul.gameserver.building.msg.CGClickBuildingFunc;
import com.hifun.soul.robot.Robot;
import com.hifun.soul.robot.task.LoopExecuteTask;

public class BuildingTask extends LoopExecuteTask {

	private BuildingType[] buildingTypes = BuildingType.values();
	
	private BuildingFuncType[] buildingFuncTypes = BuildingFuncType.values();
	
	public BuildingTask(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
	}

	@Override
	public void doAction() {
		// 点击建筑
		CGClickBuilding cgClickBuilding = new CGClickBuilding();
		cgClickBuilding.setBuildingType(buildingTypes[MathUtils.randomInt(buildingTypes.length)].getIndex());
		cgClickBuilding.setUUID(0L);
		getRobot().sendMessage(cgClickBuilding);
		
		// 点击建筑功能
		CGClickBuildingFunc cgClickBuildingFunc = new CGClickBuildingFunc();
		cgClickBuildingFunc.setBuildingType(buildingTypes[MathUtils.randomInt(buildingTypes.length)].getIndex());
		cgClickBuildingFunc.setFuncId(buildingFuncTypes[MathUtils.randomInt(buildingFuncTypes.length)].getIndex());
		cgClickBuildingFunc.setUUID(0L);
		getRobot().sendMessage(cgClickBuildingFunc);
		
	}

	@Override
	public void onResponse(IMessage message) {
		
	}

}
