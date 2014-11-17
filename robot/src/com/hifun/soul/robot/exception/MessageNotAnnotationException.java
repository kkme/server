package com.hifun.soul.robot.exception;

import com.hifun.soul.core.util.MessageTypeUtil;

@SuppressWarnings("serial")
public class MessageNotAnnotationException extends RuntimeException {
	public MessageNotAnnotationException(short messageType) {
		super(
				String.format(
						"You did not mark a @Component annotation to this message: %s, if you want create, do it!",
						MessageTypeUtil.getMessageTypeName(messageType)));
	}
}
