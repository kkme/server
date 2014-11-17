package com.hifun.soul.robot.task;

import com.hifun.soul.robot.Robot;

/**
 * 单次执行策略
 * 
 */
public abstract class OnceExecuteTask extends BaseRobotTask {
	
	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 */
	public OnceExecuteTask(Robot robot) {
		super(robot);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 * @param delay 延迟时间
	 */
	public OnceExecuteTask(Robot robot, int delay) {
		super(robot,delay,0);
	}

	@Override
	public final boolean isRepeatable() {
		return false;
	}
	
}
