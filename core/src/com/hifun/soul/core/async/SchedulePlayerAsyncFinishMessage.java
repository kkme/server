package com.hifun.soul.core.async;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.PlayerQueueMessage;
import com.hifun.soul.core.msg.sys.ScheduledMessage;

/**
 * 调度执行的异步消息通知,主要用于当异步消息处理完毕后，通知由玩家所在的场景线程进行收尾处理
 * 
 * 
 */
public class SchedulePlayerAsyncFinishMessage extends ScheduledMessage
		implements PlayerQueueMessage {

	private final AsyncOperation operation;
	private final long uuid;

	public SchedulePlayerAsyncFinishMessage(long createTime,
			AsyncOperation operation, long uuid) {
		super(createTime);
		this.operation = operation;
		this.uuid = uuid;
	}

	public AsyncOperation getOperation() {
		return operation;
	}

	@Override
	public short getType() {
		return MessageType.SCHD_PLAYER_ASYNC_FINISH;
	}

	@Override
	public String getTypeName() {
		String operationName = this.operation != null ? this.operation
				.toString() : "null";
		return "SCHD_PLAYER_ASYNC_FINISH [" + operationName + "]";
	}

	@Override
	public void execute() {
		operation.execute();
	}

	@Override
	public long getRoleUUID() {
		return this.uuid;
	}

}
