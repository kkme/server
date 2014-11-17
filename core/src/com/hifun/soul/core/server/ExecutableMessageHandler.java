package com.hifun.soul.core.server;

import com.hifun.soul.common.constants.CommonErrorLogInfo;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.IMessage;

/**
 * 可自执行的消息处理器
 * 
 * 
 */
public class ExecutableMessageHandler implements IMessageHandler<IMessage> {
	private MessageHandlerManager handlers = new MessageHandlerManager();

	public ExecutableMessageHandler() {
		handlers.init();
	}

	@Override
	public void execute(IMessage message) {
		try {
			handlers.handleMessage(message);
		} catch (Exception e) {
			Loggers.MSG_LOGGER.error(CommonErrorLogInfo.MSG_PRO_ERR_EXP, e);
			// 把异常抛出去给上层, 让上层处理;
			throw new RuntimeException("Execute message error, messageType: "
					+ message.getTypeName(), e);
		}
	}

	@Override
	public short[] getTypes() {
		return null;
	}
}
