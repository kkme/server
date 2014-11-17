package com.hifun.soul.core.server;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.SysInternalMessage;

/**
 * 计时管理器.所有与系统定时任务和心跳相关的业务,都通过此管理器进行调度.
 * 
 * @author crazyjohn
 * 
 */
public class TimerManager {

	protected static final Logger logger = Loggers.TIMMER_LOGGER;

	private Timer timer;
	/** 对应的消息处理器 */
	private IMessageProcessor msgProcessor;

	/**
	 * timer对应的线程名字;
	 * 
	 * @param threadName
	 */
	public TimerManager(String threadName) {
		timer = new Timer(threadName);
	}

	/**
	 * 增加心跳任务
	 * 
	 * @param obj
	 */
	public void addHeartbeatObject(final IHeartBeatable obj, long interval) {
		scheduleAtFixedRate(new SysInternalMessage() {

			@Override
			public void execute() {
				obj.heartBeat();
			}

		}, interval, interval);
	}

	/**
	 * 启动计时管理器及所有心跳任务
	 * 
	 * @param msgProcessor
	 */
	public void start(IMessageProcessor msgProcessor) {
		this.msgProcessor = msgProcessor;
	}

	/**
	 * 停止计时管理器及所有心跳任务
	 */
	public void stop() {
		msgProcessor = null;
		timer.cancel();
	}

	/**
	 * {@link Timer#schedule(TimerTask, long, long)}
	 */
	public void scheduleAtFixedDelay(SysInternalMessage task, long delay,
			long period) {
		timer.schedule(new MessageQueueTask(task), delay, period);
	}

	/**
	 * {@link Timer#scheduleAtFixedRate(TimerTask, long, long)}
	 */
	public void scheduleAtFixedRate(SysInternalMessage task, long delay,
			long period) {
		timer.scheduleAtFixedRate(new MessageQueueTask(task), delay, period);
	}

	/**
	 * {@link Timer#schedule(TimerTask, long)}
	 * 
	 * @param task
	 * @param delay
	 */
	public void scheduleOnece(SysInternalMessage task, long delay) {
		timer.schedule(new MessageQueueTask(task), delay);
	}

	/**
	 * 通过定时将{@link SysInternalMessage}放入消息队列实现的定时任务
	 * 
	 * @author crazyjohn
	 * 
	 */
	class MessageQueueTask extends TimerTask {
		private SysInternalMessage msg;

		public MessageQueueTask(SysInternalMessage msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			if (msgProcessor == null) {
				logger.warn("TimerManager is not started with a message processor, abort task msg: "
						+ msg.toString());
				return;
			}
			msgProcessor.put(msg);
		}
	}
}
