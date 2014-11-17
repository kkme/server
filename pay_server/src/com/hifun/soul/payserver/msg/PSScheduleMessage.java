package com.hifun.soul.payserver.msg;

import com.hifun.soul.core.msg.ICancelableMessage;
import com.hifun.soul.core.msg.SysInternalMessage;

/**
 * 支付調度消息;
 * 
 * @author crazyjohn
 * 
 */
public abstract class PSScheduleMessage extends SysInternalMessage implements
		ICancelableMessage {
	/** 调度是否已经取消了 */
	private volatile boolean isCanceled;

	@Override
	public void cancel() {
		this.isCanceled = true;
	}

	@Override
	public boolean isCanceled() {
		return isCanceled;
	}

}
