package com.hifun.soul.logserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.ErrorsUtil;
import com.hifun.soul.logserver.handler.OnlineTimeLogHandler;
import com.hifun.soul.logserver.model.OnlineTimeLog;

/**
 * 日志消息处理器
 * 
 */
public class LogMessageHandler extends IoHandlerAdapter {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(LogMessageHandler.class);
	/** 各类型日志消息的分类型处理配置，如需要更新的日志消息分配到对应的{@link IoHandlerAdapter}处理 */
	@SuppressWarnings("rawtypes")
	private final Map<Class, IMessageHandlerWithType> subLogMsgHandler = new HashMap<Class, IMessageHandlerWithType>();
    /** 主消息处理器,用于处理只插入的日志 */
	private final IoHandlerAdapter mainHandler = new SampleMessageHandler();
	
	public LogMessageHandler() {
		subLogMsgHandler.put(OnlineTimeLog.class, new OnlineTimeLogHandler());
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (logger.isErrorEnabled()) {
			logger.error(ErrorsUtil.error("LOGSERVER_EXCEPTION", "#GS.SampleMessageHandler.exceptionCaught", cause
					.toString()), cause);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void messageReceived(IoSession session, Object obj) throws Exception {
		if (obj instanceof BaseLogMessage) {
			final Class _msgClass = obj.getClass();
			IMessageHandlerWithType<BaseLogMessage> handler = getSubLogMsgHandlerAdapter(_msgClass);
			if (handler != null) {
				handler.execute((BaseLogMessage)obj);
			} else {
				//没有注册,由全局共享的处理器处理
				this.mainHandler.messageReceived(session, obj);
			}
		}
	}

	/**
	 * 根据日志消息的类型取得对应的处理器的{@link IoHandlerAdapter}
	 * 
	 * @param msgClass
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private IMessageHandlerWithType<BaseLogMessage> getSubLogMsgHandlerAdapter(final Class msgClass) {
		for (Map.Entry<Class, IMessageHandlerWithType> _entry : this.subLogMsgHandler.entrySet()) {
			Class _type = _entry.getKey();
			if (_type.isAssignableFrom(msgClass)) {
				return _entry.getValue();
			}
		}
		return null;
	}
}

