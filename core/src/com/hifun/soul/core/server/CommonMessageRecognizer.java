package com.hifun.soul.core.server;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageParseException;
import com.hifun.soul.core.msg.recognizer.BaseMessageRecognizer;
import com.hifun.soul.core.util.MessageTypeUtil;

/**
 * 通用的消息识别器;
 * 
 * @author crazyjohn
 * 
 */
public class CommonMessageRecognizer extends BaseMessageRecognizer {
	private MessageManager messageManager = new MessageManager();

	public CommonMessageRecognizer() {
		messageManager.init();
	}

	@Override
	public IMessage createMessageImpl(short type) throws MessageParseException {
		Class<?> clazz = null;
		try {
			clazz = messageManager.getMessage(type).getClass();
		} catch (Exception e) {
			throw new MessageParseException("Unknow msgType:" + type + ", "
					+ MessageTypeUtil.getMessageTypeName(type));
		}

		if (clazz == null) {
			throw new MessageParseException("Unknow msgType:" + type + ","
					+ MessageTypeUtil.getMessageTypeName(type));
		} else {
			try {
				IMessage msg = (IMessage) clazz.newInstance();
				return msg;
			} catch (Exception e) {
				throw new MessageParseException(
						"Message Newinstance Failed，msgType:" + type, e);
			}
		}
	}

}
