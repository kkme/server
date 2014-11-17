package com.hifun.soul.core.schedule;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifun.soul.core.msg.sys.ScheduledMessage;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.time.TimeService;

/**
 * 调度各种消息，这些消息由系统各个部分指定时间戳发送到这里 
 * 可以被取消 如果在触发前没有被取消，则统一发送到MessageProcessor来处理
 * 
 * 
 */
public class ScheduleServiceImpl implements IScheduleService {
	private static final Logger logger = LoggerFactory.getLogger("schedule");
	private final ScheduledExecutorService scheduler;
	private final IMessageProcessor messageProcessor;
	private final TimeService timeService;
	/** 是否已经停止 */
	private volatile boolean shutdown = false;

	public ScheduleServiceImpl(IMessageProcessor messageProcessor,
			TimeService timeService) {
		scheduler = Executors.newScheduledThreadPool(1);
		this.messageProcessor = messageProcessor;
		this.timeService = timeService;
	}

	public void shutdown() {
		this.shutdown = true;
		this.scheduler.shutdownNow();
	}


	@Override
	public void scheduleOnce(ScheduledMessage msg, long delay) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduleOnce delay:" + delay + " msg:" + msg);
		}
		msg.setState(ScheduledMessage.MESSAGE_STATE_WAITING);
		MessageCarrier carrier = new MessageCarrier(msg);
		ScheduledFuture<?> _scheduledFuture = scheduler.schedule(carrier,
				delay, TimeUnit.MILLISECONDS);
		msg.setFuture(_scheduledFuture);
	}


	@Override
	public void scheduleOnce(ScheduledMessage msg, Date d) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduleOnce date:" + d + " msg:" + msg);
		}
		msg.setState(ScheduledMessage.MESSAGE_STATE_WAITING);
		MessageCarrier carrier = new MessageCarrier(msg);
		ScheduledFuture<?> _scheduledFuture = scheduler
				.schedule(carrier, (d.getTime() - System.currentTimeMillis()),
						TimeUnit.MILLISECONDS);
		msg.setFuture(_scheduledFuture);
	}

	
	@Override
	public void scheduleWithFixedDelay(ScheduledMessage msg, long delay, long period) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduleWithFixedDelay delay:" + delay + " period:"
					+ period + " msg:" + msg);
		}
		msg.setState(ScheduledMessage.MESSAGE_STATE_WAITING);
		MessageCarrier carrier = new MessageCarrier(msg);
		ScheduledFuture<?> _scheduledFuture = scheduler.scheduleAtFixedRate(
				carrier, delay, period, TimeUnit.MILLISECONDS);
		msg.setFuture(_scheduledFuture);
	}

	/**
	 * 执行消息调度任务,向{@link ScheduleServiceImpl.messageProcessor}的消息队列中增加消息
	 * 
	 * 
	 */
	private final class MessageCarrier implements Runnable {
		private final ScheduledMessage msg;

		public MessageCarrier(ScheduledMessage msg) {
			this.msg = msg;
		}

		public void run() {
			if (shutdown) {
				return;
			}
			if (msg.isCanceled()) {
				return;
			}
			msg.setState(ScheduledMessage.MESSAGE_STATE_INQUEUE);
			msg.setTrigerTimestamp(timeService.now());
			messageProcessor.put(msg);
		}
	}
}
