package com.hifun.soul.robot;

import java.util.concurrent.ScheduledFuture;

import com.hifun.soul.robot.task.IRobotTask;

/**
 * 机器人行为
 *
 */
public class RobotAction implements Runnable {
	/** 机器人执行策略 */
	private IRobotTask task = null;
	/** 执行结果 */
	private ScheduledFuture<?> future = null;

	/**
	 * 类参数构造器
	 * 
	 * @param strategy 机器人执行策略
	 * @throws IllegalArgumentException if task is null
	 */
	public RobotAction(IRobotTask task) {
		if (task == null) {
			throw new IllegalArgumentException("task is null");
		}

		this.task = task;
	}

	@Override
	public void run() {
		if(this.task.canDoAction())
		{
			this.task.doAction();
		}
	}

	/**
	 * 获取执行结果
	 * 
	 * @return
	 */
	public ScheduledFuture<?> getFuture() {
		return future;
	}

	/**
	 * 设置执行结果
	 * 
	 * @param future
	 */
	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}
}
