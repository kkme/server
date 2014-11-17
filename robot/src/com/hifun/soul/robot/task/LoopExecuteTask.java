package com.hifun.soul.robot.task;

import com.hifun.soul.robot.Robot;

/**
 * 循环执行策略
 *
 */
public abstract class LoopExecuteTask extends BaseRobotTask {

	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 */
	public LoopExecuteTask(Robot robot) {
		super(robot);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 * @param delay 第一次执行的延迟时间
	 * @param period 循环执行时的时间间隔
	 */
	public LoopExecuteTask(Robot robot, int delay, int interval) {
		super(robot,delay,interval);
	}

	@Override
	public final boolean isRepeatable() {
		return true;
	}

}
