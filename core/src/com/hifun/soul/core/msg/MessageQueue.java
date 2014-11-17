package com.hifun.soul.core.msg;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hifun.soul.common.constants.Loggers;

/**
 * 消息队列，使用{@link BlockingQueue}实现
 * 
 * @see BlockingQueue
 */
public class MessageQueue {
	/** 玩家的消息队列 */
	private BlockingQueue<IMessage> msgQueue;

	public MessageQueue() {
		msgQueue = new LinkedBlockingQueue<IMessage>();
	}

	/**
	 * @param msg
	 */
	public void put(IMessage msg) {
		if (msg == null) {
			return;
		}
		try {
			msgQueue.put(msg);
		} catch (InterruptedException e) {
			Loggers.MSG_LOGGER.error("",e);
		}
	}

	/**
	 * 返回消息队列中的一个消息
	 * 
	 * @return
	 */
	public IMessage get() {
		if (isEmpty()) {
			return null;
		}
		try {
			return msgQueue.take();
		} catch (InterruptedException e) {
			Loggers.MSG_LOGGER.error("",e);
			return null;
		}
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		return msgQueue.isEmpty();
	}

	@Override
	public String toString() {
		return "MessageQueue [msgQueue=" + msgQueue + "]";
	}

	public IMessage poll() {
		return this.msgQueue.poll();
	}
}
