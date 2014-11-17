package com.hifun.soul.core.msg;

/**
 * 场景调度消息;
 * <p>
 * 达到指定条件后会由调度器投递到指定的场景线程中;
 * 
 * @author crazyjohn
 * 
 */
public abstract class SceneScheduleMessage extends SysInternalMessage implements
		ICancelableMessage {
	/** 调度是否已经取消了 */
	private volatile boolean isCanceled;

	@Override
	public void cancel() {
		this.isCanceled = true;
	}

	@Override
	public boolean isCanceled() {
		return this.isCanceled;
	}
}
