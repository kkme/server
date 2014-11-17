package com.hifun.soul.gamedb.msg.handler;

import org.slf4j.Logger;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.server.IMessageHandler;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.gamedb.agent.IDBAgent;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.gamedb.msg.CacheToDBSynchronizedSchedule;
import com.hifun.soul.gamedb.msg.DBCallbackMessage;
import com.hifun.soul.gamedb.msg.IDBMessage;

/**
 * 数据库相关的消息处理器
 * 
 * @author crazyjohn
 * 
 */
public class DBMessageHandler implements IMessageHandler<IMessage> {
	private static Logger logger = Loggers.DB_MAIN_LOGGER;
	/** 数据实体代理入口 */
	IDBAgent dbAgent;
	/** 主消息处理器 */
	IMessageProcessor mainProcessor;

	public void execute(IMessage msg) {
		// 缓存同步调度消息直接执行
		if (msg instanceof CacheToDBSynchronizedSchedule) {
			msg.execute();
			return;
		}
		if (!(msg instanceof IDBMessage)) {
			logger.error("Can't process this kind of message: " + msg);
			return;
		}
		IDBMessage dbMsg = (IDBMessage) msg;
		DBCallbackMessage<?> callbackMessage = dbMsg.execute(dbAgent);
		if (!callbackMessage.needToCallback()) {
			return;
		}
		if (this.mainProcessor == null) {
			return;
		}
		this.mainProcessor.put(callbackMessage);
	}

	/**
	 * 设置主线程的消息处理器;<br>
	 * 数据操作在IO线程执行完毕以后,返回的结果包成回调消息,回调消息会投递到主线程中由主线程执行;
	 * 
	 * @param mainProcessor
	 */
	public void setMainMessageProcessor(IMessageProcessor mainProcessor) {
		this.mainProcessor = mainProcessor;
	}

	public void setDBAgent(IDBAgent dbAgent) {
		this.dbAgent = dbAgent;
	}

	@Override
	public short[] getTypes() {
		throw new UnsupportedOperationException();
	}

}
