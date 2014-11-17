package com.hifun.soul.core.msg.injection;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试依赖注入(Dependency Injection)的处理器;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGDIMessageHandler implements IMessageHandlerWithType<CGDIMessage> {

	@Override
	public short getMessageType() {
		return NewMessageType.CG_DI_MESSAGE.getMessageNumber();
	}

	@Override
	public void execute(CGDIMessage message) {
		LoggerFactory.getLogger("CGDIMessageHandler").info("Handle CGDIMessage!");
	}

}
